package com.medusa.basemall.product.dao;

import com.medusa.basemall.core.Mapper;
import com.medusa.basemall.product.entity.LogisticFree;

import java.util.List;

public interface LogisticFreeMapper extends Mapper<LogisticFree> {

    int deleteByLogisticModelId(Integer logisticModelId);

    List<LogisticFree> selectBylogisticModelId(Integer logisticModelId);
}