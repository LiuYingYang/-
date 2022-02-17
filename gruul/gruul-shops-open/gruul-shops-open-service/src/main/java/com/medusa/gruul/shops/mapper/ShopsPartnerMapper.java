package com.medusa.gruul.shops.mapper;

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.medusa.gruul.shops.api.entity.ShopsPartner;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author create by zq
 * @date created in 2019/11/15
 */
@Repository
public interface ShopsPartnerMapper extends BaseMapper<ShopsPartner> {

    /**
     * 获取总店
     *
     * @param platformId 平台id
     * @return shops
     */

    ShopsPartner selectByPlatformId(@Param("platformId") Long platformId);

    /**
     * 根据平台用户id获取店铺信息
     *
     * @return com.medusa.gruul.shops.api.entity.ShopsPartner
     */

    ShopsPartner selectByTenantIdAndPartnerIdIsNull();

}
