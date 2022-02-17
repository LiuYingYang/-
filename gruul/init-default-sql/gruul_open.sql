/*
Navicat MySQL Data Transfer

Source Server         : 101.71.91.145
Source Server Version : 80025
Source Host           : 101.71.91.145:3306
Source Database       : gruul_open

Target Server Type    : MYSQL
Target Server Version : 80025
File Encoding         : 65001

Date: 2022-01-12 18:03:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for QRTZ_BLOB_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_BLOB_TRIGGERS`;
CREATE TABLE `QRTZ_BLOB_TRIGGERS` (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `QRTZ_BLOB_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for QRTZ_CALENDARS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CALENDARS`;
CREATE TABLE `QRTZ_CALENDARS` (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `CALENDAR_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for QRTZ_CRON_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CRON_TRIGGERS`;
CREATE TABLE `QRTZ_CRON_TRIGGERS` (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `CRON_EXPRESSION` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `TIME_ZONE_ID` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `QRTZ_CRON_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for QRTZ_FIRED_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_FIRED_TRIGGERS`;
CREATE TABLE `QRTZ_FIRED_TRIGGERS` (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `ENTRY_ID` varchar(95) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `INSTANCE_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `FIRED_TIME` bigint NOT NULL,
  `SCHED_TIME` bigint NOT NULL,
  `PRIORITY` int NOT NULL,
  `STATE` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `JOB_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `JOB_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for QRTZ_JOB_DETAILS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_JOB_DETAILS`;
CREATE TABLE `QRTZ_JOB_DETAILS` (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `JOB_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `JOB_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `DESCRIPTION` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `IS_DURABLE` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `IS_UPDATE_DATA` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for QRTZ_JOB_TAG_RELATION
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_JOB_TAG_RELATION`;
CREATE TABLE `QRTZ_JOB_TAG_RELATION` (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `JOB_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `JOB_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `TAG_ID` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '外来 id 关联quartz',
  `DESCRIPTION` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`TRIGGER_NAME`,`JOB_NAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='自动标签和定时任务关联表';

-- ----------------------------
-- Table structure for QRTZ_LOCKS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_LOCKS`;
CREATE TABLE `QRTZ_LOCKS` (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `LOCK_NAME` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for QRTZ_PAUSED_TRIGGER_GRPS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_PAUSED_TRIGGER_GRPS`;
CREATE TABLE `QRTZ_PAUSED_TRIGGER_GRPS` (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for QRTZ_SCHEDULER_STATE
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SCHEDULER_STATE`;
CREATE TABLE `QRTZ_SCHEDULER_STATE` (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `INSTANCE_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `LAST_CHECKIN_TIME` bigint NOT NULL,
  `CHECKIN_INTERVAL` bigint NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for QRTZ_SIMPLE_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPLE_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPLE_TRIGGERS` (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `REPEAT_COUNT` bigint NOT NULL,
  `REPEAT_INTERVAL` bigint NOT NULL,
  `TIMES_TRIGGERED` bigint NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `QRTZ_SIMPLE_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for QRTZ_SIMPROP_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPROP_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPROP_TRIGGERS` (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `STR_PROP_1` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `STR_PROP_2` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `STR_PROP_3` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `INT_PROP_1` int DEFAULT NULL,
  `INT_PROP_2` int DEFAULT NULL,
  `LONG_PROP_1` bigint DEFAULT NULL,
  `LONG_PROP_2` bigint DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `QRTZ_SIMPROP_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for QRTZ_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_TRIGGERS`;
CREATE TABLE `QRTZ_TRIGGERS` (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `JOB_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `JOB_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `DESCRIPTION` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint DEFAULT NULL,
  `PREV_FIRE_TIME` bigint DEFAULT NULL,
  `PRIORITY` int DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_TYPE` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `START_TIME` bigint NOT NULL,
  `END_TIME` bigint DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `MISFIRE_INSTR` smallint DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  KEY `SCHED_NAME` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`) USING BTREE,
  CONSTRAINT `QRTZ_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `QRTZ_JOB_DETAILS` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for t_a_log
-- ----------------------------
DROP TABLE IF EXISTS `t_a_log`;
CREATE TABLE `t_a_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `msg` varchar(256) DEFAULT NULL,
  `level` int DEFAULT NULL,
  `log` longtext,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=67464 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Log';

-- ----------------------------
-- Table structure for t_account_center
-- ----------------------------
DROP TABLE IF EXISTS `t_account_center`;
CREATE TABLE `t_account_center` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `is_deleted` tinyint DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `head_style` tinyint(1) DEFAULT NULL COMMENT '头部风格 1-系统风格 2-自定义风格',
  `custom_style` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '自定义风格样式,json存储',
  `get_cart_text` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '领卡文案',
  `hide_cart_inlet` tinyint(1) DEFAULT NULL COMMENT '非会员隐藏领卡入口 0-隐藏 1-显示',
  `order_info` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '\r\n订单信息 json存储',
  `menu_style` tinyint(1) DEFAULT NULL COMMENT '菜单选择样式 1-展开式 2-折叠式',
  `code_style` tinyint(1) DEFAULT NULL COMMENT '提货码样式,1样式1  2样式2',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='用户中心配置';

-- ----------------------------
-- Table structure for t_account_center_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_account_center_menu`;
CREATE TABLE `t_account_center_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `is_deleted` tinyint DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `menu_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '菜单名称',
  `menu_icon_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '菜单url',
  `style_type` tinyint(1) DEFAULT NULL COMMENT '菜单样式类别 1-展开式 2-折叠式',
  `sort_index` tinyint DEFAULT NULL COMMENT '排序位置',
  `p_id` bigint DEFAULT NULL COMMENT '父级id',
  `hide_menu` tinyint(1) DEFAULT NULL COMMENT '0隐藏1显示',
  `allow_use` tinyint(1) DEFAULT NULL COMMENT '是否可用 0不可用 1-可用',
  `default_icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '默认图标',
  `split_flag` tinyint(1) DEFAULT NULL COMMENT '是否有分割线',
  `link_select_item` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '跳转类型json',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `is_deleted` (`is_deleted`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4115 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='用户中心菜单配置';

-- ----------------------------
-- Table structure for t_afs_negotiate_history
-- ----------------------------
DROP TABLE IF EXISTS `t_afs_negotiate_history`;
CREATE TABLE `t_afs_negotiate_history` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '工单id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '删除状态：0->未删除；1->已删除',
  `user_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '申请人ID',
  `user_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户名',
  `user_avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户头像',
  `info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '详情',
  `image` varchar(800) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '图片',
  `order_id` bigint DEFAULT NULL COMMENT '关联订单',
  `apply_user_type` int DEFAULT NULL COMMENT '申请用户的类型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=151 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='协商历史';

-- ----------------------------
-- Table structure for t_afs_order
-- ----------------------------
DROP TABLE IF EXISTS `t_afs_order`;
CREATE TABLE `t_afs_order` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '工单id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '删除状态：0->未删除；1->已删除',
  `no` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '工单编号',
  `type` int NOT NULL COMMENT '工单类型：1.团长换货 2.团长退款 3.退款 4.换货 5.退货退款',
  `status` int NOT NULL COMMENT '审核状态：1.待商家审核 2.待退货 3.待团长确认收货 4.待发货 5.待提货  6.成功',
  `close_type` int DEFAULT NULL COMMENT '关闭原因：1.用户撤销 2.团长撤销 3.团长未收到退货 4.重新申请换货',
  `user_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '申请人ID',
  `user_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户帐号',
  `user_avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户头像',
  `product_sku_id` bigint NOT NULL COMMENT '商品sku编号',
  `template_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '消息订阅ID	',
  `order_id` bigint DEFAULT NULL COMMENT '售后生成的订单',
  `refund_amount` decimal(10,2) DEFAULT NULL COMMENT '退款金额',
  `deadline` datetime DEFAULT NULL COMMENT '过期时间',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '说明',
  `images` varchar(800) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '照片',
  `close_time` datetime DEFAULT NULL COMMENT '关闭时间',
  `refusal_time` datetime DEFAULT NULL COMMENT '拒绝时间',
  `success_time` datetime DEFAULT NULL COMMENT '成功时间',
  `refusal_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '拒绝原因',
  `receipt_bill_id` bigint DEFAULT NULL COMMENT '签收单ID',
  `note` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '订单备注',
  `delivery_sn` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '物流单号',
  `delivery_company` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '物流公司',
  `delivery_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '物流公司编码',
  `reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '申请的原因',
  `return_template_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '退货消息订阅ID	',
  `is_logistics` tinyint unsigned NOT NULL COMMENT '是否物流配送',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='售后工单';

-- ----------------------------
-- Table structure for t_afs_order_item
-- ----------------------------
DROP TABLE IF EXISTS `t_afs_order_item`;
CREATE TABLE `t_afs_order_item` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '租户ID',
  `shop_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '商铺ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '删除状态：0->未删除；1->已删除',
  `afs_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '售后申请单ID',
  `order_id` bigint NOT NULL COMMENT '售后生成的订单',
  `product_id` bigint DEFAULT NULL COMMENT '商品id',
  `product_sku_id` bigint NOT NULL COMMENT '商品sku编号',
  `product_quantity` int NOT NULL COMMENT '商品数量',
  `product_pic` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '商品图片',
  `product_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '商品名',
  `product_price` decimal(10,2) DEFAULT NULL COMMENT '销售价格',
  `refund_amount` decimal(10,2) DEFAULT NULL COMMENT '退款金额',
  `specs` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品的销售属性',
  `order_area` int DEFAULT NULL COMMENT '销售专区',
  `refund_integration` decimal(10,2) DEFAULT NULL COMMENT '退款的积分',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='提货点申请的详情';

-- ----------------------------
-- Table structure for t_afs_reason
-- ----------------------------
DROP TABLE IF EXISTS `t_afs_reason`;
CREATE TABLE `t_afs_reason` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '删除状态：0->未删除；1->已删除',
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='申请的原因';

-- ----------------------------
-- Table structure for t_attribute_template
-- ----------------------------
DROP TABLE IF EXISTS `t_attribute_template`;
CREATE TABLE `t_attribute_template` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '删除状态：0->未删除；1->已删除',
  `parent_id` bigint DEFAULT NULL COMMENT '上级分类的编号：0表示一级分类',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '模板名称',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=113 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='属性模板';

-- ----------------------------
-- Table structure for t_auth_menu_info
-- ----------------------------
DROP TABLE IF EXISTS `t_auth_menu_info`;
CREATE TABLE `t_auth_menu_info` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `menu_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源名称',
  `menu_code` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单编码',
  `menu_pid` bigint DEFAULT NULL COMMENT '父结点id,0为顶级节点',
  `menu_sort` tinyint DEFAULT NULL COMMENT '排序号',
  `is_deleted` tinyint DEFAULT NULL COMMENT '是否可用,1：可用，0不可用',
  `update_time` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user_id` bigint DEFAULT NULL,
  `modify_user_id` bigint DEFAULT NULL,
  `create_user_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `modify_user_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `model_id` bigint DEFAULT NULL COMMENT '模块id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `t_auth_menu_info_id_uindex` (`id`) USING BTREE,
  UNIQUE KEY `t_auth_menu_info_menu_code_uindex` (`menu_code`) USING BTREE,
  UNIQUE KEY `t_auth_menu_info_menu_name_uindex` (`menu_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='菜单信息表';

-- ----------------------------
-- Table structure for t_auth_model_info
-- ----------------------------
DROP TABLE IF EXISTS `t_auth_model_info`;
CREATE TABLE `t_auth_model_info` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `model_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '模块编码',
  `model_name` varchar(90) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '模块名称',
  `remark` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '备注',
  `create_user_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '创建人',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `last_modify_user_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '最近操作人',
  `last_modify_user_id` bigint DEFAULT NULL COMMENT '最后操作人ID',
  `is_deleted` tinyint(1) DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL,
  `is_lock` tinyint NOT NULL DEFAULT '0' COMMENT '锁定状态；0正常 1锁定',
  `model_version` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '模块版本号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='模块表';

-- ----------------------------
-- Table structure for t_auth_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `t_auth_operation_log`;
CREATE TABLE `t_auth_operation_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL COMMENT '用户id',
  `type` tinyint DEFAULT NULL COMMENT '操作类型 1 增加 2修改 3删除',
  `execute_sql` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '被执行的sql',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `is_delete` tinyint DEFAULT NULL,
  `role_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;


-- ----------------------------
-- Table structure for t_auth_user_info
-- ----------------------------
DROP TABLE IF EXISTS `t_auth_user_info`;
CREATE TABLE `t_auth_user_info` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `account` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户名',
  `account_pwd` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '密码',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `is_delete` tinyint DEFAULT '0',
  `mobile` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '绑定手机号',
  `auth_type` tinyint DEFAULT NULL COMMENT '认证方式1账户密码，2手机号，3邮箱',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '绑定邮箱',
  `email_bind_time` datetime DEFAULT NULL COMMENT '邮箱绑定时间',
  `user_id` bigint DEFAULT NULL COMMENT '用户id',
  `user_solt` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '对称加密字符串',
  `is_lock` tinyint DEFAULT NULL COMMENT '是否锁定 0 否 1是',
  `lock_start_time` datetime DEFAULT NULL COMMENT '锁定开始时间',
  `lock_end_time` datetime DEFAULT NULL COMMENT '锁定结束时间',
  `mobile_bind_time` datetime DEFAULT NULL COMMENT '手机号绑定时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `t_pc_user_info_account_index` (`account`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='权限用户表';

-- ----------------------------
-- Table structure for t_auth_user_model
-- ----------------------------
DROP TABLE IF EXISTS `t_auth_user_model`;
CREATE TABLE `t_auth_user_model` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '用户id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_delete` tinyint DEFAULT NULL COMMENT '是否删除；0正常1删除',
  `model_id` bigint NOT NULL COMMENT '模块id',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
  `modify_user_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新人姓名',
  `modify_user_id` bigint DEFAULT NULL COMMENT '最近更新人id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `role_id` (`model_id`,`user_id`) USING BTREE,
  KEY `t_role_user_user_id_index` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='模块用户中间表';

-- ----------------------------
-- Table structure for t_auth_user_req
-- ----------------------------
DROP TABLE IF EXISTS `t_auth_user_req`;
CREATE TABLE `t_auth_user_req` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL COMMENT '用户id',
  `refresh_token` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户token',
  `ip_white_list` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户ip白名单',
  `user_status` tinyint DEFAULT NULL COMMENT '用户状态；0:未登录，1:已登陆',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `is_delete` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;


-- ----------------------------
-- Table structure for t_base_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_base_menu`;
CREATE TABLE `t_base_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '标题',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '路径',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'name',
  `p_id` bigint DEFAULT NULL COMMENT '父级id',
  `service_number` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '服务编号',
  `service_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '指定服务id,手动维护',
  `is_deleted` tinyint(1) DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `template_code_id` bigint DEFAULT NULL COMMENT '对应版本 模板库代码id',
  `icon` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '菜单图标',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '菜单编码',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='菜单基础版本维护';

-- ----------------------------
-- Table structure for t_ent_pay
-- ----------------------------
DROP TABLE IF EXISTS `t_ent_pay`;
CREATE TABLE `t_ent_pay` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '@desc id',
  `pay_channel` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '@desc 支付渠道',
  `amount` int NOT NULL COMMENT '@desc 订单总金额，单位为分',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '@desc 企业付款备注',
  `business_params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '@desc 附加数据,格式为json字符串,怎么发送怎么返回',
  `spbill_create_ip` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc 终端ip',
  `transaction_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '@desc 支付流水号',
  `trade_status` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc 交易状态：1、 发起支付 2、 交易支付成功 3、交易支付失败 4、交易同步返回成功单业务未成功',
  `openid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc 用户标识',
  `check_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc 校验用户姓名选项',
  `re_user_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc 收款用户姓名',
  `create_time` datetime DEFAULT NULL COMMENT '@desc 创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '@desc 修改时间',
  `is_deleted` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '@desc 删除标识0否 1是',
  `pay_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '@desc 支付成功时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='服务商对个人支付表';

-- ----------------------------
-- Table structure for t_ent_pay_back_log
-- ----------------------------
DROP TABLE IF EXISTS `t_ent_pay_back_log`;
CREATE TABLE `t_ent_pay_back_log` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '@desc id',
  `ent_pay_id` bigint DEFAULT NULL COMMENT '@desc 关联支付单主键id',
  `pay_channel` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc 支付渠道:1-微信',
  `pay_channel_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc 支付渠道类型',
  `callback_context` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '@desc 回调信息json内容',
  `create_time` datetime DEFAULT NULL COMMENT '@desc 创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '@desc 更新时间',
  `is_deleted` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc 删除标识',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='商户支付个人回调日志表';

-- ----------------------------
-- Table structure for t_file
-- ----------------------------
DROP TABLE IF EXISTS `t_file`;
CREATE TABLE `t_file` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'URL地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `size` bigint DEFAULT NULL COMMENT '文件大小',
  `suffix` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文件后缀名',
  `original_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文件原名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=778 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='文件上传';

-- ----------------------------
-- Table structure for t_freight_template
-- ----------------------------
DROP TABLE IF EXISTS `t_freight_template`;
CREATE TABLE `t_freight_template` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '删除状态：0->未删除；1->已删除',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '模板名称',
  `product_id` bigint NOT NULL COMMENT '产品id',
  `charge_type` int DEFAULT NULL COMMENT '计费类型:0->按重量；1->按件数',
  `first_weight` decimal(10,2) DEFAULT '0.00' COMMENT '首重kg',
  `first_fee` decimal(10,2) DEFAULT '0.00' COMMENT '首费（元）',
  `continue_weight` decimal(10,2) DEFAULT '0.00' COMMENT '续重',
  `continue_fee` decimal(10,2) DEFAULT '0.00' COMMENT '续费（元）',
  `dest` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '目的地（省、市）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='运费模版';

-- ----------------------------
-- Table structure for t_log
-- ----------------------------
DROP TABLE IF EXISTS `t_log`;
CREATE TABLE `t_log` (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `log_type` int DEFAULT NULL COMMENT '日志类型（1登录日志，2操作日志）',
  `log_content` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '日志内容',
  `operate_type` int DEFAULT NULL COMMENT '操作类型',
  `userid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '操作用户账号',
  `username` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '操作用户名称',
  `ip` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'IP',
  `method` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '请求java方法',
  `request_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '请求路径',
  `request_param` longtext CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '请求参数',
  `request_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '请求类型',
  `cost_time` bigint DEFAULT NULL COMMENT '耗时',
  `create_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint(1) DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `index_table_userid` (`userid`) USING BTREE,
  KEY `index_logt_ype` (`log_type`) USING BTREE,
  KEY `index_operate_type` (`operate_type`) USING BTREE,
  KEY `index_log_type` (`log_type`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='系统日志表';

-- ----------------------------
-- Table structure for t_logger
-- ----------------------------
DROP TABLE IF EXISTS `t_logger`;
CREATE TABLE `t_logger` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `group_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `unit_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `tag` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `content` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `create_time` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `app_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_logistics_address
-- ----------------------------
DROP TABLE IF EXISTS `t_logistics_address`;
CREATE TABLE `t_logistics_address` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键 id',
  `name` varchar(64) DEFAULT NULL COMMENT '发货人-名称',
  `province` varchar(64) DEFAULT '' COMMENT '省',
  `province_id` varchar(20) DEFAULT '' COMMENT '省id',
  `city` varchar(64) DEFAULT '' COMMENT '市',
  `city_id` varchar(20) DEFAULT NULL COMMENT '市id',
  `country` varchar(64) DEFAULT NULL COMMENT '县',
  `country_id` varchar(20) DEFAULT NULL COMMENT '县id',
  `address` varchar(512) NOT NULL COMMENT '发货地址',
  `zip_code` varchar(64) NOT NULL COMMENT '邮编号:',
  `phone` varchar(45) NOT NULL,
  `def_send` int DEFAULT '0' COMMENT '是否为默认发货地址: 0=不是,1=是',
  `def_receive` int DEFAULT '0' COMMENT '是否为默认退货地址: 0=不是,1=是',
  `is_deleted` int NOT NULL DEFAULT '0' COMMENT '是否删除: 0=未删除,1=已删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `lat` varchar(64) DEFAULT '' COMMENT '纬度',
  `lng` varchar(64) DEFAULT '' COMMENT '经度',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=125 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='物流发货地址';

-- ----------------------------
-- Table structure for t_logistics_company
-- ----------------------------
DROP TABLE IF EXISTS `t_logistics_company`;
CREATE TABLE `t_logistics_company` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '快递公司名称',
  `code` varchar(32) NOT NULL COMMENT '快递公司代码',
  `is_deleted` int DEFAULT '0' COMMENT '是否删除,0=正常;1=删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='快递公司代码表';

-- ----------------------------
-- Table structure for t_logistics_deliver
-- ----------------------------
DROP TABLE IF EXISTS `t_logistics_deliver`;
CREATE TABLE `t_logistics_deliver` (
  `id` bigint NOT NULL COMMENT '主键 id',
  `name` varchar(128) NOT NULL COMMENT '物流发货单名称',
  `code` bigint NOT NULL COMMENT '物流编号',
  `type` int NOT NULL COMMENT '发货单状态	: 1=代发货,2=待提货,3=已完成,4=已关闭',
  `is_deleted` int NOT NULL DEFAULT '0' COMMENT '是否删除 0=正常,1=已删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for t_logistics_deliver_order_relation
-- ----------------------------
DROP TABLE IF EXISTS `t_logistics_deliver_order_relation`;
CREATE TABLE `t_logistics_deliver_order_relation` (
  `id` bigint NOT NULL COMMENT '主键id',
  `logistics_id` bigint NOT NULL COMMENT '物流发货单id',
  `order_id` bigint NOT NULL COMMENT '订单id',
  `good_obj` varchar(1024) NOT NULL COMMENT '订单包含商品信息_json格式[{"id":12,"code":"2312"}]',
  `logistics_name` varchar(64) NOT NULL DEFAULT '' COMMENT '物流公司名称',
  `logistics_code` varchar(255) NOT NULL DEFAULT '' COMMENT '物流公司单号',
  `is_send` int DEFAULT NULL COMMENT '是否发货,0=未发货,1=已发货',
  `type` int DEFAULT NULL COMMENT '物流单,状态:1=代发货',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='物流发货单关联 订单表';

-- ----------------------------
-- Table structure for t_logistics_express
-- ----------------------------
DROP TABLE IF EXISTS `t_logistics_express`;
CREATE TABLE `t_logistics_express` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键 id',
  `name` varchar(32) NOT NULL COMMENT '快递公司名称',
  `code` varchar(16) NOT NULL COMMENT '快递公司code',
  `phone` varchar(16) NOT NULL COMMENT '快递公司客服电话',
  `address_id` bigint NOT NULL COMMENT '地址id 关联物流发货地址表id',
  `customer_name` varchar(64) NOT NULL COMMENT '客户id',
  `customer_password` varchar(64) NOT NULL COMMENT '客户密匙',
  `link_name` varchar(16) NOT NULL COMMENT '负责人',
  `link_tel` varchar(16) NOT NULL COMMENT '负责人电话',
  `status` int NOT NULL COMMENT '状态 默认为1（审核通过）',
  `is_deleted` int NOT NULL DEFAULT '0' COMMENT '是否删除: 0=未删除,1=已删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='物流公司发货地址设置';

-- ----------------------------
-- Table structure for t_logistics_express_print
-- ----------------------------
DROP TABLE IF EXISTS `t_logistics_express_print`;
CREATE TABLE `t_logistics_express_print` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键 id',
  `device_type` varchar(10) NOT NULL COMMENT '设备类型',
  `device_model` varchar(64) NOT NULL COMMENT '设备型号',
  `device_name` varchar(64) NOT NULL COMMENT '设备名称',
  `device_no` varchar(64) NOT NULL COMMENT '打印机机身号',
  `device_key` varchar(64) NOT NULL COMMENT '打印机KEY',
  `status` int NOT NULL COMMENT '状态',
  `is_deleted` int NOT NULL DEFAULT '0' COMMENT '是否删除: 0=未删除,1=已删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='电子面单打印机设置';

-- ----------------------------
-- Table structure for t_logistics_include_postage
-- ----------------------------
DROP TABLE IF EXISTS `t_logistics_include_postage`;
CREATE TABLE `t_logistics_include_postage` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键 id',
  `logistics_id` bigint NOT NULL COMMENT 't_logistics_template 主键 id',
  `type` int NOT NULL COMMENT '包邮条件类型: 0= 按件数包邮,1=按重量包邮,2=按金额包邮,3=件数+金额 4=重量+金额 包邮',
  `region` json NOT NULL COMMENT '运送地区 json 格式 {"provinceid": ["cityId","cityId2"]}',
  `piece_num` int DEFAULT NULL COMMENT '包邮件数',
  `weight` decimal(18,2) DEFAULT NULL COMMENT '包邮重量 单位: 千克(kg)',
  `amount_num` decimal(18,2) DEFAULT NULL COMMENT '包邮金额',
  `is_deleted` int NOT NULL DEFAULT '0' COMMENT '是否删除 0=未删除,1=已删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='运费模板_包邮设置表';

-- ----------------------------
-- Table structure for t_logistics_manage
-- ----------------------------
DROP TABLE IF EXISTS `t_logistics_manage`;
CREATE TABLE `t_logistics_manage` (
  `id` bigint NOT NULL COMMENT '主键 id',
  `state` int NOT NULL DEFAULT '0' COMMENT '是否开启物流配送;0=不开启,1=开启',
  `deliver_build_time` varchar(128) NOT NULL COMMENT '生成订单时间: HH:mm  格式: ["HH:mm","HH:mm","HH:mm","HH:mm"] 最多四个',
  `is_deleted` int NOT NULL DEFAULT '0' COMMENT '是否删除 0=未删除,1=已删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='物流基础设置';

-- ----------------------------
-- Table structure for t_logistics_shipping_model
-- ----------------------------
DROP TABLE IF EXISTS `t_logistics_shipping_model`;
CREATE TABLE `t_logistics_shipping_model` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键 id',
  `logistics_id` bigint NOT NULL COMMENT '同 t_logistics_template 主键 id 一致.',
  `valuation_model` int NOT NULL COMMENT '计价方式: 1=按件数,2=按重量',
  `first_piece` int DEFAULT NULL COMMENT '首件数',
  `first_weight` decimal(18,2) DEFAULT NULL COMMENT '首重 单位: 千克(kg)',
  `first_amount` decimal(18,2) DEFAULT NULL,
  `second_piece` int DEFAULT NULL COMMENT '续件数量',
  `second_weight` decimal(18,2) DEFAULT NULL COMMENT '续重 单位 千克/kg',
  `second_amount` decimal(18,2) DEFAULT NULL COMMENT '续费, 单位: 元',
  `region_json` json NOT NULL COMMENT '运送地区 json 格式 {"provinceid": ["cityId","cityId2"]}',
  `is_deleted` int NOT NULL DEFAULT '0' COMMENT '是否删除 0=正常,1=已删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=427 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='物流基础运送方式表';

-- ----------------------------
-- Table structure for t_logistics_shop
-- ----------------------------
DROP TABLE IF EXISTS `t_logistics_shop`;
CREATE TABLE `t_logistics_shop` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键 id',
  `logistics_company_id` bigint NOT NULL COMMENT '物流公司表id',
  `is_default` int NOT NULL DEFAULT '0' COMMENT '是否删除 0=未删除,1=已删除',
  `is_deleted` int NOT NULL DEFAULT '0' COMMENT '是否删除 0=未删除,1=已删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='店铺物流公司绑定表';

-- ----------------------------
-- Table structure for t_logistics_template
-- ----------------------------
DROP TABLE IF EXISTS `t_logistics_template`;
CREATE TABLE `t_logistics_template` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL COMMENT '模板名称',
  `is_incl_postage` int NOT NULL DEFAULT '0' COMMENT '是否包邮: 0=不包邮,1=包邮',
  `valuation_model` int NOT NULL COMMENT '计价方式: 1=按件数,2=按重量.',
  `choice_condition_postage` int NOT NULL DEFAULT '0' COMMENT '是否指定条件包邮: 0=不包邮,1=指定条件包邮',
  `is_deleted` int DEFAULT '0' COMMENT '是否删除,0=正常;1=删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=238 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='运费模板_基础设置表';

-- ----------------------------
-- Table structure for t_member_action_template
-- ----------------------------
DROP TABLE IF EXISTS `t_member_action_template`;
CREATE TABLE `t_member_action_template` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id ',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '行为模版内容，变量用var表示',
  `is_deleted` int NOT NULL COMMENT '删除状态 0 未删除 1 已删除',
  `create_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户行为模版表';

-- ----------------------------
-- Table structure for t_mini_account
-- ----------------------------
DROP TABLE IF EXISTS `t_mini_account`;
CREATE TABLE `t_mini_account` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id ',
  `user_id` varchar(28) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户id',
  `country` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '所在国家',
  `province` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '所在省份',
  `city` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '所在城市',
  `language` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '所用的语言 en 英文 - zh_CN 简体中文 -zh_TW 繁体中文 \r',
  `nike_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户名称',
  `avatar_url` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '头像url',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '手机号码',
  `email` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '邮箱',
  `gender` tinyint(1) DEFAULT '0' COMMENT '性别 0：未知、1：男、2：女',
  `first_login_time` datetime DEFAULT NULL COMMENT '首次登陆小程序时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `is_deleted` tinyint(1) DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `whether_authorization` tinyint(1) DEFAULT NULL COMMENT '是否授权过小程序 false 未授权  turn授权过',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=342 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='小程序用户表';

-- ----------------------------
-- Table structure for t_mini_account_address
-- ----------------------------
DROP TABLE IF EXISTS `t_mini_account_address`;
CREATE TABLE `t_mini_account_address` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `is_deleted` tinyint DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '收货人姓名',
  `phone` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '收货人联系电话',
  `post_code` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邮编',
  `is_default` tinyint DEFAULT NULL COMMENT '是否默认 0-非默认 1-默认',
  `province` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT '0' COMMENT '省',
  `city` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT '0' COMMENT '市',
  `county` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT '0' COMMENT '区',
  `detail_Info` varchar(120) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT '' COMMENT '详细收货地址信息',
  `user_id` varchar(28) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户id',
  `location` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '经度,维度',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='会员地址表';

-- ----------------------------
-- Table structure for t_mini_account_collect
-- ----------------------------
DROP TABLE IF EXISTS `t_mini_account_collect`;
CREATE TABLE `t_mini_account_collect` (
  `collect_id` bigint NOT NULL AUTO_INCREMENT COMMENT '收藏表id',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户id',
  `product_id` bigint DEFAULT NULL COMMENT '商品id',
  `product_pic` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '商品主图',
  `product_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '商品名称',
  `status` int DEFAULT NULL COMMENT '商品状态  0-上架 1-下架 2-售罄',
  `product_price` decimal(10,2) DEFAULT NULL COMMENT '商品实际售价',
  `original_price` decimal(10,2) DEFAULT NULL COMMENT '指导价划线价',
  `remark` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  PRIMARY KEY (`collect_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='用户收藏表';

-- ----------------------------
-- Table structure for t_mini_account_extends
-- ----------------------------
DROP TABLE IF EXISTS `t_mini_account_extends`;
CREATE TABLE `t_mini_account_extends` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id ',
  `user_id` varchar(28) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户id',
  `last_deal_time` datetime DEFAULT NULL COMMENT '最后交易时间',
  `is_blacklist` tinyint DEFAULT NULL COMMENT '是否黑名单用户 0-否 1-是',
  `consume_num` int DEFAULT NULL COMMENT '消费次数',
  `community_id` bigint DEFAULT '0' COMMENT '所在小区id',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `is_deleted` tinyint DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `consume_totle_money` decimal(10,2) DEFAULT NULL COMMENT '交易总额',
  `join_blacklist_time` datetime DEFAULT NULL COMMENT '加入黑名单时间',
  `community_type` tinyint(1) DEFAULT '0' COMMENT '用户团长身份类型  1-团长  2-区域团长',
  `shop_user_id` varchar(28) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '店铺用户id',
  `current_status` tinyint(1) DEFAULT '0' COMMENT '当前使用  0未使用 1-当前使用',
  `last_choose_lcation` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '经度,维度',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `user_id` (`user_id`) USING BTREE,
  KEY `community_id` (`community_id`) USING BTREE,
  KEY `shop_user_id` (`shop_user_id`) USING BTREE,
  KEY `is_deleted` (`is_deleted`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=325 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='用户信息扩展表';

-- ----------------------------
-- Table structure for t_mini_account_foot_mark
-- ----------------------------
DROP TABLE IF EXISTS `t_mini_account_foot_mark`;
CREATE TABLE `t_mini_account_foot_mark` (
  `foot_mark_id` bigint NOT NULL AUTO_INCREMENT COMMENT '足迹id',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户id',
  `product_id` bigint DEFAULT NULL COMMENT '商品id',
  `product_pic` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '商品主图',
  `product_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '商品名称',
  `status` int DEFAULT NULL COMMENT '商品状态  0-上架 1-下架 2-售罄',
  `product_price` decimal(10,2) DEFAULT NULL COMMENT '商品实际售价',
  `original_price` decimal(10,2) DEFAULT NULL COMMENT '指导价划线价',
  `remark` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  PRIMARY KEY (`foot_mark_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=539 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_mini_account_oauths
-- ----------------------------
DROP TABLE IF EXISTS `t_mini_account_oauths`;
CREATE TABLE `t_mini_account_oauths` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id ',
  `user_id` varchar(28) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户唯一id',
  `oauth_type` tinyint DEFAULT NULL COMMENT '授权类型 1-微信小程序,2-公众号',
  `open_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '第三方openId',
  `union_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '第三方unionid',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `is_deleted` tinyint DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=342 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='用户第三方Id表';

-- ----------------------------
-- Table structure for t_mini_account_restrict
-- ----------------------------
DROP TABLE IF EXISTS `t_mini_account_restrict`;
CREATE TABLE `t_mini_account_restrict` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id ',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `is_deleted` tinyint DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `restrict_type` tinyint(1) DEFAULT NULL COMMENT '限制类型 1-下单 2-评论',
  `user_id` varchar(28) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='黑名单限制权限表';

-- ----------------------------
-- Table structure for t_mini_account_tag
-- ----------------------------
DROP TABLE IF EXISTS `t_mini_account_tag`;
CREATE TABLE `t_mini_account_tag` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `tag_name` varchar(24) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '组名',
  `is_deleted` tinyint(1) DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='用户标签表';

-- ----------------------------
-- Table structure for t_mini_account_tag_group
-- ----------------------------
DROP TABLE IF EXISTS `t_mini_account_tag_group`;
CREATE TABLE `t_mini_account_tag_group` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `tag_id` int NOT NULL COMMENT '组id',
  `is_deleted` tinyint(1) DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `user_id` varchar(28) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `user_id` (`user_id`) USING BTREE,
  KEY `is_deleted` (`is_deleted`) USING BTREE,
  KEY `tag_id` (`tag_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=120 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='用户所属分组表';


-- ----------------------------
-- Table structure for t_mini_info
-- ----------------------------
DROP TABLE IF EXISTS `t_mini_info`;
CREATE TABLE `t_mini_info` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `account_id` bigint DEFAULT NULL COMMENT '拥有者id',
  `mini_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '小程序名称',
  `app_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'appID',
  `authorizer_flag` tinyint(1) DEFAULT '0' COMMENT '授权状态    0-未授权 1-授权',
  `expiration_time` datetime DEFAULT NULL COMMENT '到期时间',
  `forbid_status` tinyint(1) DEFAULT '0' COMMENT '是否禁用  0-未禁用 1-已禁用',
  `account_type` tinyint(1) DEFAULT NULL COMMENT '帐号类型（1：订阅号，2：服务号，3：小程序）',
  `principal_type` tinyint DEFAULT NULL COMMENT '主体类型（1：企业）',
  `principal_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '主体名称',
  `qualification_verify` tinyint(1) DEFAULT NULL COMMENT '是否资质认证（true：是，false：否）若是，拥有微信认证相关的权限。',
  `signature` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '功能介绍',
  `signature_modify_used_count` tinyint(1) DEFAULT NULL COMMENT '功能介绍已使用修改次数（本月）',
  `head_image_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '头像url',
  `head_modify_used_count` tinyint(1) DEFAULT NULL COMMENT '头像已使用修改次数（本年）',
  `head_modify_quota` tinyint(1) DEFAULT NULL COMMENT '头像修改次数总额度（本年）',
  `mini_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '小程序码',
  `qrcode` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '小程序二维码',
  `experience_code` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '体验二维码',
  `certificate_path` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '商户支付证书路径',
  `mch_key` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '支付秘钥',
  `mch_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '商户号',
  `run_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '运行状态  0-未上传代码 1-已上传代码',
  `auth_time` datetime DEFAULT NULL COMMENT '授权时间(t_miniauth_info表中的创建时间)',
  `remark` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '后台备注',
  `current_version_id` bigint DEFAULT NULL COMMENT '当前模板代码版本id',
  `template_detail_minis_id` bigint DEFAULT NULL COMMENT '当前模板代码版本中的某个小程序版本id(t_platform_shop_template_detail_minis表id)',
  `upload_template_id` bigint DEFAULT NULL COMMENT '最新上传的模板id',
  `current_aidit_id` bigint DEFAULT NULL COMMENT '当前版本审核记录id',
  `aidit_id` bigint DEFAULT NULL COMMENT '审核中版本审核记录id',
  `is_deleted` tinyint(1) DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `service_type_info` tinyint DEFAULT NULL COMMENT '授权方类型0代表订阅号，1代表由历史老帐号升级后的订阅号，2代表服务号 3代码小程序',
  `verify_type_info` tinyint DEFAULT NULL COMMENT '授权方认证类型，-1代表未认证，0代表微信认证，1代表新浪微博认证，2代表腾讯微博认证，3代表已资质认证通过但还未通过名称认证，4代表已资质认证通过、还未通过名称认证，但通过了新浪微博认证，5代表已资质认证通过、还未通过名称认证，但通过了腾讯微博认证',
  `business_info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用以了解以下功能的开通状况（0代表未开通，1代表已开通）： open_store:是否开通微信门店功能 open_scan:是否开通微信扫商品功能 open_pay:是否开通微信支付功能 open_card:是否开通微信卡券功能 open_shake:是否开通微信摇一摇功能',
  `user_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '原始id',
  `signature_modify_quota` tinyint(1) DEFAULT NULL COMMENT '功能介绍修改次数总额度（本月）',
  `realname_status` tinyint(1) DEFAULT NULL COMMENT '实名验证状态（1：实名验证成功，2：实名验证中，3：实名验证失败） 0-false 1-true',
  `naming_verify` tinyint(1) DEFAULT NULL COMMENT '是否名称认证（true：是，false：否）对于公众号（订阅号、服务号），是名称认证，微信客户端才会有微信认证打勾的标识。',
  `annual_review` tinyint(1) DEFAULT NULL COMMENT '是否需要年审（true：是，false：否）（qualification_verify = true时才有该字段）',
  `annual_review_begin_time` datetime DEFAULT NULL COMMENT '年审开始时间，时间戳（qualification_verify = true时才有该字段）',
  `annual_review_end_time` datetime DEFAULT NULL COMMENT '年审截止时间，时间戳（qualification_verify = true时才有该字段）',
  `default_status` tinyint(1) DEFAULT NULL COMMENT '是否默认 0否 1默认',
  `alias` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '公众号绑定的微信账号',
  `combo_id` bigint DEFAULT NULL COMMENT '套餐id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='小程序基本信息';

-- ----------------------------
-- Table structure for t_mini_mp_conf
-- ----------------------------
DROP TABLE IF EXISTS `t_mini_mp_conf`;
CREATE TABLE `t_mini_mp_conf` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_deleted` tinyint(1) DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除 ',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `app_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '公众号appId',
  `app_secret` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '公众号appSecret',
  `app_aes_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '公众号aeskey',
  `app_token` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '公众号appToken',
  `mini_id` bigint DEFAULT NULL COMMENT '小程序id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='小程序关联公众号配置';

-- ----------------------------
-- Table structure for t_mini_subscriberi_base
-- ----------------------------
DROP TABLE IF EXISTS `t_mini_subscriberi_base`;
CREATE TABLE `t_mini_subscriberi_base` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `is_deleted` tinyint(1) DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除 ',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `use_type` tinyint(1) DEFAULT NULL COMMENT '使用类型 1-买家通知 2-商家通知',
  `version` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '对应版本',
  `message_type` tinyint(1) DEFAULT NULL COMMENT '消息类别 1-订单消息 2-售后消息 3-用户消息 4-营销活动',
  `template_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '默认模板id, 目前仅商家通知使用公众号通知才有默认模板id',
  `title` varchar(28) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '标题名称',
  `t_id` int DEFAULT NULL COMMENT '订阅模板标题id',
  `type` tinyint(1) DEFAULT NULL COMMENT '订阅模板订阅类型 1-永久订阅  2-一次性订阅 3-公众号模板消息',
  `category_id` int DEFAULT NULL COMMENT '订阅模板所属二级行业类目id',
  `k_ids` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '订阅模板-kid按顺序分割,例子1,3,5',
  `rules` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '订阅模板-参数规则名跟kid排序相对应amount,thingthing',
  `first_class` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '订阅模板一级类目名称',
  `first_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '订阅模板一级行业类目id',
  `second_class` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '订阅模板二级类目名称',
  `example_json` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '提供预览值',
  `send_rule` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '推送规则',
  `mini_open` tinyint(1) DEFAULT NULL COMMENT '小程序订阅消息是否开启 0-关闭  1-开启',
  `code_open` tinyint(1) DEFAULT NULL COMMENT '短信模板消息是否开启 0-关闭  1-开启',
  `mp_open` tinyint(1) DEFAULT NULL COMMENT '公众号模板消息是否开启 0-关闭  1-开启',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='订阅模板基础表提供版本默认值';

-- ----------------------------
-- Table structure for t_mini_subscriberi_message
-- ----------------------------
DROP TABLE IF EXISTS `t_mini_subscriberi_message`;
CREATE TABLE `t_mini_subscriberi_message` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `is_deleted` tinyint(1) DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除 ',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `message_type` tinyint DEFAULT NULL COMMENT '消息类别 1-订单消息 2-售后消息 3-用户消息 4-营销活动',
  `msg_title` varchar(28) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '消息标题',
  `template_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '模板消息id',
  `use_status` tinyint(1) DEFAULT NULL COMMENT '开启状态 0-未使用  1-使用',
  `k_ids` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'kid按顺序分割,例子1,3,5',
  `rules` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '参数规则名跟kid排序相对应amount1,thing3,thing5',
  `example_json` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '提供预览值',
  `second_class` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '二级类目名称',
  `mini_id` bigint DEFAULT NULL COMMENT '小程序表id',
  `send_rule` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '推送规则',
  `use_type` tinyint(1) DEFAULT NULL COMMENT '使用类型 1-买家通知 2-商家通知',
  `version` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '对应版本',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='小程序拥有的订阅消息表';

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order` (
  `id` bigint NOT NULL COMMENT '订单id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '删除状态：0->未删除；1->已删除',
  `user_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户id',
  `user_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户帐号',
  `user_avatar_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户头像',
  `user_note` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户备注',
  `type` int DEFAULT NULL COMMENT '订单类型:100->商超订单;101->拼团订单;102->秒杀订单;',
  `total_amount` decimal(10,2) DEFAULT NULL COMMENT '订单总金额',
  `discounts_amount` decimal(10,2) DEFAULT NULL COMMENT '实际金额',
  `pay_amount` decimal(10,2) DEFAULT NULL COMMENT '应付金额（实际支付金额）',
  `freight_amount` decimal(10,2) DEFAULT NULL COMMENT '运费金额',
  `promotion_amount` decimal(10,2) DEFAULT NULL COMMENT '促销优化金额（促销价、满减、会员价）',
  `coupon_id` bigint DEFAULT NULL COMMENT '优惠券id',
  `pay_type` int DEFAULT NULL COMMENT '支付方式:100->未支付;101->余额支付;102->微信支付;103->好友代付;',
  `transaction_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '支付流水号',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `source_type` int DEFAULT NULL COMMENT '订单来源:100->小程序;101->接龙;',
  `close_time` datetime DEFAULT NULL COMMENT '关闭时间',
  `status` int DEFAULT NULL COMMENT '订单状态:100->待付款;101->待发货;102->配送中;103->待提货;104->等待评价;105->已完成;200->已退款;201->部分退款;300->支付超时关闭;301->买家取消关闭;302->卖家取消关闭;303->换货成功关闭;',
  `note` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '订单备注',
  `comment_time` datetime DEFAULT NULL COMMENT '评价时间',
  `custom_form` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '自定义字段:[{"key":"IDCard","value":"332022199001010011"},{"key":"Phone","value":"13956852259"}]',
  `complete_time` datetime DEFAULT NULL COMMENT '完成时间',
  `refund_amount` decimal(10,2) DEFAULT NULL COMMENT '退款金额',
  `refund_transaction_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '退款流水号',
  `estimated_delivery_time` datetime DEFAULT NULL COMMENT '预计到货时间',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='订单表';

-- ----------------------------
-- Table structure for t_order_delivery
-- ----------------------------
DROP TABLE IF EXISTS `t_order_delivery`;
CREATE TABLE `t_order_delivery` (
  `order_id` bigint(19) unsigned zerofill NOT NULL COMMENT '订单id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '删除状态：0->未删除；1->已删除',
  `delivery_type` int DEFAULT NULL COMMENT '配送方式:100->用户自提;101->送货上门;102->物流配送;',
  `delivery_time` datetime DEFAULT NULL COMMENT '发货时间',
  `delivery_company` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '物流公司',
  `delivery_sn` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '物流单号',
  `receiver_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '收货人姓名',
  `receiver_phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收货人电话',
  `receiver_post_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '收货人邮编',
  `receiver_province` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '省份/直辖市',
  `receiver_city` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '城市',
  `receiver_region` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '区',
  `receiver_detail_address` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '详细地址',
  `is_received` tinyint unsigned DEFAULT NULL COMMENT '确认收货状态：0->未确认；1->已确认',
  `receive_time` datetime DEFAULT NULL COMMENT '确认收货时间',
  `delivery_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '物流公司编码',
  `package_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '集包地信息',
  `package_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '集包地信息',
  `sorting_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '分拣码',
  `delivery_template_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '发货订阅消息',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='订单收货信息表';

-- ----------------------------
-- Table structure for t_order_evaluate
-- ----------------------------
DROP TABLE IF EXISTS `t_order_evaluate`;
CREATE TABLE `t_order_evaluate` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '删除状态：0->未删除；1->已删除',
  `user_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `user_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户帐号',
  `user_avatar_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户头像',
  `order_id` bigint unsigned NOT NULL COMMENT '订单id',
  `shop_rate` int DEFAULT NULL COMMENT '商城模式下店铺评分',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_user_id` (`user_id`) USING BTREE,
  KEY `idx_order_id` (`order_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='订单评论表';

-- ----------------------------
-- Table structure for t_order_item
-- ----------------------------
DROP TABLE IF EXISTS `t_order_item`;
CREATE TABLE `t_order_item` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '删除状态：0->未删除；1->已删除',
  `order_id` bigint DEFAULT NULL COMMENT '订单id',
  `product_id` bigint DEFAULT NULL COMMENT '商品id',
  `product_pic` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品图片',
  `product_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品名',
  `product_sn` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '货号',
  `product_price` decimal(10,2) DEFAULT NULL COMMENT '销售价格',
  `product_original_price` decimal(10,2) DEFAULT NULL COMMENT '指导价（划线价）',
  `product_quantity` int DEFAULT NULL COMMENT '购买数量',
  `product_sku_id` bigint DEFAULT NULL COMMENT '商品sku编号',
  `product_sku_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品sku条码',
  `promotion_amount` decimal(10,2) DEFAULT NULL COMMENT '商品促销分解金额',
  `coupon_amount` decimal(10,2) DEFAULT NULL COMMENT '优惠券优惠分解金额',
  `real_amount` decimal(10,2) DEFAULT NULL COMMENT '该商品经过优惠后的最终金额',
  `specs` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品的销售属性',
  `provider_id` bigint DEFAULT NULL COMMENT '供应商id',
  `refund_amount` decimal(10,2) DEFAULT NULL COMMENT '退款金额',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_order_id` (`order_id`) USING BTREE,
  KEY `idx_product_id` (`product_id`) USING BTREE,
  KEY `idx_product_sku_id` (`product_sku_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=329 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='订单中所包含的商品';

-- ----------------------------
-- Table structure for t_order_product_evaluate
-- ----------------------------
DROP TABLE IF EXISTS `t_order_product_evaluate`;
CREATE TABLE `t_order_product_evaluate` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '删除状态：0->未删除；1->已删除',
  `order_id` bigint unsigned DEFAULT NULL COMMENT '订单id',
  `rate` int unsigned DEFAULT NULL COMMENT '商品评分',
  `product_id` bigint DEFAULT NULL COMMENT '商品id',
  `product_price` decimal(10,2) DEFAULT NULL COMMENT '销售价格',
  `product_pic` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品图片',
  `product_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品名',
  `product_quantity` int DEFAULT NULL COMMENT '购买数量',
  `product_sku_id` bigint DEFAULT NULL COMMENT '商品sku编号',
  `specs` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品的销售属性',
  `comment` varchar(600) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '评论内容',
  `picture` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '上传的图片',
  `choice` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否精选：0->否；1->精选',
  `reply` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '卖家回复',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_order_id` (`order_id`) USING BTREE,
  KEY `idx_product_id` (`product_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='产品评论表';

-- ----------------------------
-- Table structure for t_order_setting
-- ----------------------------
DROP TABLE IF EXISTS `t_order_setting`;
CREATE TABLE `t_order_setting` (
  `id` bigint(20) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '删除状态：0->未删除；1->已删除',
  `flash_order_overtime` bigint DEFAULT NULL COMMENT '秒杀订单超时关闭时间(分)',
  `normal_order_overtime` bigint DEFAULT NULL COMMENT '正常订单超时时间(分)',
  `confirm_overtime` int DEFAULT NULL COMMENT '发货后自动确认收货时间（天）',
  `finish_overtime` int DEFAULT NULL COMMENT '自动完成交易时间，不能申请售后（天）',
  `comment_overtime` int DEFAULT NULL COMMENT '订单完成后自动好评时间（天）',
  `open_evaluate` tinyint(1) DEFAULT NULL COMMENT '是否开启订单评论：0->不开启;1->开启;',
  `afs_apply_number` int DEFAULT NULL COMMENT '最大售后申请次数',
  `merchant_confirm_overtime` int DEFAULT NULL COMMENT '商家最大审核期限(天)',
  `user_return_overtime` int DEFAULT NULL COMMENT '用户最大退货期限(天)',
  `kd_app_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '快递AppId',
  `kd_app_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '快递AppKey',
  `payment_model` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '支付方式：1->微信支付;2->余额支付;3->好友代付;',
  `custom_from` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '自定义表单',
  `order_notify` tinyint(1) DEFAULT NULL COMMENT '是否开启下单通知：0->关闭；1->开启',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='订单设置表';

-- ----------------------------
-- Table structure for t_order_share_setting
-- ----------------------------
DROP TABLE IF EXISTS `t_order_share_setting`;
CREATE TABLE `t_order_share_setting` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '删除状态：0->未删除；1->已删除',
  `background` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '背景图',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '标题',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='订单晒单设置表';

-- ----------------------------
-- Table structure for t_partner_info
-- ----------------------------
DROP TABLE IF EXISTS `t_partner_info`;
CREATE TABLE `t_partner_info` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `is_deleted` tinyint(1) DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `platfrom_account_id` bigint DEFAULT NULL COMMENT '平台用户表id',
  `user_id` bigint DEFAULT NULL COMMENT '小程序用户id',
  `mp_authorized` tinyint(1) DEFAULT NULL COMMENT '合伙人是否授权 0未授权 1-已授权',
  `real_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '真实姓名',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '手机号码',
  `passwd` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '密码',
  `location` varchar(28) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '店铺地理位置所在经纬度',
  `adders` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '店铺详细地址地址',
  `partner_schema` tinyint(1) DEFAULT NULL COMMENT '合伙人模式 1-加盟模式 2-子公司模式',
  `salt` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '对称加密字符串',
  `create_type` tinyint(1) DEFAULT NULL COMMENT '创建方式  1-申请  2-商家创建',
  `region` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '省市区 逗号分隔',
  `default_partner` tinyint(1) DEFAULT NULL COMMENT '是否商家合伙人账号,也就是商家默认合伙人 0否 1是',
  `disable_status` tinyint(1) DEFAULT NULL COMMENT '禁用状态 0未禁用 1-已禁用',
  `audit_status` tinyint(1) DEFAULT NULL COMMENT '审核状态 0审核中 1审核通过  2-拒绝',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='合伙人信息表';

-- ----------------------------
-- Table structure for t_payment
-- ----------------------------
DROP TABLE IF EXISTS `t_payment`;
CREATE TABLE `t_payment` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `is_deleted` tinyint(1) DEFAULT '0' COMMENT '删除状态：0->未删除；1->已删除',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_time` datetime DEFAULT NULL COMMENT '接受服务请求时间',
  `timeout_express` datetime DEFAULT NULL COMMENT '该笔订单允许的最晚付款时间，逾期将关闭交易。',
  `pay_channel` tinyint DEFAULT NULL COMMENT '支付渠道:1-微信',
  `fee_type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '货币类型 CNY：人民币,符合ISO 4217标准的三位字母代码',
  `total_fee` decimal(10,2) DEFAULT NULL COMMENT '订单总金额，单位为元，精确到小数点后两位',
  `body` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '对一笔交易的具体描述信息',
  `business_params` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '附加数据,格式为json字符串,怎么发送怎么返回',
  `terminal_ip` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '终端ip',
  `transaction_id` bigint DEFAULT NULL COMMENT '支付流水号',
  `business_notify_url` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '业务回调地址',
  `third_party_notify_status` tinyint(1) DEFAULT '0' COMMENT '第三方回调是否已处理 0-未处理 1-已处理',
  `business_notify_status` tinyint(1) DEFAULT NULL COMMENT '业务方是否已正确处理  0-未处理 1-已处理',
  `trade_status` tinyint DEFAULT NULL COMMENT '交易状态：1（交易创建，等待买家付款）、2（未付款交易超时关闭）、3（交易支付成功）',
  `third_party_notify_number` tinyint(1) DEFAULT NULL COMMENT '回调次数',
  `route_key` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '路由键',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=197 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_payment_record
-- ----------------------------
DROP TABLE IF EXISTS `t_payment_record`;
CREATE TABLE `t_payment_record` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `request_params` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '请求对象数据',
  `send_param` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '最终发送数据',
  `notify_param` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '第三方回调的数据',
  `payment_id` bigint DEFAULT NULL COMMENT '支付 记录表id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=164 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_payment_refund
-- ----------------------------
DROP TABLE IF EXISTS `t_payment_refund`;
CREATE TABLE `t_payment_refund` (
  `order_id` bigint NOT NULL COMMENT '订单id',
  `asyn_request` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '请求退款值',
  `asyn_result` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '请求退款同步返回值',
  `syn_callback` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '回调数据',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_deleted` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '删除标识',
  `id` bigint NOT NULL AUTO_INCREMENT,
  `route_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '路由键',
  PRIMARY KEY (`id`,`order_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='支付服务退款log';

-- ----------------------------
-- Table structure for t_payment_wechat
-- ----------------------------
DROP TABLE IF EXISTS `t_payment_wechat`;
CREATE TABLE `t_payment_wechat` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `trade_type` int DEFAULT NULL COMMENT '交易类型 1-JSAPI支付（或小程序支付）、2-Native支付、3-app支付，4-H5支付，',
  `payment_id` bigint DEFAULT NULL COMMENT '主记录表id',
  `out_trade_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '业务订单号',
  `subject` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '商品的标题/交易标题/订单标题/订单关键字等。',
  `open_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户openId',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=197 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_platform_account_balance_record
-- ----------------------------
DROP TABLE IF EXISTS `t_platform_account_balance_record`;
CREATE TABLE `t_platform_account_balance_record` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `is_deleted` tinyint DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `account_id` bigint DEFAULT NULL COMMENT '商户id',
  `consumption_type` tinyint DEFAULT NULL COMMENT '操作类型 1:充值 2:套餐购买 3:套餐续费',
  `order_num` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '消费订单号(跟着操作类型走)',
  `before_amount` decimal(10,2) DEFAULT NULL COMMENT '变更之前金额',
  `after_amount` decimal(10,2) DEFAULT NULL COMMENT '变更之后金额',
  `amount` decimal(10,2) DEFAULT NULL COMMENT '变更金额',
  `type` tinyint(1) DEFAULT NULL COMMENT '变更类型：1:收入2:支出',
  `invoice_status` tinyint(1) DEFAULT NULL COMMENT '开票状态  0-未开票 1-已开票',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `is_deleted` (`is_deleted`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='账号余额明细表';

-- ----------------------------
-- Table structure for t_platform_account_info
-- ----------------------------
DROP TABLE IF EXISTS `t_platform_account_info`;
CREATE TABLE `t_platform_account_info` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '不加密的密码-临时用',
  `passwd` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '账号登录密码-加密',
  `salt` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '盐值',
  `city` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '所在城市',
  `language` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '所用的语言 en 英文 - zh_CN 简体中文 -zh_TW 繁体中文 \r',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `access_token` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '登录token-微信网页应用',
  `refresh_token` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '刷新token-微信网页应用 refresh_token拥有较长的有效期（30天），当refresh_token失效的后，需要用户重新授权。',
  `nike_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户名称',
  `avatar_url` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '头像url',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '手机号码',
  `email` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '电子发票接收邮箱',
  `gender` tinyint(1) DEFAULT '0' COMMENT '性别 0：未知、1：男、2：女',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `is_deleted` tinyint(1) DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `open_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '第三方平台openid',
  `union_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '第三方平台unionid,针对一个微信开放平台帐号下的应用',
  `province` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '省份',
  `country` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '国家',
  `privilege` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户特权信息，json数组，如微信沃卡用户为（chinaunicom）',
  `ref_expires_time` datetime DEFAULT NULL COMMENT 'refresh_token到期时间',
  `access_expires_time` datetime DEFAULT NULL COMMENT 'access_token到期时间',
  `forbid_status` tinyint(1) DEFAULT '0' COMMENT '禁用状态 0-正常 1-禁用',
  `subject_id` bigint DEFAULT NULL COMMENT '所属主体账号id,也就是平台管理的id',
  `bind_mini_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '小程序唯一id',
  `account_type` tinyint(1) DEFAULT NULL COMMENT '账号类型  0-商户账号, 1-店铺名下账号',
  `region` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '区域',
  `address` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '地址',
  `bind_mini_shop_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '小程序用户唯一id',
  `balance` decimal(10,2) DEFAULT '0.00' COMMENT '用户余额',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='平台与租户平台用户表';

-- ----------------------------
-- Table structure for t_platform_code_version
-- ----------------------------
DROP TABLE IF EXISTS `t_platform_code_version`;
CREATE TABLE `t_platform_code_version` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `pc_version` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'pc后台版本',
  `wx_version` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '微信小程序版本',
  `mobile_version` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '微信小程序版本',
  `level` tinyint DEFAULT NULL COMMENT '1 标准版，2企业版，3旗舰版',
  `type` tinyint DEFAULT NULL COMMENT '分类类型：1 系统模版 2 定制模版',
  `mobile_url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '移动web路径',
  `pc_url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'PC端后台url',
  `template_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '模版编号',
  `is_deleted` tinyint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='店铺信息表';

-- ----------------------------
-- Table structure for t_platform_default_value
-- ----------------------------
DROP TABLE IF EXISTS `t_platform_default_value`;
CREATE TABLE `t_platform_default_value` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `unique_identification` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '默认值唯一标识',
  `version` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '默认值版本',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `is_deleted` tinyint(1) DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `build_action` tinyint(1) DEFAULT NULL COMMENT '默认值生成方式 1-发送指队列 2-url地址 3-自行查询',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '请求地址',
  `mq` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'mq信息json',
  `kv` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT 'key-value默认值json字符串',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='默认数据表';

-- ----------------------------
-- Table structure for t_platform_libraries_info
-- ----------------------------
DROP TABLE IF EXISTS `t_platform_libraries_info`;
CREATE TABLE `t_platform_libraries_info` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '基础库名称',
  `type` tinyint DEFAULT NULL COMMENT '基础库类型 1通用 2 定制',
  `status` tinyint DEFAULT NULL COMMENT '1正常 2异常，3已下线',
  `version` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '版本号',
  `count` int DEFAULT NULL COMMENT '服务数量',
  `category_type` tinyint DEFAULT NULL COMMENT '分类类型：1 业务基础库 2 支撑基础库',
  `belong_id` bigint DEFAULT NULL COMMENT '所属支撑基础库id ，只有是业务基础库时，才有此值',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '备注',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '服务说明',
  `is_deleted` tinyint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `uniqueness` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '唯一标识',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='基础库信息表';

-- ----------------------------
-- Table structure for t_platform_log
-- ----------------------------
DROP TABLE IF EXISTS `t_platform_log`;
CREATE TABLE `t_platform_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `log_text` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `out_id` bigint DEFAULT NULL COMMENT '外键',
  `type` tinyint DEFAULT NULL COMMENT '1 支撑基础库更新 2 业务基础库更新 3 模版更新 ',
  `create_time` datetime DEFAULT NULL,
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_delete` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='日志更新表';

-- ----------------------------
-- Table structure for t_platform_pay_config
-- ----------------------------
DROP TABLE IF EXISTS `t_platform_pay_config`;
CREATE TABLE `t_platform_pay_config` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `certificate_path` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '商户支付证书路径',
  `mch_key` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '支付秘钥',
  `mch_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '商户号',
  `is_deleted` tinyint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `ips_mer_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '环迅商户号',
  `ips_acc_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '环迅商户账户编号',
  `ips_certificate_psw` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '环迅ipsCertificatePsw证书密码',
  `ips_rsa_public_key` varchar(355) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '环迅公钥',
  `ips_rsa_private_key` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '环迅私钥',
  `ips_aes` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '环迅AES秘钥',
  `ips_sha` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '环迅SHA公钥',
  `sxf_org_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '随行付合作机构id',
  `sxf_acc_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '微信商户号入驻商户编号',
  `sxf_certificate_psw` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '微信商户号证书密码',
  `sxf_public` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '微信商户号公钥',
  `sxf_private_key` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '微信商户号秘钥',
  `sft_terminal_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '盛付通终端号',
  `sft_md5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '盛付通秘钥',
  `sft_channel_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '盛付通代理商商户编号',
  `sft_sub_merchant_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '盛付通线下交易子商户号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_platform_server_cfg
-- ----------------------------
DROP TABLE IF EXISTS `t_platform_server_cfg`;
CREATE TABLE `t_platform_server_cfg` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `realm_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '域名',
  `ip` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'ip',
  `ram_size` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '内存大小',
  `hard_disk_size` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '硬盘大小',
  `cpu_size` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'cpu大小',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '备注',
  `is_delete` tinyint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `service_id` bigint DEFAULT NULL COMMENT '服务信息表id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='服务器配置表';

-- ----------------------------
-- Table structure for t_platform_service_info
-- ----------------------------
DROP TABLE IF EXISTS `t_platform_service_info`;
CREATE TABLE `t_platform_service_info` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '服务名称',
  `type` tinyint DEFAULT NULL COMMENT '服务类型 1通用 2 定制',
  `status` tinyint DEFAULT NULL COMMENT '1正常 2异常',
  `version` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '服务版本',
  `count` int DEFAULT NULL COMMENT '服务数量',
  `libraries_info_id` bigint DEFAULT NULL COMMENT 'oundation_libraries id 区分 业务基础库和支撑基础库',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '备注',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '服务说明',
  `is_deleted` tinyint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='服务信息表';

-- ----------------------------
-- Table structure for t_platform_shop_balance
-- ----------------------------
DROP TABLE IF EXISTS `t_platform_shop_balance`;
CREATE TABLE `t_platform_shop_balance` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `shop_info_id` bigint NOT NULL COMMENT 't_platform_shop_info 表中id',
  `available_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '可用金额（余额）',
  `used_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '已使用金额',
  `freeze_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '冻结量金额',
  `is_deleted` int NOT NULL DEFAULT '0' COMMENT '是否删除 0=未删除,1=已删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='店铺余额表';

-- ----------------------------
-- Table structure for t_platform_shop_info
-- ----------------------------
DROP TABLE IF EXISTS `t_platform_shop_info`;
CREATE TABLE `t_platform_shop_info` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `logo_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '店铺首页图片',
  `shop_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '店铺名称',
  `status` tinyint DEFAULT '1' COMMENT '0审核中，1部署中\n2正常 ，3已打烊，4禁用',
  `due_time` datetime DEFAULT NULL COMMENT '到期时间',
  `is_due` tinyint DEFAULT '0' COMMENT '是否到期 0 不是 1是',
  `shop_template_id` bigint DEFAULT NULL COMMENT 't_platform_shop_template_info 模版id',
  `account_id` bigint DEFAULT NULL COMMENT '商户idt_platform_account_info的id',
  `agree_on` tinyint DEFAULT NULL COMMENT '是否同意笔歌协议 0未同意，1已同意',
  `package_id` bigint DEFAULT NULL COMMENT '套餐id',
  `is_deleted` tinyint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_join` tinyint DEFAULT NULL COMMENT '创建入口 0-admin 1-商家控制台',
  `is_privatization_deployment` tinyint DEFAULT NULL COMMENT '是否私有化部署（0否 1是）',
  `business_hours` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '营业时间,自行分割',
  `shop_phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '店铺电话',
  `bind_mini_id` bigint DEFAULT NULL COMMENT '绑定的小程序信息id',
  `bind_mp_id` bigint DEFAULT NULL COMMENT '绑定的公众号信息id',
  `shop_template_detail_id` bigint DEFAULT NULL COMMENT '当前使用的模版版本id',
  `certificate_path` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '商户支付证书路径',
  `mch_key` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '支付秘钥',
  `mch_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '商户号',
  `pay_type` tinyint(1) DEFAULT '1' COMMENT '支付类型 1-微信支付 2-环迅支付 3-随行支付',
  `package_order_id` bigint DEFAULT NULL COMMENT '套餐订购表id',
  `mini_bottom_log` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '小程序底部打标',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='店铺信息表';

-- ----------------------------
-- Table structure for t_platform_shop_message
-- ----------------------------
DROP TABLE IF EXISTS `t_platform_shop_message`;
CREATE TABLE `t_platform_shop_message` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `is_deleted` tinyint(1) DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除 ',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `use_type` tinyint(1) DEFAULT NULL COMMENT '使用类型 1-买家通知 2-商家通知',
  `version` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '对应版本',
  `message_type` tinyint(1) DEFAULT NULL COMMENT '消息类别 1-订单消息 2-售后消息 3-用户消息 4-营销活动',
  `mini_open` tinyint(1) DEFAULT NULL COMMENT '小程序订阅消息是否开启 0-关闭  1-开启',
  `mini_msg` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '小程序订阅模板',
  `code_open` tinyint(1) DEFAULT NULL COMMENT '短信模板消息是否开启 0-关闭  1-开启',
  `code_msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '短信消息',
  `mp_open` tinyint(1) DEFAULT NULL COMMENT '公众号是否发送  0关闭- 1-开启',
  `mp_msg` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '公众号消息',
  `mark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '消息标识',
  `mini_template_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '小程序模板消息id',
  `code_template_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '短信模板id',
  `mp_template_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '公众号模板id',
  `title` varchar(28) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '标题名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='店铺消息配置';

-- ----------------------------
-- Table structure for t_platform_shop_template_detail
-- ----------------------------
DROP TABLE IF EXISTS `t_platform_shop_template_detail`;
CREATE TABLE `t_platform_shop_template_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `version` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '版本号',
  `libraries_info_id` bigint DEFAULT NULL COMMENT '业务基础库id',
  `libraries_info_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '业务基础库名称',
  `code_templete_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '小程序代码模版id',
  `code_templete_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '小程序代码模版名称',
  `pc_termina_url` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'pc端路径',
  `pc_termina_version` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'pc端版本',
  `mobile_terminal_url` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '移动端路径',
  `mobile_terminal_version` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '移动端版本',
  `server_count` int DEFAULT NULL COMMENT '服务数量',
  `shop_template_id` bigint DEFAULT NULL COMMENT '店铺模版表id',
  `is_deleted` tinyint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `version_log` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新日志',
  `pc_url_map` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'pc端关联页面,json存储,键值对',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='店铺模版详情表';

-- ----------------------------
-- Table structure for t_platform_shop_template_detail_minis
-- ----------------------------
DROP TABLE IF EXISTS `t_platform_shop_template_detail_minis`;
CREATE TABLE `t_platform_shop_template_detail_minis` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '表id',
  `is_deleted` tinyint DEFAULT NULL COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `code_templete_id` varchar(24) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '小程序模版库中templateId',
  `code_templete_version` varchar(24) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '模版库中自定义版本号',
  `version_explain` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '版本说明',
  `shop_template_detail_id` bigint DEFAULT NULL COMMENT '店铺模版详情表id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='店铺模版详情小程序版本子表';

-- ----------------------------
-- Table structure for t_platform_shop_template_info
-- ----------------------------
DROP TABLE IF EXISTS `t_platform_shop_template_info`;
CREATE TABLE `t_platform_shop_template_info` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '店铺模版名称',
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '模版编号',
  `type` tinyint(1) DEFAULT NULL COMMENT '分类类型：1 系统模版 2 定制模版',
  `shop_template_type` tinyint(1) DEFAULT NULL COMMENT '模版应用类型：1商城，2社区拼团，3门店版',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '备注',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '服务说明',
  `is_deleted` tinyint(1) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='店铺模版表';

-- ----------------------------
-- Table structure for t_product
-- ----------------------------
DROP TABLE IF EXISTS `t_product`;
CREATE TABLE `t_product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '删除状态：0->未删除；1->已删除',
  `provider_id` bigint DEFAULT NULL COMMENT '供应商id',
  `attribute_id` bigint DEFAULT NULL COMMENT '属性模版id',
  `attribute_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '属性模版名称',
  `freight_template_id` bigint DEFAULT NULL COMMENT '运费模板id',
  `sale_mode` bigint unsigned DEFAULT NULL COMMENT '销售专区(默认生成商超系统、社区团购)',
  `limit_type` tinyint unsigned DEFAULT NULL COMMENT '限购类型(默认统一限购，0--统一限购），1--多规格统一限购，2--多规格)',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品名称',
  `pic` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '展示图片',
  `wide_pic` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '宽屏展示图片',
  `album_pics` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '画册图片，连产品图片限制为6张，以逗号分割',
  `video_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '视频url',
  `product_sn` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '货号',
  `status` tinyint unsigned DEFAULT NULL COMMENT '状态(默认上架，0--下架（仓库中），1--上架，2--已售完)',
  `place` tinyint unsigned DEFAULT NULL COMMENT '商品位置(默认线上，0--线上，1--素材库)',
  `csv_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '电商平台链接',
  `sort` int DEFAULT '0' COMMENT '排序',
  `sale` int DEFAULT '0' COMMENT '销量',
  `unit` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '单位',
  `weight` decimal(10,2) DEFAULT '0.00' COMMENT '商品重量，默认为克',
  `service_ids` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '以逗号分割的产品服务：无忧退货、快速退款、免费包邮',
  `detail` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '商品详情',
  `is_open_specs` tinyint unsigned DEFAULT NULL COMMENT '规格是否展开',
  `attribute` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '销售属性',
  `sale_describe` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '卖点描述',
  `score` decimal(3,1) DEFAULT '0.0' COMMENT '评分',
  `distribution_mode` tinyint(1) DEFAULT '0' COMMENT '配送方式(0--商家配送，1--快递配送)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='商品信息';

-- ----------------------------
-- Table structure for t_product_attribute
-- ----------------------------
DROP TABLE IF EXISTS `t_product_attribute`;
CREATE TABLE `t_product_attribute` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '删除状态：0->未删除；1->已删除',
  `product_id` bigint DEFAULT NULL COMMENT '产品id',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '属性名称',
  `value` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '属性值',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=280 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='商品属性';

-- ----------------------------
-- Table structure for t_product_show_category
-- ----------------------------
DROP TABLE IF EXISTS `t_product_show_category`;
CREATE TABLE `t_product_show_category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '删除状态：0->未删除；1->已删除',
  `product_id` bigint NOT NULL COMMENT '产品id',
  `parent_id` bigint NOT NULL COMMENT '上级分类的编号：0表示一级分类',
  `show_category_id` bigint DEFAULT NULL COMMENT '商品展示分类id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=510 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='商品展示分类';

-- ----------------------------
-- Table structure for t_sale_mode
-- ----------------------------
DROP TABLE IF EXISTS `t_sale_mode`;
CREATE TABLE `t_sale_mode` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '删除状态：0->未删除；1->已删除',
  `mode_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '专区名称',
  `mode_type` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '专区类型',
  `sort` tinyint DEFAULT NULL COMMENT '专区排序(按大小升序排序)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='自定义专区';

-- ----------------------------
-- Table structure for t_send_code
-- ----------------------------
DROP TABLE IF EXISTS `t_send_code`;
CREATE TABLE `t_send_code` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `phone` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '手机号',
  `code` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '验证码',
  `verify_type` int DEFAULT NULL COMMENT '校验类型',
  `is_deleted` tinyint(1) DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `expiration_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_shop_base_stting
-- ----------------------------
DROP TABLE IF EXISTS `t_shop_base_stting`;
CREATE TABLE `t_shop_base_stting` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `is_deleted` tinyint DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `shop_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '店铺名称',
  `open_transaction_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '开始营业时间',
  `close_transaction_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '结束营业时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='店铺基础设置';

-- ----------------------------
-- Table structure for t_shop_guide_page
-- ----------------------------
DROP TABLE IF EXISTS `t_shop_guide_page`;
CREATE TABLE `t_shop_guide_page` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '网址路径',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '路径',
  `link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'path名称 用于比较',
  `app_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '小程序appid 用于跳转',
  `type` tinyint(1) DEFAULT NULL COMMENT ' 0为path 1为app_id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_shop_guide_page_switch
-- ----------------------------
DROP TABLE IF EXISTS `t_shop_guide_page_switch`;
CREATE TABLE `t_shop_guide_page_switch` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_open` tinyint(1) DEFAULT NULL COMMENT '引导页是否开启 0 -->未开启 1-->已开启',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_shop_msg_open
-- ----------------------------
DROP TABLE IF EXISTS `t_shop_msg_open`;
CREATE TABLE `t_shop_msg_open` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id ',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `is_deleted` tinyint(1) DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `mini_open` tinyint(1) DEFAULT NULL COMMENT '小程序订阅消息是否开启 0-关闭  1-开启',
  `code_open` tinyint(1) DEFAULT NULL COMMENT '短信模板消息是否开启 0-关闭  1-开启',
  `mp_open` tinyint(1) DEFAULT NULL COMMENT '公众号模板消息是否开启 0-关闭  1-开启',
  `type` tinyint(1) DEFAULT NULL COMMENT '消息类型 1-买家消息',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='店铺通知开关';

-- ----------------------------
-- Table structure for t_shop_support_pay
-- ----------------------------
DROP TABLE IF EXISTS `t_shop_support_pay`;
CREATE TABLE `t_shop_support_pay` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `is_deleted` tinyint(1) DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `wechat_pay` tinyint(1) DEFAULT NULL COMMENT '是否支持微信支持 0 不支持 1-支持',
  `balance_pay` tinyint(1) DEFAULT NULL COMMENT '是否支持余额支付  0 不支持 1-支持',
  `friend_pay` tinyint(1) DEFAULT NULL COMMENT '是否支持好友代付  0 不支持 1-支持',
  `shop_base_id` bigint DEFAULT NULL COMMENT '店铺基础配置表id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='店铺支持支付方式子表';

-- ----------------------------
-- Table structure for t_shopping_cart
-- ----------------------------
DROP TABLE IF EXISTS `t_shopping_cart`;
CREATE TABLE `t_shopping_cart` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '删除状态：0->未删除；1->已删除',
  `product_id` bigint DEFAULT NULL COMMENT '产品id',
  `user_id` varchar(28) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户id',
  `sku_id` bigint NOT NULL COMMENT 'sku_id',
  `goods_number` int NOT NULL DEFAULT '0' COMMENT '商品数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=553 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='商品购物车';

-- ----------------------------
-- Table structure for t_shops
-- ----------------------------
DROP TABLE IF EXISTS `t_shops`;
CREATE TABLE `t_shops` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '@desc id',
  `agreement` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '@desc 协议',
  `amount_raised` decimal(10,0) DEFAULT NULL COMMENT '@desc 起提金额',
  `create_time` datetime DEFAULT NULL COMMENT '@desc 创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '@desc 修改时间',
  `is_deleted` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc 删除标识',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_shops_menu_config
-- ----------------------------
DROP TABLE IF EXISTS `t_shops_menu_config`;
CREATE TABLE `t_shops_menu_config` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '@desc: id',
  `is_deleted` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '@desc  标识  0正常(def) 1已删除',
  `properties` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '@desc  组件属性 json串',
  `create_time` datetime DEFAULT NULL COMMENT '@desc  创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '@desc  修改时间',
  `operator_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '@desc  操作人id',
  `operator_name` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '@desc  操作人name',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='店铺合伙人菜单属性配置表';

-- ----------------------------
-- Table structure for t_shops_partner
-- ----------------------------
DROP TABLE IF EXISTS `t_shops_partner`;
CREATE TABLE `t_shops_partner` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '@desc id',
  `create_time` datetime DEFAULT NULL COMMENT '@desc 创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '@desc 修改时间',
  `partner_id` bigint DEFAULT NULL COMMENT '@desc 总店id',
  `is_deleted` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc 删除标识',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc 手机号',
  `name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc 姓名',
  `pass` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc 密码',
  `region` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc 地域',
  `province_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc  省code',
  `city_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc  市code',
  `area_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc  区域Code',
  `card_id_up` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc 身份证正面',
  `card_id_down` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc 身份证反面',
  `map_x` double DEFAULT NULL COMMENT '@desc 地图X',
  `map_y` double DEFAULT NULL COMMENT '@desc 地图Y',
  `partner_model` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc 合伙人模式  0加盟 1子公司',
  `approval_status` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc 状态 0 未审核 1已审核 2 拒绝',
  `prohibit_status` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc 禁用状态 0 未禁用 1 已禁用',
  `province_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc 省name',
  `city_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc 市name',
  `area_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc 区name',
  `invitation_code` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '邀请码',
  `platform_id` bigint DEFAULT NULL COMMENT '@desc  平台用户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_shops_renovation_assembly
-- ----------------------------
DROP TABLE IF EXISTS `t_shops_renovation_assembly`;
CREATE TABLE `t_shops_renovation_assembly` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '@desc: id',
  `page_id` bigint DEFAULT NULL COMMENT '@desc  所属页面ID',
  `is_deleted` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '@desc  标识  0正常(def) 1已删除',
  `properties` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '@desc  组件属性 json串',
  `create_time` datetime DEFAULT NULL COMMENT '@desc  创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '@desc  修改时间',
  `operator_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '@desc  操作人id',
  `operator_name` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '@desc  操作人name',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6533 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='店铺装修模板页面组件属性表';

-- ----------------------------
-- Table structure for t_shops_renovation_page
-- ----------------------------
DROP TABLE IF EXISTS `t_shops_renovation_page`;
CREATE TABLE `t_shops_renovation_page` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '	\r\n@desc: id',
  `template_id` bigint DEFAULT NULL COMMENT '@desc  所属模板ID',
  `is_deleted` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '@desc  逻辑删除标识  0正常(def) 1已删除',
  `page_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '@desc  自定义页面name ',
  `create_time` datetime DEFAULT NULL COMMENT '@desc  创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '@desc  修改时间',
  `operator_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '@desc  操作人id',
  `operator_name` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '@desc  操作人name',
  `is_def` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '@desc  是否默认首页',
  `copy_plugin_flag` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '@desc  默认模板copy,控件json配置页面id对应关系',
  `type` tinyint(1) DEFAULT NULL COMMENT '@desc  分类页',
  `model_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '@desc  modelId',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=161 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='店铺装修模板页面表';

-- ----------------------------
-- Table structure for t_shops_renovation_plugin
-- ----------------------------
DROP TABLE IF EXISTS `t_shops_renovation_plugin`;
CREATE TABLE `t_shops_renovation_plugin` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '@desc: id',
  `template_id` bigint DEFAULT NULL COMMENT '	@desc  所属模板ID',
  `plugin_properties` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '@desc  控件',
  `is_deleted` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc  删除标识',
  `create_time` datetime DEFAULT NULL COMMENT '@desc  创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '@desc  修改时间',
  `operator_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc  操作人id',
  `operator_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc  操作人name',
  `plugin_name_cn` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc  控件名称中文',
  `is_mandatory` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc  是否允许取消 0否 1是',
  `is_selection` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc  是否选中 0否 1是',
  `plugin_name_en` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc  控件名称英文',
  `spare` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc  备用字段',
  `copy_plugin_flag` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc  默认模板copy,控件json配置页面id对应关系',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='店铺装修页面全局控件属性表';

-- ----------------------------
-- Table structure for t_shops_renovation_template
-- ----------------------------
DROP TABLE IF EXISTS `t_shops_renovation_template`;
CREATE TABLE `t_shops_renovation_template` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '@desc id',
  `is_deleted` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '@desc  逻辑删除标识  0正常(def) 1已删除',
  `type` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '@desc  模板类型 0自定义 1拼团 2商超',
  `colour` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '1' COMMENT '@desc 模板全局颜色 0红 1绿 2蓝',
  `online_status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '@desc 模板是否使用中 0 否, 1 是',
  `create_time` datetime DEFAULT NULL COMMENT '@desc 创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '@desc 修改时间',
  `operator_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '@desc 操作人id',
  `operator_name` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '	\r\n@desc 操作人name',
  `is_dev_template` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '@desc 默认模板 1是 0否',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '@desc 模板name',
  `is_copy_template` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '@desc 是否copy源模板 0否 1是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='店铺装修模板表';

-- ----------------------------
-- Table structure for t_shops_search_terms
-- ----------------------------
DROP TABLE IF EXISTS `t_shops_search_terms`;
CREATE TABLE `t_shops_search_terms` (
  `id` bigint NOT NULL COMMENT '@desc id',
  `create_time` datetime DEFAULT NULL COMMENT '@desc 创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '@desc 修改时间',
  `is_deleted` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc 删除标识',
  `terms` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc 词语 多个以英文逗号分隔',
  `def_terms` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc 默认 搜索词语',
  `is_show` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc 是否显示',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='店铺默认搜索关键字';

-- ----------------------------
-- Table structure for t_show_category
-- ----------------------------
DROP TABLE IF EXISTS `t_show_category`;
CREATE TABLE `t_show_category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '删除状态：0->未删除；1->已删除',
  `parent_id` bigint DEFAULT NULL COMMENT '上级分类的编号：0表示一级分类',
  `sale_mode` bigint DEFAULT NULL COMMENT '销售专区',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '分类名称',
  `level` int DEFAULT NULL COMMENT '分类级别：0->1级；1->2级',
  `sort` int DEFAULT NULL COMMENT '分类排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=224 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='商品展示分类';

-- ----------------------------
-- Table structure for t_sku_stock
-- ----------------------------
DROP TABLE IF EXISTS `t_sku_stock`;
CREATE TABLE `t_sku_stock` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '删除状态：0->未删除；1->已删除',
  `version` int DEFAULT NULL COMMENT '版本号',
  `product_id` bigint DEFAULT NULL COMMENT '产品id',
  `sku_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT 'sku编码',
  `specs` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品规格',
  `weight` decimal(10,2) DEFAULT '0.00' COMMENT 'sku重量',
  `pic` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '展示图片',
  `price` decimal(10,2) DEFAULT '0.00' COMMENT '实售价',
  `original_price` decimal(10,2) DEFAULT '0.00' COMMENT '指导价（划线价）',
  `stock` int DEFAULT '0' COMMENT '库存',
  `low_stock` int DEFAULT '0' COMMENT '预警库存',
  `sale` int DEFAULT '0' COMMENT '销量',
  `per_limit` int DEFAULT '0' COMMENT '限购数量',
  `gift_integration` int DEFAULT '0' COMMENT '赠送的积分',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='sku库存';

-- ----------------------------
-- Table structure for t_sms_order
-- ----------------------------
DROP TABLE IF EXISTS `t_sms_order`;
CREATE TABLE `t_sms_order` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint NOT NULL COMMENT '商户id',
  `sms_send_time` bigint NOT NULL COMMENT '短信发送时间',
  `sms_send_type` tinyint NOT NULL COMMENT '1普通短信(待扩充)，2通知短信',
  `sms_send_content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '短信内容',
  `sms_send_zone` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '86' COMMENT '短信区号，默认中国86',
  `sms_send_param` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '短信参数json',
  `sms_send_mobiles` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '发送手机号',
  `sms_send_count` int NOT NULL DEFAULT '1' COMMENT '短信条数,(不计算字数，目前商家最好自行去对应平台对账)',
  `sms_send_success_count` int NOT NULL DEFAULT '0' COMMENT '短信成功条数)',
  `sms_send_fail_count` int NOT NULL DEFAULT '0' COMMENT '短信失败条数)',
  `sms_send_status` int NOT NULL DEFAULT '0' COMMENT '短信发送状态(0待发送，1发送中，2提交通道商，3通道商已返回,4已发送)',
  `provider_id` bigint NOT NULL COMMENT '短信供应商id(t_sms_provider表中的id)',
  `sms_type` tinyint DEFAULT '1' COMMENT '1腾讯 2阿里',
  `is_deleted` tinyint(1) DEFAULT '0' COMMENT '删除状态：0->未删除；1->已删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL,
  `template_id` bigint DEFAULT NULL COMMENT '模版id',
  `sign_id` bigint DEFAULT NULL COMMENT '签名id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='短信订单历史表';

-- ----------------------------
-- Table structure for t_sms_order_send_detail
-- ----------------------------
DROP TABLE IF EXISTS `t_sms_order_send_detail`;
CREATE TABLE `t_sms_order_send_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint NOT NULL COMMENT '商户id',
  `sms_send_time` bigint NOT NULL COMMENT '短信发送时间',
  `sms_send_report_status` tinyint NOT NULL COMMENT '(0无，1发送中，2，发送失败，3发送成功)',
  `sms_send_report_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '第三方短信回调状态码',
  `sms_send_report_msg` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '第三方短信回调详情',
  `sms_send_mobile` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '发送手机号',
  `sms_send_report_sms_size` int NOT NULL COMMENT '第三方短信回调扣费条数',
  `sms_send_report_time` datetime NOT NULL COMMENT '短信回调时间',
  `sms_send_content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '短信内容',
  `is_deleted` tinyint(1) DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='短信订单历史表';

-- ----------------------------
-- Table structure for t_sms_provider
-- ----------------------------
DROP TABLE IF EXISTS `t_sms_provider`;
CREATE TABLE `t_sms_provider` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint NOT NULL COMMENT '商户id',
  `sms_provider_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '短信供应商名称',
  `sms_provider_appId` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '短信供应商应用id',
  `sms_provider_app_secret` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '短信供应商应用密钥',
  `sms_provider_available_count` int NOT NULL COMMENT '短信供应商可用条数',
  `sms_provider_used_count` int NOT NULL COMMENT '短信供应商已用条数',
  `sms_provider_total_count` int NOT NULL COMMENT '短信供应商总条数',
  `sms_provider_status` tinyint(1) NOT NULL COMMENT '0正常，1停用',
  `is_deleted` tinyint(1) DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='短信订单历史表';

-- ----------------------------
-- Table structure for t_sms_sign
-- ----------------------------
DROP TABLE IF EXISTS `t_sms_sign`;
CREATE TABLE `t_sms_sign` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint NOT NULL COMMENT '商户id',
  `sms_provider_id` bigint NOT NULL COMMENT '供应商idt_sms_provider的id',
  `sms_sign` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '短信签名（每次发送之前都需提前报备，可通过接口或者手动去页面提交报备）',
  `sms_sign_is_pass` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0未审核，1审核中，2审核通过，3审核不通过',
  `sms_sign_remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '备注',
  `is_deleted` tinyint(1) DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL,
  `sms_sign_type` int DEFAULT NULL COMMENT '\n    0：企事业单位的全称或简称。\n    1：工信部备案网站的全称或简称。\n    2：APP应用的全称或简称。\n    3：公众号或小程序的全称或简称。\n    4：电商平台店铺名的全称或简称。\n    5：商标名的全称或简称\n',
  `sign_type` int DEFAULT NULL COMMENT '1阿里，2腾讯',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='短信签名表';

-- ----------------------------
-- Table structure for t_sms_template
-- ----------------------------
DROP TABLE IF EXISTS `t_sms_template`;
CREATE TABLE `t_sms_template` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint NOT NULL COMMENT '商户id',
  `sms_provider_id` bigint NOT NULL COMMENT '供应商idt_sms_provider的id',
  `sms_template_content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '模版内容 （在第三方创建待模版内容）',
  `sms_template_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '模版编码（在第三方创建待模版编码）',
  `sms_template_is_pass` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0未审核，1审核中，2审核通过，3审核不通过',
  `sms_remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '备注',
  `is_deleted` tinyint DEFAULT '0' COMMENT '删除状态：0->未删除；1->已删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL,
  `template_type` int DEFAULT NULL COMMENT '类型1阿里，2腾讯',
  `sms_template_type` int DEFAULT NULL COMMENT '\n    0：验证码。\n    1：短信通知。\n    2：推广短信。\n    3：国际/港澳台消息。\n',
  `sms_template_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '模版名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='短信模版表';

-- ----------------------------
-- Table structure for t_supplier
-- ----------------------------
DROP TABLE IF EXISTS `t_supplier`;
CREATE TABLE `t_supplier` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '删除状态：0->未删除；1->已删除',
  `user_id` varchar(28) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户id',
  `supplier_sn` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '供应商识别号',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '供应商名称',
  `mobile` bigint DEFAULT NULL COMMENT '手机号码',
  `province` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '省',
  `city` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '市',
  `country` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '县',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '地区',
  `area` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '完整地址',
  `product_info` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '产品信息',
  `status` int DEFAULT NULL COMMENT '状态(默认待审核，0--已关闭，1--已审核，2--待审核，3--禁用中)',
  `come_from` int DEFAULT NULL COMMENT '注册来源(0--后台注册，1--小程序)',
  `score` decimal(3,1) DEFAULT '0.0' COMMENT '评分',
  `template_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '订阅消息模版id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='供应商';

-- ----------------------------
-- Table structure for t_sys_shop_invoice_order
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_shop_invoice_order`;
CREATE TABLE `t_sys_shop_invoice_order` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `is_deleted` tinyint(1) DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `type` tinyint(1) DEFAULT NULL COMMENT '发票类型：1增值税专用发票、2普通发票 3专业发票',
  `invoice_rise_type` tinyint(1) DEFAULT NULL COMMENT '抬头类型：1个人或事业单位，2企业',
  `invoice_rise_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '抬头名称',
  `invoice_taxpayer_num` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '纳税人识别号',
  `amount` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '发票金额',
  `email` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邮件地址',
  `status` tinyint(1) DEFAULT NULL COMMENT '审核状态：0待审核，1已审核',
  `order_type` tinyint(1) DEFAULT NULL COMMENT '订单类型 1-充值订单  2-套餐订购订单',
  `order_id` bigint DEFAULT NULL COMMENT '订购订单id',
  `account_id` bigint DEFAULT NULL COMMENT '商户id',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `number_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '发票编号',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `is_deleted` (`is_deleted`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='发票工单表';

-- ----------------------------
-- Table structure for t_sys_shop_invoice_rise
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_shop_invoice_rise`;
CREATE TABLE `t_sys_shop_invoice_rise` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `is_deleted` tinyint DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `account_id` bigint DEFAULT NULL COMMENT '商户id',
  `head_type` int DEFAULT NULL COMMENT '抬头类型：1个人或事业单位，2企业',
  `invoice_rise_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '抬头名称',
  `invoice_taxpayer_num` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '纳税人识别号',
  `remark` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `default_status` tinyint(1) DEFAULT '0' COMMENT '是否默认使用 0-否  1-是',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `is_deleted` (`is_deleted`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='用户发票抬头表';

-- ----------------------------
-- Table structure for t_sys_shop_package
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_shop_package`;
CREATE TABLE `t_sys_shop_package` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `is_deleted` tinyint DEFAULT '0' COMMENT '删除状态：0->未删除；1->已删除',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '套餐名称',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '套餐说明',
  `level` int DEFAULT NULL COMMENT '套餐等级',
  `package_price` decimal(10,2) DEFAULT NULL COMMENT '套餐价格',
  `package_price_unit` tinyint(1) DEFAULT NULL COMMENT '套餐使用价格单位 1天，2月，3年',
  `discounts_json` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '优惠价json y-年 m-月 d-天  [{"unit":"y","price":100}]',
  `functionDesc` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '功能描述',
  `open_state` int DEFAULT '0' COMMENT '套餐开启状态 0-关闭  1-开启',
  `template_version_id` bigint DEFAULT NULL COMMENT '模版版本id',
  `operate_id` bigint DEFAULT NULL COMMENT '上一次操作人id',
  `operate_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '上一次操作人名称',
  `template_id` bigint DEFAULT NULL COMMENT '模版id',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `is_deleted` (`is_deleted`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='店铺套餐';

-- ----------------------------
-- Table structure for t_sys_shop_package_order
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_shop_package_order`;
CREATE TABLE `t_sys_shop_package_order` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `is_deleted` tinyint DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `account_id` bigint DEFAULT NULL COMMENT '商户id',
  `shop_template_info_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '店铺模版表id',
  `order_num` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '订单编号',
  `package_id` bigint DEFAULT NULL COMMENT '套餐 id',
  `package_data` varchar(526) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '下单时套餐完整数据',
  `order_type` tinyint DEFAULT NULL COMMENT '订单类型 1-订购  2-续费  3-升级',
  `package_time` int DEFAULT NULL COMMENT '套餐时长',
  `package_price_unit` tinyint(1) DEFAULT NULL COMMENT '套餐价格单位 1天，2月，3年',
  `package_start_time` datetime DEFAULT NULL COMMENT '套餐开始时间',
  `package_end_time` datetime DEFAULT NULL COMMENT '套餐结束时间',
  `package_price` decimal(10,2) DEFAULT NULL COMMENT '套餐价格',
  `amount_payable` decimal(10,2) DEFAULT NULL COMMENT '应付金额',
  `paid_payable` decimal(10,2) DEFAULT NULL COMMENT '实付金额',
  `pay_type` int DEFAULT NULL COMMENT '1:余额支付2:扫码支付->微信3:扫码支付->支付宝4:汇款支付',
  `status` int DEFAULT NULL COMMENT '订单支付状态 0:待处理1:处理中2:已经完成',
  `pay_info` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '银行卡支付时填写支付方信息json',
  `relauditor_id` bigint DEFAULT NULL COMMENT '审核人id',
  `auditor_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '审核人名称',
  `auditor_status` int DEFAULT NULL COMMENT '审核状态：0:待审核 1:审核通过 2:审核拒绝',
  `is_agreed` int DEFAULT NULL COMMENT '是否同意协议',
  `is_automatic_deduction` int DEFAULT NULL COMMENT '是否同意标准版自动扣除续费',
  `is_received` int DEFAULT NULL COMMENT '是否收到汇款 0:未收到 1:已收到',
  `invoice_status` tinyint(1) DEFAULT NULL COMMENT '开票状态  0-未开票 1-已开票',
  `order_source` tinyint(1) DEFAULT NULL COMMENT '订单来源  0-用户购买 1-管理台购买',
  `audit_time` datetime DEFAULT NULL COMMENT '汇款支付审核时间',
  `agent_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `package_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '套餐名称',
  `shop_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '店铺名称',
  `template_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '店铺模板名称(店铺类型)',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `is_deleted` (`is_deleted`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='店铺套餐订单表';

-- ----------------------------
-- Table structure for t_system_conf
-- ----------------------------
DROP TABLE IF EXISTS `t_system_conf`;
CREATE TABLE `t_system_conf` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `param_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'key',
  `param_value` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT 'value',
  `status` tinyint DEFAULT '1' COMMENT '状态   0：隐藏   1：显示',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除状态：0->未删除；1->已删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='系统配置';

-- ----------------------------
-- Table structure for t_template_msg_send_record
-- ----------------------------
DROP TABLE IF EXISTS `t_template_msg_send_record`;
CREATE TABLE `t_template_msg_send_record` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `is_deleted` tinyint(1) DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除 ',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `use_type` tinyint(1) DEFAULT NULL COMMENT '使用类型 1-买家通知 2-商家通知',
  `message_type` tinyint DEFAULT NULL COMMENT '消息类别 1-订单消息 2-售后消息 3-用户消息 4-营销活动',
  `send_json` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '发送内容json',
  `send_status` tinyint DEFAULT NULL COMMENT '发送状态 0-发送失败  1-发送成功',
  `failure_reason` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '失败原因',
  `use_teamplate_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '使用的模板消息id',
  `mini_id` bigint DEFAULT NULL COMMENT '小程序表id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='模板消息发送记录';
