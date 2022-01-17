package com.medusa.gruul.sms.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.medusa.gruul.sms.model.entity.TSmsProviderEntity;

import java.util.List;

/**
 * t_sms_provider表
 *
 * @author system
 *
 */
public interface TSmsProviderEntityMapper extends BaseMapper<TSmsProviderEntity> {

   /**
    * 条件查询
    * @param record
    * @return List<TSmsProviderEntity>
    */
   List<TSmsProviderEntity> searchByEntity(TSmsProviderEntity record);
}