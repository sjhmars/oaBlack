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

 Date: 24/03/2024 00:30:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_temp_wage
-- ----------------------------
DROP TABLE IF EXISTS `t_temp_wage`;
CREATE TABLE `t_temp_wage`  (
  `temp_wages_id` int NOT NULL AUTO_INCREMENT COMMENT '薪资模版id',
  `basic_salary` int NULL DEFAULT NULL COMMENT '工资值',
  `user_id` int NULL DEFAULT NULL COMMENT '领工资的人的id',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '领工资的人的名字',
  `update_by_userId` int NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `create_user_id` int NULL DEFAULT NULL COMMENT '创建人',
  `meal_supplement` int NULL DEFAULT NULL COMMENT '餐补模版',
  `is_delete` int NULL DEFAULT NULL COMMENT '是否失效',
  `performance` int NULL DEFAULT NULL COMMENT '绩效模版',
  PRIMARY KEY (`temp_wages_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_temp_wage
-- ----------------------------
INSERT INTO `t_temp_wage` VALUES (1, 10000, 7, 'test3', NULL, NULL, 1, 600, 0, 1000);
INSERT INTO `t_temp_wage` VALUES (2, 50000, 1, 'test', NULL, NULL, 1, 600, 0, 1000);

SET FOREIGN_KEY_CHECKS = 1;
