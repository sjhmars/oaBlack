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

 Date: 14/03/2024 04:34:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_department
-- ----------------------------
DROP TABLE IF EXISTS `t_department`;
CREATE TABLE `t_department`  (
  `department_id` int NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `department_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '部门名字',
  `superior_department` int NULL DEFAULT NULL COMMENT '上级部门id',
  `is_delete` int NOT NULL COMMENT '是否删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `user_id` int NULL DEFAULT NULL COMMENT '负责人',
  `head_user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '负责人姓名',
  PRIMARY KEY (`department_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_department
-- ----------------------------
INSERT INTO `t_department` VALUES (1, '管委会', NULL, 0, '2024-03-10 02:18:09', NULL, 1, 'test');
INSERT INTO `t_department` VALUES (3, '总经办', 1, 0, '2024-03-10 03:49:34', NULL, 1, 'test');
INSERT INTO `t_department` VALUES (4, '战略部', 1, 0, '2024-03-11 23:14:16', NULL, 1, 'test');
INSERT INTO `t_department` VALUES (5, '战略部', 1, 0, '2024-03-11 23:14:42', NULL, 1, 'test');
INSERT INTO `t_department` VALUES (6, '战略部', 5, 0, '2024-03-11 23:14:56', NULL, 1, 'test');
INSERT INTO `t_department` VALUES (7, '战略市场', 6, 0, '2024-03-12 23:48:36', NULL, 1, 'test');

SET FOREIGN_KEY_CHECKS = 1;
