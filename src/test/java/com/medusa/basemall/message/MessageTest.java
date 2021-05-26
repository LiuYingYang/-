package com.medusa.basemall.message;

import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.BasemallApplicationTests;
import com.medusa.basemall.messages.entity.Messages;
import com.medusa.basemall.utiles.Constant;
import com.medusa.basemall.utiles.MockMvcUtil;
import com.medusa.basemall.utils.TimeUtil;
import org.junit.Test;

public class MessageTest extends BasemallApplicationTests {

    @Test
    public void addtest() {
        for (int i = 1; i <3; i++) {
            Messages message = new Messages();
            message.setModule(5);
            message.setType(i);
            message.setReadOrNot(false);
            message.setCreateTime(TimeUtil.getNowTime());
            message.setAppmodeId(Constant.appmodelIdy);
            message.setMessageHead("");
            message.setMessageBody("");
            JSONObject post = MockMvcUtil.sendRequest("/messages/addTest/v1",
                    JSONObject.toJSONString(message), null, "post");
        }
   }
}
