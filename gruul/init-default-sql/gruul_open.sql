/*
 Navicat Premium Data Transfer

 Source Server         : xiaoq
 Source Server Type    : MySQL
 Source Server Version : 80026
 Source Host           : 121.4.158.234:3306
 Source Schema         : gruul_open

 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 15/09/2021 11:27:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_account_center
-- ----------------------------
DROP TABLE IF EXISTS `t_account_center`;
CREATE TABLE `t_account_center` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `is_deleted` tinyint DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '租户id',
  `head_style` tinyint(1) DEFAULT NULL COMMENT '头部风格 1-系统风格 2-自定义风格',
  `custom_style` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '自定义风格样式,json存储',
  `get_cart_text` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '领卡文案',
  `hide_cart_inlet` tinyint(1) DEFAULT NULL COMMENT '非会员隐藏领卡入口 0-隐藏 1-显示',
  `order_info` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '\r\n订单信息 json存储',
  `menu_style` tinyint(1) DEFAULT NULL COMMENT '菜单选择样式 1-展开式 2-折叠式',
  `code_style` tinyint(1) DEFAULT NULL COMMENT '提货码样式,1样式1  2样式2',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户中心配置';

-- ----------------------------
-- Records of t_account_center
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_account_center_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_account_center_menu`;
CREATE TABLE `t_account_center_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `is_deleted` tinyint DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '租户id',
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
) ENGINE=InnoDB AUTO_INCREMENT=1340 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户中心菜单配置';

-- ----------------------------
-- Records of t_account_center_menu
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_afs_negotiate_history
-- ----------------------------
DROP TABLE IF EXISTS `t_afs_negotiate_history`;
CREATE TABLE `t_afs_negotiate_history` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '工单id',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '租户ID',
  `shop_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '商铺ID',
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
) ENGINE=InnoDB AUTO_INCREMENT=119 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='协商历史';

-- ----------------------------
-- Records of t_afs_negotiate_history
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_afs_order
-- ----------------------------
DROP TABLE IF EXISTS `t_afs_order`;
CREATE TABLE `t_afs_order` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '工单id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `shop_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '商铺ID',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '租户ID',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '删除状态：0->未删除；1->已删除',
  `no` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '工单编号',
  `type` int NOT NULL COMMENT '工单类型：3.退款 4.换货 ',
  `status` int NOT NULL COMMENT '审核状态：1.待商家审核 2.待退货 4.待发货  6.成功',
  `close_type` int DEFAULT NULL COMMENT '关闭原因：1.用户撤销  ',
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
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='售后工单';

-- ----------------------------
-- Records of t_afs_order
-- ----------------------------
BEGIN;
COMMIT;

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
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='申请的详情';

-- ----------------------------
-- Records of t_afs_order_item
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_afs_reason
-- ----------------------------
DROP TABLE IF EXISTS `t_afs_reason`;
CREATE TABLE `t_afs_reason` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '租户ID',
  `shop_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '商铺ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '删除状态：0->未删除；1->已删除',
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='申请的原因';

-- ----------------------------
-- Records of t_afs_reason
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_agent_account_record
-- ----------------------------
DROP TABLE IF EXISTS `t_agent_account_record`;
CREATE TABLE `t_agent_account_record` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `is_deleted` tinyint DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `agent_info_id` bigint DEFAULT NULL COMMENT 't_agent_info 表中id',
  `before_amount` decimal(10,2) DEFAULT NULL COMMENT '变更之前金额',
  `after_amount` decimal(10,2) DEFAULT NULL COMMENT '变更之后金额',
  `amount` decimal(10,2) DEFAULT NULL COMMENT '变更金额',
  `type` decimal(10,2) DEFAULT NULL COMMENT '类型：1:充值收入2:消费支出3:提现支出',
  `business_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '业务订单号',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `is_deleted` (`is_deleted`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='代理商流水记录表结构';

-- ----------------------------
-- Records of t_agent_account_record
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_agent_bank
-- ----------------------------
DROP TABLE IF EXISTS `t_agent_bank`;
CREATE TABLE `t_agent_bank` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `is_deleted` tinyint DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `agent_info_id` bigint DEFAULT NULL COMMENT 't_agent_info 表中id',
  `card_phone` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '持卡人手机号',
  `card_name` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '持卡人姓名',
  `card_num` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '卡号',
  `card_bank_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '开户银行',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `is_deleted` (`is_deleted`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb3 COMMENT='代理商银行卡表结构';

-- ----------------------------
-- Records of t_agent_bank
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_agent_info
-- ----------------------------
DROP TABLE IF EXISTS `t_agent_info`;
CREATE TABLE `t_agent_info` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `is_deleted` tinyint DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `account` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '账户',
  `pwd` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '密码',
  `code` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邀请码',
  `link_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '联系人',
  `region` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '所在区域',
  `address` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '联系地址',
  `phone` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '手机号',
  `email` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邮箱地址',
  `type` int DEFAULT NULL COMMENT '1:代理商 2:直属渠道商 3:间接渠道商',
  `next_due_time` datetime DEFAULT NULL COMMENT '代理到期时间',
  `is_auto_times` int DEFAULT NULL COMMENT '满足代理目标到期自动续签时长 0不自动续期  1-自动续签',
  `discount` decimal(10,2) DEFAULT NULL COMMENT '折扣',
  `available_amount` decimal(10,2) DEFAULT NULL COMMENT '可用金额',
  `freeze_amount` decimal(10,2) DEFAULT NULL COMMENT '冻结金额',
  `sub_Ledger_amount` decimal(10,2) DEFAULT NULL COMMENT '已经分帐金额',
  `used_amount` decimal(10,2) DEFAULT NULL COMMENT '已使用金额',
  `agent_share_profit` int DEFAULT NULL COMMENT '代理商分润 单位%',
  `channel_share_profit` int DEFAULT NULL COMMENT '渠道商分润 单位%',
  `status` int DEFAULT NULL COMMENT ' 1:正常 2:冻结 3:停用',
  `parent_id` bigint DEFAULT NULL COMMENT '父节点id，间接渠道商时有值，代理商和直属渠道商时为0',
  `scale_code` varchar(24) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '公司规模编号',
  `enterprise_type` varchar(24) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '企业类型编号',
  `enterprise` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '企业名称',
  `register_type` int DEFAULT NULL COMMENT '注册方式 1.官网自行注册  2.商户控制台注册',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `is_deleted` (`is_deleted`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8mb3 COMMENT='代理商信息表';

-- ----------------------------
-- Records of t_agent_info
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_agent_notice
-- ----------------------------
DROP TABLE IF EXISTS `t_agent_notice`;
CREATE TABLE `t_agent_notice` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `is_deleted` tinyint DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `agent_info_id` bigint DEFAULT NULL COMMENT 't_agent_info 表中id',
  `title` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '消息内容',
  `type` int DEFAULT NULL COMMENT '消息分类',
  `is_read` int DEFAULT NULL COMMENT '是否已读  0:未读 1:已读',
  `msg_type` int DEFAULT NULL COMMENT '消息类型  1-产品消息 2-财务消息',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `is_deleted` (`is_deleted`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=238 DEFAULT CHARSET=utf8mb3 COMMENT='代理商推送消息表结构';

-- ----------------------------
-- Records of t_agent_notice
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_agent_package_order
-- ----------------------------
DROP TABLE IF EXISTS `t_agent_package_order`;
CREATE TABLE `t_agent_package_order` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `is_deleted` tinyint DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `agent_id` bigint DEFAULT NULL COMMENT '代理id',
  `order_num` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '订单编号',
  `account_id` bigint DEFAULT NULL COMMENT '商户id',
  `pay_type` int DEFAULT NULL COMMENT '1:余额支付2:扫码支付->微信3:扫码支付->支付宝4:汇款支付',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `paid_payable` decimal(10,2) DEFAULT NULL COMMENT '实付金额',
  `package_id` bigint DEFAULT NULL COMMENT '套餐 id',
  `package_data` varchar(526) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '下单时套餐完整数据',
  `status` int DEFAULT NULL COMMENT '订单支付状态 0:待处理1:处理中2:已经完成',
  `earn_profit_money` decimal(10,2) DEFAULT NULL COMMENT '获利金额',
  `order_source` tinyint(1) DEFAULT NULL COMMENT '订单来源 同套餐订单',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of t_agent_package_order
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_agent_recharge
-- ----------------------------
DROP TABLE IF EXISTS `t_agent_recharge`;
CREATE TABLE `t_agent_recharge` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `is_deleted` tinyint DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `recharge_num` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '充值编号',
  `pay_type` int DEFAULT NULL COMMENT '1扫码支付->支付宝2扫码支付->微信 3汇款支付',
  `pay_amount` decimal(10,2) DEFAULT NULL COMMENT '充值金额',
  `pay_info` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '充值对象',
  `finish_time` datetime DEFAULT NULL COMMENT '结束时间(异步回调时间)',
  `status` int DEFAULT NULL COMMENT '0:充值中1:充值成功2:充值失败',
  `agent_info_id` bigint DEFAULT NULL COMMENT 't_agent_info 表中id',
  `merchant_id` bigint DEFAULT NULL COMMENT '商户id',
  `prepay_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '三方预交易标识',
  `audit_time` datetime DEFAULT NULL COMMENT '汇款支付审核时间',
  `account_amount` decimal(10,2) DEFAULT NULL COMMENT '账户余额',
  `auditor_status` tinyint(1) DEFAULT NULL COMMENT '审核状态：0:待审核 1:审核通过 2:审核拒绝',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `is_deleted` (`is_deleted`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8mb3 COMMENT='代理商充值记录表';

-- ----------------------------
-- Records of t_agent_recharge
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_agent_rule
-- ----------------------------
DROP TABLE IF EXISTS `t_agent_rule`;
CREATE TABLE `t_agent_rule` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `is_deleted` tinyint DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `agent_info_id` bigint DEFAULT NULL COMMENT 't_agent_info 表中id',
  `rule_type` int DEFAULT NULL COMMENT '代理目标  1.销售总单数 2.销售总额',
  `target_time` int DEFAULT NULL COMMENT '完成目标时间节点,单位月',
  `target_value` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '完成目标所需值',
  `rule_class` int DEFAULT NULL COMMENT '规则类型 1目标规则  2-升级条件',
  `use_rule` int DEFAULT NULL COMMENT '是否使用规则 0-未使用 1-已使用',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `is_deleted` (`is_deleted`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=193 DEFAULT CHARSET=utf8mb3 COMMENT='代理目标';

-- ----------------------------
-- Records of t_agent_rule
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_agent_statistics
-- ----------------------------
DROP TABLE IF EXISTS `t_agent_statistics`;
CREATE TABLE `t_agent_statistics` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `is_deleted` tinyint DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `agent_info_id` bigint DEFAULT NULL COMMENT 't_agent_info 表中id',
  `total_sale_price` decimal(10,2) DEFAULT NULL COMMENT '累计销售总金额',
  `total_sale_count` int DEFAULT NULL COMMENT '累计销售总单数',
  `commission_amount` decimal(10,2) DEFAULT NULL COMMENT '佣金金额',
  `channal_count` int DEFAULT NULL COMMENT '下集渠道商数',
  `shop_info_count` int DEFAULT NULL COMMENT '商户数量',
  `app_info_count` int DEFAULT NULL COMMENT '店铺数量',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `is_deleted` (`is_deleted`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb3 COMMENT='代理商统计信息表结构';

-- ----------------------------
-- Records of t_agent_statistics
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_agent_transfer_log
-- ----------------------------
DROP TABLE IF EXISTS `t_agent_transfer_log`;
CREATE TABLE `t_agent_transfer_log` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `is_deleted` tinyint DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `info_id` bigint DEFAULT NULL COMMENT '被迁移的id',
  `info_type` tinyint DEFAULT NULL COMMENT '被迁移对象 1.商户 2.渠道商',
  `to_type` tinyint DEFAULT NULL COMMENT '迁移对象 1-变为代理商  2-变为渠道商 3-渠道商迁移至某代理',
  `to_id` bigint DEFAULT NULL COMMENT '迁移后的对象id',
  `transfer_time` datetime DEFAULT NULL COMMENT '迁移时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `is_deleted` (`is_deleted`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=244 DEFAULT CHARSET=utf8mb3 COMMENT='关系迁移日志表';

-- ----------------------------
-- Records of t_agent_transfer_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_agent_verify
-- ----------------------------
DROP TABLE IF EXISTS `t_agent_verify`;
CREATE TABLE `t_agent_verify` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `is_deleted` tinyint DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `phone` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '手机号',
  `link_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '联系人',
  `region` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '所在区域',
  `address` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '联系地址',
  `status` int DEFAULT NULL COMMENT '0:待审核1:审核通过:审核拒绝',
  `code` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邀请码',
  `enterprise_type` varchar(24) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '企业类型编号',
  `enterprise` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '企业名称',
  `register_type` int DEFAULT NULL COMMENT '注册方式 1.官网自行注册  2.商户控制台注册',
  `scale_code` varchar(24) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '公司规模编号',
  `account_id` bigint DEFAULT NULL COMMENT '商户id',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `is_deleted` (`is_deleted`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8mb3 COMMENT='代理申请审核表';

-- ----------------------------
-- Records of t_agent_verify
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_agent_withdraw_order
-- ----------------------------
DROP TABLE IF EXISTS `t_agent_withdraw_order`;
CREATE TABLE `t_agent_withdraw_order` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `is_deleted` tinyint DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `agent_info_id` bigint DEFAULT NULL COMMENT 't_agent_info 表中id',
  `type` int DEFAULT NULL COMMENT '提现方式  1-微信  2-支付宝  3-银行卡',
  `order_num` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '我方交易流水号',
  `name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '提现姓名',
  `bank_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '开户行名称',
  `bank_num` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '银行卡号或者支付宝微信账号',
  `amount` decimal(10,2) DEFAULT NULL COMMENT '提现金额',
  `status` int DEFAULT NULL COMMENT '0:待审核 1:审核拒绝 2:已打款',
  `agent_type` int DEFAULT NULL COMMENT '1代理商2直属渠道商3间接渠道商',
  `account` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '账户',
  `link_name` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '联系人',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `is_deleted` (`is_deleted`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb3 COMMENT='提现工单表结构';

-- ----------------------------
-- Records of t_agent_withdraw_order
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_attribute_template
-- ----------------------------
DROP TABLE IF EXISTS `t_attribute_template`;
CREATE TABLE `t_attribute_template` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '租户ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '删除状态：0->未删除；1->已删除',
  `shop_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '本店店铺id',
  `parent_id` bigint DEFAULT NULL COMMENT '上级分类的编号：0表示一级分类',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '模板名称',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb3 COMMENT='属性模板';

-- ----------------------------
-- Records of t_attribute_template
-- ----------------------------
BEGIN;
COMMIT;

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
  `tenant_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc 租户标识',
  `openid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc 用户标识',
  `check_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc 校验用户姓名选项',
  `re_user_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc 收款用户姓名',
  `create_time` datetime DEFAULT NULL COMMENT '@desc 创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '@desc 修改时间',
  `is_deleted` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '@desc 删除标识0否 1是',
  `pay_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '@desc 支付成功时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='服务商对个人支付表';

-- ----------------------------
-- Records of t_ent_pay
-- ----------------------------
BEGIN;
COMMIT;

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
  `tenant_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc 租户标识',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商户支付个人回调日志表';

-- ----------------------------
-- Records of t_ent_pay_back_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_file
-- ----------------------------
DROP TABLE IF EXISTS `t_file`;
CREATE TABLE `t_file` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'URL地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `tenant_id` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '租户ID',
  `size` bigint DEFAULT NULL COMMENT '文件大小',
  `suffix` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文件后缀名',
  `original_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文件原名',
  `shop_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=300 DEFAULT CHARSET=utf8mb3 COMMENT='文件上传';

-- ----------------------------
-- Records of t_file
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_logistics_address
-- ----------------------------
DROP TABLE IF EXISTS `t_logistics_address`;
CREATE TABLE `t_logistics_address` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键 id',
  `tenant_id` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '租户 id',
  `shop_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '本店店铺id',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '发货人-名称',
  `province` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '省',
  `province_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '省id',
  `city` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '市',
  `city_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '市id',
  `country` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '县',
  `country_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '县id',
  `address` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '发货地址',
  `zip_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '邮编号:',
  `phone` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `def_send` int DEFAULT '0' COMMENT '是否为默认发货地址: 0=不是,1=是',
  `def_receive` int DEFAULT '0' COMMENT '是否为默认退货地址: 0=不是,1=是',
  `is_deleted` int NOT NULL DEFAULT '0' COMMENT '是否删除: 0=未删除,1=已删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `lat` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '纬度',
  `lng` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '经度',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=117 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='物流发货地址';

-- ----------------------------
-- Records of t_logistics_address
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_logistics_company
-- ----------------------------
DROP TABLE IF EXISTS `t_logistics_company`;
CREATE TABLE `t_logistics_company` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '快递公司名称',
  `code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '快递公司代码',
  `is_deleted` int DEFAULT '0' COMMENT '是否删除,0=正常;1=删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `shop_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='快递公司代码表';

-- ----------------------------
-- Records of t_logistics_company
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_logistics_deliver
-- ----------------------------
DROP TABLE IF EXISTS `t_logistics_deliver`;
CREATE TABLE `t_logistics_deliver` (
  `id` bigint NOT NULL COMMENT '主键 id',
  `tenant_id` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '租户 id',
  `shop_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '本店店铺id',
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '物流发货单名称',
  `code` bigint NOT NULL COMMENT '物流编号',
  `type` int NOT NULL COMMENT '发货单状态	: 1=代发货,2=待提货,3=已完成,4=已关闭',
  `is_deleted` int NOT NULL DEFAULT '0' COMMENT '是否删除 0=正常,1=已删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of t_logistics_deliver
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_logistics_deliver_order_relation
-- ----------------------------
DROP TABLE IF EXISTS `t_logistics_deliver_order_relation`;
CREATE TABLE `t_logistics_deliver_order_relation` (
  `id` bigint NOT NULL COMMENT '主键id',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '租户id',
  `shop_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '本店店铺id',
  `logistics_id` bigint NOT NULL COMMENT '物流发货单id',
  `order_id` bigint NOT NULL COMMENT '订单id',
  `good_obj` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单包含商品信息_json格式[{"id":12,"code":"2312"}]',
  `logistics_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '物流公司名称',
  `logistics_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '物流公司单号',
  `is_send` int DEFAULT NULL COMMENT '是否发货,0=未发货,1=已发货',
  `type` int DEFAULT NULL COMMENT '物流单,状态:1=代发货',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='物流发货单关联 订单表';

-- ----------------------------
-- Records of t_logistics_deliver_order_relation
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_logistics_express
-- ----------------------------
DROP TABLE IF EXISTS `t_logistics_express`;
CREATE TABLE `t_logistics_express` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键 id',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '租户 id',
  `shop_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '本店店铺id',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '快递公司名称',
  `code` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '快递公司code',
  `phone` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '快递公司客服电话',
  `address_id` bigint NOT NULL COMMENT '地址id 关联物流发货地址表id',
  `customer_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '客户id',
  `customer_password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '客户密匙',
  `link_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '负责人',
  `link_tel` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '负责人电话',
  `status` int NOT NULL COMMENT '状态 默认为1（审核通过）',
  `is_deleted` int NOT NULL DEFAULT '0' COMMENT '是否删除: 0=未删除,1=已删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='物流公司发货地址设置';

-- ----------------------------
-- Records of t_logistics_express
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_logistics_express_print
-- ----------------------------
DROP TABLE IF EXISTS `t_logistics_express_print`;
CREATE TABLE `t_logistics_express_print` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键 id',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '租户 id',
  `shop_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '本店店铺id',
  `device_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '设备类型',
  `device_model` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '设备型号',
  `device_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '设备名称',
  `device_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '打印机机身号',
  `device_key` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '打印机KEY',
  `status` int NOT NULL COMMENT '状态',
  `is_deleted` int NOT NULL DEFAULT '0' COMMENT '是否删除: 0=未删除,1=已删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='电子面单打印机设置';

-- ----------------------------
-- Records of t_logistics_express_print
-- ----------------------------
BEGIN;
COMMIT;

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
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `shop_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='运费模板_包邮设置表';

-- ----------------------------
-- Records of t_logistics_include_postage
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_logistics_manage
-- ----------------------------
DROP TABLE IF EXISTS `t_logistics_manage`;
CREATE TABLE `t_logistics_manage` (
  `id` bigint NOT NULL COMMENT '主键 id',
  `tenant_id` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '租户 id',
  `shop_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '本店店铺id',
  `state` int NOT NULL DEFAULT '0' COMMENT '是否开启物流配送;0=不开启,1=开启',
  `deliver_build_time` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '生成订单时间: HH:mm  格式: ["HH:mm","HH:mm","HH:mm","HH:mm"] 最多四个',
  `is_deleted` int NOT NULL DEFAULT '0' COMMENT '是否删除 0=未删除,1=已删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='物流基础设置';

-- ----------------------------
-- Records of t_logistics_manage
-- ----------------------------
BEGIN;
COMMIT;

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
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '租户id',
  `shop_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '商铺id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=409 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='物流基础运送方式表';

-- ----------------------------
-- Records of t_logistics_shipping_model
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_logistics_shop
-- ----------------------------
DROP TABLE IF EXISTS `t_logistics_shop`;
CREATE TABLE `t_logistics_shop` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键 id',
  `tenant_id` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '租户 id',
  `shop_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '本店店铺id',
  `logistics_company_id` bigint NOT NULL COMMENT '物流公司表id',
  `is_default` int NOT NULL DEFAULT '0' COMMENT '是否删除 0=未删除,1=已删除',
  `is_deleted` int NOT NULL DEFAULT '0' COMMENT '是否删除 0=未删除,1=已删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='店铺物流公司绑定表';

-- ----------------------------
-- Records of t_logistics_shop
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_logistics_template
-- ----------------------------
DROP TABLE IF EXISTS `t_logistics_template`;
CREATE TABLE `t_logistics_template` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '租户 id',
  `shop_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '本店店铺id',
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '模板名称',
  `is_incl_postage` int NOT NULL DEFAULT '0' COMMENT '是否包邮: 0=不包邮,1=包邮',
  `valuation_model` int NOT NULL COMMENT '计价方式: 1=按件数,2=按重量.',
  `choice_condition_postage` int NOT NULL DEFAULT '0' COMMENT '是否指定条件包邮: 0=不包邮,1=指定条件包邮',
  `is_deleted` int DEFAULT '0' COMMENT '是否删除,0=正常;1=删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=199 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='运费模板_基础设置表';

-- ----------------------------
-- Records of t_logistics_template
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_mini_account
-- ----------------------------
DROP TABLE IF EXISTS `t_mini_account`;
CREATE TABLE `t_mini_account` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id ',
  `user_id` varchar(28) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户id',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '租户id',
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
  KEY `user_id` (`user_id`) USING BTREE,
  KEY `tenant_id` (`tenant_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='小程序用户表';

-- ----------------------------
-- Records of t_mini_account
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_mini_account_address
-- ----------------------------
DROP TABLE IF EXISTS `t_mini_account_address`;
CREATE TABLE `t_mini_account_address` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '租户id',
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
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='会员地址表';

-- ----------------------------
-- Records of t_mini_account_address
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_mini_account_collect
-- ----------------------------
DROP TABLE IF EXISTS `t_mini_account_collect`;
CREATE TABLE `t_mini_account_collect` (
  `collect_id` bigint NOT NULL AUTO_INCREMENT COMMENT '收藏表id',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户id',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '租户id',
  `shop_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '商铺id区分城市合伙人',
  `product_id` bigint DEFAULT NULL COMMENT '商品id',
  `product_pic` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '商品主图',
  `product_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '商品名称',
  `status` int DEFAULT NULL COMMENT '商品状态  0-上架 1-下架 2-售罄',
  `product_price` decimal(10,2) DEFAULT NULL COMMENT '商品实际售价',
  `original_price` decimal(10,2) DEFAULT NULL COMMENT '指导价划线价',
  `ramark` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  PRIMARY KEY (`collect_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户收藏表';

-- ----------------------------
-- Records of t_mini_account_collect
-- ----------------------------
BEGIN;
COMMIT;

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
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '租户id',
  `consume_totle_money` decimal(10,2) DEFAULT NULL COMMENT '交易总额',
  `join_blacklist_time` datetime DEFAULT NULL COMMENT '加入黑名单时间',
  `community_type` tinyint(1) DEFAULT '0' COMMENT '用户团长身份类型  1-团长  2-区域团长',
  `shop_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '商铺id(区分城市合伙人)',
  `shop_user_id` varchar(28) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '店铺用户id',
  `current_status` tinyint(1) DEFAULT '0' COMMENT '当前使用  0未使用 1-当前使用',
  `last_choose_lcation` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '经度,维度',
  `supply_bonus` decimal(10,2) unsigned zerofill DEFAULT '00000000.00' COMMENT '用户余额',
  `freeze_bonus` decimal(10,2) unsigned zerofill DEFAULT '00000000.00' COMMENT '冻结余额',
  `rebate_bonus` decimal(10,2) unsigned zerofill DEFAULT '00000000.00' COMMENT '返利金额',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `user_id` (`user_id`) USING BTREE,
  KEY `community_id` (`community_id`) USING BTREE,
  KEY `shop_id` (`shop_id`) USING BTREE,
  KEY `tenant_id` (`tenant_id`) USING BTREE,
  KEY `shop_user_id` (`shop_user_id`) USING BTREE,
  KEY `is_deleted` (`is_deleted`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户信息扩展表';

-- ----------------------------
-- Records of t_mini_account_extends
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_mini_account_foot_mark
-- ----------------------------
DROP TABLE IF EXISTS `t_mini_account_foot_mark`;
CREATE TABLE `t_mini_account_foot_mark` (
  `footmark_id` bigint NOT NULL AUTO_INCREMENT COMMENT '足迹id',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户id',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '租户id',
  `shop_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '商铺id区分城市合伙人',
  `product_id` bigint DEFAULT NULL COMMENT '商品id',
  `product_pic` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '商品主图',
  `product_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '商品名称',
  `status` int DEFAULT NULL COMMENT '商品状态  0-上架 1-下架 2-售罄',
  `product_price` decimal(10,2) DEFAULT NULL COMMENT '商品实际售价',
  `original_price` decimal(10,2) DEFAULT NULL COMMENT '指导价划线价',
  `ramark` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  PRIMARY KEY (`footmark_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=333 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of t_mini_account_foot_mark
-- ----------------------------
BEGIN;
COMMIT;

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
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '租户id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `is_deleted` tinyint DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户第三方Id表';

-- ----------------------------
-- Records of t_mini_account_oauths
-- ----------------------------
BEGIN;
COMMIT;

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
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='黑名单限制权限表';

-- ----------------------------
-- Records of t_mini_account_restrict
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_mini_account_tag
-- ----------------------------
DROP TABLE IF EXISTS `t_mini_account_tag`;
CREATE TABLE `t_mini_account_tag` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '租户id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `tag_name` varchar(24) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '组名',
  `is_deleted` tinyint(1) DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户标签表';

-- ----------------------------
-- Records of t_mini_account_tag
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_mini_account_tag_group
-- ----------------------------
DROP TABLE IF EXISTS `t_mini_account_tag_group`;
CREATE TABLE `t_mini_account_tag_group` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '租户id',
  `tag_id` int NOT NULL COMMENT '组id',
  `is_deleted` tinyint(1) DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `user_id` varchar(28) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `tenant_id` (`tenant_id`) USING BTREE,
  KEY `user_id` (`user_id`) USING BTREE,
  KEY `is_deleted` (`is_deleted`) USING BTREE,
  KEY `tag_id` (`tag_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户所属分组表';

-- ----------------------------
-- Records of t_mini_account_tag_group
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_mini_audit_record
-- ----------------------------
DROP TABLE IF EXISTS `t_mini_audit_record`;
CREATE TABLE `t_mini_audit_record` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `template_id` bigint NOT NULL COMMENT '代码库中的代码模版ID',
  `app_id` varchar(60) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL COMMENT '授权APPID',
  `audit_id` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '审核编号',
  `audit_status` int DEFAULT NULL COMMENT '审核状态 0审核通过,1审核失败，2审核中',
  `release_state` int DEFAULT '0' COMMENT '发布状态 0-未发布 1-已发布',
  `version_id` bigint DEFAULT '0' COMMENT '当前模板代码版本id(t_platform_shop_template_detail)',
  `reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '审核失败原因',
  `is_deleted` tinyint(1) DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='小程序审核记录表';

-- ----------------------------
-- Records of t_mini_audit_record
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_mini_auth_info
-- ----------------------------
DROP TABLE IF EXISTS `t_mini_auth_info`;
CREATE TABLE `t_mini_auth_info` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '租户id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '授权方昵称',
  `head_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '授权方头像',
  `service_type_info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '授权方类型0代表订阅号，1代表由历史老帐号升级后的订阅号，2代表服务号 3代码小程序',
  `verify_type_info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '授权方认证类型，-1代表未认证，0代表微信认证，1代表新浪微博认证，2代表腾讯微博认证，3代表已资质认证通过但还未通过名称认证，4代表已资质认证通过、还未通过名称认证，但通过了新浪微博认证，5代表已资质认证通过、还未通过名称认证，但通过了腾讯微博认证',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '原始ID',
  `principal_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '主体名称',
  `signature` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '帐号介绍',
  `alias` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '授权方公众号所设置的微信号，可能为空',
  `business_info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用以了解以下功能的开通状况（0代表未开通，1代表已开通）： open_store:是否开通微信门店功能 open_scan:是否开通微信扫商品功能 open_pay:是否开通微信支付功能 open_card:是否开通微信卡券功能 open_shake:是否开通微信摇一摇功能',
  `qrcode_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '二维码图片的URL',
  `authorization_info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '授权信息',
  `authorization_appid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '授权方appid',
  `miniProgramInfo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '可根据这个字段判断是否为小程序类型授权',
  `func_info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '授权给开发者的权限集列表',
  `is_deleted` tinyint(1) DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='小程序授权时信息(只在授权流程中获取或更新)';

-- ----------------------------
-- Records of t_mini_auth_info
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_mini_auth_token
-- ----------------------------
DROP TABLE IF EXISTS `t_mini_auth_token`;
CREATE TABLE `t_mini_auth_token` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '租户id',
  `authorizer_appid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '授权方appid',
  `authorizer_access_token` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '授权方令牌',
  `expires_in` datetime DEFAULT NULL COMMENT '令牌到期时间',
  `authorizer_refresh_token` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '刷新令牌',
  `func_info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '授权给开发者的权限集列表',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint(1) DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='接口调用凭据和授权信息';

-- ----------------------------
-- Records of t_mini_auth_token
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_mini_change_record
-- ----------------------------
DROP TABLE IF EXISTS `t_mini_change_record`;
CREATE TABLE `t_mini_change_record` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '租户id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `mini_info_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '小程序id',
  `change_type` tinyint(1) DEFAULT NULL COMMENT '变更类型 0-续费 1-更改套餐 2-禁用或开启  3-信息更改',
  `old_data` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '老数据',
  `new_data` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '新数据',
  `is_deleted` tinyint(1) DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='小程序变更记录';

-- ----------------------------
-- Records of t_mini_change_record
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_mini_experience
-- ----------------------------
DROP TABLE IF EXISTS `t_mini_experience`;
CREATE TABLE `t_mini_experience` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id ',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '租户id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `is_deleted` tinyint(1) DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `experience_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '体验者',
  `mini_id` bigint DEFAULT NULL COMMENT '小程序id',
  `userstr` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'userstr',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='小程序体验者表';

-- ----------------------------
-- Records of t_mini_experience
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_mini_info
-- ----------------------------
DROP TABLE IF EXISTS `t_mini_info`;
CREATE TABLE `t_mini_info` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '租户id 授权小程序时生成 随机18个字符生成',
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
) ENGINE=InnoDB AUTO_INCREMENT=94 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='小程序基本信息';

-- ----------------------------
-- Records of t_mini_info
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_mini_mp_conf
-- ----------------------------
DROP TABLE IF EXISTS `t_mini_mp_conf`;
CREATE TABLE `t_mini_mp_conf` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_deleted` tinyint(1) DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除 ',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '租户id',
  `app_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '公众号appId',
  `app_secret` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '公众号appSecret',
  `app_aes_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '公众号aeskey',
  `app_token` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '公众号appToken',
  `mini_id` bigint DEFAULT NULL COMMENT '小程序id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='小程序关联公众号配置';

-- ----------------------------
-- Records of t_mini_mp_conf
-- ----------------------------
BEGIN;
COMMIT;

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
) ENGINE=InnoDB AUTO_INCREMENT=1621 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='订阅模板基础表提供版本默认值';

-- ----------------------------
-- Records of t_mini_subscriberi_base
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_mini_subscriberi_message
-- ----------------------------
DROP TABLE IF EXISTS `t_mini_subscriberi_message`;
CREATE TABLE `t_mini_subscriberi_message` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `is_deleted` tinyint(1) DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除 ',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '租户id',
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
) ENGINE=InnoDB AUTO_INCREMENT=260 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='小程序拥有的订阅消息表';

-- ----------------------------
-- Records of t_mini_subscriberi_message
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order` (
  `id` bigint NOT NULL COMMENT '订单id',
  `tenant_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '租户ID',
  `shop_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商铺ID',
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
  `coupon_amount` decimal(10,2) DEFAULT NULL COMMENT '优惠券抵扣金额',
  `full_scale_id` bigint DEFAULT NULL COMMENT '满减活动ID',
  `full_scale_amount` decimal(10,2) DEFAULT NULL COMMENT '满减优惠金额',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='订单表';

-- ----------------------------
-- Records of t_order
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_order_delivery
-- ----------------------------
DROP TABLE IF EXISTS `t_order_delivery`;
CREATE TABLE `t_order_delivery` (
  `order_id` bigint(19) unsigned zerofill NOT NULL COMMENT '订单id',
  `tenant_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '租户ID',
  `shop_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商铺ID',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='订单收货信息表';

-- ----------------------------
-- Records of t_order_delivery
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_order_evaluate
-- ----------------------------
DROP TABLE IF EXISTS `t_order_evaluate`;
CREATE TABLE `t_order_evaluate` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '租户ID',
  `shop_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商铺ID',
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
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='订单评论表';

-- ----------------------------
-- Records of t_order_evaluate
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_order_item
-- ----------------------------
DROP TABLE IF EXISTS `t_order_item`;
CREATE TABLE `t_order_item` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `shop_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商铺ID',
  `tenant_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '租户ID',
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
) ENGINE=InnoDB AUTO_INCREMENT=226 DEFAULT CHARSET=utf8mb3 COMMENT='订单中所包含的商品';

-- ----------------------------
-- Records of t_order_item
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_order_product_evaluate
-- ----------------------------
DROP TABLE IF EXISTS `t_order_product_evaluate`;
CREATE TABLE `t_order_product_evaluate` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '租户ID',
  `shop_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商铺ID',
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
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb3 COMMENT='产品评论表';

-- ----------------------------
-- Records of t_order_product_evaluate
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_order_setting
-- ----------------------------
DROP TABLE IF EXISTS `t_order_setting`;
CREATE TABLE `t_order_setting` (
  `id` bigint(20) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '租户ID',
  `shop_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商铺ID',
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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3 COMMENT='订单设置表';

-- ----------------------------
-- Records of t_order_setting
-- ----------------------------
BEGIN;
INSERT INTO `t_order_setting` VALUES (00000000000000000001, '100001', '100001100001', '2021-07-17 09:47:16', '2021-09-15 11:07:09', 0, 30, 16, 7, 7, 3, 1, 3, 3, 7, '105851', '9cb46a5f3169dd6cbf8580233b604e2e2dbce613', '1', '[{\"key\":\"买家留言\",\"type\":\"text\",\"required\":false,\"placeholder\":\"请输入45个字内说明\"},{\"key\":\"上传图片\",\"type\":\"image\",\"required\":false,\"placeholder\":\"请上传图片\"}]', 1);
COMMIT;

-- ----------------------------
-- Table structure for t_order_share_setting
-- ----------------------------
DROP TABLE IF EXISTS `t_order_share_setting`;
CREATE TABLE `t_order_share_setting` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '租户ID',
  `shop_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '商铺ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '删除状态：0->未删除；1->已删除',
  `background` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '背景图',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '标题',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='订单晒单设置表';

-- ----------------------------
-- Records of t_order_share_setting
-- ----------------------------
BEGIN;
COMMIT;

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
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '租户标识',
  `route_key` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '路由键',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of t_payment
-- ----------------------------
BEGIN;
COMMIT;

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
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of t_payment_record
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_payment_refund
-- ----------------------------
DROP TABLE IF EXISTS `t_payment_refund`;
CREATE TABLE `t_payment_refund` (
  `order_id` bigint NOT NULL COMMENT '订单id',
  `asyn_request` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '请求退款值',
  `asyn_result` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '请求退款同步返回值',
  `tenant_id` bigint DEFAULT NULL COMMENT '租户id',
  `syn_callback` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '回调数据',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_deleted` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '删除标识',
  `id` bigint NOT NULL AUTO_INCREMENT,
  `route_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '路由键',
  PRIMARY KEY (`id`,`order_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='支付服务退款log';

-- ----------------------------
-- Records of t_payment_refund
-- ----------------------------
BEGIN;
COMMIT;

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
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '商户标识',
  `open_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户openId',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of t_payment_wechat
-- ----------------------------
BEGIN;
COMMIT;

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
) ENGINE=InnoDB AUTO_INCREMENT=155 DEFAULT CHARSET=utf8mb3 COMMENT='账号余额明细表';

-- ----------------------------
-- Records of t_platform_account_balance_record
-- ----------------------------
BEGIN;
COMMIT;

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
  `last_login_shop_id` bigint DEFAULT NULL COMMENT '最后一次进入的店铺id',
  `bind_mini_shop_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '小程序用户唯一id',
  `balance` decimal(10,2) DEFAULT '0.00' COMMENT '用户余额',
  `agent_id` bigint DEFAULT NULL COMMENT '所属代理',
  `comment_text` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '备注',
  `me_agent_id` bigint DEFAULT NULL COMMENT '用户的代理账号id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='平台与租户平台用户表';

-- ----------------------------
-- Records of t_platform_account_info
-- ----------------------------
BEGIN;
INSERT INTO `t_platform_account_info` VALUES (1, 'admin123', '86788648c7279cb47ff503316f8bda12', 'e4o6vl', '宁波', 'zh_CN', '2021-09-15 11:01:03', '', '48_z32oVVh0TraXGa15QZy3VJWrEH9ArHLDO33aVauJu-9bHrPM3LnH6PhPs07joij-sfWYNfLOf9NOKMX67iR9cDtfGFFtx2WFEAjLzMPY0R4', '启山科技', 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKHl8YscaLHTiaf4RJkianZicb8pKXvTcRYd4g6XiaibP3LxSC8k54UCJRfAaRfChl4liaZudVXnYcyx9HQ/132', '13333333333', NULL, 1, '2021-08-26 13:54:05', 0, '2021-09-15 11:01:03', '', '', '浙江', '中国', '[]', NULL, '1970-01-01 08:00:07', 0, NULL, NULL, 0, '浙江省宁波市鄞州区', '123123', 398, NULL, 0.00, NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for t_platform_account_recharge
-- ----------------------------
DROP TABLE IF EXISTS `t_platform_account_recharge`;
CREATE TABLE `t_platform_account_recharge` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `is_deleted` tinyint DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `account_id` bigint DEFAULT NULL COMMENT '商户id',
  `recharge_num` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '充值编号',
  `pay_num` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '支付单号',
  `pay_type` int DEFAULT NULL COMMENT '1扫码支付->支付宝2扫码支付->微信3汇款支付',
  `finish_time` datetime DEFAULT NULL COMMENT '结束时间(异步回调时间)',
  `pay_amount` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '支付余额',
  `pay_info` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '付款方信息(json)',
  `account_amount` decimal(10,2) DEFAULT NULL COMMENT '账户余额',
  `status` tinyint(1) DEFAULT NULL COMMENT '充值状态: 0:生成订单 1:充值中 2:充值成功 ',
  `pay_source` tinyint(1) DEFAULT NULL COMMENT '充值源头 1.商户  2.代理商 3.渠道商',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '租户id 授权小程序时生成 随机18个字符生成',
  `prepay_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '三方预交易标识',
  `audit_time` datetime DEFAULT NULL COMMENT '汇款支付审核时间',
  `invoice_status` tinyint(1) DEFAULT NULL COMMENT '开票状态  0-未开票 1-已开票',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `is_deleted` (`is_deleted`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=165 DEFAULT CHARSET=utf8mb3 COMMENT='充值订单表';

-- ----------------------------
-- Records of t_platform_account_recharge
-- ----------------------------
BEGIN;
COMMIT;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='店铺信息表';

-- ----------------------------
-- Records of t_platform_code_version
-- ----------------------------
BEGIN;
COMMIT;

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
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='默认数据表';

-- ----------------------------
-- Records of t_platform_default_value
-- ----------------------------
BEGIN;
COMMIT;

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
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='基础库信息表';

-- ----------------------------
-- Records of t_platform_libraries_info
-- ----------------------------
BEGIN;
INSERT INTO `t_platform_libraries_info` VALUES (1, NULL, NULL, 1, '0.1', 0, 2, NULL, NULL, NULL, 0, '2021-07-14 10:11:06', '2021-07-14 10:15:30', NULL);
COMMIT;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='日志更新表';

-- ----------------------------
-- Records of t_platform_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_platform_pay_config
-- ----------------------------
DROP TABLE IF EXISTS `t_platform_pay_config`;
CREATE TABLE `t_platform_pay_config` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `certificate_path` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '商户支付证书路径',
  `mch_key` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '支付秘钥',
  `mch_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '商户号',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '租户id',
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
) ENGINE=InnoDB AUTO_INCREMENT=208 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of t_platform_pay_config
-- ----------------------------
BEGIN;
INSERT INTO `t_platform_pay_config` VALUES (207, NULL, NULL, NULL, '100001', 0, '2021-09-15 11:00:55', '2021-09-15 11:00:55', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
COMMIT;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='服务器配置表';

-- ----------------------------
-- Records of t_platform_server_cfg
-- ----------------------------
BEGIN;
COMMIT;

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
) ENGINE=InnoDB AUTO_INCREMENT=254 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='服务信息表';

-- ----------------------------
-- Records of t_platform_service_info
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_platform_shop_deploy
-- ----------------------------
DROP TABLE IF EXISTS `t_platform_shop_deploy`;
CREATE TABLE `t_platform_shop_deploy` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `server_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '服务器IP',
  `domain_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '服务器域名',
  `cdn_cfg` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'cdn配置json',
  `cdn_type` tinyint DEFAULT NULL COMMENT '0不使用，1七牛云，2阿里云，腾讯云',
  `is_deleted` tinyint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `shop_id` bigint DEFAULT NULL COMMENT '店铺id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=399 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='店铺部署信息表';

-- ----------------------------
-- Records of t_platform_shop_deploy
-- ----------------------------
BEGIN;
INSERT INTO `t_platform_shop_deploy` VALUES (398, NULL, NULL, NULL, NULL, 0, '2021-09-15 11:00:52', '2021-09-15 11:00:52', 398);
COMMIT;

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
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '租户id',
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
) ENGINE=InnoDB AUTO_INCREMENT=399 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='店铺信息表';

-- ----------------------------
-- Records of t_platform_shop_info
-- ----------------------------
BEGIN;
INSERT INTO `t_platform_shop_info` VALUES (398, 'https://medusa-small-file.oss-cn-hangzhou.aliyuncs.com/gruul/20200407/logo.png', '启山商城', 2, NULL, 0, 1, 1, 1, NULL, 0, '2021-09-15 11:00:52', '2021-09-15 11:00:51', 1, 0, NULL, NULL, '100001', NULL, NULL, 1, NULL, NULL, NULL, 1, NULL, 'https://medusa-small-file.oss-cn-hangzhou.aliyuncs.com/gruul/20200407/logo.png');
COMMIT;

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
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '租户id',
  `mark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '消息标识',
  `mini_template_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '小程序模板消息id',
  `code_template_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '短信模板id',
  `mp_template_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '公众号模板id',
  `title` varchar(28) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '标题名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2460 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='店铺消息配置';

-- ----------------------------
-- Records of t_platform_shop_message
-- ----------------------------
BEGIN;
COMMIT;

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
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='店铺模版详情表';

-- ----------------------------
-- Records of t_platform_shop_template_detail
-- ----------------------------
BEGIN;
INSERT INTO `t_platform_shop_template_detail` VALUES (1, '0.1', 70, '', NULL, NULL, 'https://mall.bgniao.cn/copartner/0.1', '', 'https://mall.bgniao.cn/', '', 0, 1, 0, '2021-01-16 16:47:32', '2021-09-15 11:00:52', '', '{\"RegionalUrl\":\"https://mall.bgniao.cn/areaColonel/0.1\",\"ConsoleToAgent\":\"https://mall.bgniao.cn/agent\"}');
COMMIT;

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
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='店铺模版详情小程序版本子表';

-- ----------------------------
-- Records of t_platform_shop_template_detail_minis
-- ----------------------------
BEGIN;
INSERT INTO `t_platform_shop_template_detail_minis` VALUES (1, 0, '2021-07-29 14:17:41', '2021-07-29 14:38:23', '0', '0.1', '商超', 1);
COMMIT;

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
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='店铺模版表';

-- ----------------------------
-- Records of t_platform_shop_template_info
-- ----------------------------
BEGIN;
INSERT INTO `t_platform_shop_template_info` VALUES (1, '商超', 'MALL', 1, NULL, NULL, '', 0, '2021-02-19 20:27:50', '2021-02-19 20:27:49');
COMMIT;

-- ----------------------------
-- Table structure for t_product
-- ----------------------------
DROP TABLE IF EXISTS `t_product`;
CREATE TABLE `t_product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '租户ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '删除状态：0->未删除；1->已删除',
  `shop_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '本店店铺id',
  `provider_id` bigint DEFAULT NULL COMMENT '供应商id',
  `attribute_id` bigint DEFAULT NULL COMMENT '属性模版id',
  `attribute_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '属性模版名称',
  `freight_template_id` bigint DEFAULT NULL COMMENT '运费模板id',
  `sale_mode` bigint unsigned DEFAULT NULL COMMENT '销售专区(默认生成商超系统)',
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
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb3 COMMENT='商品信息';

-- ----------------------------
-- Records of t_product
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_product_attribute
-- ----------------------------
DROP TABLE IF EXISTS `t_product_attribute`;
CREATE TABLE `t_product_attribute` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '租户ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '删除状态：0->未删除；1->已删除',
  `shop_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '本店店铺id',
  `product_id` bigint DEFAULT NULL COMMENT '产品id',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '属性名称',
  `value` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '属性值',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=119 DEFAULT CHARSET=utf8mb3 COMMENT='商品属性';

-- ----------------------------
-- Records of t_product_attribute
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_product_show_category
-- ----------------------------
DROP TABLE IF EXISTS `t_product_show_category`;
CREATE TABLE `t_product_show_category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '租户ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '删除状态：0->未删除；1->已删除',
  `shop_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '本店店铺id',
  `product_id` bigint NOT NULL COMMENT '产品id',
  `parent_id` bigint NOT NULL COMMENT '上级分类的编号：0表示一级分类',
  `show_category_id` bigint DEFAULT NULL COMMENT '商品展示分类id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=153 DEFAULT CHARSET=utf8mb3 COMMENT='商品展示分类';

-- ----------------------------
-- Records of t_product_show_category
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_sale_mode
-- ----------------------------
DROP TABLE IF EXISTS `t_sale_mode`;
CREATE TABLE `t_sale_mode` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '租户ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '删除状态：0->未删除；1->已删除',
  `shop_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '本店店铺id',
  `mode_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '专区名称',
  `mode_type` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '专区类型',
  `sort` tinyint DEFAULT NULL COMMENT '专区排序(按大小升序排序)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3 COMMENT='自定义专区';

-- ----------------------------
-- Records of t_sale_mode
-- ----------------------------
BEGIN;
COMMIT;

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
  `shop_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '店铺id',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '租户id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of t_shop_guide_page
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_shop_guide_page_switch
-- ----------------------------
DROP TABLE IF EXISTS `t_shop_guide_page_switch`;
CREATE TABLE `t_shop_guide_page_switch` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_open` tinyint(1) DEFAULT NULL COMMENT '引导页是否开启 0 -->未开启 1-->已开启',
  `shop_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '店铺id',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '租户id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of t_shop_guide_page_switch
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_shopping_cart
-- ----------------------------
DROP TABLE IF EXISTS `t_shopping_cart`;
CREATE TABLE `t_shopping_cart` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '租户ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '删除状态：0->未删除；1->已删除',
  `shop_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '本店店铺id',
  `product_id` bigint DEFAULT NULL COMMENT '产品id',
  `user_id` varchar(28) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户id',
  `sku_id` bigint NOT NULL COMMENT 'sku_id',
  `goods_number` int NOT NULL DEFAULT '0' COMMENT '商品数量',
  `activity_id` bigint DEFAULT NULL COMMENT '活动id',
  `activity_start_time` datetime DEFAULT NULL COMMENT '活动开始时间',
  `activity_end_time` datetime DEFAULT NULL COMMENT '活动结束时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=457 DEFAULT CHARSET=utf8mb3 COMMENT='商品购物车';

-- ----------------------------
-- Records of t_shopping_cart
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_shops
-- ----------------------------
DROP TABLE IF EXISTS `t_shops`;
CREATE TABLE `t_shops` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '@desc id',
  `shop_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc 商铺id',
  `agreement` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '@desc 协议',
  `amount_raised` decimal(10,0) DEFAULT NULL COMMENT '@desc 起提金额',
  `create_time` datetime DEFAULT NULL COMMENT '@desc 创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '@desc 修改时间',
  `is_deleted` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc 删除标识',
  `tenant_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc 租户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_shops
-- ----------------------------
BEGIN;
INSERT INTO `t_shops` VALUES (22, '100001100001', NULL, NULL, '2021-09-15 11:00:54', '2021-09-15 11:00:54', '0', '100001');
COMMIT;

-- ----------------------------
-- Table structure for t_shops_menu_config
-- ----------------------------
DROP TABLE IF EXISTS `t_shops_menu_config`;
CREATE TABLE `t_shops_menu_config` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '@desc: id',
  `tenant_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '@desc  活动关联商家',
  `shop_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '@desc  页面关联店铺ID',
  `is_deleted` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '@desc  标识  0正常(def) 1已删除',
  `properties` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '@desc  组件属性 json串',
  `create_time` datetime DEFAULT NULL COMMENT '@desc  创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '@desc  修改时间',
  `operator_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '@desc  操作人id',
  `operator_name` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '@desc  操作人name',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COMMENT='店铺合伙人菜单属性配置表';

-- ----------------------------
-- Records of t_shops_menu_config
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_shops_partner
-- ----------------------------
DROP TABLE IF EXISTS `t_shops_partner`;
CREATE TABLE `t_shops_partner` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '@desc id',
  `create_time` datetime DEFAULT NULL COMMENT '@desc 创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '@desc 修改时间',
  `partner_id` bigint DEFAULT NULL COMMENT '@desc 总店id',
  `tenant_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc 租户id',
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
  `shop_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc 本店店铺id',
  `invitation_code` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '邀请码',
  `platform_id` bigint DEFAULT NULL COMMENT '@desc  平台用户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of t_shops_partner
-- ----------------------------
BEGIN;
INSERT INTO `t_shops_partner` VALUES (17, '2021-09-15 11:00:54', '2021-09-15 11:00:54', NULL, '100001', '0', '13333333333', NULL, 'admin123', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', '0', NULL, NULL, NULL, '100001100001', '90464', 1);
COMMIT;

-- ----------------------------
-- Table structure for t_shops_renovation_assembly
-- ----------------------------
DROP TABLE IF EXISTS `t_shops_renovation_assembly`;
CREATE TABLE `t_shops_renovation_assembly` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '@desc: id',
  `tenant_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '@desc  活动关联商家',
  `shop_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '@desc  页面关联店铺ID',
  `page_id` bigint DEFAULT NULL COMMENT '@desc  所属页面ID',
  `is_deleted` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '@desc  标识  0正常(def) 1已删除',
  `properties` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '@desc  组件属性 json串',
  `create_time` datetime DEFAULT NULL COMMENT '@desc  创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '@desc  修改时间',
  `operator_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '@desc  操作人id',
  `operator_name` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '@desc  操作人name',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4802 DEFAULT CHARSET=utf8mb3 COMMENT='店铺装修模板页面组件属性表';

-- ----------------------------
-- Records of t_shops_renovation_assembly
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_shops_renovation_page
-- ----------------------------
DROP TABLE IF EXISTS `t_shops_renovation_page`;
CREATE TABLE `t_shops_renovation_page` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '	\r\n@desc: id',
  `tenant_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '@desc  活动关联商家',
  `shop_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '@desc  页面关联店铺ID',
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
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb3 COMMENT='店铺装修模板页面表';

-- ----------------------------
-- Records of t_shops_renovation_page
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_shops_renovation_plugin
-- ----------------------------
DROP TABLE IF EXISTS `t_shops_renovation_plugin`;
CREATE TABLE `t_shops_renovation_plugin` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '@desc: id',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc  活动关联商家',
  `template_id` bigint DEFAULT NULL COMMENT '	@desc  所属模板ID',
  `shop_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc 页面关联店铺ID',
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
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='店铺装修页面全局控件属性表';

-- ----------------------------
-- Records of t_shops_renovation_plugin
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_shops_renovation_template
-- ----------------------------
DROP TABLE IF EXISTS `t_shops_renovation_template`;
CREATE TABLE `t_shops_renovation_template` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '@desc id',
  `tenant_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '@desc 租户ID',
  `shop_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '	@desc 模板关联店铺ID',
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
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb3 COMMENT='店铺装修模板表';

-- ----------------------------
-- Records of t_shops_renovation_template
-- ----------------------------
BEGIN;
INSERT INTO `t_shops_renovation_template` VALUES (16, '100001', '100001100001', '0', NULL, '1', '1', '2021-09-15 11:16:04', '2021-09-15 11:16:04', NULL, NULL, '0', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for t_shops_sales_config
-- ----------------------------
DROP TABLE IF EXISTS `t_shops_sales_config`;
CREATE TABLE `t_shops_sales_config` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '@desc id',
  `sales_volume` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc 销售额',
  `shares` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc 入股费百分比',
  `level` int DEFAULT NULL COMMENT '@desc 层级',
  `create_time` datetime DEFAULT NULL COMMENT '@desc 创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '@desc 修改时间',
  `shop_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc 店铺id',
  `tenant_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc 租户id',
  `is_deleted` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc 删除标识',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of t_shops_sales_config
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_shops_search_terms
-- ----------------------------
DROP TABLE IF EXISTS `t_shops_search_terms`;
CREATE TABLE `t_shops_search_terms` (
  `id` bigint NOT NULL COMMENT '@desc id',
  `shop_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc 商铺id',
  `create_time` datetime DEFAULT NULL COMMENT '@desc 创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '@desc 修改时间',
  `is_deleted` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc 删除标识',
  `tenant_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc 租户id',
  `terms` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc 词语 多个以英文逗号分隔',
  `def_terms` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc 默认 搜索词语',
  `is_show` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '@desc 是否显示',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='店铺默认搜索关键字';

-- ----------------------------
-- Records of t_shops_search_terms
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_show_category
-- ----------------------------
DROP TABLE IF EXISTS `t_show_category`;
CREATE TABLE `t_show_category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '租户ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '删除状态：0->未删除；1->已删除',
  `parent_id` bigint DEFAULT NULL COMMENT '上级分类的编号：0表示一级分类',
  `shop_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '本店店铺id',
  `sale_mode` bigint DEFAULT NULL COMMENT '销售专区',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '分类名称',
  `level` int DEFAULT NULL COMMENT '分类级别：0->1级；1->2级',
  `sort` int DEFAULT NULL COMMENT '分类排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb3 COMMENT='商品展示分类';

-- ----------------------------
-- Records of t_show_category
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_sku_stock
-- ----------------------------
DROP TABLE IF EXISTS `t_sku_stock`;
CREATE TABLE `t_sku_stock` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '租户ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '删除状态：0->未删除；1->已删除',
  `shop_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '本店店铺id',
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
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb3 COMMENT='sku库存';

-- ----------------------------
-- Records of t_sku_stock
-- ----------------------------
BEGIN;
COMMIT;

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
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='短信订单历史表';

-- ----------------------------
-- Records of t_sms_order
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_sms_order_his_201912
-- ----------------------------
DROP TABLE IF EXISTS `t_sms_order_his_201912`;
CREATE TABLE `t_sms_order_his_201912` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint NOT NULL COMMENT '商户id',
  `sms_send_time` bigint NOT NULL COMMENT '短信发送时间',
  `sms_send_type` tinyint NOT NULL COMMENT '1普通短信(待扩充)，2通知短信',
  `sms_send_content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '短信内容',
  `sms_send_zone` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '86' COMMENT '短信区号，默认中国86',
  `sms_send_param` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '短信参数json',
  `sms_send_mobiles` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '发送手机号json数组',
  `sms_send_count` int NOT NULL DEFAULT '1' COMMENT '短信条数,(不计算字数，目前商家最好自行去对应平台对账)',
  `sms_send_success_count` int NOT NULL COMMENT '短信成功条数)',
  `sms_send_fail_count` int NOT NULL COMMENT '短信失败条数)',
  `sms_send_status` int NOT NULL DEFAULT '1' COMMENT '短信发送状态(0待发送，1发送中，2提交通道商，3通道商已返回,4已发送)',
  `sms_provider_id` bigint NOT NULL COMMENT '短信供应商id(t_sms_provider表中的id)',
  `sms_type` tinyint DEFAULT '1' COMMENT '扩展字段',
  `is_deleted` tinyint(1) DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL,
  `template_id` bigint DEFAULT NULL COMMENT '模版id',
  `sign_id` bigint DEFAULT NULL COMMENT '签名id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='短信订单历史表';

-- ----------------------------
-- Records of t_sms_order_his_201912
-- ----------------------------
BEGIN;
COMMIT;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='短信订单历史表';

-- ----------------------------
-- Records of t_sms_order_send_detail
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_sms_order_send_detail_his_201912
-- ----------------------------
DROP TABLE IF EXISTS `t_sms_order_send_detail_his_201912`;
CREATE TABLE `t_sms_order_send_detail_his_201912` (
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='短信订单历史表';

-- ----------------------------
-- Records of t_sms_order_send_detail_his_201912
-- ----------------------------
BEGIN;
COMMIT;

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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='短信订单历史表';

-- ----------------------------
-- Records of t_sms_provider
-- ----------------------------
BEGIN;
COMMIT;

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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='短信签名表';

-- ----------------------------
-- Records of t_sms_sign
-- ----------------------------
BEGIN;
COMMIT;

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
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='短信模版表';

-- ----------------------------
-- Records of t_sms_template
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_supplier
-- ----------------------------
DROP TABLE IF EXISTS `t_supplier`;
CREATE TABLE `t_supplier` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '租户ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '删除状态：0->未删除；1->已删除',
  `shop_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '本店店铺id',
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3 COMMENT='供应商';

-- ----------------------------
-- Records of t_supplier
-- ----------------------------
BEGIN;
COMMIT;

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
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb3 COMMENT='发票工单表';

-- ----------------------------
-- Records of t_sys_shop_invoice_order
-- ----------------------------
BEGIN;
COMMIT;

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
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb3 COMMENT='用户发票抬头表';

-- ----------------------------
-- Records of t_sys_shop_invoice_rise
-- ----------------------------
BEGIN;
COMMIT;

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
) ENGINE=InnoDB AUTO_INCREMENT=122 DEFAULT CHARSET=utf8mb3 COMMENT='店铺套餐';

-- ----------------------------
-- Records of t_sys_shop_package
-- ----------------------------
BEGIN;
COMMIT;

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
  `tenant_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '店铺租户id',
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
) ENGINE=InnoDB AUTO_INCREMENT=434 DEFAULT CHARSET=utf8mb3 COMMENT='店铺套餐订单表';

-- ----------------------------
-- Records of t_sys_shop_package_order
-- ----------------------------
BEGIN;
COMMIT;

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
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统配置';

-- ----------------------------
-- Records of t_system_conf
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_template_msg_send_record
-- ----------------------------
DROP TABLE IF EXISTS `t_template_msg_send_record`;
CREATE TABLE `t_template_msg_send_record` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `is_deleted` tinyint(1) DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除 ',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '租户id',
  `use_type` tinyint(1) DEFAULT NULL COMMENT '使用类型 1-买家通知 2-商家通知',
  `message_type` tinyint DEFAULT NULL COMMENT '消息类别 1-订单消息 2-售后消息 3-用户消息 4-营销活动',
  `send_json` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '发送内容json',
  `send_status` tinyint DEFAULT NULL COMMENT '发送状态 0-发送失败  1-发送成功',
  `failure_reason` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '失败原因',
  `use_teamplate_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '使用的模板消息id',
  `mini_id` bigint DEFAULT NULL COMMENT '小程序表id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='模板消息发送记录';

-- ----------------------------
-- Records of t_template_msg_send_record
-- ----------------------------
BEGIN;
COMMIT;

INSERT INTO `gruul_open`.`t_platform_default_value` (`id`, `unique_identification`, `version`, `create_time`, `is_deleted`, `update_time`, `build_action`, `url`, `mq`, `kv`) VALUES (13, 'SHOP_TEMPLATE_DEFAULT', 'v0.1', '2021-08-27 17:09:12', 0, '2021-08-27 17:09:12', 1, NULL, '{\"routeKey\":\"gruul.shops.template.queue.init\",\"exchange\":\"gruul.shops.exchange\"}', '{\"isDevTemplate\":\"0\",\"colour\":\"1\",\"pages\":[{\"assemblies\":[{\"properties\":\"{\\\"icon\\\":\\\"lunbotu\\\",\\\"value\\\":\\\"HomeSwiper\\\",\\\"label\\\":\\\"轮播图\\\",\\\"id\\\":1593736550284,\\\"formData\\\":{\\\"type\\\":\\\"HomeSwiper\\\",\\\"swiperList\\\":[{\\\"title\\\":\\\"\\\",\\\"img\\\":\\\"https://oss-tencent.bgniao.cn//gruul/20200703/54fe31285d474fd793232f7b0b759f43.png\\\",\\\"link\\\":{\\\"id\\\":\\\"\\\",\\\"type\\\":\\\"\\\",\\\"name\\\":\\\"\\\",\\\"url\\\":\\\"\\\",\\\"append\\\":\\\"\\\"}}],\\\"padding\\\":2,\\\"imageStyle\\\":1,\\\"imageAngle\\\":1,\\\"indicator\\\":1,\\\"height\\\":360}}\"},{\"properties\":\"{\\\"icon\\\":\\\"https://qiniu-app.qtshe.com/%E5%BA%97%E9%93%BA%E5%AF%BC%E8%88%AA.png\\\",\\\"value\\\":\\\"StoreNavigation\\\",\\\"label\\\":\\\"店铺导航\\\",\\\"id\\\":1587020489455,\\\"formData\\\":{\\\"storeNavigations\\\":[{\\\"navName\\\":\\\"新品\\\",\\\"fontColor\\\":\\\"#333333\\\",\\\"navIcon\\\":\\\"https://oss-tencent.bgniao.cn//gruul/20200703/7230eec5db0846c984ed6770124642bd.png\\\",\\\"pathLink\\\":\\\"\\\",\\\"linkUrl\\\":\\\"/pages/index/index\\\",\\\"linkName\\\":\\\"生活用品\\\",\\\"append\\\":\\\"mall\\\",\\\"type\\\":2,\\\"id\\\":259},{\\\"navName\\\":\\\"热卖\\\",\\\"fontColor\\\":\\\"#333333\\\",\\\"navIcon\\\":\\\"https://oss-tencent.bgniao.cn//gruul/20200703/a5b7b715655a4d46937049f8b7c34bb3.png\\\",\\\"pathLink\\\":\\\"\\\",\\\"linkUrl\\\":\\\"/pages/group/group\\\",\\\"linkName\\\":\\\"社群拼团\\\",\\\"append\\\":\\\"\\\",\\\"type\\\":4,\\\"id\\\":8},{\\\"navName\\\":\\\"超市\\\",\\\"fontColor\\\":\\\"#333333\\\",\\\"navIcon\\\":\\\"https://oss-tencent.bgniao.cn//gruul/20200703/45e3c2ecdcb249749071990658994742.png\\\",\\\"pathLink\\\":\\\"\\\",\\\"linkUrl\\\":\\\"/pages/index/index\\\",\\\"linkName\\\":\\\"分类页\\\",\\\"append\\\":\\\"mall\\\",\\\"type\\\":0,\\\"id\\\":2},{\\\"navName\\\":\\\"领券\\\",\\\"fontColor\\\":\\\"#333333\\\",\\\"navIcon\\\":\\\"https://oss-tencent.bgniao.cn//gruul/20200703/f9278a121a2f407583068095ac95e56b.png\\\",\\\"pathLink\\\":\\\"\\\",\\\"linkUrl\\\":\\\"/pages/coupon/couponCenter/couponCenter\\\",\\\"linkName\\\":\\\"领券中心\\\",\\\"append\\\":\\\"\\\",\\\"type\\\":4,\\\"id\\\":3},{\\\"navName\\\":\\\"签到\\\",\\\"fontColor\\\":\\\"#333333\\\",\\\"navIcon\\\":\\\"https://oss-tencent.bgniao.cn//gruul/20200703/feb8ef7c47794309a84b16c90391110b.png\\\",\\\"pathLink\\\":\\\"\\\",\\\"linkUrl\\\":\\\"/pages/integralShop/integralShop\\\",\\\"linkName\\\":\\\"积分商城\\\",\\\"append\\\":\\\"\\\",\\\"type\\\":4,\\\"id\\\":1}]}}\"},{\"properties\":\"{\\\"icon\\\":\\\"mofang\\\",\\\"value\\\":\\\"CubeBox\\\",\\\"label\\\":\\\"魔方\\\",\\\"id\\\":1593739139109,\\\"formData\\\":{\\\"borderWidth\\\":6,\\\"layoutWidth\\\":6,\\\"layoutHeight\\\":6,\\\"showMethod\\\":7,\\\"pageMargin\\\":6,\\\"width\\\":2,\\\"subEntry\\\":[{\\\"x\\\":0,\\\"y\\\":0,\\\"width\\\":3,\\\"height\\\":2,\\\"img\\\":\\\"https://oss-tencent.bgniao.cn//gruul/20200703/7f7bbc0589bb4e4dbc4c1071fd84f63b.png\\\",\\\"link\\\":{\\\"id\\\":null,\\\"type\\\":null,\\\"name\\\":\\\"\\\",\\\"url\\\":\\\"\\\",\\\"append\\\":\\\"\\\"},\\\"linkName\\\":\\\"\\\"},{\\\"x\\\":3,\\\"y\\\":0,\\\"width\\\":3,\\\"height\\\":2,\\\"img\\\":\\\"https://oss-tencent.bgniao.cn//gruul/20200703/a46b4232a8114dc18d9da129658c92d2.png\\\",\\\"link\\\":{\\\"id\\\":null,\\\"type\\\":null,\\\"name\\\":\\\"\\\",\\\"url\\\":\\\"\\\",\\\"append\\\":\\\"\\\"},\\\"linkName\\\":\\\"\\\"}]}}\"},{\"properties\":\"{\\\"icon\\\":\\\"mofang\\\",\\\"value\\\":\\\"CubeBox\\\",\\\"label\\\":\\\"魔方\\\",\\\"id\\\":1593739995060,\\\"formData\\\":{\\\"borderWidth\\\":0,\\\"layoutWidth\\\":7,\\\"layoutHeight\\\":7,\\\"showMethod\\\":7,\\\"pageMargin\\\":0,\\\"width\\\":2,\\\"subEntry\\\":[{\\\"x\\\":0,\\\"y\\\":0,\\\"width\\\":7,\\\"height\\\":1,\\\"img\\\":\\\"https://oss-tencent.bgniao.cn//gruul/20200703/0c1a4dc152d541709d3a5515f521a1e2.png\\\",\\\"link\\\":{\\\"id\\\":null,\\\"type\\\":null,\\\"name\\\":\\\"\\\",\\\"url\\\":\\\"\\\",\\\"append\\\":\\\"\\\"},\\\"linkName\\\":\\\"\\\"}]}}\"},{\"properties\":\"{\\\"icon\\\":\\\"shangpin\\\",\\\"value\\\":\\\"Goods\\\",\\\"label\\\":\\\"商品\\\",\\\"id\\\":1593740199512,\\\"formData\\\":{\\\"goods\\\":[{\\\"id\\\":\\\"375\\\",\\\"img\\\":\\\"https://medusa-small-file-1253272780.cos.ap-shanghai.myqcloud.com/gruul/20200518/c3256f42e51c44799577530226a11533.jpg\\\",\\\"name\\\":\\\"省力型订书机学生装订机一指按压不费力订书器办公用品\\\",\\\"price\\\":\\\"0.01\\\",\\\"albumPics\\\":\\\"https://medusa-small-file-1253272780.cos.ap-shanghai.myqcloud.com/gruul/20200518/c3256f42e51c44799577530226a11533.jpg\\\"},{\\\"id\\\":\\\"374\\\",\\\"img\\\":\\\"https://medusa-small-file-1253272780.cos.ap-shanghai.myqcloud.com/gruul/20200518/598b1c96c2494f5bb62c3c6847031c78.jpg\\\",\\\"name\\\":\\\"迷你手持可充电USB小风扇办公桌面便携式电扇学生宿舍静音小电\\\",\\\"price\\\":\\\"0.01\\\",\\\"albumPics\\\":\\\"https://medusa-small-file-1253272780.cos.ap-shanghai.myqcloud.com/gruul/20200518/598b1c96c2494f5bb62c3c6847031c78.jpg\\\"}],\\\"listStyle\\\":\\\"goods-style--two\\\",\\\"pagePadding\\\":17,\\\"goodPadding\\\":12,\\\"goodsStyle\\\":\\\"is-none\\\",\\\"angle\\\":\\\"is-straight\\\",\\\"textStyle\\\":\\\"is-bold\\\",\\\"showContent\\\":{\\\"nameShow\\\":true,\\\"priceShow\\\":true,\\\"buttonShow\\\":true,\\\"buttonStyle\\\":1,\\\"buttonText\\\":\\\"\\\",\\\"tagShow\\\":false,\\\"tagStyle\\\":1},\\\"goodsItem\\\":{\\\"id\\\":1,\\\"img\\\":\\\"https://qiniu-app.qtshe.com/u391.png\\\",\\\"name\\\":\\\"商品名称商品名称商品名称商品名称商品名称商品名称商品名\\\",\\\"price\\\":\\\"99.90\\\"},\\\"goodsCount\\\":2}}\"}],\"isDef\":\"1\",\"pageName\":\"默认页面\"}],\"plugins\":[{\"pluginNameCn\":\"底部导航\",\"pluginProperties\":[{\"icon\":\"\",\"formData\":{\"menuList\":[{\"sortIndex\":0,\"selectedIconPath\":\"http://medusa-small-file.oss-cn-hangzhou.aliyuncs.com/gruul/20200327/1e26aab823e84979a6994a0d34918526.png\",\"iconType\":1,\"linkUrl\":\"/pages/index/index\",\"name\":\"\",\"linkSelectItem\":\"\",\"isHome\":true,\"text\":\"首页\",\"id\":\"471\",\"iconPath\":\"http://medusa-small-file.oss-cn-hangzhou.aliyuncs.com/gruul/20200327/9ad3a62298fd4c2aa75f93ac2b3a517a.png\",\"type\":5,\"linkName\":\"\"},{\"sortIndex\":1,\"selectedIconPath\":\"http://medusa-small-file.oss-cn-hangzhou.aliyuncs.com/gruul/20200327/7dae3bdf8ee24833b9600185fcec5fed.png\",\"iconType\":1,\"linkUrl\":\"/pages/index/index\",\"name\":\"mall\",\"linkSelectItem\":\"\",\"isHome\":false,\"text\":\"商超\",\"id\":2,\"iconPath\":\"http://medusa-small-file.oss-cn-hangzhou.aliyuncs.com/gruul/20200327/f8d2575562af4c9490cb25b0101b680e.png\",\"type\":0,\"linkName\":\"分类页\"},{\"sortIndex\":2,\"selectedIconPath\":\"http://medusa-small-file.oss-cn-hangzhou.aliyuncs.com/gruul/20200327/5f25150c75f247b590b8d8f100ea5820.png\",\"iconType\":1,\"linkUrl\":\"/pages/index/index\",\"name\":\"shopCar\",\"linkSelectItem\":\"\",\"isHome\":false,\"text\":\"购物车\",\"id\":4,\"iconPath\":\"http://medusa-small-file.oss-cn-hangzhou.aliyuncs.com/gruul/20200327/bda9aea298bc489084af4134a399d292.png\",\"type\":0,\"linkName\":\"购物车\"},{\"sortIndex\":3,\"selectedIconPath\":\"http://medusa-small-file.oss-cn-hangzhou.aliyuncs.com/gruul/20200327/13d654d202bb4fd69e2da28a6c20a90f.png\",\"iconType\":1,\"linkUrl\":\"/pages/index/index\",\"name\":\"me\",\"linkSelectItem\":\"\",\"isHome\":false,\"text\":\"我的\",\"id\":3,\"iconPath\":\"http://medusa-small-file.oss-cn-hangzhou.aliyuncs.com/gruul/20200327/4c5bcb9fb63f4e8e9e4cd50be247e00a.png\",\"type\":0,\"linkName\":\"个人中心\"}],\"defaultColor\":\"#7A7E83\",\"selectColor\":\"#F64E3F\",\"codeStyle\":1},\"label\":\"底部导航\",\"id\":1594696383853,\"value\":\"NavBar\"}],\"isSelection\":\"1\",\"pluginNameEn\":\"navBar\",\"isMandatory\":\"0\"}],\"onlineStatus\":\"1\",\"tenantId\":\"\",\"name\":\"拼团模板\",\"shopId\":\"\"}');
INSERT INTO `gruul_open`.`t_platform_default_value` (`id`, `unique_identification`, `version`, `create_time`, `is_deleted`, `update_time`, `build_action`, `url`, `mq`, `kv`) VALUES (14, 'ACCOUNT_CENTRE_DEFAULT', 'v0.1', '2021-08-27 17:09:12', 0, '2021-08-27 17:09:12', 1, '/', '{\"routeKey\":\"gruul.shops.center.queue.init\",\"exchange\":\"gruul.shops.exchange\"}', '{\"customStyle\":{\"backgroundImage\":\"https://medusa-small-file-1253272780.cos.ap-shanghai.myqcloud.com/gruul/20200418/53684d812d8e446fa688976d079f1fc0.jpg\",\"cardColor\":\"#89bf04\",\"textColor\":\"#E7C898\"},\"menuStyle\":1,\"hideCartInlet\":false,\"orderInfo\":{\"afterSaleIcon\":{\"name\":\"售后\",\"url\":\"https://medusa-small-file-1253272780.cos.ap-shanghai.myqcloud.com/gruul/20200418/ca9c9c65daeb4b3eb8afbfba8686998d.png\"},\"waitIcon\":{\"name\":\"待付款\",\"url\":\"https://medusa-small-file-1253272780.cos.ap-shanghai.myqcloud.com/gruul/20200418/c3dfb900d9b14730939a7b5e206644bc.png\"},\"waitPickingIcon\":{\"name\":\"待提货\",\"url\":\"https://medusa-small-file-1253272780.cos.ap-shanghai.myqcloud.com/gruul/20200418/9939df2bfa5f46ceac080d33ba40aaba.png\"},\"deliveryIcon\":{\"name\":\"配送中\",\"url\":\"https://medusa-small-file-1253272780.cos.ap-shanghai.myqcloud.com/gruul/20200418/9435ecaa9f304b1fa5555e8be01367ad.png\"},\"evaluateIcon\":{\"name\":\"评价中心\",\"url\":\"https://medusa-small-file-1253272780.cos.ap-shanghai.myqcloud.com/gruul/20200418/26354a7bdab144c98f46844c06e18e3f.png\"}},\"headStyle\":2,\"getCartText\":\"领卡文案\",\"codeStyle\":2,\"menuVos\":[{\"styleType\":1,\"hideMenu\":true,\"subMenu\":[],\"defaultIcon\":\"http://medusa-small-file.oss-cn-hangzhou.aliyuncs.com/%E5%9B%BE%E6%A0%87/jifen.png\",\"sortIndex\":0,\"splitFlag\":true,\"menuIconUrl\":\"http://medusa-small-file.oss-cn-hangzhou.aliyuncs.com/%E5%9B%BE%E6%A0%87/jifen.png\",\"menuName\":\"积分商城\",\"pId\":\"0\",\"linkSelectItem\":{\"name\":\"积分商城\",\"id\":1,\"type\":4,\"url\":\"/pages/integralShop/integralShop\",\"append\":\"group\"},\"allowUse\":1},{\"styleType\":1,\"hideMenu\":true,\"subMenu\":[],\"defaultIcon\":\"https://medusa-small-file-1253272780.cos.ap-shanghai.myqcloud.com/gruul/20200418/394b4cfae2c04f4aa6992a72806abec4.png\",\"sortIndex\":2,\"splitFlag\":false,\"menuIconUrl\":\"https://medusa-small-file-1253272780.cos.ap-shanghai.myqcloud.com/gruul/20200418/394b4cfae2c04f4aa6992a72806abec4.png\",\"menuName\":\"团长中心\",\"pId\":\"0\",\"linkSelectItem\":{\"name\":\"团长中心\",\"id\":10,\"type\":0,\"url\":\"/pages/commanderCenter/commanderCenter\"},\"allowUse\":1},{\"styleType\":1,\"hideMenu\":true,\"subMenu\":[],\"defaultIcon\":\"https://medusa-small-file-1253272780.cos.ap-shanghai.myqcloud.com/gruul/20200418/ca0c22ca947e41229082a32ab013ea73.png\",\"sortIndex\":3,\"splitFlag\":false,\"menuIconUrl\":\"https://medusa-small-file-1253272780.cos.ap-shanghai.myqcloud.com/gruul/20200418/ca0c22ca947e41229082a32ab013ea73.png\",\"menuName\":\"团长信息\",\"pId\":\"0\",\"linkSelectItem\":{\"name\":\"团长信息\",\"id\":11,\"type\":0,\"url\":\"/pages/commanderInfo/commanderInfo\"},\"allowUse\":1},{\"styleType\":1,\"hideMenu\":true,\"subMenu\":[],\"defaultIcon\":\"https://medusa-small-file-1253272780.cos.ap-shanghai.myqcloud.com/gruul/20200418/cc7dfc2ecdbf4271aecf3a11d1ab51e2.png\",\"sortIndex\":4,\"splitFlag\":true,\"menuIconUrl\":\"https://medusa-small-file-1253272780.cos.ap-shanghai.myqcloud.com/gruul/20200418/cc7dfc2ecdbf4271aecf3a11d1ab51e2.png\",\"menuName\":\"提货点后台\",\"pId\":\"0\",\"linkSelectItem\":{\"name\":\"提货点后台\",\"id\":12,\"type\":0,\"url\":\"/pages/pointBackground/pointBackground\"},\"allowUse\":1},{\"styleType\":1,\"hideMenu\":true,\"subMenu\":[],\"defaultIcon\":\"http://medusa-small-file.oss-cn-hangzhou.aliyuncs.com/%E5%9B%BE%E6%A0%87/ziyuanruzhu.png\",\"sortIndex\":5,\"splitFlag\":false,\"menuIconUrl\":\"http://medusa-small-file.oss-cn-hangzhou.aliyuncs.com/%E5%9B%BE%E6%A0%87/ziyuanruzhu.png\",\"menuName\":\"资源入驻\",\"pId\":\"0\",\"linkSelectItem\":{\"name\":\"资源入驻\",\"id\":5,\"type\":0,\"url\":\"/pages/resourceApply/resourceApply\"},\"allowUse\":1},{\"styleType\":1,\"hideMenu\":true,\"subMenu\":[],\"defaultIcon\":\"https://medusa-small-file-1253272780.cos.ap-shanghai.myqcloud.com/gruul/20200418/d517511764824280840012b28e834fd7.png\",\"sortIndex\":7,\"splitFlag\":false,\"menuIconUrl\":\"https://medusa-small-file-1253272780.cos.ap-shanghai.myqcloud.com/gruul/20200418/d517511764824280840012b28e834fd7.png\",\"menuName\":\"领券中心\",\"pId\":\"0\",\"linkSelectItem\":{\"name\":\"领券中心\",\"id\":3,\"type\":4,\"url\":\"/pages/coupon/couponCenter/couponCenter\"},\"allowUse\":1},{\"styleType\":1,\"hideMenu\":true,\"subMenu\":[],\"defaultIcon\":\"https://medusa-small-file-1253272780.cos.ap-shanghai.myqcloud.com/gruul/20200418/1f471a7fab9c4a47ac714d6a6515992c.png\",\"sortIndex\":8,\"splitFlag\":false,\"menuIconUrl\":\"https://medusa-small-file-1253272780.cos.ap-shanghai.myqcloud.com/gruul/20200418/1f471a7fab9c4a47ac714d6a6515992c.png\",\"menuName\":\"我的优惠券\",\"pId\":\"0\",\"linkSelectItem\":{\"name\":\"我的优惠券\",\"id\":4,\"type\":4,\"url\":\"/pages/coupon/coupon\"},\"allowUse\":1},{\"styleType\":1,\"hideMenu\":false,\"subMenu\":[],\"defaultIcon\":\"http://medusa-small-file.oss-cn-hangzhou.aliyuncs.com/%E5%9B%BE%E6%A0%87/gouwuche.png\",\"sortIndex\":9,\"splitFlag\":false,\"menuIconUrl\":\"http://medusa-small-file.oss-cn-hangzhou.aliyuncs.com/%E5%9B%BE%E6%A0%87/gouwuche.png\",\"menuName\":\"购物车\",\"pId\":\"0\",\"linkSelectItem\":{\"name\":\"购物车\",\"id\":4,\"type\":0,\"url\":\"/pages/index/index\",\"append\":\"shopCar\"},\"allowUse\":1},{\"styleType\":1,\"hideMenu\":true,\"subMenu\":[],\"defaultIcon\":\"https://medusa-small-file-1253272780.cos.ap-shanghai.myqcloud.com/gruul/20200418/708afcdfc62443938c9f07ef5d07daf5.png\",\"sortIndex\":11,\"splitFlag\":false,\"menuIconUrl\":\"https://medusa-small-file-1253272780.cos.ap-shanghai.myqcloud.com/gruul/20200418/708afcdfc62443938c9f07ef5d07daf5.png\",\"menuName\":\"地址管理\",\"pId\":\"0\",\"linkSelectItem\":{\"name\":\"地址管理\",\"id\":7,\"type\":0,\"url\":\"/pages/address/address\"},\"allowUse\":1},{\"styleType\":1,\"hideMenu\":true,\"subMenu\":[],\"defaultIcon\":\"https://medusa-small-file-1253272780.cos.ap-shanghai.myqcloud.com/gruul/20200418/80c68ba092174f3ea2067adf8944dbb6.png\",\"sortIndex\":12,\"splitFlag\":false,\"menuIconUrl\":\"https://medusa-small-file-1253272780.cos.ap-shanghai.myqcloud.com/gruul/20200418/80c68ba092174f3ea2067adf8944dbb6.png\",\"menuName\":\"设置\",\"pId\":\"0\",\"linkSelectItem\":{\"name\":\"设置\",\"id\":9,\"type\":0,\"url\":\"/pages/mySetting/mySetting\"},\"allowUse\":1},{\"styleType\":2,\"hideMenu\":true,\"subMenu\":[],\"defaultIcon\":\"http://medusa-small-file.oss-cn-hangzhou.aliyuncs.com/%E5%9B%BE%E6%A0%87/jifen.png\",\"sortIndex\":0,\"splitFlag\":true,\"menuIconUrl\":\"http://medusa-small-file.oss-cn-hangzhou.aliyuncs.com/%E5%9B%BE%E6%A0%87/jifen.png\",\"menuName\":\"积分商城\",\"pId\":\"0\",\"linkSelectItem\":{\"name\":\"积分商城\",\"id\":1,\"type\":4,\"url\":\"/pages/integralShop/integralShop\",\"append\":\"group\"},\"allowUse\":1},{\"styleType\":2,\"hideMenu\":true,\"subMenu\":[],\"defaultIcon\":\"https://medusa-small-file-1253272780.cos.ap-shanghai.myqcloud.com/gruul/20200418/394b4cfae2c04f4aa6992a72806abec4.png\",\"sortIndex\":2,\"splitFlag\":false,\"menuIconUrl\":\"https://medusa-small-file-1253272780.cos.ap-shanghai.myqcloud.com/gruul/20200418/394b4cfae2c04f4aa6992a72806abec4.png\",\"menuName\":\"团长中心\",\"pId\":\"0\",\"linkSelectItem\":{\"name\":\"团长中心\",\"id\":10,\"type\":0,\"url\":\"/pages/commanderCenter/commanderCenter\"},\"allowUse\":1},{\"styleType\":2,\"hideMenu\":true,\"subMenu\":[],\"defaultIcon\":\"https://medusa-small-file-1253272780.cos.ap-shanghai.myqcloud.com/gruul/20200418/ca0c22ca947e41229082a32ab013ea73.png\",\"sortIndex\":3,\"splitFlag\":false,\"menuIconUrl\":\"https://medusa-small-file-1253272780.cos.ap-shanghai.myqcloud.com/gruul/20200418/ca0c22ca947e41229082a32ab013ea73.png\",\"menuName\":\"团长信息\",\"pId\":\"0\",\"linkSelectItem\":{\"name\":\"团长信息\",\"id\":11,\"type\":0,\"url\":\"/pages/commanderInfo/commanderInfo\"},\"allowUse\":1},{\"styleType\":2,\"hideMenu\":true,\"subMenu\":[],\"defaultIcon\":\"https://medusa-small-file-1253272780.cos.ap-shanghai.myqcloud.com/gruul/20200418/cc7dfc2ecdbf4271aecf3a11d1ab51e2.png\",\"sortIndex\":4,\"splitFlag\":true,\"menuIconUrl\":\"https://medusa-small-file-1253272780.cos.ap-shanghai.myqcloud.com/gruul/20200418/cc7dfc2ecdbf4271aecf3a11d1ab51e2.png\",\"menuName\":\"提货点后台\",\"pId\":\"0\",\"linkSelectItem\":{\"name\":\"提货点后台\",\"id\":12,\"type\":0,\"url\":\"/pages/pointBackground/pointBackground\"},\"allowUse\":1},{\"styleType\":2,\"hideMenu\":true,\"subMenu\":[],\"defaultIcon\":\"http://medusa-small-file.oss-cn-hangzhou.aliyuncs.com/%E5%9B%BE%E6%A0%87/ziyuanruzhu.png\",\"sortIndex\":5,\"splitFlag\":false,\"menuIconUrl\":\"http://medusa-small-file.oss-cn-hangzhou.aliyuncs.com/%E5%9B%BE%E6%A0%87/ziyuanruzhu.png\",\"menuName\":\"资源入驻\",\"pId\":\"0\",\"linkSelectItem\":{\"name\":\"资源入驻\",\"id\":5,\"type\":0,\"url\":\"/pages/resourceApply/resourceApply\"},\"allowUse\":1},{\"styleType\":2,\"hideMenu\":true,\"subMenu\":[],\"defaultIcon\":\"https://medusa-small-file-1253272780.cos.ap-shanghai.myqcloud.com/gruul/20200418/d517511764824280840012b28e834fd7.png\",\"sortIndex\":7,\"splitFlag\":false,\"menuIconUrl\":\"https://medusa-small-file-1253272780.cos.ap-shanghai.myqcloud.com/gruul/20200418/d517511764824280840012b28e834fd7.png\",\"menuName\":\"领券中心\",\"pId\":\"0\",\"linkSelectItem\":{\"name\":\"领券中心\",\"id\":3,\"type\":4,\"url\":\"/pages/coupon/couponCenter/couponCenter\"},\"allowUse\":1},{\"styleType\":2,\"hideMenu\":true,\"subMenu\":[],\"defaultIcon\":\"https://medusa-small-file-1253272780.cos.ap-shanghai.myqcloud.com/gruul/20200418/1f471a7fab9c4a47ac714d6a6515992c.png\",\"sortIndex\":8,\"splitFlag\":false,\"menuIconUrl\":\"https://medusa-small-file-1253272780.cos.ap-shanghai.myqcloud.com/gruul/20200418/1f471a7fab9c4a47ac714d6a6515992c.png\",\"menuName\":\"我的优惠券\",\"pId\":\"0\",\"linkSelectItem\":{\"name\":\"我的优惠券\",\"id\":4,\"type\":4,\"url\":\"/pages/coupon/coupon\"},\"allowUse\":1},{\"styleType\":2,\"hideMenu\":false,\"subMenu\":[],\"defaultIcon\":\"http://medusa-small-file.oss-cn-hangzhou.aliyuncs.com/%E5%9B%BE%E6%A0%87/gouwuche.png\",\"sortIndex\":9,\"splitFlag\":false,\"menuIconUrl\":\"http://medusa-small-file.oss-cn-hangzhou.aliyuncs.com/%E5%9B%BE%E6%A0%87/gouwuche.png\",\"menuName\":\"购物车\",\"pId\":\"0\",\"linkSelectItem\":{\"name\":\"购物车\",\"id\":4,\"type\":0,\"url\":\"/pages/index/index\",\"append\":\"shopCar\"},\"allowUse\":1},{\"styleType\":2,\"hideMenu\":true,\"subMenu\":[],\"defaultIcon\":\"https://medusa-small-file-1253272780.cos.ap-shanghai.myqcloud.com/gruul/20200418/708afcdfc62443938c9f07ef5d07daf5.png\",\"sortIndex\":11,\"splitFlag\":false,\"menuIconUrl\":\"https://medusa-small-file-1253272780.cos.ap-shanghai.myqcloud.com/gruul/20200418/708afcdfc62443938c9f07ef5d07daf5.png\",\"menuName\":\"地址管理\",\"pId\":\"0\",\"linkSelectItem\":{\"name\":\"地址管理\",\"id\":7,\"type\":0,\"url\":\"/pages/address/address\"},\"allowUse\":1},{\"styleType\":2,\"hideMenu\":true,\"subMenu\":[],\"defaultIcon\":\"https://medusa-small-file-1253272780.cos.ap-shanghai.myqcloud.com/gruul/20200418/80c68ba092174f3ea2067adf8944dbb6.png\",\"sortIndex\":12,\"splitFlag\":false,\"menuIconUrl\":\"https://medusa-small-file-1253272780.cos.ap-shanghai.myqcloud.com/gruul/20200418/80c68ba092174f3ea2067adf8944dbb6.png\",\"menuName\":\"设置\",\"pId\":\"0\",\"linkSelectItem\":{\"name\":\"设置\",\"id\":9,\"type\":0,\"url\":\"/pages/mySetting/mySetting\"},\"allowUse\":1}]}');
INSERT INTO `gruul_open`.`t_platform_default_value` (`id`, `unique_identification`, `version`, `create_time`, `is_deleted`, `update_time`, `build_action`, `url`, `mq`, `kv`) VALUES (16, 'MALL', 'v0.1', '2021-08-27 17:09:12', 0, '2021-08-27 17:09:12', 3, '/', '{}', '{\"packages\":[{\"level\":1,\"openState\":1,\"name\":\"门店版\",\"packagePrice\":1,\"remark\":\"适用于门店级商家订购，满足商品销售、推广营销等基础经营需求\",\"packagePriceUnit\":1},{\"level\":2,\"openState\":1,\"name\":\"企业版\",\"packagePrice\":4800,\"remark\":\"适用于在单一城市发展的企业级社区团购商家\",\"packagePriceUnit\":3},{\"level\":3,\"openState\":1,\"name\":\"集团版\",\"packagePrice\":12000,\"remark\":\"适用于多城市布局的商家\",\"packagePriceUnit\":3},{\"level\":4,\"openState\":1,\"name\":\"私有化部署\",\"packagePrice\":48000,\"remark\":\"开通旗舰版权限，3年使用期限，后续12800元/年，8核32G以上配置的服务器\",\"packagePriceUnit\":3}]}');
INSERT INTO `gruul_open`.`t_platform_default_value` (`id`, `unique_identification`, `version`, `create_time`, `is_deleted`, `update_time`, `build_action`, `url`, `mq`, `kv`) VALUES (17, 'GOODS_DEFUALT', 'v0.1', '2021-08-27 17:09:12', 0, '2021-08-27 17:09:12', 1, '', '{\"routeKey\":\"gruul.goods.default.create\",\"exchange\":\"gruul.goods.direct\"}', '{}');
INSERT INTO `gruul_open`.`t_platform_default_value` (`id`, `unique_identification`, `version`, `create_time`, `is_deleted`, `update_time`, `build_action`, `url`, `mq`, `kv`) VALUES (18, 'MINI_SHOP_MSG', 'v0.1', '2021-08-27 17:09:12', 0, '2021-08-27 17:09:12', 1, '/', '{\"routeKey\":\"gruul.platform.subscribe.msg.create\",\"exchange\":\"gruul.platform.exchange\"}', '{\"useType\":\"2\",\"version\":\"v0.1\",\"items\":[{\"messageType\":1,\"templateId\":\"mo39_g0c0I6RdUlszbrmMxqJ1KIhq2I5PPaC7KZSKs0\",\"title\":\"订单提醒\",\"type\":3,\"sendRule\":\"客户订单成功付款后，立即提醒\",\"exampleJson\":{\"title\":\"下单成功通知\",\"items\":[{\"v\":\"儿童书包\",\"k\":\"感谢您选择神工007，您的订单[D234254433]正在分配工人，请稍候。\"},{\"v\":\"英超海淘\",\"k\":\"店铺名称\"},{\"v\":\"2019年11月11日\",\"k\":\"下单时间\"},{\"v\":\"300元\",\"k\":\"支付金额\"},{\"v\":\"请在13:57之前完成支付\",\"k\":\"支付提醒\"}]}},{\"messageType\":1,\"templateId\":\"I7wGGmIF6zjLU5CCyM_g_jzy0RuVP58VsjNk7WN1k9I\",\"title\":\"订单待发货提醒\",\"type\":3,\"sendRule\":\"等待商家后台点发货的订单，立即提醒\",\"exampleJson\":{\"title\":\"您有一个新的待发货订单\",\"items\":[{\"v\":\"3705121411\",\"k\":\"订单号\"},{\"v\":\"666\",\"k\":\"订单金额\"},{\"v\":\"小王子\",\"k\":\"买家\"},{\"v\":\"已支付\",\"k\":\"小王子\"},{\"v\":\"客户已付款，尽快发货吧\",\"k\":\"备注\"}]}},{\"messageType\":1,\"templateId\":\"\\t7spASYaTQElyXT7JhmyxZP7cUZLxGNtAMJp7DrrwG1o\",\"title\":\"订单取消通知\",\"type\":3,\"sendRule\":\"订单关闭后后(商家关闭、超时关闭、用户关闭)，立即提醒\",\"exampleJson\":{\"title\":\"尊敬的张先生您好！\",\"items\":[{\"v\":\"144752465421-5456452\",\"k\":\"订单编号\"},{\"v\":\"取消\",\"k\":\"订单类型\"},{\"v\":\"220元\",\"k\":\"订单金额\"},{\"v\":\"2014年7月21日 18:36\",\"k\":\"订单时间\"},{\"v\":\"15885024256\",\"k\":\"用户信息\"},{\"v\":\"此单已于2014年7月21日 08:36 被用户取消，如有疑问，请联系我们。\",\"k\":\"备注\"}]}},{\"messageType\":1,\"templateId\":\"\\t\\tw6wVo4gAxI4vXP7G-soHTfbku4BFYSebIu2sDHVgbPs\",\"title\":\"订单确认收货通知\",\"type\":3,\"sendRule\":\"订单客户确认收货后，立即提醒\",\"exampleJson\":{\"title\":\"亲：您在我们商城买的宝贝已经确认收货。\",\"items\":[{\"v\":\"323232323232\",\"k\":\"订单号\"},{\"v\":\"最新款男鞋\",\"k\":\"商品名称\"},{\"v\":\"2015 01 01 12:00\",\"k\":\"下单时间\"},{\"v\":\"2015 01 01 14:00\",\"k\":\"发货时间\"},{\"v\":\"2015 01 02 14:00\",\"k\":\"确认收货时间\"},{\"v\":\"感谢您的支持与厚爱。\",\"k\":\"备注\"}]}},{\"messageType\":1,\"templateId\":\"1tqWjs79uTml2bAmqtwcrnwrphftm5XEl9R8eSI3LME\",\"title\":\"团长申请通知\",\"type\":3,\"sendRule\":\"客户申请团长提交成功后，立即提醒\",\"exampleJson\":{\"title\":\"您好，您有新的团长申请\",\"items\":[{\"v\":\"张三\",\"k\":\"姓名\"},{\"v\":\"13973861234\",\"k\":\"联系电话\"},{\"v\":\"2017年12月29日\",\"k\":\"申请时间\"},{\"v\":\"请点击详情进入审核界面进行审核，祝您生活愉快！\",\"k\":\"备注\"}]}},{\"messageType\":1,\"templateId\":\"BKYQci1ZTTQVMY8S5ZnymH6Q8Q3x6KHMOF62Xw7zf_I\",\"title\":\"供应商申请通知\",\"type\":3,\"sendRule\":\"客户申请供应商提交成功后，立即提醒\",\"exampleJson\":{\"title\":\"您有一个申请\",\"items\":[{\"v\":\"市场文档资料\",\"k\":\"申请名称\"},{\"v\":\"小刘\",\"k\":\"申请人\"},{\"v\":\"编辑\",\"k\":\"申请类型\"},{\"v\":\"2016-01-16 16:16\",\"k\":\"申请时间\"},{\"v\":\"请及时审核\",\"k\":\"备注\"}]}},{\"messageType\":1,\"templateId\":\"avfJeyyPNbobVmZ0F10h3oMmX8nt_OLI6jqL0ufraBk\",\"title\":\"提现申请通知\",\"type\":3,\"sendRule\":\"团长、区域团长、合伙人、分销商、供应商、提货点申请提现后，立即提现\",\"exampleJson\":{\"title\":\"你好,提现申请已经收到\",\"items\":[{\"v\":\"张三\",\"k\":\"昵称\"},{\"v\":\"2016年5月13日\",\"k\":\"时间\"},{\"v\":\"10.00元\",\"k\":\"金额\"},{\"v\":\"微信\",\"k\":\"提现方式\"},{\"v\":\"感谢你的使用。\",\"k\":\"备注\"}]}},{\"messageType\":1,\"templateId\":\"2A8UABJHwJAezwskb66X6Y7W-EKhYT5AbHzMV-a0IhE\",\"title\":\"分销商申请通知\",\"type\":3,\"sendRule\":\"商家拒绝售后申请后，立即提醒\",\"exampleJson\":{\"title\":\"您好！您申请已处理完成。\",\"items\":[{\"v\":\"开户申请\",\"k\":\"申请业务\"},{\"v\":\"2017-4-14 10:10:10\",\"k\":\"申请时间\"},{\"v\":\"开户成功\",\"k\":\"处理结果\"},{\"v\":\"如果您需要开通燃气，请拨打热线电话0512-69111预约开通\",\"k\":\"备注\"}]}},{\"messageType\":1,\"templateId\":\"2A8UABJHwJAezwskb66X6Y7W-EKhYT5AbHzMV-a0IhE\",\"title\":\"合伙人申请通知\",\"type\":3,\"sendRule\":\"商家同意退款退货申请时，立即提醒\",\"exampleJson\":{\"title\":\"您好！您申请已处理完成。\",\"items\":[{\"v\":\"开户申请\",\"k\":\"申请业务\"},{\"v\":\"2017-4-14 10:10:10\",\"k\":\"申请时间\"},{\"v\":\"开户成功\",\"k\":\"处理结果\"},{\"v\":\"如果您需要开通燃气，请拨打热线电话0512-69111预约开通\",\"k\":\"备注\"}]}},{\"messageType\":1,\"templateId\":\"2A8UABJHwJAezwskb66X6Y7W-EKhYT5AbHzMV-a0IhE\",\"title\":\"新会员加入通知\",\"type\":3,\"sendRule\":\"商家拒绝售后申请后，立即提醒\",\"exampleJson\":{\"title\":\"您好！您申请已处理完成。\",\"items\":[{\"v\":\"开户申请\",\"k\":\"申请业务\"},{\"v\":\"2017-4-14 10:10:10\",\"k\":\"申请时间\"},{\"v\":\"开户成功\",\"k\":\"处理结果\"},{\"v\":\"如果您需要开通燃气，请拨打热线电话0512-69111预约开通\",\"k\":\"备注\"}]}}]}');
INSERT INTO `gruul_open`.`t_platform_default_value` (`id`, `unique_identification`, `version`, `create_time`, `is_deleted`, `update_time`, `build_action`, `url`, `mq`, `kv`) VALUES (19, 'ACCOUNT_DEFAULT', 'v0.1', '2021-08-27 17:09:12', 0, '2021-08-27 17:09:12', 1, '/', '{\"routeKey\":\"gruul.account.default\",\"exchange\":\"gruul.account.exchange\"}', '{}');
INSERT INTO `gruul_open`.`t_platform_default_value` (`id`, `unique_identification`, `version`, `create_time`, `is_deleted`, `update_time`, `build_action`, `url`, `mq`, `kv`) VALUES (20, 'PLATFROM_SHOP_MSG', 'v0.1', '2021-08-27 17:09:12', 0, '2021-08-27 17:09:12', 3, '/', '{}', '{\"useType\":\"1\",\"version\":\"v0.1\",\"items\":[{\"miniOpen\":0,\"messageType\":1,\"codeOpen\":0,\"codeMsg\":\"\",\"miniMsg\":{\"secondClass\":\"家电/数码/手机\",\"firstClass\":\"商家自营\",\"rules\":\"character_string,date,thing,character_string,name\",\"title\":\"订单发货提醒\",\"type\":2,\"kIds\":\"1,10,2,4,5\",\"sendRule\":\"商家后台点请上传审核小程序之后再开启击发货后，立即提醒\",\"tId\":467,\"categoryId\":311,\"firstId\":304,\"exampleJson\":{\"title\":\"订单发货提醒\",\"items\":[{\"v\":\"20191111232312\",\"k\":\"订单编号\"},{\"v\":\"2019年12月12日 14:02\",\"k\":\"发货时间\"},{\"v\":\"华为手机1件\",\"k\":\"商品详情\"},{\"v\":\"sf2312312122\",\"k\":\"快递单号\"},{\"v\":\"某某某\",\"k\":\"收货人\"}]}},\"mpMsg\":{\"templateId\":\"I7wGGmIF6zjLU5CCyM_g_jzy0RuVP58VsjNk7WN1k9I\",\"title\":\"订单待发货提醒\",\"type\":3,\"sendRule\":\"等待商家后台点发货的订单，立即提醒\",\"exampleJson\":{\"title\":\"您有一个新的待发货订单\",\"items\":[{\"v\":\"3705121411\",\"k\":\"订单号\"},{\"v\":\"666\",\"k\":\"订单金额\"},{\"v\":\"小王子\",\"k\":\"买家\"},{\"v\":\"已支付\",\"k\":\"小王子\"},{\"v\":\"客户已付款，尽快发货吧\",\"k\":\"备注\"}]}},\"title\":\"订单发货提醒\",\"mpOpen\":0,\"mark\":\"sendMsg\"},{\"miniOpen\":0,\"messageType\":3,\"mpTemplateId\":\"BKYQci1ZTTQVMY8S5ZnymH6Q8Q3x6KHMOF62Xw7zf_I\",\"codeOpen\":0,\"codeMsg\":\"\",\"miniMsg\":{\"secondClass\":\"企业管理\",\"firstClass\":\"工具\",\"rules\":\"thing,date,name\",\"title\":\"审核结果通知\",\"type\":2,\"kIds\":\"2,3,4\",\"sendRule\":\"客户申请合伙人通知商家同意后，立即提醒\",\"tId\":4599,\"categoryId\":620,\"firstId\":287,\"exampleJson\":{\"title\":\"审核结果通知\",\"items\":[{\"v\":\"您的申请已通过\",\"k\":\"审核结果\"},{\"v\":\"2017-1-10\",\"k\":\"审核时间\"},{\"v\":\"张三\",\"k\":\"申请人\"}]}},\"mpMsg\":{\"title\":\"供应商申请通知\",\"type\":3,\"sendRule\":\"客户申请供应商提交成功后，立即提醒\",\"exampleJson\":{\"title\":\"您有一个申请\",\"items\":[{\"v\":\"市场文档资料\",\"k\":\"申请名称\"},{\"v\":\"小刘\",\"k\":\"申请人\"},{\"v\":\"编辑\",\"k\":\"申请类型\"},{\"v\":\"2016-01-16 16:16\",\"k\":\"申请时间\"},{\"v\":\"请及时审核\",\"k\":\"备注\"}]}},\"title\":\"审核结果通知\",\"mpOpen\":0,\"mark\":\"auditMsg\"},{\"miniOpen\":0,\"messageType\":1,\"mpTemplateId\":\"\",\"codeOpen\":0,\"codeMsg\":\"\",\"miniMsg\":{\"secondClass\":\"家电/数码/手机\",\"firstClass\":\"工具\",\"rules\":\"character_string,thing,thing,thing\",\"title\":\"会员开通通知\",\"type\":2,\"kIds\":\"1,2,4,5\",\"sendRule\":\"用户开通会员卡时通知开卡成功\",\"tId\":8883,\"categoryId\":311,\"firstId\":304,\"exampleJson\":{\"title\":\"会员开通通知\",\"items\":[{\"v\":\"11231132\",\"k\":\"会员编号\"},{\"v\":\"张三\",\"k\":\"会员名称\"},{\"v\":\"永久\",\"k\":\"会员期限\"},{\"v\":\"会员权益\",\"k\":\"备注\"}]}},\"title\":\"会员开通通知\",\"mpOpen\":0,\"mark\":\"memberCartOpen\"},{\"miniOpen\":0,\"messageType\":1,\"mpTemplateId\":\"\",\"codeOpen\":0,\"codeMsg\":\"\",\"miniMsg\":{\"secondClass\":\"海淘\",\"firstClass\":\"商家自营\",\"rules\":\"number,thing,phrase,amount,thing\",\"title\":\"订单退款通知\",\"type\":2,\"kIds\":\"1,6,5,2,10\",\"sendRule\":\"订单退款通知\",\"tId\":2491,\"categoryId\":784,\"firstId\":304,\"exampleJson\":{\"title\":\"订单退款通知\",\"items\":[{\"v\":\"2019111900000001\",\"k\":\"订单号\"},{\"v\":\"这是个商品名称\",\"k\":\"商品名称\"},{\"v\":\"退款失败\",\"k\":\"退款状态\"},{\"v\":\"￥100\",\"k\":\"退款金额\"},{\"v\":\"100积分\",\"k\":\"退回积分\"}]}},\"title\":\"退款通知\",\"mpOpen\":0,\"mark\":\"orderRefund\"},{\"miniOpen\":0,\"messageType\":1,\"mpTemplateId\":\"\",\"codeOpen\":0,\"codeMsg\":\"\",\"miniMsg\":{\"secondClass\":\"海淘\",\"firstClass\":\"商家自营\",\"rules\":\"character_string,thing,amount,thing,thing\",\"title\":\"提醒买家退货通知\",\"type\":2,\"kIds\":\"1,2,3,5,4\",\"sendRule\":\"订单退款通知\",\"tId\":4932,\"categoryId\":784,\"firstId\":304,\"exampleJson\":{\"title\":\"填写退货信息提醒\",\"items\":[{\"v\":\"1234567890\",\"k\":\"订单编号\"},{\"v\":\"iPhone\",\"k\":\"商品名称\"},{\"v\":\"100积分+￥2\",\"k\":\"退款金额\"},{\"v\":\"100积分\",\"k\":\"退回积分\"},{\"v\":\"商家已同意退款申请，请及时退货！\",\"k\":\"温馨提示\"}]}},\"title\":\"提醒买家退货通知\",\"mpOpen\":0,\"mark\":\"orderRefundNotify\"}]}');
INSERT INTO `gruul_open`.`t_platform_default_value` (`id`, `unique_identification`, `version`, `create_time`, `is_deleted`, `update_time`, `build_action`, `url`, `mq`, `kv`) VALUES (22, 'ORDER_CENTRE_DEFAULT', 'v0.1', '2021-08-27 17:09:12', 0, '2021-08-27 17:09:12', 1, NULL, '{\"routeKey\":\"gruul.order.data.init\",\"exchange\":\"gruul.account.exchange\"}', '{}');

SET FOREIGN_KEY_CHECKS = 1;
