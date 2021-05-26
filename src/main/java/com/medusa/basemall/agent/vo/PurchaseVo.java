package com.medusa.basemall.agent.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author whh
 */
public class PurchaseVo<E> {

	@ApiModelProperty(value = "有效的采购单商品")
	List<E> available;

	@ApiModelProperty(value = "无效的采购单商品")
	List<E> notAvailable;

	public List<E> getAvailable() {
		return available;
	}

	public void setAvailable(List<E> available) {
		this.available = available;
	}

	public List<E> getNotAvailable() {
		return notAvailable;
	}

	public void setNotAvailable(List<E> notAvailable) {
		this.notAvailable = notAvailable;
	}
}
