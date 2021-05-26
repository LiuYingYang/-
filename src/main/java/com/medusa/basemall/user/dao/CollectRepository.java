package com.medusa.basemall.user.dao;

import com.medusa.basemall.user.entity.Collect;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author whh
 */
@Repository
public interface CollectRepository extends MongoRepository<Collect, String> {

    Page<Collect> findByWxuserIdAndAppmodelId(Long wxuserId, String appmodeId, Pageable pageable);

    Collect findByWxuserIdAndProductId(Long wxuserId, Long productId);

	List<Collect> findByWxuserIdEquals(Long wxuserId);


}
