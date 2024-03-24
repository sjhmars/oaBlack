/*
 Navicat Premium Data Transfer

 Source Server         : text1
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : localhost:3306
 Source Schema         : office_oa

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 24/03/2024 14:03:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_leave
-- ----------------------------
DROP TABLE IF EXISTS `t_leave`;
CREATE TABLE `t_leave`  (
  `leave_id` int NOT NULL AUTO_INCREMENT,
  `begin_time` datetime NULL DEFAULT NULL COMMENT '请假开始时间',
  `end_time` datetime NULL DEFAULT NULL COMMENT '请假结束时间',
  `day` int NULL DEFAULT NULL COMMENT '请假天数',
  `reviewer_user_id` int NULL DEFAULT NULL COMMENT '审核人',
  `reviewer_user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '审核人姓名',
  `status` int NULL DEFAULT NULL COMMENT '假条状态',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_id` int NULL DEFAULT NULL COMMENT '申请人',
  `create_user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '申请人姓名',
  `leave_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '请假类型',
  `leave_details` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT '请假详情',
  PRIMARY KEY (`leave_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_leave
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
