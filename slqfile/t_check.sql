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

 Date: 21/03/2024 09:03:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_check
-- ----------------------------
DROP TABLE IF EXISTS `t_check`;
CREATE TABLE `t_check`  (
  `check_id` int NOT NULL AUTO_INCREMENT COMMENT '打卡记录id',
  `user_id` int NOT NULL COMMENT '打卡人id',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '打卡人姓名',
  `this_date` datetime NULL DEFAULT NULL COMMENT '今天日期',
  `check_start_time` datetime NULL DEFAULT NULL COMMENT '第一次打卡时间',
  `check_end_time` datetime NULL DEFAULT NULL COMMENT '最后一次打卡时间',
  `late_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '迟到时间',
  `is_late` int NULL DEFAULT NULL COMMENT '是否迟到',
  `is_early` int NULL DEFAULT NULL COMMENT '是否早退',
  `early_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '早退时间',
  `leave_status` int NULL DEFAULT NULL COMMENT '上班状态',
  PRIMARY KEY (`check_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_check
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
