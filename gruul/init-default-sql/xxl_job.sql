/*
 Navicat Premium Data Transfer

 Source Server         : xiaoq
 Source Server Type    : MySQL
 Source Server Version : 80026
 Source Host           : 127.0.0.1:3306
 Source Schema         : xxl_job

 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 14/09/2021 15:05:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Records of t_mini_account_foot_mark
-- ----------------------------

-- ----------------------------
-- Table structure for xxl_job_group
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_group`;
CREATE TABLE `xxl_job_group` (
  `id` int NOT NULL AUTO_INCREMENT,
  `app_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '执行器AppName',
  `title` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '执行器名称',
  `order` int NOT NULL DEFAULT '0' COMMENT '排序',
  `address_type` tinyint NOT NULL DEFAULT '0' COMMENT '执行器地址类型：0=自动注册、1=手动录入',
  `address_list` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '执行器地址列表，多地址逗号分隔',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of xxl_job_group
-- ----------------------------
BEGIN;
INSERT INTO `xxl_job_group` VALUES (1, 'xxl-job-executor-sample', '示例执行器', 1, 0, NULL);
INSERT INTO `xxl_job_group` VALUES (2, 'payment-job', '支付任务', 1, 0, NULL);
INSERT INTO `xxl_job_group` VALUES (3, 'wuliu-job', '物流job', 3, 1, '127.0.0.1:12000');
INSERT INTO `xxl_job_group` VALUES (5, 'integral-job', '积分job', 1, 0, NULL);
INSERT INTO `xxl_job_group` VALUES (6, 'sms-job', '短信job', 1, 0, '192.168.1.228:12000');
INSERT INTO `xxl_job_group` VALUES (7, 'finance-job', '财务job', 1, 0, NULL);
INSERT INTO `xxl_job_group` VALUES (8, 'platefrom-job', '平台job', 1, 1, '169.254.123.7:12000');
COMMIT;

-- ----------------------------
-- Table structure for xxl_job_info
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_info`;
CREATE TABLE `xxl_job_info` (
  `id` int NOT NULL AUTO_INCREMENT,
  `job_group` int NOT NULL COMMENT '执行器主键ID',
  `job_cron` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务执行CRON',
  `job_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `author` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '作者',
  `alarm_email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '报警邮件',
  `executor_route_strategy` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '执行器路由策略',
  `executor_handler` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '执行器任务handler',
  `executor_param` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '执行器任务参数',
  `executor_block_strategy` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '阻塞处理策略',
  `executor_timeout` int NOT NULL DEFAULT '0' COMMENT '任务执行超时时间，单位秒',
  `executor_fail_retry_count` int NOT NULL DEFAULT '0' COMMENT '失败重试次数',
  `glue_type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'GLUE类型',
  `glue_source` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT 'GLUE源代码',
  `glue_remark` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'GLUE备注',
  `glue_updatetime` datetime DEFAULT NULL COMMENT 'GLUE更新时间',
  `child_jobid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '子任务ID，多个逗号分隔',
  `trigger_status` tinyint NOT NULL DEFAULT '0' COMMENT '调度状态：0-停止，1-运行',
  `trigger_last_time` bigint NOT NULL DEFAULT '0' COMMENT '上次调度时间',
  `trigger_next_time` bigint NOT NULL DEFAULT '0' COMMENT '下次调度时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of xxl_job_info
-- ----------------------------
BEGIN;
INSERT INTO `xxl_job_info` VALUES (1, 1, '0 0 0 * * ? *', '测试任务1', '2018-11-03 22:21:31', '2018-11-03 22:21:31', 'XXL', '', 'FIRST', 'demoJobHandler', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2018-11-03 22:21:31', '', 0, 0, 0);
INSERT INTO `xxl_job_info` VALUES (2, 2, '0 0/15 * * * ? ', '关闭超时订单', '2019-11-06 13:08:53', '2019-11-06 13:09:23', 'xxx', 'xxx@qq.com', 'ROUND', 'transactionCloseJob', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2019-11-06 13:08:53', '', 1, 1631602800000, 1631603700000);
INSERT INTO `xxl_job_info` VALUES (3, 3, '5,15,25,35,45,55 * * * * ? *', 'test', '2019-11-23 07:30:15', '2019-11-23 09:20:12', '1', '', 'FIRST', 'wuliu-job', 'xxx', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2019-11-23 07:30:15', '', 0, 0, 0);
INSERT INTO `xxl_job_info` VALUES (4, 3, '5,15,25,35,45,55 * * * * ? *', 'test2', '2019-11-23 08:30:36', '2019-11-23 09:20:09', '12', '', 'FIRST', 'wuliu-job', 'xxx', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2019-11-23 08:30:36', '1', 0, 0, 0);
INSERT INTO `xxl_job_info` VALUES (5, 3, '5,15,25,35,45,55 * * * * ? *', 'test3', '2019-11-23 08:32:31', '2019-11-23 09:20:06', '1', '', 'FIRST', 'wuliu-job', 'xx', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2019-11-23 08:32:31', '', 0, 0, 0);
INSERT INTO `xxl_job_info` VALUES (6, 3, '5,15,25,35,45,55 * * * * ? *', 'test4', '2019-11-23 08:32:54', '2019-11-23 09:20:04', '1', '', 'FIRST', 'wuliu-job', 'xxx', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2019-11-23 08:32:54', '', 0, 0, 0);
INSERT INTO `xxl_job_info` VALUES (7, 5, '55 59 23 * * ? ', '每日23:59点对用户签到信息进行修改', '2019-12-20 17:36:06', '2019-12-27 11:17:36', 'xiaoq', 'xxx@163.com', 'FIRST', 'singInUpdateJob', '1', 'SERIAL_EXECUTION', 0, 1, 'BEAN', '', 'GLUE代码初始化', '2019-12-20 17:36:06', '', 1, 1631462395000, 1631635195000);
INSERT INTO `xxl_job_info` VALUES (9, 5, '51 59 23 * * ?', '每日23:59点对用户当日兑换步数清空为0', '2019-12-24 16:42:47', '2019-12-31 16:55:02', 'xiaoq', 'xxx@163.com', 'FIRST', 'stepNumberEmptyJob', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2019-12-24 16:42:47', '', 1, 1631462391000, 1631635191000);
INSERT INTO `xxl_job_info` VALUES (10, 5, '55 59 23 * * ?', '每日23:59点对用户登入信息进行修改', '2019-12-31 16:21:02', '2020-04-09 01:42:01', 'xiaoq', 'xxx@163.com', 'FIRST', 'enterUpdateJob', '1', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2019-12-31 16:21:02', '', 1, 1631462395000, 1631635195000);
INSERT INTO `xxl_job_info` VALUES (11, 6, '*/5 * * * * ?', '短信发送job', '2020-01-01 10:52:44', '2020-01-01 11:13:45', 'xxx', '', 'FIRST', 'doSendSmsTask', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2020-01-01 10:52:44', '', 0, 0, 0);
INSERT INTO `xxl_job_info` VALUES (14, 5, '0 0 0 * * ? ', '将用户的连续签到或者连续登入大于商家设置的天数进行归零', '2020-01-03 14:10:10', '2020-01-03 15:02:19', 'xiaoq', 'xxx@163.com', 'FIRST', 'ReturnToZoreJobJob', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2020-01-03 14:10:10', '', 1, 1631462400000, 1631635200000);
INSERT INTO `xxl_job_info` VALUES (15, 5, '0 0 0 * * ?', '清空积分', '2020-01-08 11:41:30', '2020-01-10 13:57:45', 'xiaoq', 'xxx', 'FIRST', 'ClearIntegralJob', 'a', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2020-01-08 11:41:30', '', 1, 1631462400000, 1631635200000);
INSERT INTO `xxl_job_info` VALUES (16, 7, '0 0/1 * * * ?', '每月结算佣金', '2020-03-07 11:53:36', '2020-05-16 08:58:08', 'xxx', '', 'ROUND', 'settlementJob', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2020-03-07 11:53:36', '', 1, 1631603100000, 1631603160000);
INSERT INTO `xxl_job_info` VALUES (17, 8, '0 0 0,12 * * ? ', '套餐过期检测', '2020-09-04 15:42:55', '2020-09-04 15:43:05', 'xxx', 'xxx', 'FIRST', 'packageeExpireJob', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2020-09-04 15:42:55', '', 1, 1631592000000, 1631635200000);
COMMIT;

-- ----------------------------
-- Table structure for xxl_job_lock
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_lock`;
CREATE TABLE `xxl_job_lock` (
  `lock_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '锁名称',
  PRIMARY KEY (`lock_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of xxl_job_lock
-- ----------------------------
BEGIN;
INSERT INTO `xxl_job_lock` VALUES ('schedule_lock');
COMMIT;
-- ----------------------------
-- Table structure for xxl_job_log
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_log`;
CREATE TABLE `xxl_job_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `job_group` int NOT NULL COMMENT '执行器主键ID',
  `job_id` int NOT NULL COMMENT '任务，主键ID',
  `executor_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '执行器地址，本次执行的地址',
  `executor_handler` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '执行器任务handler',
  `executor_param` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '执行器任务参数',
  `executor_sharding_param` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '执行器任务分片参数，格式如 1/2',
  `executor_fail_retry_count` int NOT NULL DEFAULT '0' COMMENT '失败重试次数',
  `trigger_time` datetime DEFAULT NULL COMMENT '调度-时间',
  `trigger_code` int NOT NULL COMMENT '调度-结果',
  `trigger_msg` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '调度-日志',
  `handle_time` datetime DEFAULT NULL COMMENT '执行-时间',
  `handle_code` int NOT NULL COMMENT '执行-状态',
  `handle_msg` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '执行-日志',
  `alarm_status` tinyint NOT NULL DEFAULT '0' COMMENT '告警状态：0-默认、1-无需告警、2-告警成功、3-告警失败',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `I_trigger_time` (`trigger_time`) USING BTREE,
  KEY `I_handle_code` (`handle_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=327096 DEFAULT CHARSET=utf8mb3;
-- ----------------------------
-- Table structure for xxl_job_logglue
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_logglue`;
CREATE TABLE `xxl_job_logglue` (
  `id` int NOT NULL AUTO_INCREMENT,
  `job_id` int NOT NULL COMMENT '任务，主键ID',
  `glue_type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'GLUE类型',
  `glue_source` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT 'GLUE源代码',
  `glue_remark` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'GLUE备注',
  `add_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of xxl_job_logglue
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for xxl_job_registry
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_registry`;
CREATE TABLE `xxl_job_registry` (
  `id` int NOT NULL AUTO_INCREMENT,
  `registry_group` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `registry_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `registry_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `i_g_k_v` (`registry_group`,`registry_key`,`registry_value`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=885 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of xxl_job_registry
-- ----------------------------
BEGIN;
INSERT INTO `xxl_job_registry` VALUES (882, 'EXECUTOR', 'sms-job', '192.168.1.228:12000', '2021-09-14 15:05:33');
INSERT INTO `xxl_job_registry` VALUES (883, 'EXECUTOR', 'payment-job-open', '192.168.1.228:12002', '2021-09-14 15:05:31');
INSERT INTO `xxl_job_registry` VALUES (884, 'EXECUTOR', 'platefrom-job', '192.168.1.228:12001', '2021-09-14 15:05:27');
COMMIT;

-- ----------------------------
-- Table structure for xxl_job_user
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_user`;
CREATE TABLE `xxl_job_user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账号',
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `role` tinyint NOT NULL COMMENT '角色：0-普通用户、1-管理员',
  `permission` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '权限：执行器ID列表，多个逗号分割',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `i_username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of xxl_job_user
-- ----------------------------
BEGIN;
INSERT INTO `xxl_job_user` VALUES (1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', 1, NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
