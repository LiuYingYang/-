package com.medusa.basemall.order.vo;

import com.medusa.basemall.order.entity.Buycar;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class BuyCarsVo {

    @ApiModelProperty(value = "有效的购物车商品")
    List<Buycar> available;

    @ApiModelProperty(value = "无效的购物车商品")
    List<Buycar> notAvailable;

    public List<Buycar> getAvailable() {
        return available;
    }

    public void setAvailable(List<Buycar> available) {
        this.available = available;
    }

    public List<Buycar> getNotAvailable() {
        return notAvailable;
    }

    public void setNotAvailable(List<Buycar> notAvailable) {
        this.notAvailable = notAvailable;
    }
}
