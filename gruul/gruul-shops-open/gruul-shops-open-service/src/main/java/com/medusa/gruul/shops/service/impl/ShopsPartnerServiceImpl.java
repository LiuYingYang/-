package com.medusa.gruul.shops.service.impl;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.medusa.gruul.common.core.exception.ServiceException;
import com.medusa.gruul.common.core.util.Result;
import com.medusa.gruul.shops.api.entity.Shops;
import com.medusa.gruul.shops.api.entity.ShopsPartner;
import com.medusa.gruul.shops.mapper.ShopsPartnerMapper;
import com.medusa.gruul.shops.model.vo.ShopsPartnerVo;
import com.medusa.gruul.shops.properties.GlobalConstant;
import com.medusa.gruul.shops.service.ShopsPartnerService;
import com.medusa.gruul.shops.service.ShopsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author create by zq
 * @date created in 2020/01/14
 */
@Service(value = "shopsPartnerServiceImpl")
public class ShopsPartnerServiceImpl extends ServiceImpl<ShopsPartnerMapper, ShopsPartner> implements ShopsPartnerService {

    @Autowired
    private ShopsService shopsService;



    /**
     * 获取店铺list
     *
     * @return list
     */
    @Override
    public Result listShopsPartner() {
        return Result.ok(this.baseMapper.selectList(new QueryWrapper<ShopsPartner>()
                .eq("approval_status", GlobalConstant.STRING_ONE)
                .eq("prohibit_status", GlobalConstant.STRING_ZERO)));
    }


    /**
     * 获取店铺
     *
     * @return shops
     */
    @Override
    public ShopsPartner oneByShopId() {
        return baseMapper.selectOne(new QueryWrapper<ShopsPartner>()
                .eq("approval_status", GlobalConstant.STRING_ONE)
                .eq("prohibit_status", GlobalConstant.STRING_ZERO));
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<ShopsPartner> saveShopsPartner(String pass, String phone, Long platformId) {
        //查询默认账号是否存在存在;存在则不做操作直接返回
        ShopsPartner shopsPartner = this.baseMapper.selectByTenantIdAndPartnerIdIsNull();
        if (ObjectUtil.isNotNull(shopsPartner)) {
            return Result.ok(shopsPartner);
        }
        shopsPartner = new ShopsPartner();
        shopsPartner.setPass(pass);
        shopsPartner.setPhone(phone);
        shopsPartner.setInvitationCode(generateInvitationCode());
        shopsPartner.setPlatformId(platformId);
        shopsPartner.setProhibitStatus(GlobalConstant.STRING_ZERO);
        shopsPartner.setApprovalStatus(GlobalConstant.STRING_ONE);
        int insert = this.baseMapper.insert(shopsPartner);
        if (insert < 1 || null == shopsPartner.getId()) {
            throw new ServiceException(String.format("insert [shopPartner] fail! status is not  : %s, pass : %s, phone : %s", pass, phone));
        }
        Shops shops = new Shops();
        if (!shopsService.save(shops)) {
            throw new ServiceException(String.format("insert [shop] fail! status is not  : %s : %s", shopsPartner.getId()));
        }
        return Result.ok(shopsPartner);
    }

    @Override
    public ShopsPartner getByPlatformId(Long platformId) {
        return this.baseMapper.selectByPlatformId(platformId);
    }

    private String generateInvitationCode() {
        int randomInt = RandomUtil.randomInt(10000, 99999);
        ShopsPartner shopsPartner = this.baseMapper.selectOne(new QueryWrapper<ShopsPartner>().eq("invitation_code", randomInt));
        if (shopsPartner != null) {
            return generateInvitationCode();
        }
        return String.valueOf(randomInt);
    }
}
