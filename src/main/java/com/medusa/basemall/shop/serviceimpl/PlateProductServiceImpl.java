package com.medusa.basemall.shop.serviceimpl;

import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.shop.dao.PlateProductMapper;
import com.medusa.basemall.shop.entity.PlateProduct;
import com.medusa.basemall.shop.service.PlateProductService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author Created by wx on 2018/05/26.
 */

@Service

public class PlateProductServiceImpl extends AbstractService<PlateProduct> implements PlateProductService {
    @Resource
    private PlateProductMapper tPlateProductMapper;

    @Override
    public List<PlateProduct> seleteByPlateId(Integer plateId) {
        return tPlateProductMapper.seleteByPlateId(plateId);
    }

    @Override
    public int deleteByPlateId(Integer plateId) {
        return tPlateProductMapper.deleteByPlateId(plateId);
    }

    @Override
    public int insertByproductIds(PlateProduct plateProduct) {
        return tPlateProductMapper.insertByproductIds(plateProduct);
    }

	@Override
	public int deleteByPlateIdAndProduct(Integer plateId, Long productId) {
		Condition condition = new Condition(PlateProduct.class);
		condition.createCriteria().andEqualTo("plateId",plateId).andEqualTo("productId",productId);
		return tPlateProductMapper.deleteByCondition(condition);
	}
}
