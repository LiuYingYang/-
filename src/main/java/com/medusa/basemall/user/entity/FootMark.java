package com.medusa.basemall.user.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author whh
 */
@Data
@Document(collection = "foot_mark")
public class FootMark {

    /**
     * 足迹id
     */
    @Id
    @Field("footmark_id")
    @ApiModelProperty(value = "足迹id"  )
    private String footmarkId;
    /**
     * 更新时间
     */
    @Field("update_time")
    @ApiModelProperty(value = "更新时间"  )
    private String updateTime;
    /**
     * 用户id:wxuserId
     */
    @Field("wxuser_id")
    @ApiModelProperty(value = "用户id"  )
    private long wxuserId;
    /**
     * 商品图片地址
     */
    @Field("img_url")
    @ApiModelProperty(value = "商品图片地址"  )
    private String imgUrl;
    /**
     * 商品id
     */
    @Field("product_id")
    @ApiModelProperty(value = "商品id"  )
    private Long productId;
    /**
     * 商品名称
     */
    @Field("product_name")
    @ApiModelProperty(value = "商品名称"  )
    private String productName;
    /**
     * 备注
     */
    @Field("remark")
    @ApiModelProperty(value = "remark"  )
    private String remark;
    /**
     * 商品实际价格
     */
    @Field("max_price")
    @ApiModelProperty(value = "商品实际价格" )
    private double maxPrice;
    /**
     * 商品当前价格
     */
    @Field("min_price")
    @ApiModelProperty(value = "商品当前价格" )
    private double minPrice;

    @Field("appmodel_id")
    @ApiModelProperty(value = "商家唯一id"  )
    private String appmodelId;

    /**
     * 活动信息
     */
    @Field("product_type_list")
    @ApiModelProperty(value = "活动信息")
    private String productTypeList;

    @Field("state")
    @ApiModelProperty(value = "状态 0-上架 1-下架 2-售罄")
    private Integer state;


}
