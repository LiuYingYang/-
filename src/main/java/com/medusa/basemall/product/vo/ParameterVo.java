package com.medusa.basemall.product.vo;

import com.medusa.basemall.product.entity.Parameter;
import com.medusa.basemall.product.entity.ParameterClass;

import javax.validation.Valid;
import java.util.List;

/**
 * 商品属性
 */

public class ParameterVo extends ParameterClass {

	@Valid
    private List<Parameter> parameterList;

    public List<Parameter> getParameterList() {
        return parameterList;
    }

    public void setParameterList(List<Parameter> parameterList) {
        this.parameterList = parameterList;
    }
}
