/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 50643
 Source Host           : localhost:3306
 Source Schema         : shiro

 Target Server Type    : MySQL
 Target Server Version : 50643
 File Encoding         : 65001

 Date: 10/06/2019 15:08:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_permission`;
CREATE TABLE `t_permission`  (
  `ID` int(10) NOT NULL COMMENT '用户id',
  `URL` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'url地址',
  `NAME` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'url描述'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_permission
-- ----------------------------
INSERT INTO `t_permission` VALUES (1, '/user', 'user:user');
INSERT INTO `t_permission` VALUES (2, '/user/add', 'user:add');
INSERT INTO `t_permission` VALUES (3, '/user/delete', 'user:delete');

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role`  (
  `ID` int(11) NOT NULL,
  `NAME` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `MEMO` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色描述'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES (1, 'admin', '超级管理员');
INSERT INTO `t_role` VALUES (2, 'test', '测试用户');

-- ----------------------------
-- Table structure for t_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_role_permission`;
CREATE TABLE `t_role_permission`  (
  `RID` int(11) NOT NULL COMMENT ' 角色id',
  `PID` int(11) NOT NULL COMMENT '权限id'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_role_permission
-- ----------------------------
INSERT INTO `t_role_permission` VALUES (1, 2);
INSERT INTO `t_role_permission` VALUES (1, 3);
INSERT INTO `t_role_permission` VALUES (2, 1);
INSERT INTO `t_role_permission` VALUES (1, 1);

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `ID` int(11) NOT NULL,
  `USER_NAME` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `PASSWORD` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `CREATE_TIME` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建时间',
  `STATUS` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '是否有效 1 : 有效    2 : 锁定'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, 'admin', 'df655ad8d3229f3269fad2a8bab59b6c', '2017-12-11 10:52:48', '1');
INSERT INTO `t_user` VALUES (2, 'tester', '76f119eaca2d2e6ea75152127f831cfd', '2017-12-11 10:52:48', '1');

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role`  (
  `USER_ID` int(11) NOT NULL COMMENT '用户id',
  `RID` int(11) NOT NULL COMMENT '角色id'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES (1, 1);
INSERT INTO `t_user_role` VALUES (2, 2);

SET FOREIGN_KEY_CHECKS = 1;
