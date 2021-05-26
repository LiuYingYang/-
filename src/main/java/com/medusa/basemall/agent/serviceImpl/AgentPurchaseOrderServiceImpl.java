package com.medusa.basemall.agent.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.medusa.basemall.activemq.ActiveMqClient;
import com.medusa.basemall.agent.dao.AgentMapper;
import com.medusa.basemall.agent.dao.AgentProductMapper;
import com.medusa.basemall.agent.dao.AgentPurchaseOrderDetailedMapper;
import com.medusa.basemall.agent.dao.AgentPurchaseOrderMapper;
import com.medusa.basemall.agent.entity.Agent;
import com.medusa.basemall.agent.entity.AgentProduct;
import com.medusa.basemall.agent.entity.AgentPurchaseOrder;
import com.medusa.basemall.agent.entity.AgentPurchaseOrderDetailed;
import com.medusa.basemall.agent.service.AgentPurchaseOrderDetailedService;
import com.medusa.basemall.agent.service.AgentPurchaseOrderService;
import com.medusa.basemall.agent.vo.*;
import com.medusa.basemall.configurer.WxServiceUtils;
import com.medusa.basemall.constant.ActiviMqQueueName;
import com.medusa.basemall.constant.AgentOrderPayFlag;
import com.medusa.basemall.constant.TimeType;
import com.medusa.basemall.constant.Url;
import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.core.ServiceException;
import com.medusa.basemall.jobhandler.ActiveDelaySendJobHandler;
import com.medusa.basemall.product.dao.ProductMapper;
import com.medusa.basemall.product.dao.ProductSpecItemMapper;
import com.medusa.basemall.product.entity.Product;
import com.medusa.basemall.product.entity.ProductSpecItem;
import com.medusa.basemall.user.dao.MemberMapper;
import com.medusa.basemall.user.dao.WxuserMapper;
import com.medusa.basemall.user.entity.Member;
import com.medusa.basemall.user.entity.Wxuser;
import com.medusa.basemall.user.vo.MiniWxuserVo;
import com.medusa.basemall.utils.IdGenerateUtils;
import com.medusa.basemall.utils.TimeUtil;
import com.vip.vjtools.vjkit.mapper.BeanMapper;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 *
 * @author medusa
 * @date 2018/06/02
 * 需要事物时添加  @Transactional
 */

