package com.medusa.gruul.goods.service.api.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.medusa.gruul.common.core.constant.CommonConstants;
import com.medusa.gruul.common.core.exception.ServiceException;
import com.medusa.gruul.common.core.util.StringUtil;
import com.medusa.gruul.common.core.util.SystemCode;
import com.medusa.gruul.goods.api.entity.Product;
import com.medusa.gruul.goods.api.entity.SaleMode;
import com.medusa.gruul.goods.api.model.param.api.ApiProductParam;
import com.medusa.gruul.goods.api.model.vo.api.ApiAliveProductVo;
import com.medusa.gruul.goods.api.model.vo.api.ApiProductVo;
import com.medusa.gruul.goods.api.model.vo.api.ApiShowCategoryProductVo;
import com.medusa.gruul.goods.api.model.vo.api.ApiShowCategoryVo;
import com.medusa.gruul.goods.mapper.api.*;
import com.medusa.gruul.goods.service.api.IApiProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 小程序商品信息 服务实现类
 *
 * @author kyl
 * @since 2019-10-06
 */
@Service
public class ApiProductServiceImpl extends ServiceImpl<ApiProductMapper, Product> implements IApiProductService {

    @Autowired
    private ApiProductMapper productMapper;

    @Autowired
    private ApiAliveProductMapper apiAliveProductMapper;

    @Autowired
    private ApiShowCategoryMapper apiShowCategoryMapper;

    @Autowired
    private ApiSaleModeMapper apiSaleModeMapper;


    /**
     * 根据主键id查询商品详情
     *
     * @param id 商品id
     * @return 商品信息
     */
    @Override
    public ApiProductVo getProductById(Long id) {
        ApiProductVo apiProductVo = productMapper.queryByPrimaryKey(id);
        if (BeanUtil.isEmpty(apiProductVo)) {
            throw new ServiceException("商品不存在！", SystemCode.DATA_EXISTED.getCode());
        }
        return apiProductVo;
    }

    /**
     * 商品分页列表
     *
     * @param apiProductParam 商品查询条件
     * @return 分页对象
     */
    @Override
    public IPage<ApiAliveProductVo> getPageList(ApiProductParam apiProductParam) {
        IPage<ApiAliveProductVo> aliveProductVoPage = new Page<>(apiProductParam.getCurrent(), apiProductParam.getSize());
        List<ApiAliveProductVo> aliveProductVos = apiAliveProductMapper.querySuperMarketProductList(aliveProductVoPage, apiProductParam);
        return aliveProductVoPage.setRecords(aliveProductVos);
    }


    /**
     * 商超系统分类列表
     *
     * @param apiProductParam 商品查询条件
     * @return 分页对象
     */
    @Override
    public IPage<ApiAliveProductVo> getSupermarketList(ApiProductParam apiProductParam) {
        IPage<ApiAliveProductVo> aliveProductVoPage = new Page<>(apiProductParam.getCurrent(), apiProductParam.getSize());
        List<ApiAliveProductVo> aliveProductVos = apiAliveProductMapper.querySuperMarketProductList(aliveProductVoPage, apiProductParam);
        return aliveProductVoPage.setRecords(aliveProductVos);
    }

    //=============================================商品组件根据商品集合匹配未删除的商品===================================================

    /**
     * 根据商品数组匹配未删除的商品
     *
     * @param ids
     * @param launchArea
     * @param saleMode
     * @return List<DiscountProductVo>
     */
    @Override
    public List<ApiAliveProductVo> getAliveProductList(Long[] ids, String launchArea, Long saleMode) {
        List<ApiAliveProductVo> aliveProductVos = apiAliveProductMapper.querySaveProductList(Arrays.asList(ids), saleMode);
        return aliveProductVos;
    }


    //=============================================商品分类页组件根据商品分类集合匹配对应分类下的商品===================================================

