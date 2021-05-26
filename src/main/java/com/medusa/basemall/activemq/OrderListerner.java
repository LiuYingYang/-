package com.medusa.basemall.activemq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.constant.ActiviMqQueueName;
import com.medusa.basemall.constant.PayFlag;
import com.medusa.basemall.constant.SendTemplatMessageType;
import com.medusa.basemall.jobhandler.ActiveDelaySendJobHandler;
import com.medusa.basemall.order.entity.Order;
import com.medusa.basemall.order.entity.OrderDetail;
import com.medusa.basemall.order.service.BuycarService;
import com.medusa.basemall.order.service.OrderDetailService;
import com.medusa.basemall.order.service.OrderService;
import com.medusa.basemall.product.entity.Product;
import com.medusa.basemall.product.service.ProductService;
import com.medusa.basemall.product.service.ProductSpecItemService;
import com.medusa.basemall.promotion.entity.ActivityProduct;
import com.medusa.basemall.promotion.entity.ActivityProductStock;
import com.medusa.basemall.promotion.service.ActivityProductService;
import com.medusa.basemall.promotion.service.ActivityProductStockService;
import com.medusa.basemall.user.service.CollectService;
import com.medusa.basemall.user.service.FootMarkService;
import com.vip.vjtools.vjkit.time.ClockUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author whh
 */
@Component
public class OrderListerner {

	@Resource
	public OrderService orderService;

