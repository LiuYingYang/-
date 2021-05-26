package com.medusa.basemall.article.dao;

import com.medusa.basemall.article.entity.LeaveWord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Created by wx on 2018/06/07.
 */
@Repository
public interface LeaveWordDao extends MongoRepository<LeaveWord, String> {

    /***
     * 根据留言id查询留言
     *
     * @param leaveWorleId
     * @return LeaveWord
     */
    LeaveWord findByLeaveWordId(String leaveWorleId);

    /***
     * 根据文章id查询留言
     *
     * @param articleId
     * @return List<LeaveWord>
     */
    List<LeaveWord> findByArticleId(String articleId);


    /***
     * 根据文章id删除留言
     *
     * @param articleId
     * @return void
     */
    void deleteByArticleId (String articleId);
}