    /**
     * pc商品分类集合匹配对应分类下的商品
     *
     * @param ids
     * @return List<DiscountProductVo>
     */
    @Override
    public List<ApiShowCategoryProductVo> getAliveProductListGroupByCategory(Long[] ids) {
        List<Long> idList = Arrays.asList(ids);
        List<ApiShowCategoryVo> apiShowCategoryVos = apiShowCategoryMapper.queryAllApiShowCategoryList();
        List<ApiShowCategoryProductVo> apiShowCategoryProductVos = new ArrayList<>(apiShowCategoryVos.size());
        if (CollectionUtil.isNotEmpty(apiShowCategoryVos)) {
            apiShowCategoryVos.stream().forEach(apiShowCategoryVo -> {
                if (idList.contains(apiShowCategoryVo.getId()) && CollectionUtil.isNotEmpty(apiShowCategoryVo.getShowCategoryVos())) {
                    apiShowCategoryVo.getShowCategoryVos().stream().forEach(apiShowCategorySecondVo -> {
                        List<ApiAliveProductVo> aliveProductVos = apiAliveProductMapper.queryShowCategoryProductList(apiShowCategorySecondVo.getId());
                        if (CollectionUtil.isNotEmpty(aliveProductVos)) {
                            ApiShowCategoryProductVo apiShowCategoryProductVo = new ApiShowCategoryProductVo();
                            apiShowCategoryProductVo.setId(apiShowCategorySecondVo.getId());
                            apiShowCategoryProductVo.setName(apiShowCategorySecondVo.getName());
                            apiShowCategoryProductVo.setApiAliveProductVos(aliveProductVos);
                            apiShowCategoryProductVos.add(apiShowCategoryProductVo);
                        }
                    });
                }
            });
        }
        return apiShowCategoryProductVos;
    }

    /**
     * 小程序商品分类集合匹配对应分类下的商品
     *
     * @param ids
     * @param saleMode
     * @return List<DiscountProductVo>
     */
    @Override
    public List<ApiShowCategoryProductVo> getAliveProductListByCategory(Long[] ids, Long saleMode) {
        SaleMode saleModeSearch = apiSaleModeMapper.selectOne(new QueryWrapper<SaleMode>().eq("id", saleMode));
        if (BeanUtil.isEmpty(saleModeSearch)) {
            return new ArrayList<>(CommonConstants.NUMBER_ZERO);
        }
        List<Long> idList = Arrays.asList(ids);
        List<ApiShowCategoryVo> apiShowCategoryVos;
        apiShowCategoryVos = apiShowCategoryMapper.queryApiSupermarketShowCategoryList(saleMode);
        List<ApiShowCategoryProductVo> apiShowCategoryProductVos = new ArrayList<>(apiShowCategoryVos.size());
        if (CollectionUtil.isNotEmpty(apiShowCategoryVos)) {
            apiShowCategoryVos.stream().forEach(apiShowCategoryVo -> {
                //判断是否有二级分类并且在过滤的一级分类下
                if (idList.contains(apiShowCategoryVo.getId()) && CollectionUtil.isNotEmpty(apiShowCategoryVo.getShowCategoryVos())) {
                    apiShowCategoryVo.getShowCategoryVos().stream().forEach(apiShowCategorySecondVo -> {
                        List<ApiAliveProductVo> aliveProductVos = apiAliveProductMapper.queryShowCategoryProductListBySaleMode(apiShowCategorySecondVo.getId(), saleMode);
                        if (CollectionUtil.isNotEmpty(aliveProductVos)) {
                            ApiShowCategoryProductVo apiShowCategoryProductVo = new ApiShowCategoryProductVo();
                            apiShowCategoryProductVo.setId(apiShowCategorySecondVo.getId());
                            apiShowCategoryProductVo.setName(apiShowCategorySecondVo.getName());
                            apiShowCategoryProductVo.setApiAliveProductVos(aliveProductVos);
                            apiShowCategoryProductVos.add(apiShowCategoryProductVo);
                        }
                    });
                }
            });
        }
        return apiShowCategoryProductVos;

    }
}
