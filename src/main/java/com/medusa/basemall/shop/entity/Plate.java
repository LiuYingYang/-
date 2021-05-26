package com.medusa.basemall.shop.entity;

import com.medusa.basemall.product.vo.ProductBackViewVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @author Created by wx on 2018/05/26.
 */
@Table(name = "t_plate")
@Data
public class Plate {

    @ApiModelProperty(value = "模快id")
    @Id
    @Column(name = "plate_id")
    @GeneratedValue(generator = "JDBC")
    private Integer plateId;

    @ApiModelProperty(value = "板块名称")
    @Column(name = "plate_name")
    private String plateName;

    @ApiModelProperty(value = "板块图片")
    @Column(name = "plate_img")
    private String plateImg;

    @ApiModelProperty(value = "创建时间")
    @Column(name = "create_time")
    private String createTime;

    @ApiModelProperty(value = "板块简介")
    private String remark;

    @ApiModelProperty(value = "模板id")
    @Column(name = "appmodel_id")
    private String appmodelId;

    @ApiModelProperty(value = "板块状态")
    @Column(name = "plate_flag")
    private Boolean plateFlag;

    @ApiModelProperty(value = "产品数量")
    @Column(name = "product_num")
    private Integer productNum;

    @ApiModelProperty(value = "排序值")
    private Integer sort;

    /***
     * 用于查找时返回
     */
    @Transient
    @ApiModelProperty(value = "查找模快时返回模快包含的商品")
    private List<ProductBackViewVo> products;

    /***
     * 添加时需要
     */
    @Transient
    @ApiModelProperty(value = "添加模快时模快包含的商品id字符串")
    private String productIds;


}