package com.medusa.basemall.shop.entity;



import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;

import java.util.List;

/**
 * @author Created by wx on 2018/05/25.
 */
public class ShopInfo {

    @ApiModelProperty(value = "店铺信息编号")
	@Id
    private String shopInfoId;

    @ApiModelProperty(value = "店铺名称")
    private String shopName;

    @ApiModelProperty(value = "店铺轮播图")
    private List<TopImg> topImgs;

    @ApiModelProperty(value = "店铺简介")
    private String remark;

    @ApiModelProperty(value = "店铺地址")
    private String shopAddress;

    @ApiModelProperty(value = "联系电话")
    private String telephone;

    @ApiModelProperty(value = "营业时间")
    private String businessTime;

    @ApiModelProperty(value = "店铺logo")
    private String shopLogo;

    @ApiModelProperty(value = "店铺二维码")
    private String shopQrcode;

    @ApiModelProperty(value = "wifi账号")
    private String wifiInfo;

    @ApiModelProperty(value = "wifi密码")
    private String wifiPass;

    @ApiModelProperty(value = "模板id")
    private String appmodelId;

    @ApiModelProperty(value = "微信")
    private String wechatNumber;

    @ApiModelProperty(value = "店铺颜色")
    private String colorStyle;

    public String getShopInfoId() {
        return shopInfoId;
    }

    public void setShopInfoId(String shopInfoId) {
        this.shopInfoId = shopInfoId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getBusinessTime() {
        return businessTime;
    }

    public void setBusinessTime(String businessTime) {
        this.businessTime = businessTime;
    }

    public String getShopLogo() {
        return shopLogo;
    }

    public void setShopLogo(String shopLogo) {
        this.shopLogo = shopLogo;
    }

    public String getShopQrcode() {
        return shopQrcode;
    }

    public void setShopQrcode(String shopQrcode) {
        this.shopQrcode = shopQrcode;
    }

    public String getWifiInfo() {
        return wifiInfo;
    }

    public void setWifiInfo(String wifiInfo) {
        this.wifiInfo = wifiInfo;
    }

    public String getWifiPass() {
        return wifiPass;
    }

    public void setWifiPass(String wifiPass) {
        this.wifiPass = wifiPass;
    }

    public String getAppmodelId() {
        return appmodelId;
    }

    public void setAppmodelId(String appmodelId) {
        this.appmodelId = appmodelId;
    }

    public String getWechatNumber() {
        return wechatNumber;
    }

    public void setWechatNumber(String wechatNumber) {
        this.wechatNumber = wechatNumber;
    }

    public String getColorStyle() {
        return colorStyle;
    }

    public void setColorStyle(String colorStyle) {
        this.colorStyle = colorStyle;
    }

    public List<TopImg> getTopImgs() {
        return topImgs;
    }

    public void setTopImgs(List<TopImg> topImgs) {
        this.topImgs = topImgs;
    }

    @Override
    public String toString() {
        return "ShopInfo{" +
                "shopInfoId=" + shopInfoId +
                ", shopName='" + shopName + '\'' +
                ", topImgs='" + topImgs + '\'' +
                ", remark='" + remark + '\'' +
                ", shopAddress='" + shopAddress + '\'' +
                ", telephone='" + telephone + '\'' +
                ", businessTime='" + businessTime + '\'' +
                ", shopLogo='" + shopLogo + '\'' +
                ", shopQrcode='" + shopQrcode + '\'' +
                ", wifiInfo='" + wifiInfo + '\'' +
                ", wifiPass='" + wifiPass + '\'' +
                ", appmodelId='" + appmodelId + '\'' +
                ", wechatNumber='" + wechatNumber + '\'' +
                ", colorStyle='" + colorStyle + '\'' +
                '}';
    }

}
