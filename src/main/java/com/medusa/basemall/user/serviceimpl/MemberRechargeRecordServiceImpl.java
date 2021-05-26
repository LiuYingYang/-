package com.medusa.basemall.user.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.medusa.basemall.activemq.ActiveMqClient;
import com.medusa.basemall.configurer.WxServiceUtils;
import com.medusa.basemall.constant.ActiviMqQueueName;
import com.medusa.basemall.constant.SendTemplatMessageType;
import com.medusa.basemall.constant.Url;
import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.core.ServiceException;
import com.medusa.basemall.order.service.BalanceDetaiService;
import com.medusa.basemall.user.dao.MemberActiveRechargeMapper;
import com.medusa.basemall.user.dao.MemberMapper;
import com.medusa.basemall.user.dao.MemberRechargeRecordMapper;
import com.medusa.basemall.user.dao.WxuserMapper;
import com.medusa.basemall.user.entity.Member;
import com.medusa.basemall.user.entity.MemberActiveRecharge;
import com.medusa.basemall.user.entity.MemberRechargeRecord;
import com.medusa.basemall.user.entity.Wxuser;
import com.medusa.basemall.user.service.MemberRechargeRecordService;
import com.medusa.basemall.utils.IdGenerateUtils;
import com.medusa.basemall.utils.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * @author medusa
 * @date 2018/05/30
 * 需要事物时添加  @Transactional
 */

@Service

public class MemberRechargeRecordServiceImpl extends AbstractService<MemberRechargeRecord> implements MemberRechargeRecordService {

    @Resource
    private MemberRechargeRecordMapper tRechargeRecordMapper;

    @Resource
    private MemberActiveRechargeMapper tActiveRechargeMapper;

    @Resource
    private WxServiceUtils wxServiceUtils;

    @Resource
    private WxuserMapper tWxuserMapper;

    @Resource
    private MemberMapper tMemberMapper;
    @Resource
    private BalanceDetaiService balanceDetaiService;
	@Resource
	private ActiveMqClient activeMqClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(MemberRechargeRecordServiceImpl.class);



    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result payRechargeActive(Long wxuserId, Long memberId, double price, Integer activeRechargeId, String appmodelId, int type, String userIp) {
        MemberRechargeRecord record = new MemberRechargeRecord();
        record.setState(0);
        record.setCreateTime(TimeUtil.getNowTime());
        record.setOrderNumber(IdGenerateUtils.getOrderNum());
        record.setMemberId(memberId);
        record.setPrice(price);
        record.setActiveRechargeId(activeRechargeId);
        record.setAppmodelId(appmodelId);
        record.setType(type);
        if (tRechargeRecordMapper.insertSelective(record) > 0) {
            Wxuser wxuser = tWxuserMapper.selectByPrimaryKey(wxuserId);
            try {
                Map<String, String> map = wxServiceUtils.wxJsapiPayRequest("会员充值活动", record.getOrderNumber(),
		                String.valueOf(price), wxuser.getOpenId(), Url.MEMBER_RECHARGE_URL,
                       appmodelId);
                return ResultGenerator.genSuccessResult(map);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ResultGenerator.genFailResult("会员充值失败");
    }


    @Override
    public String payRechargeActiveNotify(String xmlResult) {
        LOGGER.info("订单回调处理 --------->" + xmlResult);
        try {
            WxPayOrderNotifyResult wxPayOrderNotifyResult = WxPayOrderNotifyResult.fromXML(xmlResult);
            if ("SUCCESS".equalsIgnoreCase(wxPayOrderNotifyResult.getReturnCode())) {
	            MemberRechargeRecord record = new MemberRechargeRecord();
                record.setOrderNumber(wxPayOrderNotifyResult.getOutTradeNo());
                record = tRechargeRecordMapper.selectOne(record);
                if (record.getState().equals(0)) {
                    record.setState(1);
                    record.setPayTime(TimeUtil.getNowTime());
                    if (tRechargeRecordMapper.updateByPrimaryKeySelective(record) > 0) {
                        Integer activeRechargeId = record.getActiveRechargeId();
                        Member member = tMemberMapper.selectByPrimaryKey(record.getMemberId());
                        member.setSupplyBonus(member.getSupplyBonus() + record.getPrice());
                        //查看是否参加活动
                        if (null != activeRechargeId && activeRechargeId > 0) {
                            MemberActiveRecharge activeRecharge = new MemberActiveRecharge();
                            activeRecharge.setActiveRechargeId(activeRechargeId);
                            activeRecharge = tActiveRechargeMapper.selectOne(activeRecharge);
                            //查看金额是否达标
                            if (activeRecharge.getMaxPrice() <= record.getPrice()) {
                                member.setSupplyBonus(member.getSupplyBonus() + activeRecharge.getSendPrice());
	                            balanceDetaiService.addRechargeActive(record,activeRecharge);
                            }
                        }else{
	                        balanceDetaiService.addRechargeActive(record,null);
                        }
                        if (tMemberMapper.updateByPrimaryKeySelective(member) == 0) {
                        	throw new ServiceException("会员充值回调更新会员出错......");
                        }
	                    JSONObject jsonObject = new JSONObject();
                        jsonObject.put("rechargeRecordId",record.getRechargeRecordId());
	                    jsonObject.put("messageType",SendTemplatMessageType.BE_RECHARGED_SUCCESSFULLY);
	                    activeMqClient
			                    .send(jsonObject.toJSONString(), ActiviMqQueueName.ORDER_MINIPROGRAM_TEMPLATE_MESSAGE);
	                    return WxPayNotifyResponse.success("处理成功");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw  new ServiceException("会员充值回调任务出错......");
        }
        return WxPayNotifyResponse.success("已处理");
    }

    @Override
    public List<MemberRechargeRecord> findByMemberId(Long memberId) {
	    MemberRechargeRecord record = new MemberRechargeRecord();
        record.setMemberId(memberId);
        return tRechargeRecordMapper.select(record);
    }


}
