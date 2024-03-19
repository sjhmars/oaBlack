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

 Date: 19/03/2024 08:31:14
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
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '领工资的人名字',
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
INSERT INTO `t_wages` VALUES (1, NULL, NULL, NULL, '2024-03-18 23:30:51', NULL, 6, NULL, NULL, 0, 0);
INSERT INTO `t_wages` VALUES (2, 9000, 7, NULL, '2024-03-18 23:47:50', NULL, 6, 200, 200, 0, 0);

SET FOREIGN_KEY_CHECKS = 1;
