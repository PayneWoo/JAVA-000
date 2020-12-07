/*
Navicat MySQL Data Transfer

Source Server         : local57
Source Server Version : 50722
Source Host           : localhost:3307
Source Database       : db0

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2020-05-21 20:44:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `user01`
-- ----------------------------
DROP TABLE IF EXISTS `user01`;
CREATE TABLE `user01` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user01
-- ----------------------------
INSERT INTO `user01` VALUES ('1', 'James', '35');
