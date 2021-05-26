package com.medusa.basemall.messages.service;

import com.medusa.basemall.messages.entity.Messages;
import com.medusa.basemall.messages.vo.MessagesResultVo;
import com.medusa.basemall.utils.MongoPageModel;
import com.mongodb.DBObject;

/**
 * @author Created by wx on 2018/08/09.
 */
public interface MessagesService {

    /**
     * 根据类型分页查询商家消息
     *
     * @param page
     * @param basicDBObject
     * @param appmodeId
     * @param module
     * @return MongoPageModel<Messages>
     */
    MessagesResultVo select(MongoPageModel<Messages> page, DBObject basicDBObject,
                            String appmodeId, Integer module);

	void sava(Messages messages);
}
