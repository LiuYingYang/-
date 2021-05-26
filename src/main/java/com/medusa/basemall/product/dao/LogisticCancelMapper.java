package com.medusa.basemall.product.dao;

import com.medusa.basemall.core.Mapper;
import com.medusa.basemall.product.entity.LogisticCancel;

public interface LogisticCancelMapper extends Mapper<LogisticCancel> {
    int updateDefaultState(String appmodelId);

    LogisticCancel selectDefultTrue(String appmodelId);
}