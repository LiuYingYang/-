package com.medusa.basemall.shop.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Created by wx on 2018/05/26.
 */
@Table(name = "t_plate_product")
public class PlateProduct {

    @ApiModelProperty(value = "模快商品id")
    @Id
    @Column(name = "plate_product_id")
    private Integer plateProductId;

    @ApiModelProperty(value = "板块id")
    @Column(name = "plate_id")
    private Integer plateId;

    @ApiModelProperty(value = "商品id")
    @Column(name = "product_id")
    private Long productId;

    @ApiModelProperty(value = "创建时间")
    @Column(name = "create_time")
    private String createTime;

    @ApiModelProperty(value = "模板id")
    @Column(name = "appmodel_id")
    private String appmodelId;

    /**
     * 获取编号
     *
     * @return plate_product_id - 编号
     */
    public Integer getPlateProductId() {
        return plateProductId;
    }

    /**
     * 设置编号
     *
     * @param plateProductId 编号
     */
    public void setPlateProductId(Integer plateProductId) {
        this.plateProductId = plateProductId;
    }

    /**
     * 获取板块ID
     *
     * @return plate_id - 板块ID
     */
    public Integer getPlateId() {
        return plateId;
    }

    /**
     * 设置板块ID
     *
     * @param plateId 板块ID
     */
    public void setPlateId(Integer plateId) {
        this.plateId = plateId;
    }

    /**
     * 获取产品ID
     *
     * @return product_id - 产品ID
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * 设置产品ID
     *
     * @param productId 产品ID
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取模板ID
     *
     * @return appmodel_id - 模板ID
     */
    public String getAppmodelId() {
        return appmodelId;
    }

    /**
     * 设置模板ID
     *
     * @param appmodelId 模板ID
     */
    public void setAppmodelId(String appmodelId) {
        this.appmodelId = appmodelId;
    }

    @Override
    public String toString() {
        return "PlateProduct{" +
                "plateProductId=" + plateProductId +
                ", plateId=" + plateId +
                ", productId=" + productId +
                ", createTime='" + createTime + '\'' +
                ", appmodelId='" + appmodelId + '\'' +
                '}';
    }
}