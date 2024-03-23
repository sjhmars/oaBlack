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

 Date: 23/03/2024 14:27:24
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
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_post
-- ----------------------------
INSERT INTO `t_post` VALUES (1, '测试岗位', 1, '2024-03-10 16:43:20', NULL);
INSERT INTO `t_post` VALUES (2, '{\r\n    \"postName\": \"新增测试\"\r\n}', 1, '2024-03-11 01:19:35', 1);
INSERT INTO `t_post` VALUES (3, '\"新增测试\"', 1, '2024-03-11 01:19:59', 1);
INSERT INTO `t_post` VALUES (4, '新增测试', 1, '2024-03-11 01:20:32', 1);
INSERT INTO `t_post` VALUES (5, '新增测试', 1, '2024-03-11 01:21:09', 1);
INSERT INTO `t_post` VALUES (6, '新增测试', 1, '2024-03-11 23:12:58', 1);
INSERT INTO `t_post` VALUES (7, '新增测试', 1, '2024-03-11 23:16:22', 1);
INSERT INTO `t_post` VALUES (8, '产品经理', 1, '2024-03-13 23:37:41', 6);
INSERT INTO `t_post` VALUES (10, '新增岗位', 1, '2024-03-13 23:48:28', 6);
INSERT INTO `t_post` VALUES (11, '岗位1', 1, '2024-03-14 23:17:15', 6);
INSERT INTO `t_post` VALUES (12, '岗位1', 1, '2024-03-14 23:17:25', 6);
INSERT INTO `t_post` VALUES (13, '岗位2', 1, '2024-03-14 23:39:45', 6);
INSERT INTO `t_post` VALUES (14, '岗位2', 1, '2024-03-19 07:37:37', 6);

SET FOREIGN_KEY_CHECKS = 1;
