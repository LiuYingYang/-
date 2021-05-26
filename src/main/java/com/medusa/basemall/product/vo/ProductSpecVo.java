package com.medusa.basemall.product.vo;

import com.medusa.basemall.product.entity.ProductSpec;
import com.medusa.basemall.product.entity.ProductSpecClass;
import com.medusa.basemall.product.entity.ProductSpecItem;
import lombok.Data;

import java.util.List;

/**
 * 微信端查看商品规格
 */
@Data
public class ProductSpecVo {

    private List<ProductSpecClass> productSpecClassList;

    private List<ProductSpec> productSpecList;

    private List<ProductSpecItem> productSpecItemList;

    private List<SpecificationVo> specificationVoList;

}
