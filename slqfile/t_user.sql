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

 Date: 12/03/2024 03:56:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `user_id` int NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `user_password` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户密码',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_id` int NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '地址',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '用户头像',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '用户邮箱',
  `mobile` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '用户手机',
  `sex` int UNSIGNED NOT NULL COMMENT '性别0女/1男',
  `status` int UNSIGNED NOT NULL COMMENT '0启用/1停用',
  `birth` datetime NOT NULL COMMENT '出生年月日',
  `role_id` int NULL DEFAULT NULL COMMENT '角色id列表',
  `role_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '角色名称',
  `post_id` int NULL DEFAULT NULL COMMENT '岗位id',
  `department_id` int NULL DEFAULT NULL COMMENT '部门id',
  `department_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '拥有的部门id列表',
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '用户昵称',
  `last_login_time` datetime NULL DEFAULT NULL COMMENT '登录时间',
  `last_login_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '登录地址',
  `work_status` int NULL DEFAULT NULL COMMENT '0实习/1试用/2正式',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, 'test', '$2a$10$LW6BWlZ8Uq8C.PtaANGo5.YFIDLRUYdsXaR/.29ThCOjr/R9Mme5u', '1', '2024-01-20 22:46:21', 1, '2024-03-12 02:11:09', '紫荆花园', NULL, '13342862235@qq.com', '13342862235', 0, 1, '2001-10-11 15:46:55', 1, '管理员', 1, 1, '1,3,4,6', '我好困', '2024-03-12 02:11:09', 'hhh3', 2);
INSERT INTO `t_user` VALUES (2, 'test1', '$2a$10$PXI3FIJq9a6Hmvp10ej.SuwdiNAa/erFdHegncdVDU7502LLKPdIS', 'test', '2024-03-10 16:43:50', NULL, NULL, NULL, NULL, NULL, '13098524221', 1, 1, '2001-08-23 11:11:11', NULL, NULL, 1, 3, NULL, NULL, NULL, NULL, 0);
INSERT INTO `t_user` VALUES (3, 'test1', '$2a$10$U2tqqlRkD.GnJ6vLCezHoON6BngOromgtUo2ufGwTx4os/RM959.S', 'test', '2024-03-11 23:14:08', NULL, NULL, NULL, NULL, NULL, '13098524222', 1, 1, '2001-08-23 11:11:11', NULL, NULL, 1, 3, NULL, NULL, NULL, NULL, 0);
INSERT INTO `t_user` VALUES (4, 'test1', '$2a$10$GF4SIyRKooDs/d.6iUF6Yemd0YvmbXVk4Tmy3ZpThI7B7dsN37jQi', 'test', '2024-03-12 02:48:58', NULL, NULL, NULL, NULL, NULL, '13098524223', 1, 1, '2001-08-23 11:11:11', NULL, NULL, 1, 3, NULL, NULL, NULL, NULL, 0);
INSERT INTO `t_user` VALUES (5, 'test2', '$2a$10$C4wS7S4z99Huhkbxn2SraOzcCD5WkPcl9acvnw8ylv28bPQStPYAW', 'test', '2024-03-12 02:50:39', NULL, NULL, NULL, NULL, NULL, '13098524224', 1, 1, '2001-08-23 11:11:11', NULL, NULL, 1, 3, NULL, NULL, NULL, NULL, 0);
INSERT INTO `t_user` VALUES (6, 'test2', '$2a$10$POdb2mrMHm3AFekc6PEuuebjCbWlM.dyorPKPO8ZcZytlDS8J70GC', 'test', '2024-03-12 02:56:03', 6, '2024-03-12 03:45:15', NULL, NULL, NULL, '13098524225', 1, 1, '2001-08-23 11:11:11', 1, '管理员', 1, 3, NULL, NULL, '2024-03-12 03:45:15', 'hhh3', 0);

SET FOREIGN_KEY_CHECKS = 1;
