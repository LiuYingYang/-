package com.medusa.basemall.shop.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Created by wx on 2018/05/25.
 */
@Table(name = "t_poster")
public class Poster {

    @ApiModelProperty(value = "轮播图编号")
    @Id
    @Column(name = "poster_id")
    private Integer posterId;

    @ApiModelProperty(value = " 轮播图跳转类型（0-跳转到商品，1-跳转到公告，2-跳转到发现）")
    @Column(name = "jump_type")
    private Integer jumpType;

    @ApiModelProperty(value = "轮播图图片")
    @Column(name = "poster_img")
    private String posterImg;

    @ApiModelProperty(value = "创建时间")
    @Column(name = "create_time")
    private String createTime;

    @ApiModelProperty(value = "跳转地址")
    @Column(name = "target_url")
    private String targetUrl;

    @ApiModelProperty(value = "模板id")
    @Column(name = "appmodel_id")
    private String appmodelId;

    @ApiModelProperty(value = "排序值")
    @Column(name = "sort")
    private Integer sort;

    @ApiModelProperty(value = "商品id")
    @Column(name = "product_id")
    private Long productId;

    @ApiModelProperty(value = "链接名称")
    @Column(name = "target_name")
    private String targetName;

    @ApiModelProperty(value = "文章id")
    @Column(name = "article_id")
    private String articleId;

    @ApiModelProperty(value = "公告id")
    @Column(name = "notice_id")
    private Integer noticeId;

    @ApiModelProperty(value = "商品分类id")
    @Column(name = "category_id")
    private Long categoryId;

    /**
     * 获取编号
     *
     * @return poster_id - 编号
     */
    public Integer getPosterId() {
        return posterId;
    }

    /**
     * 设置编号
     *
     * @param posterId 编号
     */
    public void setPosterId(Integer posterId) {
        this.posterId = posterId;
    }



    /**
     * 获取跳转类型（0-跳转到商品，1-跳转到图文）
     *
     * @return jump_type - 跳转类型（0-跳转到商品，1-跳转到图文）
     */
    public Integer getJumpType() {
        return jumpType;
    }

    /**
     * 设置跳转类型（0-跳转到商品，1-跳转到图文）
     *
     * @param jumpType 跳转类型（0-跳转到商品，1-跳转到图文）
     */
    public void setJumpType(Integer jumpType) {
        this.jumpType = jumpType;
    }

    /**
     * 获取海报图片
     *
     * @return poster_img - 海报图片
     */
    public String getPosterImg() {
        return posterImg;
    }

    /**
     * 设置海报图片
     *
     * @param posterImg 海报图片
     */
    public void setPosterImg(String posterImg) {
        this.posterImg = posterImg;
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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public Integer getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Integer noticeId) {
        this.noticeId = noticeId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    @Override
    public String toString() {
        return "Poster{" +
                "posterId=" + posterId +
                ", jumpType=" + jumpType +
                ", posterImg='" + posterImg + '\'' +
                ", createTime='" + createTime + '\'' +
                ", targetUrl='" + targetUrl + '\'' +
                ", appmodelId='" + appmodelId + '\'' +
                ", sort=" + sort +
                ", productId=" + productId +
                ", articleId='" + articleId + '\'' +
                ", noticeId=" + noticeId +
                ", categoryId=" + categoryId +
                '}';
    }
}