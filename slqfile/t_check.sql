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

 Date: 01/04/2024 09:17:36
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
  `early_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '早退时间',
  `leave_status` int NULL DEFAULT NULL COMMENT '上班状态',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '打卡地点',
  `other_status` int NULL DEFAULT NULL COMMENT '其他状态，请假了后还是迟到，会判定次状态',
  PRIMARY KEY (`check_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_check
-- ----------------------------
INSERT INTO `t_check` VALUES (2, 1, 'test', '2024-03-24 00:00:00', NULL, NULL, NULL, NULL, NULL, '哈哈哈哈哈哈', NULL);
INSERT INTO `t_check` VALUES (3, 1, 'test', '2024-03-25 00:00:00', NULL, NULL, NULL, NULL, NULL, '哈哈哈哈哈哈', NULL);
INSERT INTO `t_check` VALUES (4, 2, 'test1', '2024-03-25 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_check` VALUES (5, 3, 'test1', '2024-03-25 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_check` VALUES (6, 4, 'test1', '2024-03-25 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_check` VALUES (7, 5, 'test2', '2024-03-25 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_check` VALUES (8, 6, 'test2', '2024-03-25 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_check` VALUES (9, 7, 'test3', '2024-03-25 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_check` VALUES (10, 8, '测试', '2024-03-25 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_check` VALUES (11, 9, '测试', '2024-03-25 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_check` VALUES (12, 10, '测试ren', '2024-03-25 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_check` VALUES (13, 11, '测试ren', '2024-03-25 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_check` VALUES (36, 1, 'test', '2024-03-31 00:00:00', NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `t_check` VALUES (37, 2, 'test1', '2024-03-31 00:00:00', NULL, NULL, NULL, NULL, 1, NULL, NULL);
INSERT INTO `t_check` VALUES (38, 3, 'test1', '2024-03-31 00:00:00', NULL, NULL, NULL, NULL, 1, NULL, NULL);
INSERT INTO `t_check` VALUES (39, 4, 'test1', '2024-03-31 00:00:00', NULL, NULL, NULL, NULL, 1, NULL, NULL);
INSERT INTO `t_check` VALUES (40, 5, 'test2', '2024-03-31 00:00:00', NULL, NULL, NULL, NULL, 1, NULL, NULL);
INSERT INTO `t_check` VALUES (41, 6, 'test2', '2024-03-31 00:00:00', NULL, NULL, NULL, NULL, 1, NULL, NULL);
INSERT INTO `t_check` VALUES (42, 7, 'test3', '2024-03-31 00:00:00', NULL, NULL, NULL, NULL, 1, NULL, NULL);
INSERT INTO `t_check` VALUES (43, 8, '测试', '2024-03-31 00:00:00', NULL, NULL, NULL, NULL, 1, NULL, NULL);
INSERT INTO `t_check` VALUES (44, 9, '测试', '2024-03-31 00:00:00', NULL, NULL, NULL, NULL, 1, NULL, NULL);
INSERT INTO `t_check` VALUES (45, 10, '测试ren', '2024-03-31 00:00:00', NULL, NULL, NULL, NULL, 1, NULL, NULL);
INSERT INTO `t_check` VALUES (46, 11, '测试ren', '2024-03-31 00:00:00', NULL, NULL, NULL, NULL, 1, NULL, NULL);
INSERT INTO `t_check` VALUES (47, 1, 'test', '2024-04-01 00:00:00', '2024-04-01 09:00:00', NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `t_check` VALUES (48, 2, 'test1', '2024-04-01 00:00:00', NULL, NULL, NULL, NULL, 1, NULL, NULL);
INSERT INTO `t_check` VALUES (49, 3, 'test1', '2024-04-01 00:00:00', NULL, NULL, NULL, NULL, 1, NULL, NULL);
INSERT INTO `t_check` VALUES (50, 4, 'test1', '2024-04-01 00:00:00', NULL, NULL, NULL, NULL, 1, NULL, NULL);
INSERT INTO `t_check` VALUES (51, 5, 'test2', '2024-04-01 00:00:00', NULL, NULL, NULL, NULL, 1, NULL, NULL);
INSERT INTO `t_check` VALUES (52, 6, 'test2', '2024-04-01 00:00:00', NULL, NULL, NULL, NULL, 1, NULL, NULL);
INSERT INTO `t_check` VALUES (53, 7, 'test3', '2024-04-01 00:00:00', NULL, NULL, NULL, NULL, 1, NULL, NULL);
INSERT INTO `t_check` VALUES (54, 8, '测试', '2024-04-01 00:00:00', NULL, NULL, NULL, NULL, 1, NULL, NULL);
INSERT INTO `t_check` VALUES (55, 9, '测试', '2024-04-01 00:00:00', NULL, NULL, NULL, NULL, 1, NULL, NULL);
INSERT INTO `t_check` VALUES (56, 10, '测试ren', '2024-04-01 00:00:00', NULL, NULL, NULL, NULL, 1, NULL, NULL);
INSERT INTO `t_check` VALUES (57, 11, '测试ren', '2024-04-01 00:00:00', NULL, NULL, NULL, NULL, 1, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
