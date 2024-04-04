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

 Date: 05/04/2024 05:01:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_meeting
-- ----------------------------
DROP TABLE IF EXISTS `t_meeting`;
CREATE TABLE `t_meeting`  (
  `room_id` int NOT NULL AUTO_INCREMENT COMMENT '会议室id',
  `room_size` int NULL DEFAULT NULL COMMENT '会议室大小',
  `room_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '会议室名称',
  `room_floor` int NULL DEFAULT NULL COMMENT '会议室所在楼层',
  `room_identifier` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '会议室编号',
  `is_delete` int NULL DEFAULT NULL COMMENT '是否启用',
  PRIMARY KEY (`room_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_meeting
-- ----------------------------
INSERT INTO `t_meeting` VALUES (1, 2, 'cc房', 1, 'cc-bb', 0);

SET FOREIGN_KEY_CHECKS = 1;
