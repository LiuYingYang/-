package com.medusa.basemall.article.service;

import com.medusa.basemall.article.entity.LeaveWord;
import com.medusa.basemall.utils.MongoPageModel;
import com.mongodb.DBObject;

import java.util.List;

/**
 * @author Created by wx on 2018/06/07.
 */
public interface LeaveWordService {

    /***
     * 根据文章id查询所有留言后台
     *
     * @param articleId
     * @return List<LeaveWord>
     */
    List<LeaveWord> findAll(String articleId);

    /***
     * 根据输入条件查询留言
     *
     * @param findWord
     * @param appmodelId
     * @return List<LeaveWord>
     */
    List<LeaveWord> findLeaveWord(String findWord, String appmodelId);

    /***
     * 根据文章id查询留言
     *
     * @param articleId
     * @return List<LeaveWord>
     */
    List<LeaveWord> findByArticleId(String articleId);


    /***
     * 分页查询用户自己留言小程序端
     *
     * @param page
     * @param basicDBObject
     * @param wxuserId
     * @param articleId
     * @return MongoPageModel<Article>
     */
    MongoPageModel<LeaveWord> findByWxuserIdAndArticleId(MongoPageModel<LeaveWord> page, DBObject basicDBObject,
                                                         Long wxuserId, String articleId);

    /***
     * 分页查询小程序端文章精选留言
     *
     * @param page
     * @param basicDBObject
     * @param articleId
     * @return MongoPageModel<Article>
     */
    MongoPageModel<LeaveWord> getByChoicenessType(MongoPageModel<LeaveWord> page, DBObject basicDBObject, String articleId);
}
