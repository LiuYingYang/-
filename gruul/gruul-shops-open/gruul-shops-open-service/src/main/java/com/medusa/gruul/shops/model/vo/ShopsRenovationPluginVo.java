package com.medusa.gruul.shops.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * @author create by zq
 * @date created in 2019/11/15
 */
@Data
@ApiModel(value = "ShopsRenovationPluginVo 实体", description = "店铺装修模板全局控件实体 vo")
public class ShopsRenovationPluginVo implements Serializable {

    private static final long serialVersionUID = 1L;


    private Long id;


    /**
     * 所属模板ID
     */
    @ApiModelProperty(value = "所属模板ID")
    private Long templateId;


    /**
     * 控件JSON
     */
    @ApiModelProperty(value = "控件JSON")
    private String pluginProperties;


    /**
     * 逻辑删除标识  0正常 1已删除
     */
    @ApiModelProperty(value = "逻辑删除标识  0正常 1已删除")
    private String isDeleted;


    /**
     * 操作人id
     */
    @ApiModelProperty(value = "操作人id")
    private String operatorId;


    /**
     * 操作人name
     */
    @ApiModelProperty(value = "操作人name")
    private String operatorName;


    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;


    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;


    /**
     * 备用字段
     */
    @ApiModelProperty(value = "备用字段")
    private String spare;


    /**
     * 控件名称中文
     */
    @ApiModelProperty(value = "控件名称中文")
    private String pluginNameCn;

    /**
     * 控件名称英文
     */
    @ApiModelProperty(value = "控件名称英文")
    private String pluginNameEn;


    /**
     * 是否选中 0否 1是
     */
    @ApiModelProperty(value = "是否选中 0否 1是")
    private String isSelection;


    /**
     * 是否允许取消 0否 1是
     */
    @ApiModelProperty(value = "是否允许取消 0否 1是")
    private String isMandatory;

}