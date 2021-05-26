package com.medusa.basemall.promotion.serviceimpl;

import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.promotion.dao.ActivityProductStockMapper;
import com.medusa.basemall.promotion.entity.ActivityProductStock;
import com.medusa.basemall.promotion.service.ActivityProductStockService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author Created by psy on 2018/05/30.
 */
@Service
public class ActivityProductStockServiceImpl extends AbstractService<ActivityProductStock> implements ActivityProductStockService {
    @Resource
    private ActivityProductStockMapper tActivityProductStockMapper;

	@Override
	public List<ActivityProductStock> findByActivityProductIds(List<Long> activityProductIds) {
		Condition condition = new Condition(ActivityProductStock.class);
		condition.createCriteria().andIn("activityProductId",activityProductIds);
		return tActivityProductStockMapper.selectByCondition(condition);
	}

	@Override
	public List<ActivityProductStock> findActivityProductSpec(Long activityProductId) {
		Condition condition = new Condition(ActivityProductStock.class);
		condition.createCriteria().andEqualTo("activityProductId",activityProductId);
		return tActivityProductStockMapper.selectByCondition(condition);
	}

	@Override
	public void updateDeleteState(List<Integer> activityProductStockIds, boolean isDelete) {
		Condition condition = new Condition(ActivityProductStock.class);
		condition.createCriteria().andIn("activityProductStockId",activityProductStockIds);
		ActivityProductStock productStock = new ActivityProductStock();
		productStock.setDeleteState(isDelete);
		tActivityProductStockMapper.updateByConditionSelective(productStock,condition);
	}

	@Override
	public List<ActivityProductStock> findByProductSpecItemId(Long productSpecItemId) {
		return tActivityProductStockMapper.selectByProductSpecItemId(productSpecItemId);
	}

	@Override
	public ActivityProductStock findByActivityIdAndActivityTypeAndActivityProductId(Integer activityId,
			Integer activityType, Long activityProductId) {

		return tActivityProductStockMapper.selectByActivityIdAndActivityTypeAndActivityProductId(activityId,activityType,activityProductId);
	}
}
