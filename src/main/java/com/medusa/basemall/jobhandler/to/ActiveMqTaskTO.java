package com.medusa.basemall.jobhandler.to;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "ActiveMqTaskTO")
public class ActiveMqTaskTO {

	@Id
	private String id;

	/**
	 * 商家appmodelId
	 */
	private String appmodelId;

	/**
	 * 队列创建时间
	 */
	private Date createTime;

	/**
	 * 发送指定的队列名
	 */
	private String queueName;

	/**
	 * 发送至队列的数据
	 */
	private String jsonData;

	/**
	 * 队列执行日期
	 */
	private Date endTime;

	/**
	 * 队列消费状态  true-执行成功  false-未执行成功
	 */
	private Boolean state;

	/**
	 *是否已经发送
	 */
	private Boolean sendState;
	/**
	 *发送次数
	 */
	private Integer sendSum;

	private Date lastSendTime;

}
