package com.medusa.basemall.article.controller;


import com.medusa.basemall.aop.annotation.VersionManager;
import com.medusa.basemall.article.dao.ArticleRepository;
import com.medusa.basemall.article.dao.LeaveWordDao;
import com.medusa.basemall.article.dao.LeaveWordLaudDao;
import com.medusa.basemall.article.entity.Article;
import com.medusa.basemall.article.entity.LeaveWord;
import com.medusa.basemall.article.entity.LeaveWordLaud;
import com.medusa.basemall.article.service.LeaveWordLaudService;
import com.medusa.basemall.article.service.LeaveWordService;
import com.medusa.basemall.article.vo.FindVo;
import com.medusa.basemall.article.vo.LeaveWordLaudVo;
import com.medusa.basemall.article.vo.LeaveWordVo;
import com.medusa.basemall.article.vo.ReplyVo;
import com.medusa.basemall.constant.VersionNumber;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.utils.MongoPageModel;
import com.medusa.basemall.utils.TimeUtil;
import com.mongodb.BasicDBObject;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 留言
 *
 * @author Created by wx on 2018/06/07.
 */
@Api(tags = "所有接口")
@RestController
@RequestMapping("/leaveWord")
@VersionManager(versoinNumber = VersionNumber.MARKETINGVERSION)
public class LeaveWordController {
    @Autowired
    LeaveWordDao leaveWordDao;
    @Autowired
    LeaveWordService leaveWordService;
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    LeaveWordLaudDao leaveWordLaudDao;
    @Autowired
    LeaveWordLaudService leaveWordLaudService;

    @ApiOperation(value = "留言添加", tags = "添加接口")
    @PostMapping("/v1/add")
    public Result add(@RequestBody LeaveWord leaveWord){
        leaveWord.setLeaveTime(TimeUtil.getNowTime());
        leaveWord.setSortType(0);
        leaveWord.setSortTime(0L);
        leaveWord.setReplyType(0);
        leaveWord.setChoiceness(0);
        leaveWord.setLaud(0);
        LeaveWord leaveWord1Result = leaveWordDao.save(leaveWord);
        Article article = articleRepository.getArticleByArticleId(leaveWord.getArticleId());
        article.setDiscussSum(leaveWordDao.findByArticleId(leaveWord.getArticleId()).size());
        articleRepository.save(article);
        if (leaveWord1Result != null){
            return ResultGenerator.genSuccessResult("留言成功");
        } else {
            return ResultGenerator.genFailResult("保存失败");
        }
    }


    @ApiOperation(value = "留言删除", tags = "删除接口")
    @DeleteMapping("/v1/delete")
    public Result delete(@ApiParam(value = "留言id") @RequestParam String leaveWordId,
                         @ApiParam(value = "文章id") @RequestParam String articleId){
        Article article = articleRepository.getArticleByArticleId(articleId);
        leaveWordDao.deleteById(leaveWordId);
        article.setDiscussSum(leaveWordDao.findByArticleId(articleId).size());
        articleRepository.save(article);
        return ResultGenerator.genSuccessResult("删除成功");
    }


