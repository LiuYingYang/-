package com.medusa.basemall.integral.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Created by wx on 2018/06/06.
 */
@Data
public class FindProductVo {

    @ApiModelProperty(value = "当前页数")
    private int pageNum;

    @ApiModelProperty(value = "每页数量")
    private int pageSize;

    @ApiModelProperty(value = "模板id", required = true)
    private String appmodelId;

    @ApiModelProperty(value = "活动开始时间", required = true)
    private String startDate;

    @ApiModelProperty(value = "活动结束时间", required = true)
    private String endDate;

    @ApiModelProperty(value = "是否可叠加", required = true)
    private Boolean overlayState;

    @ApiModelProperty(value = "当前活动id(更新时传)")
    private Integer activityId;

	@ApiModelProperty(value = "是否创建活动 true 创建活动  false编辑活动")
	private Boolean insertIf;


}
