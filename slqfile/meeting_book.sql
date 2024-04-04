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

 Date: 05/04/2024 05:01:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for meeting_book
-- ----------------------------
DROP TABLE IF EXISTS `meeting_book`;
CREATE TABLE `meeting_book`  (
  `book_id` int NOT NULL AUTO_INCREMENT COMMENT '预约id',
  `book_start_time` datetime NULL DEFAULT NULL COMMENT '会议开始时间',
  `book_end_time` datetime NULL DEFAULT NULL COMMENT '会议结束时间',
  `create_user_id` int NULL DEFAULT NULL COMMENT '预定人',
  `create_user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '预订人姓名',
  `status` int NULL DEFAULT NULL COMMENT '预定状态',
  `reviewer_user_id` int NULL DEFAULT NULL COMMENT '审核人id',
  `reviewer_user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '审核人姓名',
  `meeting_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '会议标题',
  `meeting_details` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT '会议摘要',
  `members_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '开会成员',
  `room_id` int NULL DEFAULT NULL COMMENT '会议室id',
  PRIMARY KEY (`book_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of meeting_book
-- ----------------------------
INSERT INTO `meeting_book` VALUES (1, '2024-04-05 03:00:00', '2024-04-05 04:00:00', 1, 'test', 1, 1, 'test', '测试', '测试', NULL, 1);
INSERT INTO `meeting_book` VALUES (2, '2024-04-04 03:00:00', '2024-04-04 04:00:00', 2, 'test', 1, 1, 'test', '测试', '测试', NULL, 1);

SET FOREIGN_KEY_CHECKS = 1;
