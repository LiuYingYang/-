package com.medusa.basemall.article.entity;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;

/**
 * @author Created by wx on 2018/06/07.
 */
public class Article {

    @ApiModelProperty(value = "文章id")
    @Id
    private String articleId;

    @ApiModelProperty(value = "文章封面")
    private String coverUrl;

    @ApiModelProperty(value = "文章标题")
    private String articleTitle;

    @ApiModelProperty(value = "所属分类Id字符串")
    private String categoryIds;

    @ApiModelProperty(value = "视频")
    private String videoUrl;

    @ApiModelProperty(value = "更新时间")
    private String updateTime;

    @ApiModelProperty(value = "文章内容")
    private String centent;

    @ApiModelProperty(value = "点赞数量")
    private Integer laud;

    @ApiModelProperty(value = "排序值")
    private Integer sort;

    @ApiModelProperty(value = "阅读量")
    private Integer lookSum;

    @ApiModelProperty(value = "评论量")
    private Integer discussSum;

    @ApiModelProperty(value = "模板Id")
    private String appmodelId;

    @ApiModelProperty(value = "链接商品Id")
    private Long productId;

    @ApiModelProperty(value = "链接商品名称")
    private String productName;

    @ApiModelProperty(value = "所属分类名称字符串")
    private String categoryNames;

    @ApiModelProperty(value = "当前用户是否点赞状态")
    private Boolean laudOrNot;

/*    @ApiModelProperty(value = "上下线状态:1上线0下线")
    private Boolean state;

    @ApiModelProperty(value = "发布位置:1发布0草稿箱")
    private Boolean shelfState;*/

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(String categoryIds) {
        this.categoryIds = categoryIds;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCentent() {
        return centent;
    }

    public void setCentent(String centent) {
        this.centent = centent;
    }

    public Integer getLaud() {
        return laud;
    }

    public void setLaud(Integer laud) {
        this.laud = laud;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getLookSum() {
        return lookSum;
    }

    public void setLookSum(Integer lookSum) {
        this.lookSum = lookSum;
    }

    public String getAppmodelId() {
        return appmodelId;
    }

    public void setAppmodelId(String appmodelId) {
        this.appmodelId = appmodelId;
    }

    public String getCategoryNames() {
        return categoryNames;
    }

    public void setCategoryNames(String categoryNames) {
        this.categoryNames = categoryNames;
    }

    public Boolean getLaudOrNot() {
        return laudOrNot;
    }

    public void setLaudOrNot(Boolean laudOrNot) {
        this.laudOrNot = laudOrNot;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getDiscussSum() {
        return discussSum;
    }

    public void setDiscussSum(Integer discussSum) {
        this.discussSum = discussSum;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

   /*
    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public Boolean getShelfState() {
        return shelfState;
    }

    public void setShelfState(Boolean shelfState) {
        this.shelfState = shelfState;
    }
*/

    @Override
    public String toString() {
        return "Article{" +
                "articleId='" + articleId + '\'' +
                ", coverUrl='" + coverUrl + '\'' +
                ", articleTitle='" + articleTitle + '\'' +
                ", categoryIds='" + categoryIds + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", centent='" + centent + '\'' +
                ", laud=" + laud +
                ", sort=" + sort +
                ", lookSum=" + lookSum +
                ", appmodelId='" + appmodelId + '\'' +
                ", productId='" + productId + '\'' +
                ", categoryNames='" + categoryNames + '\'' +
                '}';
    }
}
