package com.medusa.basemall.product.serviceimpl;

import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.product.dao.LogisticDistrobutionMapper;
import com.medusa.basemall.product.entity.LogisticDistrobution;
import com.medusa.basemall.product.service.LogisticDistrobutionService;
import com.medusa.basemall.utils.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by psy on 2018/05/24.
 * 需要事物时添加  @Transactional
 */

@Service
public class LogisticDistrobutionServiceImpl extends AbstractService<LogisticDistrobution>
		implements LogisticDistrobutionService {
	@Resource
	private LogisticDistrobutionMapper logisticDistrobutionMapper;


	/**
	 * 添加或更新配送
	 *
	 * @param distrobution
	 * @return
	 */
	@Override
	public int saveOrUpDate(LogisticDistrobution distrobution) {
		//查询是否有配送，一个小程序只有一个商家配送
			LogisticDistrobution find = new LogisticDistrobution();
		find.setAppmodelId(distrobution.getAppmodelId());
		List<LogisticDistrobution> list = logisticDistrobutionMapper.select(find);
		int rs = 0;
		//根据ID是否为-1判断是添加还是更新
		if (distrobution.getDistrobutionId() == -1 && list.size() < 1) {
			distrobution.setCreateTime(TimeUtil.getNowTime());
			distrobution.setDistrobutionId(null);
			rs = logisticDistrobutionMapper.insertSelective(distrobution);
		} else if (distrobution.getDistrobutionId() > 1 && list.size() == 1) {
			distrobution.setDistrobutionId(list.get(0).getDistrobutionId());
			rs = logisticDistrobutionMapper.updateByPrimaryKeySelective(distrobution);
		}
		return rs;
	}

	@Override
	public LogisticDistrobution findDistrobution(String appmodelId) {
		LogisticDistrobution distrobution = new LogisticDistrobution();
		distrobution.setAppmodelId(appmodelId);
		return logisticDistrobutionMapper.selectOne(distrobution);
	}
}
