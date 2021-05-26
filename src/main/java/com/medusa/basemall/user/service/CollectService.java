package com.medusa.basemall.user.service;

import com.medusa.basemall.core.Result;
import com.medusa.basemall.product.entity.Product;
import com.medusa.basemall.user.entity.Collect;

import java.util.List;

public interface CollectService {
    Result add(Collect collect);

    Result list(Long wxuserId, String appmodelId, Integer page, Integer size);

    Result delete(String collectId);

    Collect detailed(String collectId);

    Collect findWxuserCollect(Long wxuserId, Long productId);

	int count(Long wxuserId);

	Long updateShelfState(List<Long> productIds, Integer shelfState);

	List<Collect> findByProductId(List<Long> productId);

	void updateCollects(List<Collect> collects);

	/**
	 * 更新标记
	 * @param type    1添加 2删除
	 * @param productTypeList
	 * @param appmodelId
	 */
	void updateActivityMark(Product productTypeList, String appmodelId);
}
