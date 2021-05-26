package com.medusa.basemall.shop.dao;

import com.medusa.basemall.core.Mapper;
import com.medusa.basemall.shop.entity.Manager;

public interface ManagerMapper extends Mapper<Manager> {


    Manager selectByAppmodelId(String appmodelId);
}