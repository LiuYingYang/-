package com.medusa.basemall.order.entity;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;


/**
 * @author Created by wx on 2018/09/13.
 */
public class CommonlyUsed {

    @ApiModelProperty()
    @Id
    private String commonlyUsedId;

    @ApiModelProperty(value = "模板id")
    private String appmodelId;

    @ApiModelProperty(value = "识别字符串")
    private String commonlyUseds;

    public String getCommonlyUsedId() {
        return commonlyUsedId;
    }

    public void setCommonlyUsedId(String commonlyUsedId) {
        this.commonlyUsedId = commonlyUsedId;
    }

    public String getAppmodelId() {
        return appmodelId;
    }

    public void setAppmodelId(String appmodelId) {
        this.appmodelId = appmodelId;
    }

    public String getCommonlyUseds() {
        return commonlyUseds;
    }

    public void setCommonlyUseds(String commonlyUseds) {
        this.commonlyUseds = commonlyUseds;
    }
}
