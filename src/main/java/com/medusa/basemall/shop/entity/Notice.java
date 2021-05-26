package com.medusa.basemall.shop.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Created by wx on 2018/05/24.
 */
@Table(name = "t_notice")
public class Notice {

    @ApiModelProperty(value = "公告id")
    @Id
    @Column(name = "notice_id")
    private Integer noticeId;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "顶部图片")
    @Column(name = "notice_img")
    private String noticeImg;

    @ApiModelProperty(value = "发布公告时间(更新后为更新时间)")
    @Column(name = "create_time")
    private String createTime;

    @ApiModelProperty(value = "商品id")
    @Column(name = "product_id")
    private Long productId;

    @ApiModelProperty(value = "链接名称")
    @Column(name = "target_name")
    private String targetName;

    @ApiModelProperty(value = "模板id")
    @Column(name = "appmodel_id")
    private String appmodelId;

    @ApiModelProperty(value = "跳转地址")
    @Column(name = "target_url")
    private String targetUrl;

    @ApiModelProperty(value = "公告内容")
    @Column(name = "notice_body")
    private String noticeBody;


/*    @ApiModelProperty(value = "排序值")
    private Integer sort;

    @ApiModelProperty(value = "展示开始时间")
    private String startTime;

    @ApiModelProperty(value = "展示结束时间")
    private String endTime;*/

    /**
     * 获取编号
     *
     * @return notice_id - 编号
     */
    public Integer getNoticeId() {
        return noticeId;
    }

    /**
     * 设置编号
     *
     * @param noticeId 编号
     */
    public void setNoticeId(Integer noticeId) {
        this.noticeId = noticeId;
    }

    /**
     * 获取标题
     *
     * @return title - 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取顶部图片
     *
     * @return notice_img - 顶部图片
     */
    public String getNoticeImg() {
        return noticeImg;
    }

    /**
     * 设置顶部图片
     *
     * @param noticeImg 顶部图片
     */
    public void setNoticeImg(String noticeImg) {
        this.noticeImg = noticeImg;
    }

    /**
     * 获取发布公告时间
     *
     * @return create_time - 发布公告时间
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 设置发布公告时间
     *
     * @param createTime 发布公告时间
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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

    /**
     * 获取跳转地址
     *
     * @return target_url - 跳转地址
     */
    public String getTargetUrl() {
        return targetUrl;
    }

    /**
     * 设置跳转地址
     *
     * @param targetUrl 跳转地址
     */
    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    /**
     * 获取公告内容
     *
     * @return notice_body - 公告内容
     */
    public String getNoticeBody() {
        return noticeBody;
    }

    /**
     * 设置公告内容
     *
     * @param noticeBody 公告内容
     */
    public void setNoticeBody(String noticeBody) {
        this.noticeBody = noticeBody;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

/*       public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }*/

    @Override
    public String toString() {
        return "Notice{" +
                "noticeId=" + noticeId +
                ", title='" + title + '\'' +
                ", noticeImg='" + noticeImg + '\'' +
                ", createTime='" + createTime + '\'' +
                ", productId=" + productId +
                ", appmodelId='" + appmodelId + '\'' +
                ", targetUrl='" + targetUrl + '\'' +
                ", noticeBody='" + noticeBody + '\'' +
                '}';
    }
}