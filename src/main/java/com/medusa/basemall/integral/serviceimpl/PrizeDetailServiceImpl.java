package com.medusa.basemall.integral.serviceimpl;

import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.integral.dao.PrizeDetailMapper;
import com.medusa.basemall.integral.dao.PrizeMapper;
import com.medusa.basemall.integral.entity.Prize;
import com.medusa.basemall.integral.entity.PrizeDetail;
import com.medusa.basemall.integral.entity.PrizeRule;
import com.medusa.basemall.integral.service.PrizeDetailService;
import com.medusa.basemall.integral.service.PrizeRuleService;
import com.medusa.basemall.integral.vo.PrizeDetailVo;
import com.medusa.basemall.promotion.dao.CouponMapper;
import com.medusa.basemall.promotion.entity.Coupon;
import com.medusa.basemall.utils.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author Created by wx on 2018/06/06.
 */
@Service
public class PrizeDetailServiceImpl extends AbstractService<PrizeDetail> implements PrizeDetailService {
    @Resource
    private PrizeDetailMapper tPrizeDetailMapper;

    @Resource
    private PrizeMapper prizeMapper;

    @Resource
    private CouponMapper couponMapper;
	@Resource
	private PrizeRuleService prizeRuleService;

    @Override
    public List<PrizeDetail> seletePrizeDetailByWxuserId(PrizeDetailVo prizeDetailVo) {
        List<PrizeDetail> prizeDetails = tPrizeDetailMapper.seletePrizeDetailByWxuserId(prizeDetailVo);
        if (prizeDetails.size() > 0) {
            for (PrizeDetail prizeDetail : prizeDetails) {
                if (prizeDetail.getType() == 1) {
                    Prize prize = prizeMapper.selectByPrimaryKey(prizeDetail.getPrizeId());
                    prizeDetail.setPrize(prize);
                    if (prize.getPrizeType() == 2) {
                        Coupon coupon = couponMapper.selectByPrimaryKey(prize.getCouponId());
                        prize.setCoupon(coupon);
                    }
                }
            }
        }
        return prizeDetails;
    }

	@Override
	public Integer addIntegral(int type, String appmodelId, Long wxuserId) {
		PrizeDetail prizeDetail = new PrizeDetail();
		prizeDetail.setCreateTime(TimeUtil.getNowTime());
		prizeDetail.setAppmodelId(appmodelId);
		prizeDetail.setWxuserId(wxuserId);
		prizeDetail.setDeleteState(false);
		prizeDetail.setType(type);
		PrizeRule prizeRule = prizeRuleService.findByAppmodelId(appmodelId);
		prizeDetail.setIntegral(prizeRule.getTypeTwo());
		tPrizeDetailMapper.insert(prizeDetail);
		return prizeRule.getTypeTwo();
	}


}
