/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : localhost:3306
 Source Schema         : basemall

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 02/12/2020 15:25:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_activity_coupon
-- ----------------------------
DROP TABLE IF EXISTS `t_activity_coupon`;
CREATE TABLE `t_activity_coupon`  (
  `activity_coupon_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `activity_coupon_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动名称',
  `activity_remark` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动备注',
  `activity_model` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动模板',
  `activity_img` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动海报',
  `overlay_state` tinyint(1) NULL DEFAULT NULL COMMENT '是否叠加（0不叠加，1叠加）',
  `turn_state` tinyint(1) NULL DEFAULT NULL COMMENT '开启状态(1---true开启，0---false关闭',
  `now_state` tinyint(1) NULL DEFAULT NULL COMMENT '活动当前状态(0未开始，1，进行中，2已结束)',
  `full_state` tinyint(1) NULL DEFAULT NULL COMMENT '是否适用与所有商品的状态（0指定商品，1所有商品）',
  `create_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `upate_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新时间',
  `start_date` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动开始时间',
  `end_date` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动结束时间',
  `delete_state` tinyint(1) NULL DEFAULT NULL COMMENT '删除标志',
  `appmodel_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户模板ID',
  PRIMARY KEY (`activity_coupon_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '优惠活动' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_activity_group
-- ----------------------------
DROP TABLE IF EXISTS `t_activity_group`;
CREATE TABLE `t_activity_group`  (
  `activity_group_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `activity_group_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动名称',
  `activity_remark` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动备注',
  `activity_img` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动海报',
  `limit_num` int(8) NULL DEFAULT NULL COMMENT '限制人数',
  `limit_time` int(8) NULL DEFAULT NULL COMMENT '限制时间',
  `overlay_state` tinyint(1) NULL DEFAULT NULL COMMENT '是否叠加（0不叠加，1叠加）',
  `turn_state` tinyint(1) NULL DEFAULT NULL COMMENT '开启状态(1---true开启，0---false关闭)',
  `now_state` tinyint(1) NULL DEFAULT NULL COMMENT '活动当前状态(0未开始，1，进行中，2已结束)',
  `create_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新时间',
  `start_date` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动开始时间',
  `end_date` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动结束时间',
  `delete_state` tinyint(1) NULL DEFAULT NULL COMMENT '删除标志',
  `appmodel_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户模板ID',
  PRIMARY KEY (`activity_group_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '团购活动' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_activity_product
-- ----------------------------
DROP TABLE IF EXISTS `t_activity_product`;
CREATE TABLE `t_activity_product`  (
  `activity_product_id` bigint(20) NOT NULL COMMENT '编号',
  `product_id` bigint(20) NULL DEFAULT NULL COMMENT '商品ID',
  `activity_id` int(11) NULL DEFAULT NULL COMMENT '活动ID',
  `activity_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '活动价',
  `activity_discount` decimal(3, 2) NULL DEFAULT NULL COMMENT '活动折扣',
  `activity_stock` int(8) NULL DEFAULT NULL COMMENT '活动库存',
  `sold_quantity` int(8) NOT NULL DEFAULT 0 COMMENT '已售数量',
  `max_quantity` int(8) NULL DEFAULT NULL COMMENT '限购',
  `activity_type` int(8) NULL DEFAULT NULL COMMENT '活动类型（优惠券活动1001，特价活动2001，拼团3001）',
  `appmodel_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户模板ID',
  PRIMARY KEY (`activity_product_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '活动商品关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_activity_product_stock
-- ----------------------------
DROP TABLE IF EXISTS `t_activity_product_stock`;
CREATE TABLE `t_activity_product_stock`  (
  `activity_product_stock_id` int(11) NOT NULL AUTO_INCREMENT,
  `activity_product_id` bigint(20) NULL DEFAULT NULL,
  `product_spec_item_id` bigint(20) NULL DEFAULT NULL,
  `activity_stock` int(8) NULL DEFAULT NULL,
  PRIMARY KEY (`activity_product_stock_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_activity_seckill
-- ----------------------------
DROP TABLE IF EXISTS `t_activity_seckill`;
CREATE TABLE `t_activity_seckill`  (
  `activity_seckill_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `activity_seckill_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动名称',
  `activity_remark` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动备注',
  `activity_img` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动海报',
  `limit_time` int(8) NULL DEFAULT NULL COMMENT '限制时间',
  `overlay_state` tinyint(1) NULL DEFAULT NULL COMMENT '是否叠加（0不叠加，1叠加）',
  `turn_state` tinyint(1) NULL DEFAULT NULL COMMENT '开启状态(1---true开启，0---false关闭)',
  `now_state` tinyint(1) NULL DEFAULT NULL COMMENT '活动当前状态(0未开始，1，进行中，2已结束)',
  `create_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新时间',
  `start_date` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动开始时间',
  `end_date` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动结束时间',
  `delete_state` tinyint(1) NULL DEFAULT NULL COMMENT '删除标志',
  `appmodel_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户模板ID',
  PRIMARY KEY (`activity_seckill_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '秒杀活动' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_activity_special
-- ----------------------------
DROP TABLE IF EXISTS `t_activity_special`;
CREATE TABLE `t_activity_special`  (
  `activity_special_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `activity_special_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动名称',
  `activity_remark` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动备注',
  `activity_img` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动海报',
  `overlay_state` tinyint(1) NULL DEFAULT NULL COMMENT '是否叠加（0不叠加，1叠加）',
  `turn_state` tinyint(1) NULL DEFAULT NULL COMMENT '开启状态(1---true开启，0---false关闭)',
  `now_state` int(1) NULL DEFAULT NULL COMMENT '活动当前状态',
  `create_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后操作时间',
  `start_date` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动开始时间',
  `end_date` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动结束时间',
  `delete_state` tinyint(1) NULL DEFAULT NULL COMMENT '删除标志',
  `appmodel_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户模板ID',
  PRIMARY KEY (`activity_special_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '特价活动' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_agent
-- ----------------------------
DROP TABLE IF EXISTS `t_agent`;
CREATE TABLE `t_agent`  (
  `agent_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '代理人id',
  `wxuser_id` bigint(20) NOT NULL COMMENT '用户id',
  `agent_name` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '代理人姓名',
  `agent_phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '代理人手机号',
  `agent_grade` tinyint(1) NOT NULL COMMENT '代理等级',
  `agent_domain` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所在区域',
  `audit_date` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审核通过时间',
  `agent_status` tinyint(1) NULL DEFAULT NULL COMMENT '代理人状态   1通过审核，2取消分销商资格(禁用)',
  `appmodel_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '模板id',
  PRIMARY KEY (`agent_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '代理表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_agent_commission_rule
-- ----------------------------
DROP TABLE IF EXISTS `t_agent_commission_rule`;
CREATE TABLE `t_agent_commission_rule`  (
  `commission_rule_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '规则表id',
  `commission_scale` decimal(10, 2) NULL DEFAULT NULL COMMENT '佣金比例',
  `create_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新时间',
  `agent_rank` tinyint(1) NULL DEFAULT NULL COMMENT '代理等级',
  `appmodel_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模板id',
  `standard_amount` int(11) NULL DEFAULT NULL COMMENT '达标金额',
  `explain` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分销等级说明',
  PRIMARY KEY (`commission_rule_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '佣金规则表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_agent_commission_withdraw
-- ----------------------------
DROP TABLE IF EXISTS `t_agent_commission_withdraw`;
CREATE TABLE `t_agent_commission_withdraw`  (
  `commission_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '提现表id',
  `agent_id` bigint(20) NULL DEFAULT NULL COMMENT '代理人id',
  `withdraw_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '自购金额',
  `withdraw_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '提现时间',
  `bank_card` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '银行卡号',
  `type` tinyint(1) NULL DEFAULT NULL COMMENT '提款类别  1.微信   2.银行卡',
  `state` tinyint(1) NULL DEFAULT NULL COMMENT '提款状态  1.待审核 2.已提款',
  `appmodel_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模板id',
  PRIMARY KEY (`commission_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '提现表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_agent_product
-- ----------------------------
DROP TABLE IF EXISTS `t_agent_product`;
CREATE TABLE `t_agent_product`  (
  `agent_product_id` bigint(20) NOT NULL COMMENT '编号',
  `product_id` bigint(20) NOT NULL COMMENT '商品ID',
  `agent_stock` int(8) NULL DEFAULT NULL COMMENT '分销商品库存',
  `agent_sales_volume` int(8) NULL DEFAULT NULL COMMENT '分销商品销量',
  `agent_shelf_state` int(1) NULL DEFAULT NULL COMMENT '分销商品上下架状态（默认上架，0------上架，1---------下架（仓库中），2---------已售完）',
  `price` double(10, 2) NOT NULL COMMENT '原价',
  `common_price` double(10, 2) NOT NULL COMMENT '普通代理价格',
  `goldMedal_price` double(10, 2) NOT NULL COMMENT '金牌代理价格',
  `delete_state` int(1) NULL DEFAULT NULL COMMENT '删除标志',
  `appmodel_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板id',
  PRIMARY KEY (`agent_product_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 't_product_agent' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_agent_purchase
-- ----------------------------
DROP TABLE IF EXISTS `t_agent_purchase`;
CREATE TABLE `t_agent_purchase`  (
  `purchase_id` bigint(20) NOT NULL COMMENT '采购表ID',
  `product_id` bigint(20) NOT NULL COMMENT '商品ID',
  `quantity` int(8) NOT NULL COMMENT '商品数量',
  `product_spec_item_id` bigint(20) NULL DEFAULT NULL COMMENT '商品-规格详情编号',
  `wxuser_id` bigint(20) NOT NULL COMMENT '用户ID',
  `create_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '添加时间',
  `delete_state` tinyint(1) NULL DEFAULT NULL COMMENT '删除标志',
  `appmodel_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '模板ID',
  PRIMARY KEY (`purchase_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '采购表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_agent_purchase_order
-- ----------------------------
DROP TABLE IF EXISTS `t_agent_purchase_order`;
CREATE TABLE `t_agent_purchase_order`  (
  `purchase_order_id` bigint(20) NOT NULL COMMENT '采购订单表id',
  `agent_product_id` bigint(20) NULL DEFAULT NULL COMMENT '代理商品id',
  `out_trade_no_ext` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '再次支付订单号',
  `out_trade_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单号',
  `create_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `pay_fee` decimal(10, 2) NOT NULL COMMENT '支付价',
  `total_fee` decimal(10, 2) NULL DEFAULT NULL COMMENT '总价',
  `wxuser_id` bigint(20) NULL DEFAULT NULL COMMENT '用户ID',
  `pay_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付时间',
  `send_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发货时间',
  `record_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货时间',
  `refound_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '退款时间',
  `remark` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `address` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收货地址',
  `wl_num` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '物流单号',
  `wl_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '物流公司代码',
  `wl_price` bigint(10) NULL DEFAULT NULL COMMENT '邮费',
  `distri_mode` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '配送方式',
  `back_remark` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商家备注',
  `close_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单关闭时间',
  `delete_state` tinyint(1) NULL DEFAULT NULL COMMENT '删除标志',
  `appmodel_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '模板ID',
  `wl_name` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '物流公司名称',
  `update_time` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新时间',
  `delivery_staff` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '配送信息',
  PRIMARY KEY (`purchase_order_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '采购订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_agent_purchase_order_detailed
-- ----------------------------
DROP TABLE IF EXISTS `t_agent_purchase_order_detailed`;
CREATE TABLE `t_agent_purchase_order_detailed`  (
  `purchase_order_detailed_id` bigint(20) NOT NULL COMMENT '采购订单表id',
  `product_id` bigint(20) NULL DEFAULT NULL COMMENT '商品ID',
  `product_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '商品价格',
  `product_img` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品主图',
  `quantity` int(8) NULL DEFAULT NULL COMMENT '商品数量',
  `product_spec_info` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品-规格详情编号',
  `activity_info` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动详情编号',
  `out_trade_no` bigint(32) NULL DEFAULT NULL COMMENT '订单ID',
  `create_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '添加时间',
  `appmodel_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '模板ID',
  `delete_state` tinyint(1) NULL DEFAULT NULL COMMENT '删除标志',
  PRIMARY KEY (`purchase_order_detailed_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '采购订单详情表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_agent_register
-- ----------------------------
DROP TABLE IF EXISTS `t_agent_register`;
CREATE TABLE `t_agent_register`  (
  `reg_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '申请表id',
  `wxuser_id` bigint(20) NOT NULL COMMENT '用户id',
  `reg_name` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '申请人名称',
  `reg_phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '申请人手机',
  `reg_code` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '绑定码',
  `binding` int(1) NULL DEFAULT NULL COMMENT '是否已绑定',
  `type` tinyint(1) NULL DEFAULT NULL COMMENT '申请类别   1-小程序端申请,2-后端注册时需要发送绑定码',
  `agentGrade` int(3) NULL DEFAULT NULL COMMENT '申请等级',
  `reg_auditor` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审核人',
  `reg_state` tinyint(1) NULL DEFAULT NULL COMMENT '审核状态  1-通过   0-不通过',
  `reg_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审核时间',
  `reg_remark` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `refuse_remark` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '拒绝原因',
  `appmodel_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '模板id',
  `agent_domain` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '所在区域',
  PRIMARY KEY (`reg_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '代理申请表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_agent_withdraw_rule
-- ----------------------------
DROP TABLE IF EXISTS `t_agent_withdraw_rule`;
CREATE TABLE `t_agent_withdraw_rule`  (
  `withdraw_rule_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '规则表id',
  `price_limit` decimal(10, 2) NULL DEFAULT NULL COMMENT '金额限制',
  `sum_limit` int(11) NULL DEFAULT NULL COMMENT '次数限制',
  `create_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `appmodel_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '模板id',
  PRIMARY KEY (`withdraw_rule_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '提现规则表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_buycar
-- ----------------------------
DROP TABLE IF EXISTS `t_buycar`;
CREATE TABLE `t_buycar`  (
  `buycar_id` bigint(20) NOT NULL COMMENT '购物车ID',
  `product_id` bigint(20) NULL DEFAULT NULL COMMENT '商品ID',
  `quantity` int(8) NULL DEFAULT NULL COMMENT '商品数量',
  `wxuser_id` bigint(20) NULL DEFAULT NULL COMMENT '用户ID',
  `product_spec_item_id` bigint(20) NULL DEFAULT NULL COMMENT '商品-规格详情编号',
  `create_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '添加时间',
  `appmodel_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模板ID',
  `delete_state` tinyint(1) NULL DEFAULT NULL COMMENT '删除状态',
  PRIMARY KEY (`buycar_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '购物车信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_category
-- ----------------------------
DROP TABLE IF EXISTS `t_category`;
CREATE TABLE `t_category`  (
  `category_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '分类id',
  `category_name` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类名',
  `category_type` int(1) NULL DEFAULT 1 COMMENT '(1-一级分类 2-二级分类)',
  `father_id` bigint(20) NULL DEFAULT -1 COMMENT 'father_id 分类父ID',
  `create_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `category_icon` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类图标',
  `category_img` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类主图',
  `sort_index` int(8) NULL DEFAULT NULL COMMENT '排序值',
  `appmodel_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板ID',
  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品分类有二级分类，一个一级下有多个二级分类，一个商品有多个一级分类，多个二级分类' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_column_flag
-- ----------------------------
DROP TABLE IF EXISTS `t_column_flag`;
CREATE TABLE `t_column_flag`  (
  `column_flag_id` int(11) NOT NULL AUTO_INCREMENT,
  `appmodel_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板Id',
  `serarch_flag` bit(1) NULL DEFAULT b'1' COMMENT '搜索栏开关',
  `notice_flag` bit(1) NULL DEFAULT b'1' COMMENT '店铺公告开关',
  `shopInfo_flag` bit(1) NULL DEFAULT b'1' COMMENT '店铺故事开关',
  `classify_flag` bit(1) NULL DEFAULT b'1' COMMENT '分类页开关',
  `article_flag` bit(1) NULL DEFAULT b'1' COMMENT '发现页开关',
  `member_flag` bit(1) NULL DEFAULT b'1' COMMENT '会员功能开关',
  `shop_flag` bit(1) NULL DEFAULT b'1' COMMENT '积分商城开关',
  `proxy_flag` bit(1) NULL DEFAULT b'1' COMMENT '代理功能开关',
  `foot_flag` bit(1) NULL DEFAULT b'1' COMMENT '底部打标开关',
  PRIMARY KEY (`column_flag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '栏目开关' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_coupon
-- ----------------------------
DROP TABLE IF EXISTS `t_coupon`;
CREATE TABLE `t_coupon`  (
  `coupon_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `activity_coupon_id` int(11) NULL DEFAULT NULL COMMENT '优惠活动ID',
  `stock_quantity` int(8) NULL DEFAULT NULL COMMENT '优惠券数量',
  `limit_quantity` int(8) NULL DEFAULT NULL COMMENT '单人限领量',
  `min_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '优惠值',
  `max_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '满减值',
  `discount` decimal(6, 2) NULL DEFAULT NULL COMMENT '折扣值',
  `coupon_type` int(2) NULL DEFAULT NULL COMMENT '优惠券类型（1---满减，2---满折，3---无门槛现金券，4---无门槛折扣）',
  `source_type` int(2) NULL DEFAULT NULL COMMENT '优惠券来源类型（1---优惠活动创建，2---积分领取）',
  `obtain_quantity` int(8) NULL DEFAULT NULL COMMENT '领取量',
  `used_quantity` int(8) NULL DEFAULT NULL COMMENT '使用量',
  `appmodel_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户模板ID',
  `delete_state` tinyint(1) NULL DEFAULT NULL COMMENT '删除标志',
  `convert_price` int(11) NULL DEFAULT NULL COMMENT '兑换所需积分',
  `live_time` tinyint(2) NULL DEFAULT NULL COMMENT '使用期限(0-无时间限制,1-有效天数,2-有效日期)',
  `day` int(11) NULL DEFAULT NULL COMMENT '有效天数',
  `statr_time` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开始日期',
  `end_time` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结束日期',
  PRIMARY KEY (`coupon_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 63 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '优惠卷' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_coupon_wxuser
-- ----------------------------
DROP TABLE IF EXISTS `t_coupon_wxuser`;
CREATE TABLE `t_coupon_wxuser`  (
  `wxuser_coupon_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `wxuser_id` bigint(11) NULL DEFAULT NULL COMMENT '用户ID',
  `coupon_id` int(11) NULL DEFAULT NULL COMMENT '优惠券ID',
  `flag` tinyint(1) NULL DEFAULT NULL COMMENT '使用状态',
  `create_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '领取优惠券时间',
  `use_time` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '使用优惠券时间',
  `appmodel_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模板ID',
  `activity_coupon_id` int(11) NULL DEFAULT NULL COMMENT '优惠活动ID',
  PRIMARY KEY (`wxuser_coupon_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户优惠券' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_firstpage_classify
-- ----------------------------
DROP TABLE IF EXISTS `t_firstpage_classify`;
CREATE TABLE `t_firstpage_classify`  (
  `classify_id` int(11) NOT NULL AUTO_INCREMENT,
  `classify_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类名称',
  `classify_img` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类图片',
  `link_type` int(10) NULL DEFAULT NULL COMMENT '链接类型',
  `product_id` int(10) NULL DEFAULT NULL COMMENT '商品Id',
  `notice_id` int(10) NULL DEFAULT NULL COMMENT '公告Id',
  `article_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文章Id',
  `appmodel_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板Id',
  `sort` int(10) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`classify_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '首页分类' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_footer
-- ----------------------------
DROP TABLE IF EXISTS `t_footer`;
CREATE TABLE `t_footer`  (
  `footer_id` int(11) NOT NULL AUTO_INCREMENT,
  `appmodel_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板Id',
  `footer_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '导航名称',
  `footer_img_no` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '未选中图片',
  `footer_img_yes` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '选中图片',
  `footer_link` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '链接',
  `footer_flag` bit(1) NULL DEFAULT b'1' COMMENT '开启关闭状态',
  PRIMARY KEY (`footer_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '底部导航' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_group
-- ----------------------------
DROP TABLE IF EXISTS `t_group`;
CREATE TABLE `t_group`  (
  `group_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `wxuser_id` bigint(20) NULL DEFAULT NULL COMMENT '用户ID',
  `activity_group_id` int(11) NULL DEFAULT NULL COMMENT '开团用户ID',
  `create_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '开团时间',
  `end_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '拼团结束时间',
  `group_state` tinyint(1) NULL DEFAULT NULL COMMENT '成团状态',
  `product_id` bigint(20) NULL DEFAULT NULL COMMENT '产品ID',
  `limit_num` int(8) NULL DEFAULT NULL COMMENT '限购人数',
  `delete_state` tinyint(1) NULL DEFAULT NULL COMMENT '删除状态',
  `appmodel_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户模板ID',
  PRIMARY KEY (`group_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '一个团' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_group_member
-- ----------------------------
DROP TABLE IF EXISTS `t_group_member`;
CREATE TABLE `t_group_member`  (
  `group_member_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `wxuser_id` bigint(11) NULL DEFAULT NULL COMMENT '用户ID',
  `group_id` int(11) NULL DEFAULT NULL COMMENT '团ID',
  `order_id` bigint(20) NULL DEFAULT NULL COMMENT '订单ID',
  `create_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参团时间',
  `member_type` tinyint(1) NULL DEFAULT NULL COMMENT '参团人类型',
  `nick_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `avatar_url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `appmodel_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模板ID',
  `delete_state` tinyint(1) NULL DEFAULT NULL COMMENT '删除标志',
  PRIMARY KEY (`group_member_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '团成员' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_logistic_cancel
-- ----------------------------
DROP TABLE IF EXISTS `t_logistic_cancel`;
CREATE TABLE `t_logistic_cancel`  (
  `logistic_cancel_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '退货地址ID',
  `user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系人',
  `phone` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `post_code` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮编',
  `location_json` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `default_state` tinyint(1) NULL DEFAULT NULL COMMENT '是否默认',
  `appmodel_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板ID',
  PRIMARY KEY (`logistic_cancel_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '退货地址表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_logistic_charge
-- ----------------------------
DROP TABLE IF EXISTS `t_logistic_charge`;
CREATE TABLE `t_logistic_charge`  (
  `logistic_charge_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '物流价格ID',
  `logistic_model_id` int(11) NULL DEFAULT NULL COMMENT '物流模板Id',
  `charge_address` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '物流地区',
  `first_weight` int(16) NULL DEFAULT NULL COMMENT '物流首重',
  `first_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '首重价格',
  `next_weight` int(16) NULL DEFAULT NULL COMMENT '物流续重',
  `next_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '续重价格',
  `default_state` tinyint(1) NULL DEFAULT NULL COMMENT '默认状态',
  `appmodel_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模板ID',
  PRIMARY KEY (`logistic_charge_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '物流计价' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_logistic_distrobution
-- ----------------------------
DROP TABLE IF EXISTS `t_logistic_distrobution`;
CREATE TABLE `t_logistic_distrobution`  (
  `distrobution_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商家配送ID',
  `shop_address` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商家地址',
  `min_pay_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '起送价（顾客支付最低xx元才配送）',
  `delivery_range` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '配送范围',
  `seller_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '配送费用',
  `turn_state` tinyint(1) NULL DEFAULT NULL COMMENT '开启关闭',
  `appmodel_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户模板Id',
  `delivery_staffs` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '配送员',
  `create_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`distrobution_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商家配送' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_logistic_free
-- ----------------------------
DROP TABLE IF EXISTS `t_logistic_free`;
CREATE TABLE `t_logistic_free`  (
  `free_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '包邮ID',
  `free_address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '包邮地区',
  `unit_type` int(1) NULL DEFAULT NULL COMMENT '包邮类型（0件数,1金额,2件数+金额）',
  `max_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '包邮条件-价钱（满100包邮）',
  `logistic_model_id` int(11) NULL DEFAULT NULL COMMENT '物流模板ID',
  `condition_ship` int(8) NULL DEFAULT NULL COMMENT '包邮条件-数量（1件）',
  `appmodel_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户模板ID',
  PRIMARY KEY (`free_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '物流包邮' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_logistic_model
-- ----------------------------
DROP TABLE IF EXISTS `t_logistic_model`;
CREATE TABLE `t_logistic_model`  (
  `logistic_model_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '物流配模板送ID',
  `logistic_model_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '配送模板名称',
  `valuation_type` int(1) NULL DEFAULT NULL COMMENT '计价方式（0-件 1-kg 2-m³）',
  `turn_state` tinyint(1) NULL DEFAULT NULL COMMENT '模板开启关闭的状态',
  `create_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `appmodel_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户模板Id',
  `free_state` tinyint(1) NULL DEFAULT NULL COMMENT '是否指定包邮',
  PRIMARY KEY (`logistic_model_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '物流模板' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_manager
-- ----------------------------
DROP TABLE IF EXISTS `t_manager`;
CREATE TABLE `t_manager`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `Username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `PassWord` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `Appmodel_ID` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户模板ID',
  `IsDelete` int(1) NULL DEFAULT 0 COMMENT '删除标志',
  `Flag` int(1) NULL DEFAULT 0 COMMENT '用户状态',
  `Createtime` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户时间',
  `LastTime` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '上次登录时间',
  `AppID` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `Secret` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `Mch_ID` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `MchKey` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '后台管理员' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_member
-- ----------------------------
DROP TABLE IF EXISTS `t_member`;
CREATE TABLE `t_member`  (
  `member_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '会员id',
  `membership_number` varchar(28) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '会员卡编号',
  `code_type` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '会员卡二维码',
  `wxuser_id` bigint(20) NOT NULL COMMENT '用户id',
  `supply_bonus` double(10, 2) NULL DEFAULT 0.00 COMMENT '会员卡余额',
  `growth_value` int(11) NULL DEFAULT 0 COMMENT '成长值',
  `integral` int(11) NULL DEFAULT 0 COMMENT '积分值',
  `integral_total` int(11) NULL DEFAULT 0 COMMENT '总积分值',
  `phone` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `create_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `upgrade_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后升级时间',
  `state` int(1) NOT NULL DEFAULT 0 COMMENT '注册状态0未注册,1已注册',
  `rank_id` int(11) NULL DEFAULT NULL COMMENT '等级表Id',
  `member_remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `appmodel_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '模板id',
  PRIMARY KEY (`member_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1532054646815018 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '会员表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_member_active_recharge
-- ----------------------------
DROP TABLE IF EXISTS `t_member_active_recharge`;
CREATE TABLE `t_member_active_recharge`  (
  `active_recharge_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '活动id',
  `active_name` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动名称',
  `start_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '截止时间',
  `state` int(1) NULL DEFAULT NULL COMMENT '活动状态  1-进行中 2.已结束',
  `active_rule` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动规则',
  `create_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `appmodel_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板id',
  `delete_state` int(11) NULL DEFAULT NULL COMMENT '删除标志',
  PRIMARY KEY (`active_recharge_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '会员充值活动表\r\n1.活动规则存储json字符串规则必须包含一个\r\n2.多个活动之间时间不能产生交集' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_member_growing_rule
-- ----------------------------
DROP TABLE IF EXISTS `t_member_growing_rule`;
CREATE TABLE `t_member_growing_rule`  (
  `growing_rule_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '表id',
  `genre` int(2) NULL DEFAULT NULL COMMENT '规则类型 1.积分规则,2成长值规则',
  `type` int(2) NULL DEFAULT NULL COMMENT '类型 1.分享2.登录,3购买商品',
  `swap_good` int(11) NULL DEFAULT NULL COMMENT '相应条件 指定相应条件   例如:genre=2 type=3 swapGood=100 swap_integral = 5 购买商品金额满100送5成长值',
  `swap_integral` int(11) NULL DEFAULT NULL COMMENT '可换取积分   对应可换取的积分',
  `state` int(11) NULL DEFAULT NULL COMMENT '状态   true/false',
  `info` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '等级/积分说明',
  `indate` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '等级/积分有效期',
  `create_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `appmodel_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板id',
  PRIMARY KEY (`growing_rule_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '会员卡规则表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_member_gruop_category
-- ----------------------------
DROP TABLE IF EXISTS `t_member_gruop_category`;
CREATE TABLE `t_member_gruop_category`  (
  `group_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '组别id',
  `group_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '组名',
  `create_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `appmodel_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '模板id',
  PRIMARY KEY (`group_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '会员分组表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_member_rank
-- ----------------------------
DROP TABLE IF EXISTS `t_member_rank`;
CREATE TABLE `t_member_rank`  (
  `rank_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '等级表id',
  `rank_name` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '等级名称',
  `discount` double(10, 1) NULL DEFAULT NULL COMMENT '折扣',
  `back_ground_pic_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '会员卡图片',
  `title` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '会员卡标题',
  `deduct_growth` int(11) NULL DEFAULT NULL COMMENT '到期后减扣得成长值',
  `create_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `delete_state` int(1) NULL DEFAULT NULL COMMENT '删除状态 0-可用 1-不可删除',
  `appmodel_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模板id',
  `growth_value` int(11) NULL DEFAULT NULL COMMENT '达标成长值',
  PRIMARY KEY (`rank_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '会员等级表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_member_recharge_record
-- ----------------------------
DROP TABLE IF EXISTS `t_member_recharge_record`;
CREATE TABLE `t_member_recharge_record`  (
  `recharge_record_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '表id',
  `order_number` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单号',
  `type` int(1) NULL DEFAULT NULL COMMENT '充值方式  1.微信支付,2银行卡充值',
  `member_id` bigint(20) NULL DEFAULT NULL COMMENT '会员id',
  `price` double(10, 2) NULL DEFAULT NULL COMMENT '充值金额',
  `active_recharge_id` int(11) NULL DEFAULT NULL COMMENT '充值时间',
  `create_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '充值活动id',
  `appmodel_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板id',
  PRIMARY KEY (`recharge_record_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '会员充值记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_notice
-- ----------------------------
DROP TABLE IF EXISTS `t_notice`;
CREATE TABLE `t_notice`  (
  `notice_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
  `notice_img` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '顶部图片',
  `create_time` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发布公告时间',
  `notice_body` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '公告内容',
  `target_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品ID',
  `jump_type` int(2) NULL DEFAULT NULL COMMENT '链接类型（0-链接公告内容，1-链接商品）',
  `delete_state` tinyint(1) NULL DEFAULT 0 COMMENT '删除状态',
  `appmodel_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模板ID',
  `target_url` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '跳转地址',
  PRIMARY KEY (`notice_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 46 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '公告信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order`  (
  `order_id` bigint(30) NOT NULL COMMENT '编号',
  `tel_phone` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `pay_flag` int(4) NOT NULL DEFAULT 0 COMMENT '支付状态  \r\n0.等待买家付款\r\n1.买家已付款 待发货状态\r\n2.卖家已发货  待收货状态\r\n3.交易成功\r\n4.订单关闭,\r\n5用户超时关闭订单\r\n6.商家关闭订单',
  `out_trade_no` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单号',
  `pay_fee` decimal(10, 2) NULL DEFAULT NULL COMMENT '支付价',
  `total_fee` decimal(10, 2) NULL DEFAULT NULL COMMENT '总价',
  `wxuser_id` bigint(30) NULL DEFAULT NULL COMMENT '用户ID',
  `create_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `pay_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支付时间',
  `send_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发货时间',
  `record_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货时间',
  `remark` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `wl_name` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '物流公司名称',
  `wl_num` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '物流单号',
  `wl_code` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '物流公司代码',
  `user_address` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货地址',
  `user_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货人',
  `wl_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '邮费',
  `out_trade_no_ext` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '再次支付订单号',
  `order_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单类型',
  `back_remark` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '商家备注',
  `delete_state` tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除标识   0正常  1逻辑删除',
  `appmodel_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '模板ID',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `distribute_mode` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '配送方式（商家配送，物流配送，到店自取）',
  `delivery_staff` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '配送信息',
  `group_state` int(1) NULL DEFAULT 0 COMMENT '成团状态',
  `group_member_id` int(11) NULL DEFAULT NULL COMMENT '团成员ID',
  `factPay_wxuser_id` bigint(20) NULL DEFAULT NULL COMMENT '实际支付人id',
  `pay_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付方式,微信支付,余额支付,好友代付',
  `member_discount` double(3, 2) NULL DEFAULT NULL COMMENT '会员折扣',
  `wxuser_coupon_id` int(11) NULL DEFAULT NULL COMMENT '用户使用的优惠券id',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_order_detail
-- ----------------------------
DROP TABLE IF EXISTS `t_order_detail`;
CREATE TABLE `t_order_detail`  (
  `order_detail_id` bigint(30) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `order_id` bigint(30) NULL DEFAULT NULL COMMENT '订单编号',
  `product_id` bigint(30) NULL DEFAULT NULL COMMENT '产品ID',
  `quantity` int(8) NULL DEFAULT NULL COMMENT '商品数量',
  `product_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '商品价格',
  `product_img` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品主图',
  `product_spec_info` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品规格',
  `create_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '添加时间',
  `appmodel_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模板ID',
  `delete_state` tinyint(1) NULL DEFAULT NULL COMMENT '删除标志',
  `activity_info` varchar(520) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动信息',
  PRIMARY KEY (`order_detail_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单详情' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_order_refound
-- ----------------------------
DROP TABLE IF EXISTS `t_order_refound`;
CREATE TABLE `t_order_refound`  (
  `order_refound_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '退款ID',
  `order_id` bigint(20) NULL DEFAULT NULL COMMENT '订单ID',
  `wxuser_id` bigint(20) NULL DEFAULT NULL COMMENT '用户ID',
  `refound_state` tinyint(1) NULL DEFAULT NULL COMMENT '退款状态',
  `reason` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '退款原因',
  `rimgs` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '退款凭证（图片）',
  `remark` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '退款备注',
  `refuse_reason` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拒绝原因',
  `refound_type` tinyint(1) NULL DEFAULT NULL COMMENT '退款类型  0--只退款，1--退货退款',
  `wlName` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '退款物流公司',
  `wlNum` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '物流单号',
  `shop_address` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '退款地址',
  `create_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请时间',
  `agree_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '同意/拒绝时间',
  `refound_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '退款时间',
  `appmodel_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板ID',
  `delete_state` tinyint(1) NULL DEFAULT NULL COMMENT '删除标志',
  PRIMARY KEY (`order_refound_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '退款信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_parameter
-- ----------------------------
DROP TABLE IF EXISTS `t_parameter`;
CREATE TABLE `t_parameter`  (
  `param_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `param_key` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数名称',
  `param_value` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数值',
  `param_class_id` int(11) NULL DEFAULT NULL COMMENT '参数分类ID',
  `appmodel_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '模板ID',
  PRIMARY KEY (`param_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '规格参数' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_parameter_class
-- ----------------------------
DROP TABLE IF EXISTS `t_parameter_class`;
CREATE TABLE `t_parameter_class`  (
  `param_class_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '总分类编号',
  `param_class_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类别名称',
  `create_time` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `is_delete` int(1) NOT NULL DEFAULT 0 COMMENT '删除标识',
  `appmodel_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '模板ID',
  PRIMARY KEY (`param_class_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '规格参数分类' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_plate
-- ----------------------------
DROP TABLE IF EXISTS `t_plate`;
CREATE TABLE `t_plate`  (
  `plate_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `plate_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '板块名称',
  `plate_img` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '板块图片',
  `create_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '板块简介',
  `appmodel_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板ID',
  `plate_flag` tinyint(1) NULL DEFAULT 0 COMMENT '板块状态',
  `product_num` int(8) NULL DEFAULT NULL COMMENT '产品数量',
  PRIMARY KEY (`plate_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '首页板块产品' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_plate_product
-- ----------------------------
DROP TABLE IF EXISTS `t_plate_product`;
CREATE TABLE `t_plate_product`  (
  `plate_product_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `plate_id` int(11) NULL DEFAULT NULL COMMENT '板块ID',
  `product_id` bigint(20) NULL DEFAULT NULL COMMENT '产品ID',
  `create_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `delete_state` tinyint(1) NULL DEFAULT 0 COMMENT '删除状态',
  `appmodel_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板ID',
  PRIMARY KEY (`plate_product_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '首页板块产品关联' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_poster
-- ----------------------------
DROP TABLE IF EXISTS `t_poster`;
CREATE TABLE `t_poster`  (
  `poster_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `position_type` int(1) NULL DEFAULT NULL COMMENT '海报位置类型（0为店铺管理-首页中心，1分类页装修）',
  `jump_type` int(2) NULL DEFAULT NULL COMMENT '跳转类型（0-跳转到商品，1-跳转到图文）',
  `poster_img` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '海报图片',
  `create_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `poster_body` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '海报内容',
  `target_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品ID',
  `appmodel_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模板ID',
  `target_url` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '跳转地址',
  PRIMARY KEY (`poster_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '轮播图' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_prize
-- ----------------------------
DROP TABLE IF EXISTS `t_prize`;
CREATE TABLE `t_prize`  (
  `prize_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '积分商品id',
  `prize_type` int(2) NULL DEFAULT NULL COMMENT '积分商品类型  奖品类型：1.优惠卷2.商品',
  `coupon_id` int(11) NULL DEFAULT NULL COMMENT '优惠券id',
  `prize_name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '积分商品名',
  `prize_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '积分商品主图片,如果是奖励实物的话，该字段存储实物图片地址，比如店铺宝贝主图src',
  `round_sowing_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '积分商品轮播图,存储json格式',
  `remark` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '描述',
  `send_place` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发货地址',
  `send_date` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发货日期',
  `delete_state` tinyint(1) NULL DEFAULT NULL COMMENT '删除标志',
  `create_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `update_state` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新时间',
  `appmodel_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '模板id',
  `prize_stock` int(10) NULL DEFAULT 0 COMMENT '积分商品库存',
  `text_img` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '图文详情',
  `price` double(10, 2) NULL DEFAULT NULL COMMENT '实际价格',
  `convert_price` int(11) NULL DEFAULT NULL COMMENT '兑换积分',
  `sales_volume` int(11) NULL DEFAULT NULL COMMENT '销量',
  `state` int(1) NULL DEFAULT NULL COMMENT '商品状态    商品状态    1.上架  2.仓库中 3.已售完',
  `delivery_fees` double(10, 2) NULL DEFAULT NULL COMMENT '统一邮费',
  `logistic_model_id` int(11) NULL DEFAULT NULL COMMENT '邮费模板',
  `property_model_id` int(11) NULL DEFAULT NULL COMMENT '属性模板',
  `property_mine` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '自定义属性',
  PRIMARY KEY (`prize_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '积分商品表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_prize_detail
-- ----------------------------
DROP TABLE IF EXISTS `t_prize_detail`;
CREATE TABLE `t_prize_detail`  (
  `integral_detail_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '明细表id',
  `type` int(11) NULL DEFAULT NULL COMMENT '使用类别  1. 兑换商品 2.购买商品',
  `quantity` int(11) NULL DEFAULT NULL COMMENT '购买商品数量',
  `create_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '添加时间',
  `integral` int(11) NULL DEFAULT NULL COMMENT '积分',
  `prize_id` int(11) NULL DEFAULT NULL COMMENT '积分商品id',
  `wxuser_id` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  `appmodel_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '模板id',
  `delete_state` bit(1) NULL DEFAULT NULL COMMENT '删除状态',
  PRIMARY KEY (`integral_detail_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '积分明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_prize_order
-- ----------------------------
DROP TABLE IF EXISTS `t_prize_order`;
CREATE TABLE `t_prize_order`  (
  `prize_order_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `change_num` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '兑换单号',
  `wxuser_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '用户id',
  `wxuser_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `prize_id` int(11) NOT NULL COMMENT '积分商品id',
  `pay_fee` double(10, 2) NULL DEFAULT NULL COMMENT '支付费用',
  `order_type` int(2) NULL DEFAULT NULL COMMENT '订单类型（1商品，2优惠券）',
  `price` double(10, 2) NULL DEFAULT NULL COMMENT '实际价格',
  `sum` int(11) NULL DEFAULT NULL COMMENT '数量',
  `expend_integral` int(10) NULL DEFAULT NULL COMMENT '付款积分',
  `name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货人',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货地址',
  `tel_phone` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `wl_num` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '物流单号',
  `wl_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '物流公司代码',
  `wl_price` double(10, 2) NULL DEFAULT NULL COMMENT '邮费',
  `distri_mode` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '配送方式   (商家配送，物流配送，到店自取）',
  `delivery_staff` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '配送信息',
  `remark` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `back_remark` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商家备注',
  `order_state` tinyint(1) NULL DEFAULT NULL COMMENT '订单状态 (0等待买家付款,1买家已付款    ,2卖家已发货,3交易成功,4退款中,5退款完成,6已关闭)',
  `pay_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支付时间',
  `send_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发货时间',
  `record_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货时间',
  `refound_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '退款时间',
  `delete_state` tinyint(1) NULL DEFAULT 1 COMMENT '删除状态  0-删除 ,1-正常',
  `create_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新时间',
  `appmodel_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '模板id',
  PRIMARY KEY (`prize_order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1532059924867331 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '积分订单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_prize_rule
-- ----------------------------
DROP TABLE IF EXISTS `t_prize_rule`;
CREATE TABLE `t_prize_rule`  (
  `prize_rule_id` int(11) NOT NULL AUTO_INCREMENT,
  `type_one` int(10) NULL DEFAULT NULL COMMENT '分享获取积分值',
  `type_two` int(10) NULL DEFAULT NULL COMMENT '登录获取积分值',
  `type_three_pay` int(10) NULL DEFAULT NULL COMMENT '购买满值',
  `type_three_get` int(10) NULL DEFAULT NULL COMMENT '购买满获取积分值',
  `indate` int(10) NULL DEFAULT NULL COMMENT '有效期',
  `info` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '说明',
  `appmodel_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板id',
  PRIMARY KEY (`prize_rule_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_product
-- ----------------------------
DROP TABLE IF EXISTS `t_product`;
CREATE TABLE `t_product`  (
  `product_id` bigint(20) NOT NULL COMMENT '商品编号',
  `product_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `remark` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `shelf_state` int(1) NOT NULL DEFAULT 0 COMMENT '上下状态(默认上架，0--上架，1--下架（仓库中），2--已售完)',
  `send_place` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发货地',
  `send_date` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发货日期',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '销售价（购买价）',
  `market_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '市场价（划掉的价格）',
  `stock` int(8) NOT NULL DEFAULT 0 COMMENT '库存',
  `sales_volume` int(8) NOT NULL DEFAULT 0 COMMENT '销量',
  `spec_type` tinyint(1) NULL DEFAULT NULL COMMENT '是否统一规格',
  `delivery_type` tinyint(1) NULL DEFAULT NULL COMMENT '是否统一邮费',
  `delivery_fees` decimal(10, 2) NULL DEFAULT NULL COMMENT '统一邮费',
  `logistic_model_id` int(11) NULL DEFAULT NULL COMMENT '邮费模板',
  `distribute_type` tinyint(1) NULL DEFAULT NULL COMMENT '是否配送',
  `service_assurance` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '服务保障',
  `delete_state` tinyint(1) NOT NULL DEFAULT 0 COMMENT '1删除，	0不删除',
  `appmodel_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板ID',
  `product_img` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主图',
  `rimg_url` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '轮播图',
  `text_img` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '图文详情',
  `param_value` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '规格参数',
  `create_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `upate_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新时间',
  `product_type_list` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动类型',
  `product_bulk` double(10, 2) NULL DEFAULT NULL COMMENT '商品的体积或重量或重量,  计算方式根据运费模板的类型计算',
  PRIMARY KEY (`product_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_product_category
-- ----------------------------
DROP TABLE IF EXISTS `t_product_category`;
CREATE TABLE `t_product_category`  (
  `product_category_id` bigint(20) NOT NULL COMMENT '商品分类编号',
  `category_id` bigint(20) NULL DEFAULT NULL COMMENT '分类ID',
  `product_id` bigint(20) NULL DEFAULT NULL COMMENT '商品编号',
  `appmodel_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板ID',
  PRIMARY KEY (`product_category_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品-分类关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_product_spec
-- ----------------------------
DROP TABLE IF EXISTS `t_product_spec`;
CREATE TABLE `t_product_spec`  (
  `product_spec_id` bigint(20) NOT NULL COMMENT '编号',
  `specification_class_id` int(11) NULL DEFAULT NULL COMMENT '规格分类编号',
  `specification_id` int(11) NULL DEFAULT NULL COMMENT '规格ID',
  `product_id` bigint(20) NULL DEFAULT NULL COMMENT '商品编号',
  `appmodel_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板ID',
  PRIMARY KEY (`product_spec_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品-规格关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_product_spec_class
-- ----------------------------
DROP TABLE IF EXISTS `t_product_spec_class`;
CREATE TABLE `t_product_spec_class`  (
  `product_spec_class_id` bigint(20) NOT NULL COMMENT '编号',
  `specification_class_id` int(11) NULL DEFAULT NULL COMMENT '规格分类编号',
  `product_id` bigint(20) NULL DEFAULT NULL COMMENT '商品编号',
  `appmodel_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板ID',
  PRIMARY KEY (`product_spec_class_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品-规格分类关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_product_spec_item
-- ----------------------------
DROP TABLE IF EXISTS `t_product_spec_item`;
CREATE TABLE `t_product_spec_item`  (
  `product_spec_item_id` bigint(20) NOT NULL COMMENT '编号',
  `product_id` bigint(20) NULL DEFAULT NULL COMMENT '商品ID',
  `sku_img` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'sku图',
  `specification_value` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规格值',
  `specification_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '价格',
  `stock` int(8) NULL DEFAULT NULL COMMENT '库存',
  `activity_stock` int(8) NULL DEFAULT NULL COMMENT '活动库存',
  `appmodel_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板ID',
  PRIMARY KEY (`product_spec_item_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品-规格详情表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_search_word
-- ----------------------------
DROP TABLE IF EXISTS `t_search_word`;
CREATE TABLE `t_search_word`  (
  `search_word_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `keyword` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关键词',
  `wordtype` tinyint(1) NULL DEFAULT NULL COMMENT '关键词类型',
  `appmodel_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板ID',
  PRIMARY KEY (`search_word_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '搜索关键词' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_sms
-- ----------------------------
DROP TABLE IF EXISTS `t_sms`;
CREATE TABLE `t_sms`  (
  `sms_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '短信id',
  `user_tel` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号',
  `sms_code` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '验证码',
  `type` int(1) NULL DEFAULT NULL COMMENT '验证类型 1.会员注册获取验证码 2.代理商绑定获取验证码3.代理商申请获取验证码',
  `create_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新时间',
  `appmodel_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模板id',
  PRIMARY KEY (`sms_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '短信表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_specification
-- ----------------------------
DROP TABLE IF EXISTS `t_specification`;
CREATE TABLE `t_specification`  (
  `specification_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `specification_class_id` int(11) NULL DEFAULT NULL COMMENT '规格分类',
  `name` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规格名称',
  `appmodel_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板ID',
  `delete_state` tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除标志（1删除，	0不删除）',
  PRIMARY KEY (`specification_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 62 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '规格值表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_specification_class
-- ----------------------------
DROP TABLE IF EXISTS `t_specification_class`;
CREATE TABLE `t_specification_class`  (
  `specification_class_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规格分类名称',
  `create_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `appmodel_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板ID',
  `delete_state` tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除标志（1删除，	0不删除）',
  PRIMARY KEY (`specification_class_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '规格表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_wlcompany
-- ----------------------------
DROP TABLE IF EXISTS `t_wlcompany`;
CREATE TABLE `t_wlcompany`  (
  `wl_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `company_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '物流公司名称',
  `company_code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司编码',
  PRIMARY KEY (`wl_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 146 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '物流公司' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_wxuser
-- ----------------------------
DROP TABLE IF EXISTS `t_wxuser`;
CREATE TABLE `t_wxuser`  (
  `wxuser_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `open_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信用户openId',
  `nike_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名称',
  `avatar_url` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像url',
  `create_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '授权时间',
  `last_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后登录时间',
  `authorize_type` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '是否授权',
  `delete_state` int(1) NULL DEFAULT 0 COMMENT '删除标示  0-可用,1-删除',
  `appmodel_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '模板id',
  `back_remark` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '后台备注',
  `mark_level` int(1) NULL DEFAULT NULL COMMENT '备注等级',
  `member_id` bigint(20) NULL DEFAULT NULL COMMENT '会员id',
  `session_key` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户的sessionkey',
  PRIMARY KEY (`wxuser_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1532054646815918 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '微信用户表' ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
