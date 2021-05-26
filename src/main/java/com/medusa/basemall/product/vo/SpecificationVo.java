package com.medusa.basemall.product.vo;

import com.medusa.basemall.product.entity.Specification;
import com.medusa.basemall.product.entity.SpecificationClass;

import javax.validation.Valid;
import java.util.List;

/**
 * 规格信息
 */
public class SpecificationVo extends SpecificationClass {

	@Valid
    private List<Specification> specificationList;

    public List<Specification> getSpecificationList() {
        return specificationList;
    }

    public void setSpecificationList(List<Specification> specificationList) {
        this.specificationList = specificationList;
    }
}
