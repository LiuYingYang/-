package com.medusa.basemall.article.serviceimpl;


import com.medusa.basemall.article.entity.LeaveWordLaud;
import com.medusa.basemall.article.service.LeaveWordLaudService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Created by wx on 2018/06/07.
 */
@Service
public class LeaveWordLaudServiceImpl implements LeaveWordLaudService {
    @Resource
    MongoTemplate mongoTemplate;

    @Override
    public LeaveWordLaud getByWxuserIdAndLeaveWordId(Long wxuserId, String leaveWordId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("wxuserId").is(wxuserId));
        query.addCriteria(Criteria.where("leaveWordId").is(leaveWordId));
        LeaveWordLaud leaveWordLaud = mongoTemplate.findOne(query, LeaveWordLaud.class);
        return leaveWordLaud;
    }
}
