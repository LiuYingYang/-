package com.medusa.basemall.order.dao;

import com.medusa.basemall.order.entity.BalanceDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author whh
 */

public interface BalanceDetailRepository extends  MongoRepository<BalanceDetail,String>{

	Page<BalanceDetail>  findByMemberId(Long memberId,Pageable pageable);

	BalanceDetail findByOrderId(Long orderId);
}
