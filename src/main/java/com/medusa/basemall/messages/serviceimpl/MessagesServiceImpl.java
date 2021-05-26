package com.medusa.basemall.messages.serviceimpl;

import com.medusa.basemall.messages.dao.MessagesDao;
import com.medusa.basemall.messages.entity.Messages;
import com.medusa.basemall.messages.service.MessagesService;
import com.medusa.basemall.messages.vo.MessagesResultVo;
import com.medusa.basemall.utils.MongoPageModel;
import com.mongodb.DBObject;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Created by wx on 2018/08/09.
 */
@Service
public class MessagesServiceImpl implements MessagesService {

    @Resource
    private MongoTemplate mongoTemplate;

    @Resource
    private MessagesDao messagesDao;

    @Override
    public MessagesResultVo select(MongoPageModel<Messages> page, DBObject basicDBObject,
                                   String appmodeId, Integer module) {
        Query query = new Query();
        query.addCriteria(Criteria.where("appmodeId").is(appmodeId));
        query.addCriteria(Criteria.where("module").is(module));
        int count = (int) mongoTemplate.count(query, Messages.class);
        page.setRowCount(count);
        query.skip(page.getSkip()).limit(page.getPageSize());
        query.with(new Sort(Sort.Direction.DESC, "creatTime"));
        List<Messages> datas = mongoTemplate.find(query, Messages.class);
        datas.forEach(messages -> {
            messages.setReadOrNot(true);
            messagesDao.save(messages);
        });
        page.setDatas(datas);
        MessagesResultVo messagesResultVo = new MessagesResultVo();
        messagesResultVo.setPage(page);

        Query queryOne = new Query();
        queryOne.addCriteria(Criteria.where("appmodeId").is(appmodeId));
        queryOne.addCriteria(Criteria.where("module").is(1));
        queryOne.addCriteria(Criteria.where("readOrNot").is(0));
        messagesResultVo.setFifthNotRead(mongoTemplate.find(queryOne, Messages.class).size());

        Query queryTwo = new Query();
        queryTwo.addCriteria(Criteria.where("appmodeId").is(appmodeId));
        queryTwo.addCriteria(Criteria.where("module").is(2));
        queryTwo.addCriteria(Criteria.where("readOrNot").is(0));
        messagesResultVo.setSecondNotRead(mongoTemplate.find(queryTwo, Messages.class).size());

        Query queryThree = new Query();
        queryThree.addCriteria(Criteria.where("appmodeId").is(appmodeId));
        queryThree.addCriteria(Criteria.where("module").is(3));
        queryThree.addCriteria(Criteria.where("readOrNot").is(0));
        messagesResultVo.setThirdNotRead(mongoTemplate.find(queryThree, Messages.class).size());

        Query queryFour = new Query();
        queryFour.addCriteria(Criteria.where("appmodeId").is(appmodeId));
        queryFour.addCriteria(Criteria.where("module").is(4));
        queryFour.addCriteria(Criteria.where("readOrNot").is(0));
        messagesResultVo.setFourthNotRead(mongoTemplate.find(queryFour, Messages.class).size());

        Query queryFive = new Query();
        queryFive.addCriteria(Criteria.where("appmodeId").is(appmodeId));
        queryFive.addCriteria(Criteria.where("module").is(5));
        queryFive.addCriteria(Criteria.where("readOrNot").is(0));
        messagesResultVo.setFifthNotRead(mongoTemplate.find(queryFive, Messages.class).size());

        return messagesResultVo;
    }

	@Override
	public void sava(Messages messages) {
		messagesDao.save(messages);
	}
}
