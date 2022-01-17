package com.medusa.gruul.sms.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.medusa.gruul.sms.model.entity.TSmsSignEntity;

import java.util.List;

/**
 * t_sms_sign表
 *
 * @author system
 *
 */
public interface TSmsSignEntityMapper extends BaseMapper<TSmsSignEntity> {
   /**
    * 条件查询
    * @param record
    * @return List<TSmsSignEntity>
    */
   List<TSmsSignEntity> searchByEntity(TSmsSignEntity record);
}