package com.medusa.basemall.product.serviceimpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.core.ServiceException;
import com.medusa.basemall.integral.entity.Prize;
import com.medusa.basemall.integral.service.PrizeService;
import com.medusa.basemall.product.dao.LogisticCancelMapper;
import com.medusa.basemall.product.entity.*;
import com.medusa.basemall.product.service.LogisticCancelService;
import com.medusa.basemall.product.service.LogisticDistrobutionService;
import com.medusa.basemall.product.service.LogisticModelService;
import com.medusa.basemall.product.service.ProductService;
import com.medusa.basemall.product.vo.CalculateFareVo;
import com.medusa.basemall.product.vo.CalculateLogsticFeeVo;
import com.medusa.basemall.product.vo.LogisticModelVo;
import com.medusa.basemall.product.vo.ProductInfoVo;
import com.medusa.basemall.utils.GeoCodeUtil;
import com.medusa.basemall.utils.KdniaoTrackQueryAPI;
import com.vip.vjtools.vjkit.mapper.BeanMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by psy on 2018/05/25.
 * 需要事物时添加  @Transactional
 */

@Service

public class LogisticCancelServiceImpl extends AbstractService<LogisticCancel> implements LogisticCancelService {

	@Resource
	private LogisticCancelMapper logisticCancelMapper;

	@Resource
	private LogisticDistrobutionService logisticDistrobutionService;

	@Resource
	private LogisticModelService logisticModelService;

	@Resource
	private ProductService productService;

	@Resource
	private PrizeService prizeService;

	/**
	 * 保存或更新退货地址  只有一个默认的地址
	 *
	 * @param logisticCancel
	 * @return
	 */
	@Override
	public int saveOrUpdateLogisticCancel(LogisticCancel logisticCancel) {

		int rs = 0;
		if (logisticCancel.getLogisticCancelId() == -1) {
			//如果有默认地址，则其他设置为非默认
			if (logisticCancel.getDefaultState()) {
				logisticCancelMapper.updateDefaultState(logisticCancel.getAppmodelId());
			}
			logisticCancel.setLogisticCancelId(null);
			rs = logisticCancelMapper.insertSelective(logisticCancel);
		} else {
			//如果有默认地址，则其他设置为非默认
			if (logisticCancel.getDefaultState()) {
				logisticCancelMapper.updateDefaultState(logisticCancel.getAppmodelId());
			}
			rs = logisticCancelMapper.updateByPrimaryKeySelective(logisticCancel);
		}

		return rs;
	}

	/**
	 * 查询退货地址
	 *
	 * @param appmodelId
	 * @return
	 */
	@Override
	public List<LogisticCancel> findByAppmodelId(String appmodelId) {
		LogisticCancel logisticCancel = new LogisticCancel();
		logisticCancel.setAppmodelId(appmodelId);

		return logisticCancelMapper.select(logisticCancel);
	}

