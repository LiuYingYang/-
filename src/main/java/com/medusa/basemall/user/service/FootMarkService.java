package com.medusa.basemall.user.service;

import com.medusa.basemall.core.Result;
import com.medusa.basemall.product.entity.Product;
import com.medusa.basemall.user.entity.FootMark;

import java.util.List;

/**
 * @author whh
 */
public interface FootMarkService {

    Result save(FootMark footMark);

    Result delete(Long wxuserId, Integer type, String footmarkId);

    Result findUserFootMark(Long wxuserId, String appmodelId, Integer page, Integer size);

	int count(Long wxuserId);

	Long updateShelfState(List<Long> productIds, Integer shelfState);

	void updateFootMarks(List<FootMark> footMarks);

	List<FootMark> findByProductId(List<Long> productId);

	void updateActivityMark(Product product,  String appmodelId);
}
