package com.medusa.basemall.messages.controller;


import com.medusa.basemall.aop.annotation.VersionManager;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.messages.dao.MessagesDao;
import com.medusa.basemall.messages.entity.Messages;
import com.medusa.basemall.messages.service.MessagesService;
import com.medusa.basemall.messages.vo.MessagesResultVo;
import com.medusa.basemall.messages.vo.MessagesVo;
import com.medusa.basemall.utils.MongoPageModel;
import com.mongodb.BasicDBObject;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 商家消息
 *
 * @author Created by wx on 2018/08/09.
 */
@Api(tags = "所有接口")
@RestController
@RequestMapping("/messages")
@VersionManager
public class MessagesController {

    @Resource
    private MessagesDao messagesDao;

    @Resource
    private MessagesService messagesService;

    @ApiOperation(value = "删除消息(可批量)", tags = "删除接口")
    @DeleteMapping("/v1/deleteMessages")
    public Result deleteMessages(@ApiParam(value = "删除消息对象Id字符串", required = true)@RequestParam String messagesIds) {
        String[] messageId = messagesIds.split(",");
        for (int i = 0; i < messageId.length; i++) {
            messagesDao.deleteById(messageId[i]);
        }
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "根据模块以及类型查找消息", tags = "查找接口")
    @ApiResponses({
            @ApiResponse(code = 100,message = "success",response = MessagesResultVo.class,responseContainer = "MessagesResultVo"),
    })
    @GetMapping("/v1/select")
    public Result select(@ApiParam(value = "根据模块以及类型查找消息", required = true)MessagesVo messagesVo) {
        MongoPageModel<Messages> page = new MongoPageModel<Messages>();
        page.setPageNum(messagesVo.getPageNum());
        page.setPageSize(messagesVo.getPageSize());
        MessagesResultVo messagesResultVo = messagesService.select(page, new BasicDBObject(), messagesVo.getAppmodelId(),
                messagesVo.getModule());
        return ResultGenerator.genSuccessResult(messagesResultVo);
    }

}
