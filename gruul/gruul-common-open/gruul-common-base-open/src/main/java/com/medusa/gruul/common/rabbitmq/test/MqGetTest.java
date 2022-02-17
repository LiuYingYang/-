package com.medusa.gruul.common.rabbitmq.test;

import com.medusa.gruul.common.rabbitmq.core.MdsConstant;
import com.medusa.gruul.common.rabbitmq.core.MqConsumer;
import com.medusa.gruul.common.rabbitmq.core.MqSend;

import java.util.Set;

/**
 * @Description: 基础mq组件测试类
 * @Author: wangpeng
 * @Date: 2019/7/21 10:05
 */
public class MqGetTest {

    public static void main(String[] args) throws Exception {

        get();


    }



    private static void get(){
        MqConsumer testConsumerTask = new MqConsumer();
         testConsumerTask.execute("OTHER_QUEUE","222222", "111111",TestConsumer.class);



    }


}
