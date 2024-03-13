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

 Date: 14/03/2024 04:34:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_wages
-- ----------------------------
DROP TABLE IF EXISTS `t_wages`;
CREATE TABLE `t_wages`  (
  `wages_id` int NOT NULL AUTO_INCREMENT,
  `basic_salary` int NULL DEFAULT NULL COMMENT '工资值',
  `user_id` int NULL DEFAULT NULL COMMENT '领工资的人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '发工资日期',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `create_user_id` int NULL DEFAULT NULL COMMENT '发工资的人',
  `meal_supplement` int NULL DEFAULT NULL COMMENT '餐补',
  `performance` int NULL DEFAULT NULL COMMENT '绩效',
  `lose_money` int NULL DEFAULT NULL COMMENT '当月扣除',
  `is_delete` int NULL DEFAULT NULL COMMENT '0删除/1启用',
  PRIMARY KEY (`wages_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_wages
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
