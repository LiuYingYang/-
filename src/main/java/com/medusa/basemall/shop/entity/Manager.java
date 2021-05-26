package com.medusa.basemall.shop.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;


/**
 * @author whh
 */
@Data
@Table(name = "t_manager")
public class Manager {

	/**
	 * 编号
	 */
	@Id
	@GeneratedValue(generator = "JDBC")
	private Integer id;

	/**
	 * 用户模板ID
	 */
	@Column(name = "appmodel_id")
	private String appmodelId;

	/**
	 * 是否缴纳保障金 (曾经有过标准版以上的版本该参数就一定为true)
	 */
	@Column(name = "flag")
	private Boolean flag;

	/**
	 * 版本 1-基础班,2-标准版,3-营销版
	 */
	@Column(name = "version")
	private Integer version;

	/**
	 * 创建用户时间
	 */
	@Column(name = "create_time")
	private String createTime;

	/**
	 * 截止日期
	 */
	@Column(name = "expiry_date")
	private Date expiryDate;


	/**
	 *到期通知是否确认  true 确认  false 未确认
	 */
	@Column(name = "expiry_date_notify")
	private Boolean expiryDateNotify;

	/**
	 * 上次登录时间
	 */
	@Column(name = "last_time")
	private String lastTime;

	@Column(name = "app_id")
	private String appId;

	@Column(name = "mch_id")
	private String mchId;

	@Column(name = "mch_key")
	private String mchKey;
	/**
	 * 商户支付证书路径
	 */
	@Column(name = "certificate_path")
	private String certificatePath;

	private String logo;

	@Column(name = "mini_code")
	private String miniCode;

	@Column(name = "version_subscript")
	private String versionSubscript;

	@Column(name = "mini_name")
	private String miniName;

	/**
	 * 删除标志
	 */
	@Column(name = "del_state")
	private Integer delState;

	private BigDecimal balance;

	private String secret;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 用户名
	 */
	@Column(name = "user_name")
	private String userName;


}