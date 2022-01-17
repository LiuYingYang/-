package com.medusa.gruul.sms.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.medusa.gruul.sms.model.entity.TSmsTemplateEntity;

import java.util.List;

/**
 * t_sms_template表
 *
 * @author system
 *
 */
public interface TSmsTemplateEntityMapper extends BaseMapper<TSmsTemplateEntity> {

   /**
    * 条件查询
    * @param record
    * @return List<TSmsTemplateEntity>
    */
   List<TSmsTemplateEntity> searchByEntity(TSmsTemplateEntity record);
}