package com.medusa.basemall.shop.serviceimpl;

import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.integral.dao.PrizeDetailMapper;
import com.medusa.basemall.integral.entity.PrizeDetail;
import com.medusa.basemall.product.vo.ColumnFlagVo;
import com.medusa.basemall.shop.dao.ColumnFlagMapper;
import com.medusa.basemall.shop.entity.ColumnFlag;
import com.medusa.basemall.shop.service.ColumnFlagService;
import com.medusa.basemall.utils.TimeUtil;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author Created by wx on 2018/06/04.
 */

@Service
public class ColumnFlagServiceImpl extends AbstractService<ColumnFlag> implements ColumnFlagService {
	@Resource
	private ColumnFlagMapper tColumnFlagMapper;

	private ColumnFlag columnFlag;
	@Resource
	private PrizeDetailMapper prizeDetailMapper;

	@Override
	public ColumnFlag findByAppmodelId(String appmodelId) {
		return tColumnFlagMapper.findByAppmodelId(appmodelId);
	}

	@Override
	public Boolean homePageFlag(ColumnFlagVo columnFlagVo) {
		columnFlag = new ColumnFlag();
		columnFlag.setClassifyFlag(columnFlagVo.getFlag());
		Condition condition = new Condition(ColumnFlag.class);
		condition.createCriteria().andEqualTo("appmodelId",columnFlagVo.getAppmodelId());
		return tColumnFlagMapper.updateByConditionSelective(columnFlag,condition) > 0;
	}

	@Override
	public Boolean noticeFlag(ColumnFlagVo columnFlagVo) {
		columnFlag = new ColumnFlag();
		columnFlag.setNoticeFlag(columnFlagVo.getFlag());
		Condition condition = new Condition(ColumnFlag.class);
		condition.createCriteria().andEqualTo("appmodelId",columnFlagVo.getAppmodelId());
		return tColumnFlagMapper.updateByConditionSelective(columnFlag,condition) > 0;

	}

	@Override
	public Boolean articleFlag(ColumnFlagVo columnFlagVo) {
		columnFlag = new ColumnFlag();
		columnFlag.setArticleFlag(columnFlagVo.getFlag());
		Condition condition = new Condition(ColumnFlag.class);
		condition.createCriteria().andEqualTo("appmodelId",columnFlagVo.getAppmodelId());
		return tColumnFlagMapper.updateByConditionSelective(columnFlag,condition) > 0;

	}

	@Override
	public Boolean footFlag(ColumnFlagVo columnFlagVo) {
		columnFlag = new ColumnFlag();
		columnFlag.setFootFlag(columnFlagVo.getFlag());
		Condition condition = new Condition(ColumnFlag.class);
		condition.createCriteria().andEqualTo("appmodelId",columnFlagVo.getAppmodelId());
		return tColumnFlagMapper.updateByConditionSelective(columnFlag,condition) > 0;
	}

	@Override
	public Boolean proxyFlag(ColumnFlagVo columnFlagVo) {
		columnFlag = new ColumnFlag();
		columnFlag.setProxyFlag(columnFlagVo.getFlag());
		Condition condition = new Condition(ColumnFlag.class);
		condition.createCriteria().andEqualTo("appmodelId",columnFlagVo.getAppmodelId());
		return tColumnFlagMapper.updateByConditionSelective(columnFlag,condition) > 0;
	}

	@Override
	public Boolean integralShopFlag(ColumnFlagVo columnFlagVo) {
		columnFlag = new ColumnFlag();
		columnFlag.setShopFlag(columnFlagVo.getFlag());
		Condition condition = new Condition(ColumnFlag.class);
		condition.createCriteria().andEqualTo("appmodelId",columnFlagVo.getAppmodelId());
		List<PrizeDetail> prizeDetails = prizeDetailMapper.selectByAppmodelId(columnFlag.getAppmodelId());
		if (prizeDetails.size() > 0) {
			String now = TimeUtil.getNowTime();
			for (PrizeDetail prizeDetail : prizeDetails) {
				prizeDetail.setCreateTime(now);
				prizeDetailMapper.updateByPrimaryKeySelective(prizeDetail);
			}
		}
		return tColumnFlagMapper.updateByConditionSelective(columnFlag,condition) > 0;
	}

	@Override
	public Boolean memberRechargeFlag(ColumnFlagVo columnFlagVo) {
		columnFlag = new ColumnFlag();
		columnFlag.setMemberRechargeFlag(columnFlagVo.getFlag());
		Condition condition = new Condition(ColumnFlag.class);
		condition.createCriteria().andEqualTo("appmodelId",columnFlagVo.getAppmodelId());
		return tColumnFlagMapper.updateByConditionSelective(columnFlag,condition) > 0;
	}

	@Override
	public Boolean memberFlag(ColumnFlagVo columnFlagVo) {
		columnFlag = new ColumnFlag();
		columnFlag.setMemberFlag(columnFlagVo.getFlag());
		columnFlag.setMemberRechargeFlag(columnFlagVo.getFlag());
		Condition condition = new Condition(ColumnFlag.class);
		condition.createCriteria().andEqualTo("appmodelId",columnFlagVo.getAppmodelId());
		if (tColumnFlagMapper.updateByConditionSelective(columnFlag,condition) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public Boolean serarchFlag(ColumnFlagVo columnFlagVo) {
		columnFlag = new ColumnFlag();
		columnFlag.setSerarchFlag(columnFlagVo.getFlag());
		Condition condition = new Condition(ColumnFlag.class);
		condition.createCriteria().andEqualTo("appmodelId",columnFlagVo.getAppmodelId());
		return tColumnFlagMapper.updateByConditionSelective(columnFlag,condition) > 0;
	}

	@Override
	public Boolean shopinfoFlag(ColumnFlagVo columnFlagVo) {
		columnFlag = new ColumnFlag();
		columnFlag.setShopinfoFlag(columnFlagVo.getFlag());
		Condition condition = new Condition(ColumnFlag.class);
		condition.createCriteria().andEqualTo("appmodelId",columnFlagVo.getAppmodelId());
		return tColumnFlagMapper.updateByConditionSelective(columnFlag,condition) > 0;
	}
}