    @ApiOperation(value = "设置留言是否精选/或回复/或删除回复", tags = "更新接口")
    @PutMapping("/v1/update")
    public Result update(@RequestBody ReplyVo replyVo){
        String leaveWordId = replyVo.getLeaveWordId();
        String replyInfo = replyVo.getReplyInfo();
        Integer choiceness = replyVo.getChoiceness();
        LeaveWord leaveWord = leaveWordDao.findByLeaveWordId(leaveWordId);
        // 留言回复删除
        if (replyInfo == null && choiceness == null) {
            leaveWord.setReplyType(0);
            leaveWord.setReplyInfo(null);
            leaveWordDao.save(leaveWord);
            return ResultGenerator.genSuccessResult();
        }
        // 新增留言回复或更新
        if (replyInfo != null) {
            leaveWord.setReplyInfo(replyInfo);
            leaveWord.setReplyTime(TimeUtil.getNowTime());
            leaveWord.setReplyType(1);
            leaveWordDao.save(leaveWord);
            return ResultGenerator.genSuccessResult();
        }
        // 设置是否精选
        if (choiceness != null) {
            if (choiceness == 0) {
                if (leaveWord.getSortType() == 1) {
                    leaveWord.setSortType(0);
                    leaveWord.setSortTime(0L);
                }
            }
            leaveWord.setChoiceness(choiceness);
            leaveWordDao.save(leaveWord);
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genSuccessResult();
    }


    @ApiOperation(value = "留言置顶/取消", tags = "更新接口")
    @PutMapping("/v1/sort")
    public Result sort(@RequestBody ReplyVo replyVo){
        Integer sortType = replyVo.getSortType();
        LeaveWord leaveWord = leaveWordDao.findByLeaveWordId(replyVo.getLeaveWordId());
        if (sortType == 1) {
            leaveWord.setChoiceness(1);
            leaveWord.setSortType(sortType);
            leaveWord.setSortTime(System.currentTimeMillis());
            leaveWordDao.save(leaveWord);
        } else {
            leaveWord.setSortType(sortType);
            leaveWord.setSortTime(0L);
            leaveWordDao.save(leaveWord);
        }
        return ResultGenerator.genSuccessResult();
    }


    @ApiOperation(value = "文章留言查询后台", tags = "查询接口")
    @ApiResponses({
            @ApiResponse(code = 100, message = "success", response = LeaveWord.class, responseContainer = "List"),})
    @GetMapping("/v1/selectLater")
    public Result selectLater(String articleId){
        List<LeaveWord> leaveWords = leaveWordService.findAll(articleId);
        return ResultGenerator.genSuccessResult(leaveWords);
    }


    @ApiOperation(value = "小程序端文章留言查询", tags = "查询接口")
    @ApiResponses({
            @ApiResponse(code = 100, message = "success", response = LeaveWord.class, responseContainer = "List"),})
    @GetMapping("/v1/selectBefore")
    public Result selectBefore(LeaveWordVo leaveWordVo){
        Integer pageNum = leaveWordVo.getPageNum();
        Integer pageSize = leaveWordVo.getPageSize();
        Long wxuserId = leaveWordVo.getWxuserId();
        String articleId = leaveWordVo.getArticleId();
        MongoPageModel<LeaveWord> page = new MongoPageModel<LeaveWord>();
        page.setPageNum(pageNum);
        page.setPageSize(pageSize);
        page = leaveWordService.getByChoicenessType(page, new BasicDBObject(), articleId);
        List<LeaveWord> leaveWords = page.getList();
        if (leaveWords.size() > 0) {
            for (LeaveWord leaveWord : leaveWords) {
                LeaveWordLaud leaveWordLaud = leaveWordLaudService.getByWxuserIdAndLeaveWordId(wxuserId, leaveWord.getLeaveWordId());
                if (leaveWordLaud == null) {
                    leaveWord.setLaudOrNot(false);
                } else {
                    leaveWord.setLaudOrNot(leaveWordLaud.getLaudOrNot());
                }
            }
        }
        return ResultGenerator.genSuccessResult(page);
    }



    @ApiOperation(value = "查询用户自己留言", tags = "查询接口")
    @ApiResponses({
            @ApiResponse(code = 100, message = "success", response = LeaveWord.class, responseContainer = "List"),})
    @GetMapping("/v1/selectMine")
    public Result selectMine(LeaveWordVo leaveWordVo){
        Integer pageNum = leaveWordVo.getPageNum();
        Integer pageSize = leaveWordVo.getPageSize();
        Long wxuserId = leaveWordVo.getWxuserId();
        String articleId = leaveWordVo.getArticleId();
        MongoPageModel<LeaveWord> page = new MongoPageModel<LeaveWord>();
        page.setPageNum(pageNum);
        page.setPageSize(pageSize);
        page = leaveWordService.findByWxuserIdAndArticleId(page, new BasicDBObject(),wxuserId, articleId);
        return ResultGenerator.genSuccessResult(page);
    }


    @ApiOperation(value = "根据输入条件查询留言", tags = "查询接口")
    @ApiResponses({
            @ApiResponse(code = 100, message = "success", response = LeaveWord.class, responseContainer = "List"),})
    @GetMapping("/v1/findLeaveWord")
    public Result findLeaveWord(FindVo findVo){
        String findWord = findVo.getFindWord();
        String appmodelId = findVo.getAppmodelId();
        List<LeaveWord> leaveWords = leaveWordService.findLeaveWord(findWord, appmodelId);
        return ResultGenerator.genSuccessResult(leaveWords);
    }


    @ApiOperation(value = "留言点赞", tags = "更新接口")
    @PutMapping("/v1/updateLeaveWordLaud")
    public Result updateLeaveWordLaud(@RequestBody LeaveWordLaudVo leaveWordLaudVo) {
        Long wxuserId = leaveWordLaudVo.getWxuserId();
        String leaveWordId = leaveWordLaudVo.getLeaveWordId();
        LeaveWord leaveWord = leaveWordDao.findByLeaveWordId(leaveWordId);
        LeaveWordLaud leaveWordLaud = leaveWordLaudService.getByWxuserIdAndLeaveWordId(wxuserId, leaveWordId);
        if (leaveWordLaud == null) {
            LeaveWordLaud leaveWordLaudNew = new LeaveWordLaud();
            leaveWordLaudNew.setWxuserId(wxuserId);
            leaveWordLaudNew.setLeaveWordId(leaveWordId);
            leaveWordLaudNew.setLaudOrNot(true);
            leaveWordLaudDao.save(leaveWordLaudNew);
            leaveWord.setLaud(leaveWord.getLaud() + 1);
            leaveWordDao.save(leaveWord);
        } else {
            if (leaveWordLaud.getLaudOrNot() == false) {
                leaveWordLaud.setLaudOrNot(true);
                leaveWordLaudDao.save(leaveWordLaud);
                leaveWord.setLaud(leaveWord.getLaud() + 1);
                leaveWordDao.save(leaveWord);
            } else {
                leaveWordLaud.setLaudOrNot(false);
                leaveWordLaudDao.save(leaveWordLaud);
                leaveWord.setLaud(leaveWord.getLaud() - 1);
                leaveWordDao.save(leaveWord);
            }
        }
        return ResultGenerator.genSuccessResult();
    }
}
