package com.medusa.gruul.logistics.mapper;

import com.baomidou.mybatisplus.test.autoconfigure.MybatisPlusTest;
import com.medusa.gruul.common.core.constant.CommonConstants;
import com.medusa.gruul.logistics.api.entity.LogisticsCompany;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@MybatisPlusTest
@ActiveProfiles("unit-test")
public class LogisticsCompanyMapperTest {

    @Resource
    private LogisticsCompanyMapper logisticsCompanyMapper;

    @Test
    public void selectListCompany() {
        Map<String, Object> param = new HashMap<>(CommonConstants.NUMBER_TWO);
        List<LogisticsCompany> logisticsCompanies = logisticsCompanyMapper.selectListCompany(param);
        Assert.assertNotNull(logisticsCompanies);
        System.out.println(logisticsCompanies.toString());
    }

    @Test
    public void selectListCompanyByParam() {
        Map<String, Object> param = new HashMap<>(CommonConstants.NUMBER_TWO);
        param.put("code", "zt");
        LogisticsCompany logisticsCompany = logisticsCompanyMapper.selectListCompanyByParam(param);
        Assert.assertNotNull(logisticsCompany);
        System.out.println(logisticsCompany.toString());
    }
}