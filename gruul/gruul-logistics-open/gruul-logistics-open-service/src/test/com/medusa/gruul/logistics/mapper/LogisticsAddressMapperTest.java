package com.medusa.gruul.logistics.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.test.autoconfigure.MybatisPlusTest;
import com.medusa.gruul.logistics.model.param.LogisticsAddressParam;
import com.medusa.gruul.logistics.model.vo.LogisticsAddressVo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.List;

@RunWith(SpringRunner.class)
@MybatisPlusTest
@ActiveProfiles("unit-test")
public class LogisticsAddressMapperTest {

    @Resource
    private LogisticsAddressMapper logisticsAddressMapper;

    @Test
    public void queryLogisticsAddressList() {
        LogisticsAddressParam logisticsAddressParam = new LogisticsAddressParam();
        IPage<LogisticsAddressVo> page = new Page<>(logisticsAddressParam.getCurrent(), logisticsAddressParam.getSize());
        List<LogisticsAddressVo> couponVos = logisticsAddressMapper.queryLogisticsAddressList(page, logisticsAddressParam);
        Assert.assertNotNull(couponVos);
        System.out.println(couponVos.toString());
    }

    @Test
    public void queryAllLogisticsAddressList() {
        List<LogisticsAddressVo> couponVos = logisticsAddressMapper.queryAllLogisticsAddressList();
        Assert.assertNotNull(couponVos);
        System.out.println(couponVos.toString());
    }

    @Test
    public void queryDefaultAddress() {
        Integer type = 1;
        LogisticsAddressVo logisticsAddressVo = logisticsAddressMapper.queryDefaultAddress(type);
        Assert.assertNotNull(logisticsAddressVo);
        System.out.println(logisticsAddressVo.toString());
    }
}