	@Resource
	private ActiveMqClient activeMqClient;

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderListerner.class);

	@Resource
	private OrderDetailService orderDetailService;

	@Resource
	private ProductService productService;

	@Resource
	private ProductSpecItemService specItemService;

	@Resource
	private ActivityProductService activityProductService;
	@Resource
	private ActivityProductStockService activityProductStockService;
	@Resource
	private BuycarService buycarService;
	@Resource
	private CollectService collectService;
	@Resource
	private FootMarkService footMarkService;
	@Resource
	private ActiveDelaySendJobHandler activeDelaySendJobHandler;

	/**
	 * 订单关闭库存归还
	 *   订单id
	 */
	@JmsListener(destination = ActiviMqQueueName.ORDER_CLOSE_STOCK_RETURN)
	public void OrderCloseStockReturn(String jsonData) {
		JSONObject jsonObject = JSON.parseObject(jsonData);
		Long orderId = jsonObject.getLong("id");
		Order order = orderService.findById(orderId);
		stockReturn(orderId);
		JSONObject json = new JSONObject();
		json.put("orderId", order.getOrderId());
		json.put("messageType", SendTemplatMessageType.ORDER_CLOSE);
		activeMqClient.send(json.toJSONString(), ActiviMqQueueName.ORDER_MINIPROGRAM_TEMPLATE_MESSAGE);
		activeDelaySendJobHandler.updateState(jsonObject.getString("activeMqTaskTOId"));
	}

	private void stockReturn(Long orderId) {
		List<OrderDetail> details = orderDetailService.findByList("orderId", orderId);
		List<OrderDetail> product = new ArrayList<>();
		List<OrderDetail> activeProduct = new ArrayList<>();
		//区分活动商品和普通商品
		for (OrderDetail detail : details) {
			if (detail.getActivityInfo() == null || detail.getActivityInfo().equals("")) {
				product.add(detail);
			} else {
				JSONObject jsonObject = JSON.parseObject(detail.getActivityInfo());
				//如果只是参加优惠券活动当做普通商品库存处理
				if (jsonObject.size() == 1 && jsonObject.getJSONObject("ActivityCouponInfo") != null) {
					product.add(detail);
				}else{
					activeProduct.add(detail);
				}
			}
		}
		//处理普通商品库存
		if (product.size() > 0) {
			StringBuilder sb = new StringBuilder();
			product.forEach(obj -> sb.append(obj.getProductId() + ","));
			//商品总库存
			List<Long> productsStockAdd = new ArrayList<>();
			List<Product> products = productService.findByIds(sb.substring(0, sb.length() - 1));
			products.forEach(pro -> {
				details.forEach(detail -> {
					if (detail.getProductId().equals(pro.getProductId())) {
						pro.setStock(pro.getStock() + detail.getQuantity());
						if (pro.getStock() > 0 && pro.getShelfState().equals(2)) {
							productsStockAdd.add(pro.getProductId());
							pro.setShelfState(0);
						}
						productService.update(pro);
					}
				});
			});
			//如果商品库存从原本0变0+;购物车中的商品都需要从售罄改为正常
			if (productsStockAdd.size() > 0) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("productIds", StringUtils.join(productsStockAdd, ","));
				jsonObject.put("shelfState", 0);
				activeMqClient.send(jsonObject.toJSONString(), ActiviMqQueueName.PRODUCT_SHELFSTATE_CHANGE);
			}
			//处理多规格库存
			List<OrderDetail> spces = product.stream()
					.filter(obj -> obj.getProductSpecInfo() != null && !obj.getProductSpecInfo().equals(""))
					.collect(Collectors.toList());
			if (spces != null && spces.size() > 0) {
				Map<Long, Integer> map = new HashMap<>(1);
				for (OrderDetail spce : spces) {
					JSONObject jsonObject = JSON.parseObject(spce.getProductSpecInfo());
					map.put(jsonObject.getLong("productSpecItemId"), spce.getQuantity());
				}
				specItemService.updateSpecProductStockReturn(map);
			}

		}
		if (activeProduct.size() > 0) {
			for (OrderDetail orderDetail : activeProduct) {
				JSONObject avtivityInfo = JSON.parseObject(orderDetail.getActivityInfo());
				ActivityProduct activityProductInfo = JSON
						.parseObject(avtivityInfo.getString("ActivityProductInfo"), ActivityProduct.class);
				ActivityProduct activityProduct = activityProductService
						.findById(activityProductInfo.getActivityProductId());
				//更新多规格库存
				if(activityProduct.getSpecType().equals(false)){
					JSONObject spec = JSON.parseObject(orderDetail.getProductSpecInfo());
					//因为是活动商品,productSpecItemId代表的是 活动商品库存中的id
					Long activityProductStockId = spec.getLong("productSpecItemId");
					ActivityProductStock productStock = activityProductStockService.findById(activityProductStockId);
					int spceStock = productStock.getActivityStock() + orderDetail.getQuantity();
					productStock.setActivityStock(spceStock);
					activityProductStockService.update(productStock);
				}
				//更新总库存
				int stock = activityProduct.getActivityStock() + orderDetail.getQuantity();
				activityProduct.setActivityStock(stock);
				activityProductService.update(activityProduct);
			}
		}
	}

	/**
	 * 普通订单 48小时  (2天)
	 * 特价订单 48小时  (2天)
	 * 秒杀订单 30分钟
	 *  orderId
	 */
	@JmsListener(destination = ActiviMqQueueName.ORDER_CLOSE)
	public void generalOrder(String jsonData) {
		JSONObject jsonObject = JSON.parseObject(jsonData);
		Order order = orderService.findById(jsonObject.getLong("id"));
		activeDelaySendJobHandler.updateState(jsonObject.getString("activeMqTaskTOId"));
		if (null != order) {
			if (order.getPayFlag().equals(PayFlag.BUSINESS_CLOASE) || order.getPayFlag()
					.equals(PayFlag.USER_OVER_CLOASE) || order.getPayFlag().equals(PayFlag.SELLER_CLOASE)) {
				LOGGER.info("订单号:" + order.getOrderId() + "已关闭");
				return;
			}
			if (order.getPayFlag().equals(PayFlag.WAIT_PAY)) {
				order.setPayFlag(PayFlag.USER_OVER_CLOASE);
				order.setUpdateTime(ClockUtil.currentDate());
				if (orderService.update(order) > 0) {
					OrderCloseStockReturn(jsonData);

					LOGGER.info("关闭单成功,订单号:  " + order.getOrderId());
					return;
				}
				LOGGER.info("关闭订单更新失败,订单号: " + order.getOrderId());
				return;
			}
		}

	}


}
