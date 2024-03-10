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

 Date: 11/03/2024 01:22:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_post
-- ----------------------------
DROP TABLE IF EXISTS `t_post`;
CREATE TABLE `t_post`  (
  `post_id` int NOT NULL AUTO_INCREMENT COMMENT '职位id',
  `post_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '职位名字',
  `post_status` int NOT NULL COMMENT '岗位状态',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by_user` int NULL DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`post_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_post
-- ----------------------------
INSERT INTO `t_post` VALUES (1, '测试岗位', 1, '2024-03-10 16:43:20', NULL);
INSERT INTO `t_post` VALUES (2, '{\r\n    \"postName\": \"新增测试\"\r\n}', 1, '2024-03-11 01:19:35', 1);
INSERT INTO `t_post` VALUES (3, '\"新增测试\"', 1, '2024-03-11 01:19:59', 1);
INSERT INTO `t_post` VALUES (4, '新增测试', 1, '2024-03-11 01:20:32', 1);
INSERT INTO `t_post` VALUES (5, '新增测试', 1, '2024-03-11 01:21:09', 1);

SET FOREIGN_KEY_CHECKS = 1;
