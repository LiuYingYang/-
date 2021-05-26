package com.medusa.basemall.promotion.vo;

import com.medusa.basemall.product.vo.ProductWxVo;
import com.medusa.basemall.promotion.entity.ActivityGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Created by psy on 2018/05/30.
 */
@Data
public class ActivityGroupVo extends ActivityGroup {

    /*    成交量：doneOrder
        成交额：doneMoney
        拼团成功：doneGroup		成功的团的个数
        参团人数：joinNum
        未满员：doingGroup		拼团中的团个数*/

    @ApiModelProperty(value="成交量")
    private Integer doneOrder;

    @ApiModelProperty(value="成交额")
    private Double doneMoney;

    @ApiModelProperty(value="拼团成功")
    private Integer doneGroup;

    @ApiModelProperty(value="成功的团的个数")
    private Integer joinNum;

    @ApiModelProperty(value="拼团中的团个数")
    private Integer doingGroup;

    private List<ProductWxVo> productList;

}
