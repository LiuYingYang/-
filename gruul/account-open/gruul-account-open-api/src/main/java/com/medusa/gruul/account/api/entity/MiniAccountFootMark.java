package com.medusa.gruul.account.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.medusa.gruul.common.data.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author xiaoq
 *
 * @data 2020/2/27 17:16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_mini_account_foot_mark")
@ApiModel(value = "MiniAccountFootmark对象", description = "用户足迹表")
public class MiniAccountFootMark extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @TableId(value = "foot_mark_id", type = IdType.AUTO)
    private Long footMarkId;

    @ApiModelProperty(value = "用户id")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty(value = "商品id")
    @TableField("product_id")
    private Long productId;


    @ApiModelProperty(value = "商品主图")
    @TableField("product_pic")
    private String productPic;

    @ApiModelProperty(value = "商品名称")
    @TableField("product_name")
    private String productName;

    @ApiModelProperty(value = "商品状态  0-上架 1-下架 2-售罄")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "商品实际售价")
    @TableField("product_price")
    private BigDecimal productPrice;

    @ApiModelProperty(value = "指导价划线价")
    @TableField("original_price")
    private BigDecimal originalPrice;


    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;




}
