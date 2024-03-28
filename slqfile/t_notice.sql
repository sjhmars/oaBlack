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

 Date: 28/03/2024 08:58:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_notice
-- ----------------------------
DROP TABLE IF EXISTS `t_notice`;
CREATE TABLE `t_notice`  (
  `notice_id` int NOT NULL AUTO_INCREMENT COMMENT '消息id',
  `notice_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '通知内容',
  `notice_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '通知标题',
  `notice_type` int NULL DEFAULT NULL COMMENT '通知类型',
  `send_user_id` int NULL DEFAULT NULL COMMENT '发送人',
  `recipient_user_id` int NULL DEFAULT NULL COMMENT '接收人',
  `status` int NULL DEFAULT NULL COMMENT '消息状态',
  `is_delete` int NULL DEFAULT NULL COMMENT '是否删除',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `operation_status` int NULL DEFAULT NULL COMMENT '操作状态',
  PRIMARY KEY (`notice_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_notice
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
