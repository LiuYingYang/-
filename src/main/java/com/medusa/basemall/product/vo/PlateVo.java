package com.medusa.basemall.product.vo;

import com.medusa.basemall.shop.entity.Plate;
import com.medusa.basemall.shop.entity.PlateProduct;
import lombok.Data;

import java.util.List;

/**
 * @author whh
 */
@Data
public class PlateVo extends Plate {

	List<PlateProduct> plateProducts;
}
