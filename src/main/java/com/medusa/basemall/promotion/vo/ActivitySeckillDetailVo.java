package com.medusa.basemall.promotion.vo;

import com.medusa.basemall.promotion.entity.ActivitySeckill;
import lombok.Data;

import java.util.List;

@Data
public class ActivitySeckillDetailVo extends ActivitySeckill {

	List<OptionalProductItems> optionalProductItems;
}
