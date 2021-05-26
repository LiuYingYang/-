package com.medusa.basemall.product.controller;

import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.activemq.ActiveMqClient;
import com.medusa.basemall.aop.annotation.VersionManager;
import com.medusa.basemall.constant.ActiviMqQueueName;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.product.entity.Product;
import com.medusa.basemall.product.service.ProductService;
import com.medusa.basemall.product.service.ProductSpecItemService;
import com.medusa.basemall.product.vo.SpecItemUpdateVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/product/spece/item")
@VersionManager
public class ProductSpecItemController {

	@Autowired
	private ProductSpecItemService specItemService;

	@Autowired
	private ProductService productService;
	@Resource
	private ActiveMqClient activeMqClient;

	@PutMapping("/v1/update")
	@ApiOperation(value = "修改多规格库存/价格", tags = "更新接口")
	@ApiResponses({@ApiResponse(code = 100, message = "success"), @ApiResponse(code = 99, message = "修改失败"),})
	public Result updateSpecStockOrPrice(@RequestBody JSONObject jsonObject) {
		List<SpecItemUpdateVo> specItemUpdateVos = JSONObject
				.parseArray(jsonObject.getString("specItemUpdateVos"), SpecItemUpdateVo.class);
		if (specItemService.updateSpecStockOrPrice(specItemUpdateVos) > 0) {
			int sum = specItemUpdateVos.stream().mapToInt(SpecItemUpdateVo::getStock).sum();
			Product product = new Product();
			product.setProductId(specItemUpdateVos.get(0).getProductId());
			product.setStock(sum);
			if(sum == 0){
				product.setShelfState(2);
			}else{
				product.setShelfState(0);
			}
			productService.update(product);
			JSONObject jsondata = new JSONObject();
			jsondata.put("productIds", product.getProductId());
			jsondata.put("shelfState", product.getShelfState());
			activeMqClient.send(jsondata.toJSONString(), ActiviMqQueueName.PRODUCT_SHELFSTATE_CHANGE);
			return ResultGenerator.genSuccessResult();
		}
		return ResultGenerator.genFailResult("修改失败");
	}

}
