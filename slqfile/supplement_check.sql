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

 Date: 02/04/2024 23:25:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for supplement_check
-- ----------------------------
DROP TABLE IF EXISTS `supplement_check`;
CREATE TABLE `supplement_check`  (
  `supplement_id` int NOT NULL AUTO_INCREMENT COMMENT '补卡记录id',
  `check_start_time` datetime NULL DEFAULT NULL COMMENT '补卡早上时间',
  `check_end_time` datetime NULL DEFAULT NULL COMMENT '补卡晚上时间',
  `reason_content` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT '补卡理由',
  `check_id` int NULL DEFAULT NULL COMMENT '补卡id',
  `create_user_id` int NULL DEFAULT NULL COMMENT '申请人id',
  `create_user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '申请人姓名',
  `reviewer_user_id` int NULL DEFAULT NULL COMMENT '审核人',
  `reviewer_user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '审核人姓名',
  `status` int NULL DEFAULT NULL COMMENT '补卡申请状态',
  PRIMARY KEY (`supplement_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of supplement_check
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
