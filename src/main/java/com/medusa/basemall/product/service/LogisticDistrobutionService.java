package com.medusa.basemall.product.service;

import com.medusa.basemall.core.Service;
import com.medusa.basemall.product.entity.LogisticDistrobution;

/**
 * Created by psy on 2018/05/24.
 */
public interface LogisticDistrobutionService extends Service<LogisticDistrobution> {

    int saveOrUpDate(LogisticDistrobution distrobution);

    LogisticDistrobution findDistrobution(String appmodelId);
}
