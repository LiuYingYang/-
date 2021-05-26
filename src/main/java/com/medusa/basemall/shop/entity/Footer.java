package com.medusa.basemall.shop.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Created by wx on 2018/06/04.
 */
@Table(name = "t_footer")
@Data
public class Footer {
    @ApiModelProperty(value = "导航id")
    @Id
    @Column(name = "footer_id")
    private Integer footerId;

    @ApiModelProperty(value = "模板Id")
    @Column(name = "appmodel_id")
    private String appmodelId;

    @ApiModelProperty(value = "导航名称")
    @Column(name = "footer_name")
    private String footerName;

    @ApiModelProperty(value = "未选中图片")
    @Column(name = "footer_img_no")
    private String footerImgNo;

    @ApiModelProperty(value = "选中图片")
    @Column(name = "footer_img_yes")
    private String footerImgYes;

    @ApiModelProperty(value = "链接")
    @Column(name = "footer_link")
    private String footerLink;

    @ApiModelProperty(value = "开启关闭状态")
    @Column(name = "footer_flag")
    private Boolean footerFlag;


}