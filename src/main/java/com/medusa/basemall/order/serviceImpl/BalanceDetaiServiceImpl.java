package com.medusa.basemall.order.serviceImpl;

import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.order.dao.BalanceDetailRepository;
import com.medusa.basemall.order.entity.BalanceDetail;
import com.medusa.basemall.order.service.BalanceDetaiService;
import com.medusa.basemall.user.entity.MemberActiveRecharge;
import com.medusa.basemall.user.entity.MemberRechargeRecord;
import com.medusa.basemall.utils.TimeUtil;
import com.vip.vjtools.vjkit.time.ClockUtil;
import com.vip.vjtools.vjkit.time.DateFormatUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author whh
 */
@Service
public class BalanceDetaiServiceImpl implements BalanceDetaiService {

	@Resource
	private BalanceDetailRepository balanceDetailRepository;

	@Resource
	private MongoTemplate mongoTemplate;

	@Override
	public Integer addOrderRecord(Long orderId, Long memberId, int countSize, Double payFee) {
		BalanceDetail detail = new BalanceDetail();
		detail.setProductSize(countSize);
		detail.setBalance(payFee + "");
		detail.setBalanceType(0);
		detail.setType(1);
		detail.setOrderId(orderId);
		detail.setCreateTime(TimeUtil.getNowTime());
		detail.setMemberId(memberId);
		if (balanceDetailRepository.insert(detail).getBalanceDetailId() != null) {
			return 1;
		}
		return 0;
	}

	@Override
	public Result selectAll(Long memberId, Integer pageNum, Integer pageSize, String startTime, String endTime) {
		Query query = new Query();
		query.addCriteria(Criteria.where("memberId").is(memberId));
		if (!startTime.equals("")) {
			query.addCriteria((Criteria.where("createTime").gt(startTime)));
		}
		if (!endTime.equals("")) {
			query.addCriteria((Criteria.where("createTime").lt(endTime)));
		}
		long count = mongoTemplate.count(query, BalanceDetail.class);
		query.with(PageRequest.of(pageNum - 1, pageSize, Sort.Direction.DESC, "createTime"));
		Map<String, Object> map = new HashMap<>();
		map.put("list", mongoTemplate.find(query, BalanceDetail.class));
		map.put("totle", count);
		return ResultGenerator.genSuccessResult(map);
	}

	@Override
	public Integer addRechargeActive(MemberRechargeRecord record, MemberActiveRecharge activeRecharge) {
		//添加余额明细
		BalanceDetail detail = new BalanceDetail();
		detail.setMemberId(record.getMemberId());
		detail.setType(2);
		detail.setBalance(record.getPrice() + "");
		detail.setCreateTime(TimeUtil.getNowTime());
		detail.setBalanceType(1);
		if (activeRecharge != null) {
			Double sendPrice = activeRecharge.getSendPrice();
			Double maxPrice = activeRecharge.getMaxPrice();
			detail.setRechargeActiveInfo("参与充值活动 冲" + maxPrice + "送" + sendPrice);
		}
		if (balanceDetailRepository.insert(detail).getBalanceDetailId() != null) {
			return 1;
		}
		return 0;
	}

	@Override
	public Result delete(String balanceDetailId) {
		balanceDetailRepository.deleteById(balanceDetailId);
		return ResultGenerator.genSuccessResult();
	}

	@Override
	public void orderRefoundUpdate(Long orderId, String balance, Integer quantity, Long memberId) {
		BalanceDetail detail = new BalanceDetail();
		detail.setOrderId(orderId);
		detail.setBalanceType(1);
		detail.setBalance(balance);
		detail.setType(3);
		detail.setProductSize(quantity);
		detail.setCreateTime(DateFormatUtil.DEFAULT_ON_SECOND_FORMAT.format(ClockUtil.currentDate()));
		detail.setMemberId(memberId);
		balanceDetailRepository.save(detail);
	}

}
