package com.medusa.basemall.shop.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.Arrays;

/**
 * @author Created by wx on 2018/05/26.
 */
public class PlateVO {

    @ApiModelProperty(value = "商品展示区id字符串")
    private String plateIds;

    @ApiModelProperty(value = "商品展示区id数组")
    private String[] plateId;

    @ApiModelProperty(value = "板块将要改变成的状态")
    private Boolean plateFlag;

    public String getPlateIds() {
        return plateIds;
    }

    public void setPlateIds(String plateIds) {
        this.plateIds = plateIds;
    }

    public Boolean getPlateFlag() {
        return plateFlag;
    }

    public void setPlateFlag(Boolean plateFlag) {
        this.plateFlag = plateFlag;
    }

    public String[] getPlateId() {
        return plateId;
    }

    public void setPlateId(String[] plateId) {
        this.plateId = plateId;
    }

    @Override
    public String toString() {
        return "PlateVO{" +
                "plateIds='" + plateIds + '\'' +
                ", plateId=" + Arrays.toString(plateId) +
                ", plateFlag=" + plateFlag +
                '}';
    }
}
