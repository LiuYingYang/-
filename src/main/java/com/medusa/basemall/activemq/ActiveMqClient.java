package com.medusa.basemall.activemq;

import org.apache.activemq.ScheduledMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.*;

/**
 * 发送消息类
 *
 * @author whh
 */
@Component
public class ActiveMqClient {

    @Autowired
    private JmsTemplate jmsTemplate;


    /**
     * @desc 立即发送
     */
    public void send(String message,String queueName) {
        jmsTemplate.convertAndSend(queueName, message);
    }

    /**
     * @desc
     */
    public void send2(String text, String queueName) {
        //获取连接工厂
        ConnectionFactory connectionFactory = this.jmsTemplate.getConnectionFactory();
        try {
            connectionFactory.createConnection();
            //获取连接
            Connection connection = connectionFactory.createConnection();
            connection.start();
            //获取session
            Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            // 创建一个消息队列
            Topic topic = session.createTopic(queueName);
            MessageProducer producer = session.createProducer(topic);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
            TextMessage message = session.createTextMessage(text);
            //发送
            producer.send(message);
            session.commit();
            producer.close();
            session.close();
            connection.close();
        } catch (Exception e) {
            e.getMessage();
        }
    }


    /**
     * 延时发送
     *
     * @param text      文本内容
     * @param queueName 队列名称
     * @param delayTime      延迟时间(毫秒值)
     */
    public void delaySend(String text, String queueName, Long delayTime) {
        //获取连接工厂
        ConnectionFactory connectionFactory = this.jmsTemplate.getConnectionFactory();
        try {
            connectionFactory.createConnection();
            //获取连接
            Connection connection = connectionFactory.createConnection();
            connection.start();
            //获取session
            Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            // 创建一个消息队列
            Destination destination = session.createQueue(queueName);
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
            TextMessage message = session.createTextMessage(text);
            //设置延迟时间
            message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, delayTime);
            //发送
            producer.send(message);
            session.commit();
            producer.close();
            session.close();
            connection.close();
        } catch (Exception e) {
            e.getMessage();
        }
    }

	public  void deleteAllScheduleMessage()  {
		ConnectionFactory connectionFactory = this.jmsTemplate.getConnectionFactory();
		try {
			Connection conn = connectionFactory.createConnection();
			Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination management = session.createTopic(ScheduledMessage.AMQ_SCHEDULER_MANAGEMENT_DESTINATION);
			MessageProducer producer = session.createProducer(management);
			Message request = session.createMessage();
			request.setStringProperty(ScheduledMessage.AMQ_SCHEDULER_ACTION, ScheduledMessage.AMQ_SCHEDULER_ACTION_REMOVEALL);
			producer.send(request);
		} catch (JMSException e) {
			e.printStackTrace();
		}

	}
}
