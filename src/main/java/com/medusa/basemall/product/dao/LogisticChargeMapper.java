package com.medusa.basemall.product.dao;

import com.medusa.basemall.core.Mapper;
import com.medusa.basemall.product.entity.LogisticCharge;

import java.util.List;

public interface LogisticChargeMapper extends Mapper<LogisticCharge> {

    int deleteByLogisticModelId(Integer logisticModelId);

    List<LogisticCharge> selectBylogisticModelId(Integer logisticModelId);

}