@Service
public class AgentPurchaseOrderServiceImpl extends AbstractService<AgentPurchaseOrder>
		implements AgentPurchaseOrderService {
	@Resource
	private AgentPurchaseOrderMapper tAgentPurchaseOrderMapper;
	@Resource
	private AgentPurchaseOrderDetailedMapper tAgentPurchaseOrderDetailedMapper;
	@Resource
	private ProductSpecItemMapper tProductSpecItemMapper;
	@Resource
	private AgentProductMapper tAgentProductMapper;
	@Resource
	private AgentPurchaseOrderDetailedService agentPurchaseOrderDetailedService;
	@Resource
	private WxServiceUtils wxServiceUtils;
	@Resource
	private WxuserMapper tWxuserMapper;
	@Resource
	private MemberMapper tMemberMapper;
	@Resource
	private ProductMapper tProductMapper;
	@Resource
	private AgentMapper tAgentMapper;
	@Resource
	private ActiveDelaySendJobHandler activeDelaySendJobHandler;
	@Resource
	private ActiveMqClient activeMqClient;

	private static Logger log = LoggerFactory.getLogger(AgentPurchaseOrderServiceImpl.class);

	@Override
	public List<MiniOrderListVo> miniList(Long wxuserId, Integer payFlag, String shopName) {
		Map<String, Object> map = new HashMap<>();
		map.put("wxuserId", wxuserId);
		map.put("payFlag", payFlag);
		map.put("shopName", getLike(shopName));


		List<MiniOrderListVo> miniOrderListVos = tAgentPurchaseOrderMapper.selectMiniList(map);
		return miniOrderListVos;
	}

	@Override
	public MiniOrderListVo miniListDetail(Long purchaseOrderId) {
		MiniOrderListVo miniOrderListVo = tAgentPurchaseOrderMapper.selectMiniListDetail(purchaseOrderId);
		return miniOrderListVo;
	}

	@Override
	public List<BackstageOrderListVo> backstageList(Integer payFlag, String appmodelId, String shopName,
			String nikeName, String user, String phone, String outTradeNo, String wlNum, String payTime) {
		Map<String, Object> map = new HashMap<>();
		map.put("appmodelId", appmodelId);
		map.put("payFlag", payFlag);
		map.put("shopName", getLike(shopName));
		map.put("user", getLike(user));
		map.put("nikeName", getLike(nikeName));
		map.put("phone", getLike(phone));
		map.put("outTradeNo", outTradeNo);
		map.put("wlNum", wlNum);
		if (null != payTime && !payTime.equals("")) {
			String[] split = payTime.split(",");
			map.put("startTime", split[0]);
			map.put("endTime", split[1]);
		} else {
			map.put("startTime", null);
			map.put("endTime", null);
		}
		List<BackstageOrderListVo> backstageOrderListVos = tAgentPurchaseOrderMapper.selectBackstageList(map);
		StringBuffer buffer = new StringBuffer();

		if (backstageOrderListVos.size() > 0) {
			for (BackstageOrderListVo backstageOrderListVo : backstageOrderListVos) {
				buffer.append(backstageOrderListVo.getWxuserId() + ",");
			}
			List<Wxuser> wxusers = tWxuserMapper.selectByIds(buffer.substring(0, buffer.length() - 1));
			backstageOrderListVos.forEach(obj -> {
				wxusers.forEach(wxuser -> {
					if (obj.getWxuserId().equals(wxuser.getWxuserId())) {
						obj.setNikeName(wxuser.getNikeName());
					}
				});
			});
		}

		return backstageOrderListVos;
	}

	@Override
	public Result deliverGoods(List<AgentDeliverGoods> deliverGoods) {
		List<AgentPurchaseOrder> purchaseOrders = BeanMapper.mapList(deliverGoods, AgentPurchaseOrder.class);
		String nowTime = TimeUtil.getNowTime();
		if (purchaseOrders.size() >= 1) {
			for (AgentPurchaseOrder obj : purchaseOrders) {
				obj.setSendTime(nowTime);
				obj.setPayFlag(AgentOrderPayFlag.DELIVERY);
				obj.setUpdateTime(nowTime);
				if (tAgentPurchaseOrderMapper.updateByPrimaryKeySelective(obj) > 0) {
					activeDelaySendJobHandler.savaTask(obj.getPurchaseOrderId().toString(),
							ActiviMqQueueName.AGENT_PURCHASE_ORDER_CONFIRM_RECEIPT, TimeType.TWENTYDAY,
							obj.getAppmodelId());
				}
			}
			return ResultGenerator.genSuccessResult();
		}
		return ResultGenerator.genFailResult("发货失败");
	}

	@Override
	public Result closeOrder(String purchaseOrderIds, Integer operatiPerson) {
		//客户操作
		if (operatiPerson.equals(1001)) {
			tAgentPurchaseOrderMapper.closeOrder(AgentOrderPayFlag.BUSINESS_CLOASE, purchaseOrderIds.split(","));
		} else if (operatiPerson.equals(2001)) {
			tAgentPurchaseOrderMapper.closeOrder(AgentOrderPayFlag.SELLER_CLOASE, purchaseOrderIds.split(","));
		} else {
			return ResultGenerator.genFailResult("非法操作");
		}
		return ResultGenerator.genSuccessResult();
	}

	@Override
	public Result batchBackRemrk(JSONObject jsonObject) {
		String purchaseOrderIds = jsonObject.getString("purchaseOrderIds");
		String backRemark = jsonObject.getString("backRemark");
		Boolean coverType = jsonObject.getBoolean("coverType");
		if (purchaseOrderIds.length() > 0) {
			List<AgentPurchaseOrder> purchaseOrders = tAgentPurchaseOrderMapper
					.selectByIds(ArrayUtils.toString(purchaseOrderIds));
			//todo 后期优化批量更新
			if (coverType) {
				purchaseOrders.forEach(obj -> {
					obj.setBackRemark(backRemark);
					tAgentPurchaseOrderMapper.updateByPrimaryKey(obj);
				});
			} else {
				//不覆盖原有备注,过滤出非空备注的用户
				List<AgentPurchaseOrder> collect = purchaseOrders.stream()
						.filter(obj -> obj.getBackRemark() == null || "".equals(obj.getBackRemark()))
						.collect(Collectors.toList());
				collect.forEach(obj -> {
					obj.setBackRemark(backRemark);
					tAgentPurchaseOrderMapper.updateByPrimaryKeySelective(obj);
				});

			}
			return ResultGenerator.genSuccessResult();
		}
		return ResultGenerator.genFailResult("备注失败");
	}

	@Override
	public void updateOrderAddres(Long purchaseOrderId, String addres) {
		AgentPurchaseOrder purchaseOrder = new AgentPurchaseOrder();
		purchaseOrder.setPurchaseOrderId(purchaseOrderId);
		purchaseOrder.setAddress(addres);
		tAgentPurchaseOrderMapper.updateByPrimaryKeySelective(purchaseOrder);
	}

	@Override
	public BackstageOrderListVo backstageDetail(Long purchaseOrderId) {
		BackstageOrderListVo backstageDetail = tAgentPurchaseOrderMapper.selectBackstageDetail(purchaseOrderId);
		if (backstageDetail != null) {
			Wxuser wxuser = tWxuserMapper.selectByPrimaryKey(backstageDetail.getWxuserId());
			backstageDetail.setNikeName(wxuser.getNikeName());

		} return backstageDetail;
	}

	private String getLike(String like) {
		if (like != null && !like.equals("")) {
			char[] chars = like.toCharArray();
			StringBuffer buffer = new StringBuffer("%");
			for (char aChar : chars) {
				buffer.append(aChar + "%");
			}
			return buffer.toString();
		}
		return null;
	}


	@Override
	@Transactional
	public Result saveOrder(PayAgentOrderVo saveOrderVo) {
		//查询库存是否足够
		List<AgentProductOrderVo> notStockValue = stockJudge(saveOrderVo.getProductList());
		if (notStockValue != null) {
			return ResultGenerator.genFailResult("商品库存不足", notStockValue);
		}
		AgentPurchaseOrder agentPurchaseOrder = generateOrder(saveOrderVo);
		if (agentPurchaseOrder != null) {
			activeDelaySendJobHandler.savaTask(agentPurchaseOrder.getPurchaseOrderId().toString(),
					ActiviMqQueueName.AGENT_PURCHASE_ORDER, TimeType.HALFHOUR, agentPurchaseOrder.getAppmodelId());
			return ResultGenerator.genSuccessResult(agentPurchaseOrder.getPurchaseOrderId());
		}
		return ResultGenerator.genFailResult("下单失败");
	}

	@Override
	public String notify(String xmlResult) {
		log.info("代理订单回调XmlResult --------->" + xmlResult);
		WxPayOrderNotifyResult payOrderNotifyResult = WxPayOrderNotifyResult.fromXML(xmlResult);
		if ("SUCCESS".equalsIgnoreCase(payOrderNotifyResult.getResultCode())) {
			log.info("开始处理代理订单回调: " + payOrderNotifyResult.getOutTradeNo() + " !!!");
			AgentPurchaseOrder purchaseOrder = tAgentPurchaseOrderMapper
					.selectByOutTradeNo(payOrderNotifyResult.getOutTradeNo());
			if (purchaseOrder.getPayFlag().equals(AgentOrderPayFlag.WAIT_PAY)) {
				paySuccessAgentOrder(purchaseOrder);
				return WxPayNotifyResponse.success("处理成功");
			}
			return WxPayNotifyResponse.fail("订单已处理");
		}
		return WxPayNotifyResponse.fail("处理失败");
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result paymentOrder(PaymentAgentOrderVo payAgentOrderVo, HttpServletRequest request) {
		AgentPurchaseOrder agentPurchaseOrder = tAgentPurchaseOrderMapper
				.selectByPrimaryKey(payAgentOrderVo.getPurchaseOrderId());
		if (agentPurchaseOrder == null) {
			return ResultGenerator.genFailResult("订单不存在");
		}
		if (!agentPurchaseOrder.getPayFlag().equals(0)) {
			String message = "";
			switch (agentPurchaseOrder.getPayFlag()) {
				case 1:
					message = "该订单已付款";
					break;
				case 2:
					message = "该订单已发货";
					break;
				case 3:
					message = "该订单已交易成功";
					break;
				case 4:
					message = "该订单已订单关闭";
					break;
				default:
					message = "未知错误";
					break;
			}
			return ResultGenerator.genFailResult(message);
		}

		agentPurchaseOrder.setPayType(payAgentOrderVo.getPayType());
		tAgentPurchaseOrderMapper.updateByPrimaryKeySelective(agentPurchaseOrder);
		if (agentPurchaseOrder.getPayType().equals(1)) {
			return payOrder(request, agentPurchaseOrder, agentPurchaseOrder.getPayFee().toString(),
					payAgentOrderVo.getOpenId(), Url.AGENT_ORDER_NOTIFY);
		}
		if (agentPurchaseOrder.getPayType().equals(2)) {
			//判断余额是否足够,减少余额
			MiniWxuserVo wxuser = tWxuserMapper.selectByWxuserId(agentPurchaseOrder.getWxuserId());
			if (null == wxuser.getMemberInfo()) {
				return ResultGenerator.genFailResult("非会员用户,不可使用余额支付");
			}
			Double supplyBonus = wxuser.getMemberInfo().getSupplyBonus() - agentPurchaseOrder.getPayFee().doubleValue();
			if (supplyBonus < 0.0) {
				return ResultGenerator.genFailResult("余额不足");
			}
			Member memberInfo = wxuser.getMemberInfo();
			memberInfo.setSupplyBonus(supplyBonus);
			tMemberMapper.updateByPrimaryKeySelective(memberInfo);
			//支付成功的订单
			paySuccessAgentOrder(agentPurchaseOrder);
			return ResultGenerator.genSuccessResult();
		}
		return ResultGenerator.genFailResult("订单错误");
	}


	private void paySuccessAgentOrder(AgentPurchaseOrder purchaseOrder) {
		//更新订单
		purchaseOrder.setPayFlag(AgentOrderPayFlag.PAY_OK);
		String nowTime = TimeUtil.getNowTime();
		purchaseOrder.setPayTime(nowTime);
		purchaseOrder.setUpdateTime(nowTime);
		tAgentPurchaseOrderMapper.updateByPrimaryKeySelective(purchaseOrder);
		//更新消费额度
		Agent agent = new Agent();
		agent.setWxuserId(purchaseOrder.getWxuserId());
		agent = tAgentMapper.selectOne(agent);
		double limite = agent.getAgentBalance() + purchaseOrder.getPayFee().doubleValue();
		agent.setAgentBalance(limite);
		tAgentMapper.updateByPrimaryKeySelective(agent);
		//减少库存
		stockReduceJudge(purchaseOrder.getPurchaseOrderId());
	}

	/**
	 * 库存减少处理
	 * @param agentProductOrderVos
	 */
	private synchronized void stockReduceJudge(Long agentProductOrderVos) {
		Condition condition = new Condition(AgentPurchaseOrderDetailed.class);
		condition.createCriteria().andEqualTo("purchaseOrderId", agentProductOrderVos);
		List<AgentPurchaseOrderDetailed> detaileds = tAgentPurchaseOrderDetailedMapper.selectByCondition(condition);
		List<AgentProductOrderVo> voList = BeanMapper.mapList(detaileds, AgentProductOrderVo.class);
		StringBuffer productIds = new StringBuffer();
		detaileds.forEach(obj -> {
			productIds.append(obj.getProductId() + ",");
		});
		String ids = productIds.substring(0, productIds.length() - 1);
		List<Product> products = tProductMapper.selectByIds(ids);
		Condition c = new Condition(AgentProduct.class);
		c.createCriteria().andEqualTo("productId", ids);
		List<AgentProduct> agentProducts = tAgentProductMapper.selectByCondition(c);
		//todo 后期优化  批量更新
		//总库存减少,代理商品库存减少
		voList.forEach(obj -> {
			products.forEach(product -> {
				if (obj.getProductId().equals(product.getProductId())) {
					product.setStock(product.getStock() - obj.getQuantity());
					tProductMapper.updateByPrimaryKeySelective(product);
				}
			});
			agentProducts.forEach(agentProduct -> {
				if (obj.getProductId().equals(agentProduct.getProductId())) {
					agentProduct.setAgentStock(agentProduct.getAgentStock() - obj.getQuantity());
					tAgentProductMapper.updateByPrimaryKeySelective(agentProduct);
				}
			});
		});

		List<AgentProductOrderVo> havaSpec = voList.stream().filter(obj -> obj.getProductSpecInfo().length() > 0)
				.collect(Collectors.toList());
		if (havaSpec.size() > 0) {
			//代理商品规格总库存 减少   代理商品库存减少
			StringBuffer itemIds = new StringBuffer();
			havaSpec.forEach(obj -> {
				ProductSpecItem productSpecItem = JSONObject
						.parseObject(obj.getProductSpecInfo(), ProductSpecItem.class);
				itemIds.append(productSpecItem.getProductSpecItemId() + ",");
			});
			List<ProductSpecItem> specItems = tProductSpecItemMapper
					.selectByIds(itemIds.substring(0, itemIds.length() - 1));
			havaSpec.forEach(obj -> {
				specItems.forEach(specItem -> {
					if (obj.getProductId().equals(specItem.getProductId())) {
						specItem.setAgentStock(specItem.getAgentStock() - obj.getQuantity());
						specItem.setStock(specItem.getStock() - obj.getQuantity());
						tProductSpecItemMapper.updateByPrimaryKeySelective(specItem);
					}
				});
			});
		}
	}

	private Result payOrder(HttpServletRequest request, AgentPurchaseOrder order, String payFee, String openId,
			String notify) {
		try {
			String outTradeNo = order.getOutTradeNo();
			if (order.getOutTradeNoExt() != null) {
				outTradeNo = order.getOutTradeNoExt();
			}
			Map<String, String> resultMap = wxServiceUtils
					.wxJsapiPayRequest("标准商城代理商品下单", outTradeNo, payFee, openId, notify,
							 order.getAppmodelId());
			if (resultMap.get("returnCode").equalsIgnoreCase("SUCCESS")) {
				resultMap.put("orderId", order.getPurchaseOrderId() + "");
			}
			return ResultGenerator.genSuccessResult(resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("下单失败");
		}
	}

	/**
	 * 商品库存判断
	 * 待规格商品需要判断规格库存是否足够
	 * 统一规格商品需要判断代理商库存是否足够
	 * @param productList
	 * @return
	 */
	private synchronized List<AgentProductOrderVo> stockJudge(List<AgentProductOrderVo> productList) {
		List<AgentProductOrderVo> notStockValue = new ArrayList<>();
		List<AgentProductOrderVo> havaSpec = new ArrayList<>();
		List<AgentProductOrderVo> notSpec = new ArrayList<>();
		//区分带规格和同一规格的商品
		for (AgentProductOrderVo p : productList) {
			if (null != p.getProductSpecInfo() && p.getProductSpecInfo().length() > 1) {
				havaSpec.add(p);
			} else {
				notSpec.add(p);
			}
		}
		//带规格的商品,只查询商品当前选中的规格库存
		if (havaSpec.size() > 0) {
			//规格库存查询
			StringBuffer specIds = new StringBuffer();
			havaSpec.forEach(obj -> {
				ProductSpecItem parse = JSON.parseObject(obj.getProductSpecInfo(), ProductSpecItem.class);
				specIds.append(parse.getProductSpecItemId() + ",");
			});
			//查询商品规格目前的库存量
			List<ProductSpecItem> productSpecItems = tProductSpecItemMapper
					.selectByIds(specIds.substring(0, specIds.length() - 1));
			for (AgentProductOrderVo productOrderVo : havaSpec) {
				int i = 0;
				for (ProductSpecItem productSpecItem : productSpecItems) {
					if (i == 0 && productOrderVo.getProductId().equals(productSpecItem.getProductId())) {
						if (productSpecItem.getAgentStock() - productOrderVo.getQuantity() < 0) {
							notStockValue.add(productOrderVo);
						}
					}
				}
			}
		}
		//处理不带规格的商品
		if (notSpec.size() > 0) {
			StringBuffer productIds = new StringBuffer();
			notSpec.forEach(obj -> {
				productIds.append(obj.getProductId() + ",");
			});
			List<AgentProduct> products = tAgentProductMapper
					.selectByIds(productIds.substring(0, productIds.length() - 1));
			for (AgentProductOrderVo obj : notSpec) {
				int i = 0;
				for (AgentProduct product : products) {
					if (i == 0 && product.getProductId().equals(obj.getProductId())) {
						i = 1;
						if (product.getAgentStock() - obj.getQuantity() < 0) {
							notStockValue.add(obj);
						}
					}
				}
			}
		}
		return notStockValue.size() > 0 ? notStockValue : null;
	}

	private AgentPurchaseOrder generateOrder(PayAgentOrderVo saveOrderVo) {
		AgentPurchaseOrder order = BeanMapper.map(saveOrderVo.getOrder(), AgentPurchaseOrder.class);
		String outTradeNo = IdGenerateUtils.getOrderNum();
		order.setPurchaseOrderId(IdGenerateUtils.getItemId());
		order.setOutTradeNo(outTradeNo);
		order.setCreateTime(TimeUtil.getNowTime());
		order.setPayFlag(0);
		if (tAgentPurchaseOrderMapper.insertSelective(order) > 0) {
			agentPurchaseOrderDetailedService.saveOrderDetail(saveOrderVo.getProductList(), order);
			return order;
		}
		return null;
	}


}
