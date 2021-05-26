package com.medusa.basemall.product.vo;

import com.medusa.basemall.product.entity.LogisticCharge;
import com.medusa.basemall.product.entity.LogisticFree;
import com.medusa.basemall.product.entity.LogisticModel;

import javax.persistence.Transient;
import java.util.List;

/**
 * 物流信息
 */
public class LogisticModelVo extends LogisticModel {

	@Transient
    private List<LogisticCharge> chargeList;
	@Transient
    private List<LogisticFree> freeList;

    public List<LogisticCharge> getChargeList() {
        return chargeList;
    }

    public void setChargeList(List<LogisticCharge> chargeList) {
        this.chargeList = chargeList;
    }

    public List<LogisticFree> getFreeList() {
        return freeList;
    }

    public void setFreeList(List<LogisticFree> freeList) {
        this.freeList = freeList;
    }
}
