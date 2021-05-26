package com.medusa.basemall.order.entity;

import com.medusa.basemall.product.entity.ProductSpecItem;
import com.medusa.basemall.product.vo.JoinActiveInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "BuycarInfo")
public class Buycar {

    /**
	 * 购物车ID
	 */
	@Id
	@Field("buycarId")
	@ApiModelProperty(value = "购物车ID" )
	private String buycarId;
    /**
     * 商品ID
     */
    @ApiModelProperty(value = "商品ID" )
    private Long productId;

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称"  )
    private String productName;
    /**
     * 商品图片
     */
    @ApiModelProperty(value = "商品图片"  )
    private String productImg;

    /**
     * 商品数量
     */
    @ApiModelProperty(value = "商品数量")
    private Integer quantity;
    /**
     * 商品价格
     */
    @ApiModelProperty(value = "商品价格" )
    private Double countPrice;
    /**
     * 是否下架
     */
	    @ApiModelProperty(value = "是否下架  上下状态(默认上架，0--上架，1--下架（仓库中），2--已售完)")
    private Integer shelfState;
    /**
     * 商品选中的规格信息
     */
    @ApiModelProperty(value = "商品选中的规格信息" )
    private ProductSpecItem productSpecItemInfo;

    /**
     * 商品参加活动信息
     */
    @ApiModelProperty(value = "参加的活动信息  只有存储参加优惠券和特价的活动")
    private JoinActiveInfo joinActiveInfo;
    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID"  )
    private Long wxuserId;

    /**
     * 添加时间
     */
    @ApiModelProperty(value = "添加时间"  )
    private String createTime;
    /**
     * 模板ID
     */
    @ApiModelProperty(value = "模板ID"  )
    private String appmodelId;

	@ApiModelProperty(value = "商品总数")
	private String totle;

	@ApiModelProperty(value = "最大购买值")
	@Transient
	private Integer maxQuantity;

}
