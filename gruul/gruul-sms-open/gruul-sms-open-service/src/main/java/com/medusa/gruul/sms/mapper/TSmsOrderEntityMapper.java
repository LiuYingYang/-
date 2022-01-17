package com.medusa.gruul.sms.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.medusa.gruul.sms.model.dto.SmsDoSendDto;
import com.medusa.gruul.sms.model.entity.TSmsOrderEntity;

import java.util.List;

/**
 * t_sms_order表
 *
 * @author system
 *
 */
public interface TSmsOrderEntityMapper extends BaseMapper<TSmsOrderEntity> {

    /**
     * 条件查询
     * @param smsSendStatus
     * @return List<TSmsOrderEntity>
     */
    List<TSmsOrderEntity> doListWaitSendOrder(int smsSendStatus);

    /**
     * 条件查询
     * @param id
     * @return SmsDoSendDto
     */
    SmsDoSendDto getSendSmsCfg(Long id);
}