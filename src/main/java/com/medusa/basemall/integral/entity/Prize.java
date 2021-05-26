package com.medusa.basemall.integral.entity;

import com.medusa.basemall.promotion.entity.Coupon;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Created by wx on 2018/06/06.
 */
@Table(name = "t_prize")
@Data
public class Prize {

    @Id
    @Column(name = "prize_id")
    @ApiModelProperty(value="积分商品编号(新增时不传,其余操作时传)")
    private Integer prizeId;

    @Column(name = "prize_type")
    @ApiModelProperty(value="积分商品类型  奖品类型：1.商品2.优惠券", required = true)
    private Integer prizeType;

    @Column(name = "prize_name")
    @ApiModelProperty(value="积分商品名(类型为商品时传)")
    private String prizeName;

    @Column(name = "prize_img")
    @ApiModelProperty(value="积分商品主图片(类型为商品时传)")
    private String prizeImg;

    @Column(name = "round_sowing_img")
    @ApiModelProperty(value="积分商品轮播图(类型为商品时传)")
    private String roundSowingImg;

    @Column(name = "delete_state")
    @ApiModelProperty(value="删除标志(不需要)")
    private Boolean deleteState;

    @Column(name = "create_time")
    @ApiModelProperty(value="创建时间(不需要)")
    private String createTime;

    @Column(name = "update_state")
    @ApiModelProperty(value="更新时间(不需要)")
    private String updateState;

    @Column(name = "appmodel_id")
    @ApiModelProperty(value="模板id", required = true)
    private String appmodelId;

    @Column(name = "prize_stock")
    @ApiModelProperty(value="积分商品库存(类型为商品时传)")
    private Integer prizeStock;

    @ApiModelProperty(value="实际价格(类型为商品时传)")
    private Double price;

    @Column(name = "convert_price")
    @ApiModelProperty(value="兑换积分(类型为商品时传)")
    private Integer convertPrice;

    @Column(name = "sales_volume")
    @ApiModelProperty(value="销量(类型为商品时传)")
    private Integer salesVolume;

    @ApiModelProperty(value="商品状态 1.上架  2.仓库中 3.已售完(类型为商品时传1或者2,类型位优惠券时不需要)")
    private Integer state;

    @Column(name = "delivery_fees")
    @ApiModelProperty(value="统一邮费(类型为商品时传)")
    private Double deliveryFees;

    @Column(name = "logistic_model_id")
    @ApiModelProperty(value="邮费模板(类型为商品时传)")
    private Integer logisticModelId;

    @Column(name = "property_model_id")
    @ApiModelProperty(value="属性模板(类型为商品时传)")
    private Integer propertyModelId;

    @Column(name = "property_mine")
    @ApiModelProperty(value="自定义属性(类型为商品时传)")
    private String propertyMine;

    @Column(name = "text_img")
    @ApiModelProperty(value="图库详情(类型为商品时传)")
    private String textImg;

    @Column(name = "coupon_id")
    @ApiModelProperty(value="优惠券id(不需要)")
    private Integer couponId;

    @Column(name = "remark")
    @ApiModelProperty(value="描述(类型为商品时传)")
    private String remark;

    @Column(name = "send_place")
    @ApiModelProperty(value="发货地址(类型为商品时传)")
    private String sendPlace;

    @Column(name = "send_date")
    @ApiModelProperty(value="发货日期(类型为商品时传)")
    private String sendDate;

    @Column(name = "all_integral")
    @ApiModelProperty(value="此积分商品的兑换总额(不需要)")
    private Integer allIntegral;

    @ApiModelProperty(value="优惠券对象(不需要)")
    private Coupon coupon;

	/**
	 *
	 */
	private Double productBulk;

}