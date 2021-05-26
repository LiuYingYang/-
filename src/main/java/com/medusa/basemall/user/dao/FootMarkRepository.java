package com.medusa.basemall.user.dao;

import com.medusa.basemall.user.entity.FootMark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FootMarkRepository extends MongoRepository<FootMark, String> {

    Page<FootMark> findByWxuserIdAndAppmodelId(Long wxuserId, String appmodeId, Pageable pageable);

    int deleteByWxuserId(Long wxuserId);

	List<FootMark> findByWxuserIdEquals(Long wxuserId);

	int deleteByFootmarkId(String footmarkId);
}
