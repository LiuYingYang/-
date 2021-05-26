package com.medusa.basemall.product.dao;

import com.medusa.basemall.core.Mapper;
import com.medusa.basemall.product.entity.LogisticModel;
import com.medusa.basemall.product.vo.LogisticModelVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LogisticModelMapper extends Mapper<LogisticModel> {

    List<LogisticModelVo> findModelByAppmodelIdDetail(String appmodelId);

    int openOrCloseModel(LogisticModel logisticModel);

	List<LogisticModelVo> findByWlCalculateFare(@Param("logisticModelIds") String[] logisticModelIds);
}

