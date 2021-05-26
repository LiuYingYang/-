package com.medusa.basemall.shop.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Created by wx on 2018/06/04.
 */
@Table(name = "t_firstpage_classify")
public class FirstpageClassify {
    @ApiModelProperty(value = "分类导航id")
    @Id
    @Column(name = "classify_id")
    private Integer classifyId;

    @ApiModelProperty(value = "分类名称")
    @Column(name = "classify_name")
    private String classifyName;

    @ApiModelProperty(value = "分类图片")
    @Column(name = "classify_img")
    private String classifyImg;

    @ApiModelProperty(value = "链接类型(0-跳转到商品，1-跳转到公告，2-跳转到发现)")
    @Column(name = "link_type")
    private Integer linkType;

    @ApiModelProperty(value = "商品Id")
    @Column(name = "product_id")
    private Long productId;

    @ApiModelProperty(value = "链接名称")
    @Column(name = "target_name")
    private String targetName;

    @ApiModelProperty(value = "商品分类id")
    @Column(name = "category_id")
    private Long categoryId;

    @ApiModelProperty(value = "公告Id")
    @Column(name = "notice_id")
    private Integer noticeId;

    @ApiModelProperty(value = "文章Id")
    @Column(name = "article_id")
    private String articleId;

    @ApiModelProperty(value = "模板Id")
    @Column(name = "appmodel_id")
    private String appmodelId;

    @ApiModelProperty(value = "排序值")
    private Integer sort;

    @ApiModelProperty(value = "跳转地址")
    @Column(name = "target_url")
    private String targetUrl;

    /**
     * @return classify_id
     */
    public Integer getClassifyId() {
        return classifyId;
    }

    /**
     * @param classifyId
     */
    public void setClassifyId(Integer classifyId) {
        this.classifyId = classifyId;
    }

    /**
     * 获取分类名称
     *
     * @return classify_name - 分类名称
     */
    public String getClassifyName() {
        return classifyName;
    }

    /**
     * 设置分类名称
     *
     * @param classifyName 分类名称
     */
    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    /**
     * 获取分类图片
     *
     * @return classify_img - 分类图片
     */
    public String getClassifyImg() {
        return classifyImg;
    }

    /**
     * 设置分类图片
     *
     * @param classifyImg 分类图片
     */
    public void setClassifyImg(String classifyImg) {
        this.classifyImg = classifyImg;
    }

    /**
     * 获取链接类型
     *
     * @return link_type - 链接类型
     */
    public Integer getLinkType() {
        return linkType;
    }

    /**
     * 设置链接类型
     *
     * @param linkType 链接类型
     */
    public void setLinkType(Integer linkType) {
        this.linkType = linkType;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * 获取公告Id
     *
     * @return notice_id - 公告Id
     */
    public Integer getNoticeId() {
        return noticeId;
    }

    /**
     * 设置公告Id
     *
     * @param noticeId 公告Id
     */
    public void setNoticeId(Integer noticeId) {
        this.noticeId = noticeId;
    }

    /**
     * 获取文章Id
     *
     * @return article_id - 文章Id
     */
    public String getArticleId() {
        return articleId;
    }

    /**
     * 设置文章Id
     *
     * @param articleId 文章Id
     */
    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    /**
     * 获取模板Id
     *
     * @return appmodel_id - 模板Id
     */
    public String getAppmodelId() {
        return appmodelId;
    }

    /**
     * 设置模板Id
     *
     * @param appmodelId 模板Id
     */
    public void setAppmodelId(String appmodelId) {
        this.appmodelId = appmodelId;
    }

    /**
     * 获取排序
     *
     * @return sort - 排序
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 设置排序
     *
     * @param sort 排序
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    @Override
    public String toString() {
        return "FirstpageClassify{" +
                "classifyId=" + classifyId +
                ", classifyName='" + classifyName + '\'' +
                ", classifyImg='" + classifyImg + '\'' +
                ", linkType=" + linkType +
                ", productId=" + productId +
                ", categoryId=" + categoryId +
                ", noticeId=" + noticeId +
                ", articleId='" + articleId + '\'' +
                ", appmodelId='" + appmodelId + '\'' +
                ", sort=" + sort +
                ", targetUrl='" + targetUrl + '\'' +
                '}';
    }
}