	@Override
	public Result findWLMsg(String wlCode, String wlNum) {
		if (!wlNum.equals("") && !wlCode.equals("")) {
			KdniaoTrackQueryAPI kd = new KdniaoTrackQueryAPI();
			String rs = "";
			try {
				rs = kd.getOrderTracesByJson(wlCode, wlNum);
				JSONArray traces = JSONObject.parseObject(rs).getJSONArray("Traces");
				Collections.reverse(traces);
				return ResultGenerator.genSuccessResult(traces);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			return ResultGenerator.genFailResult("无数据");
		}
		return ResultGenerator.genFailResult("参数有误");
	}

	@Override
	public List<CalculateFareVo> calculateLogsticFee(CalculateLogsticFeeVo calculateLogsticFeeVo) {
		if (calculateLogsticFeeVo.getProductInfos().size() == 0) {
			throw new ServiceException("商品不能为空");
		}
		//用户坐标地址
		String addressEncoded = GeoCodeUtil
				.getAddressEncoded(calculateLogsticFeeVo.getAddress().replace(",", ""), false);
		String userAddresslocation = JSON.parseObject(addressEncoded).getJSONArray("geocodes").getJSONObject(0)
				.getString("location");
		StringBuffer productIds = new StringBuffer();
		calculateLogsticFeeVo.getProductInfos().forEach(obj -> {
			productIds.append(obj.getProductId() + ",");
		});
		List<ProductInfoVo> productInfoVos = null;
		if (calculateLogsticFeeVo.getType().equals(1)) {
			List<Product> products = productService.findByIds(productIds.substring(0, productIds.length() - 1));
			productInfoVos = BeanMapper.mapList(products, ProductInfoVo.class);
		} else {
			List<Prize> prizes = prizeService.findByIds(productIds.substring(0, productIds.length() - 1));
			productInfoVos = new ArrayList<>();
			for (Prize prize : prizes) {
				ProductInfoVo productInfoVo = new ProductInfoVo();
				productInfoVo.setProductId(prize.getPrizeId().longValue());
				productInfoVo.setProductBulk(prize.getProductBulk());
				productInfoVo.setLogisticModelId(prize.getLogisticModelId());
				productInfoVo.setNum(1);
				productInfoVos.add(productInfoVo);
			}
		}
		//计算商家配送价格
		List<CalculateFareVo> fareVos = new ArrayList<>();
		CalculateFareVo merchantDistribution = calculateMerchantDistribution(userAddresslocation,
				calculateLogsticFeeVo.getAppmodelId());
		if (merchantDistribution != null) {
			//商家配送运费价格
			fareVos.add(merchantDistribution);
		}
		//把商品的模板id和商品的体积或重量或重量装入
		List<ProductInfoVo> finalProductInfoVos = productInfoVos;
		calculateLogsticFeeVo.getProductInfos().forEach(obj -> {
			finalProductInfoVos.forEach(product -> {
				if (product.getProductId().equals(obj.getProductId())) {
					obj.setLogisticModelId(product.getLogisticModelId());
					obj.setProductBulk(product.getProductBulk());
				}
			});
		});
		//计算物流配送价格,按模板计算配送价格,如果商品没有对应的模板,按0计算
		CalculateFareVo logisticsDistribution = calculateLogisticsDistribution(calculateLogsticFeeVo);
		if (logisticsDistribution != null) {
			fareVos.add(logisticsDistribution);
		}
		return fareVos;
	}

	@Override
	public LogisticCancel selectDefultTrue(String appmodelId) {
		return logisticCancelMapper.selectDefultTrue(appmodelId);
	}

	/**
	 * 计算物流配送运费价格
	 * @return
	 */
	private CalculateFareVo calculateLogisticsDistribution(CalculateLogsticFeeVo calculateLogsticFeeVo) {
		List<ProductInfoVo> productInfos = calculateLogsticFeeVo.getProductInfos();
		//查询商品的对应的物流模板
		StringBuffer logisticModelIds = new StringBuffer();
		productInfos.forEach(obj -> {
			if (obj.getLogisticModelId() > 0) {
				logisticModelIds.append(obj.getLogisticModelId() + ",");
			}
		});
		List<LogisticModelVo> logisticModels = logisticModelService
				.findByWlCalculateFare(logisticModelIds.toString().split(","));
		if (logisticModels.size() == 0 || logisticModels.get(0).getTurnState().equals(false)) {
			return null;
		}
		double price = 0.0;
		//按模板分组商品 筛选出按件/按重量/按体积的商品
		List<LogisticModelVo> itemModel = blockSortModel(logisticModels, 0);
		List<ProductInfoVo> item = blockSortProduct(productInfos, itemModel);
		//按件计算
		if (item.size() > 0) {
			price += itemCalculate(item, itemModel, calculateLogsticFeeVo.getAddress(),
					calculateLogsticFeeVo.getTotlePrice());
		}
		List<LogisticModelVo> weighModel = blockSortModel(logisticModels, 1);
		List<ProductInfoVo> weigh = blockSortProduct(productInfos, weighModel);
		//按重量计算
		if (weigh.size() > 0) {
			price += weighCalculate(weigh, weighModel, calculateLogsticFeeVo.getAddress(),
					calculateLogsticFeeVo.getTotlePrice());
		}
		List<LogisticModelVo> volumeModel = blockSortModel(logisticModels, 2);
		List<ProductInfoVo> volume = blockSortProduct(productInfos, volumeModel);
		//按体积计算
		if (volume.size() > 0) {
			price += volumeCalculate(volume, volumeModel, calculateLogsticFeeVo.getAddress(),
					calculateLogsticFeeVo.getTotlePrice());

		}
		return new CalculateFareVo("物流配送", price, 1, "");
	}

	/**
	 * 筛选指定模板
	 * @param logisticModels
	 * @param type   计价方式
	 * @return
	 */
	private List<LogisticModelVo> blockSortModel(List<LogisticModelVo> logisticModels, int type) {
		List<ProductInfoVo> productInfoVos = new ArrayList<>();
		return logisticModels.stream().filter(modelVo -> modelVo.getValuationType().equals(type))
				.collect(Collectors.toList());
	}

	/**
	 * 筛选指定商品模板
	 * @param productInfos
	 * @param logisticModels
	 * @return
	 */
	private List<ProductInfoVo> blockSortProduct(List<ProductInfoVo> productInfos,
			List<LogisticModelVo> logisticModels) {
		List<ProductInfoVo> newProductInfoVos = new ArrayList<>();
		logisticModels.forEach(obj -> {
			for (ProductInfoVo productInfoVo : productInfos) {
				if (obj.getLogisticModelId().equals(productInfoVo.getLogisticModelId())) {
					newProductInfoVos.add(productInfoVo);
				}
			}
		});
		return newProductInfoVos;
	}

	private double volumeCalculate(List<ProductInfoVo> volume, List<LogisticModelVo> logisticModels, String address,
			Double totlePrice) {
		double price = 0.0;
		price = getPrice(volume, logisticModels, address, totlePrice, price);
		return price;
	}

	/**
	 * 按件计算
	 * @param item      商品
	 * @param logisticModels   按件计算的模板
	 * @param address   用户地址
	 * @param totlePrice
	 * @return
	 */
	private double itemCalculate(List<ProductInfoVo> item, List<LogisticModelVo> logisticModels, String address,
			Double totlePrice) {
		double price = 0.0;
		price = getPrice(item, logisticModels, address, totlePrice, price);
		return price;
	}

	private double getPrice(List<ProductInfoVo> item, List<LogisticModelVo> logisticModels, String address,
			Double totlePrice, double price) {
		for (LogisticModelVo modelVo : logisticModels) {
			//判断是否有指定包邮区域
			if (modelVo.getFreeState().equals(true)) {
				//判断用户是否在指定包邮区域,包邮条件是否达标
				if (exemptionFromPostageCalculate(modelVo.getFreeList(), address, item, totlePrice)) {
					return 0;
				}
			}
			//计算物流费用(如是指定区域则使用指定区域模板)
			List<ProductInfoVo> productInfoVos = item.stream()
					.filter(obj -> obj.getLogisticModelId().equals(modelVo.getLogisticModelId()))
					.collect(Collectors.toList());
			price += wlPriceCalculate(modelVo.getChargeList(), productInfoVos, address, modelVo.getValuationType());
		}
		return price;
	}

	/**
	 * 计算物流费用(如是指定区域则使用指定区域模板)
	 * @param chargeList
	 * @param item
	 * @param address
	 * @param valuationType
	 * @return
	 */
	private double wlPriceCalculate(List<LogisticCharge> chargeList, List<ProductInfoVo> item, String address,
			Integer valuationType) {
		double price = 0;
		List<LogisticCharge> defaultModel = chargeList.stream().filter(obj -> obj.getChargeAddress().equals("全国"))
				.collect(Collectors.toList());
		List<LogisticCharge> specifyModel = chargeList.stream().filter(obj -> !obj.getChargeAddress().equals("全国"))
				.collect(Collectors.toList());
		for (LogisticCharge logisticCharge : specifyModel) {
			if (valuationType.equals(0)) {
				price = itemWlCalculate(logisticCharge, item, address);
				if (price > 0) {
					return price;
				}
			}
			if (valuationType.equals(1) || valuationType.equals(2)) {
				price = weighWlCalculate(logisticCharge, item, address);
				if (price > 0) {
					return price;
				}
			}
			if (defaultModel.size() > 0) {
				return itemWlCalculate(defaultModel.get(0), item, address);
			}
		}

		return price;
	}

	private double weighWlCalculate(LogisticCharge logisticCharge, List<ProductInfoVo> item, String address) {
		double num = item.stream().mapToDouble(ProductInfoVo::getProductBulk).sum();
		for (String s : logisticCharge.getChargeAddress().split(",")) {
			if (address.contains(s)) {
				if (num <= logisticCharge.getFirstWeight()) {
					return logisticCharge.getFirstPrice();
				} else {
					double price = logisticCharge.getFirstPrice();
					double sum = num - logisticCharge.getFirstWeight();
					if (sum < logisticCharge.getNextWeight()) {
						price += logisticCharge.getNextPrice();
					} else {
						price += sum / logisticCharge.getNextWeight() * logisticCharge.getNextPrice();
					}
					return price;
				}
			}
		}
		if (logisticCharge.getChargeAddress().equals("全国")) {
			if (num <= logisticCharge.getFirstWeight()) {
				return logisticCharge.getFirstPrice();
			} else {
				double price = (num - logisticCharge.getFirstWeight()) / logisticCharge.getNextWeight() * logisticCharge
						.getFirstPrice() + logisticCharge.getFirstPrice();
				return price;
			}
		}

		return 0;
	}

	private double itemWlCalculate(LogisticCharge logisticCharge, List<ProductInfoVo> item, String address) {
		int sum = item.stream().mapToInt(ProductInfoVo::getNum).sum();
		for (String s : logisticCharge.getChargeAddress().split(",")) {
			if (address.contains(s)) {
				if (sum <= logisticCharge.getFirstWeight()) {
					return logisticCharge.getFirstPrice();
				} else {
					double price = logisticCharge.getFirstPrice();
					int num = sum - logisticCharge.getFirstWeight();
					if (num < logisticCharge.getNextWeight()) {
						price += logisticCharge.getNextPrice();
					} else {
						price += num / logisticCharge.getNextWeight() * logisticCharge.getNextPrice();
					}
					return price;
				}
			}
		}
		if (logisticCharge.getChargeAddress().equals("全国")) {
			if (sum <= logisticCharge.getFirstWeight()) {
				return logisticCharge.getFirstPrice();
			} else {
				double price = logisticCharge.getFirstPrice();
				int num = sum - logisticCharge.getFirstWeight();
				if (num < logisticCharge.getNextWeight()) {
					price += logisticCharge.getNextPrice();
				} else {
					price += num / logisticCharge.getNextWeight() * logisticCharge.getNextPrice();
				}
				return price;
			}
		}
		return 0;
	}

	/**
	 * 是否满足包邮计算
	 * @param freeList
	 * @param address
	 * @param item
	 * @param totlePrice
	 * @return
	 */
	private boolean exemptionFromPostageCalculate(List<LogisticFree> freeList, String address, List<ProductInfoVo> item,
			Double totlePrice) {
		int num = item.stream().mapToInt(ProductInfoVo::getNum).sum();
		for (LogisticFree logisticFree : freeList) {
			for (String s : logisticFree.getFreeAddress().split(",")) {
				if (address.contains(s)) {
					//如果用户处在可包邮的地区,判断满不满足包邮条件
					switch (logisticFree.getUnitType()) {
						case 0:
							if (num >= logisticFree.getConditionShip()) {
								return true;
							}
							break;
						case 1:
							if (totlePrice >= logisticFree.getMaxPrice()) {
								return true;
							}
							break;
						case 2:
							if (num >= logisticFree.getConditionShip() && totlePrice >= logisticFree.getMaxPrice()) {
								return true;
							}
							break;
						default:
							break;
					}
				}
			}
		}
		return false;
	}

	/**
	 *1取最大首重价格的模版首重作为订单首重价格和首重门槛，然后你分别计算商品独自的续重价格。
	 *2当有最大首重价格相同，首重单位价格相同，续重价格不同的模版时，选取最小续重的模版作为订单首重。
	 *3当有最大首重价格相同，单位价格不同时，取单位价格最大的模版作为订单的首重。
	 *4包邮商品不参与运费计算
	 * 按重量计算
	 * @param weigh
	 * @param logisticModels
	 * @param address
	 * @param totlePrice
	 * @return
	 */
	private double weighCalculate(List<ProductInfoVo> weigh, List<LogisticModelVo> logisticModels, String address,
			Double totlePrice) {
		double price = 0.0;
		price = getPrice(weigh, logisticModels, address, totlePrice, price);
		return price;
	}


	/**
	 * 计算商家配送运费价格
	 * @param addresslocation
	 * @param appmodelId
	 * @return
	 */
	private CalculateFareVo calculateMerchantDistribution(String addresslocation, String appmodelId) {
		LogisticDistrobution logisticDistrobution = logisticDistrobutionService.findBy("appmodelId", appmodelId);
		if (logisticDistrobution != null && logisticDistrobution.getTurnState().equals(false)) {
			return null;
		}
		JSONObject latLng = JSON.parseObject(logisticDistrobution.getShopAddress()).getJSONObject("latLng");
		String origins = latLng.getString("lng") + "," + latLng.getString("lat");
		String rangeMeasurement = GeoCodeUtil.getRangeMeasurement(origins, addresslocation, null);
		Long distance = JSON.parseObject(JSON.parseObject(rangeMeasurement).getJSONArray("results").getString(0))
				.getLong("distance");
		if (logisticDistrobution.getDeliveryRange() * 1000 < distance) {
			return new CalculateFareVo("商家配送", 0.0, 2, "超出商家配送范围");
		}
		return new CalculateFareVo("商家配送", logisticDistrobution.getSellerPrice(), 1,
				logisticDistrobution.getOperatingTime());
	}


}
