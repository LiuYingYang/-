package com.medusa.basemall.product.service;

import com.medusa.basemall.core.Service;
import com.medusa.basemall.product.entity.LogisticModel;
import com.medusa.basemall.product.vo.LogisticModelVo;

import java.util.List;

/**
 * Created by psy on 2018/05/24.
 */
public interface LogisticModelService extends Service<LogisticModel> {

    int saveOrUpdateModel(LogisticModelVo logisticModelVo);

    int openOrCloseModel(String appmodelId, Boolean turnState);

    int deleteModelById(Integer logisticModelId);

    List<LogisticModelVo> findModelByAppmodelIdDetail(String appmodelId);

    List<LogisticModel> findModelByAppmodelId(String appmodelId);

	List<LogisticModelVo> findByWlCalculateFare( String[] logisticModelIds);
}
