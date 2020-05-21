-- ----------------------------
--  Source Server Type    : MySQL
--  Source Server Version : 8.0.20
--  Source Host           : localhost:3306
--  Source Schema         : dung-beetle
--
--  Target Server Type    : MySQL
--  Target Server Version : 8.0.20
--
--  File Encoding         : 65001
--
--  Date: 18/05/2020 14:57:28
-- ----------------------------

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for `banner`
-- ----------------------------
DROP TABLE IF EXISTS `banner`;
CREATE TABLE `banner` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `create_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3),
  `update_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  `delete_time` datetime(3) DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '部分banner可能有标题图片',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of `banner`
-- ----------------------------
BEGIN;
INSERT INTO `banner` VALUES ('1', 'b-1', '首页顶部主banner', '2019-07-28 04:47:15.000', '2019-08-04 01:03:16.000', null, null, null);
INSERT INTO `banner` VALUES ('2', 'b-2', '热销商品banner', '2019-08-01 00:37:47.000', '2019-09-20 00:56:45.843', null, null, null);
COMMIT;

-- ----------------------------
-- Table structure for `banner_item`
-- ----------------------------
DROP TABLE IF EXISTS `banner_item`;
CREATE TABLE `banner_item` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `keyword` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `type` smallint(5) unsigned NOT NULL DEFAULT '0',
  `create_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3),
  `update_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  `delete_time` datetime(3) DEFAULT NULL,
  `banner_id` int(10) unsigned NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of `banner_item`
-- ----------------------------
BEGIN;
INSERT INTO `banner_item` VALUES ('5', null, '28', '1', '2019-08-01 00:41:41.000', '2019-09-20 01:02:57.058', null, '2', 'left');
INSERT INTO `banner_item` VALUES ('6', null, '26', '1', '2019-08-01 00:41:41.000', '2019-09-20 01:03:20.431', null, '2', 'right-top');
INSERT INTO `banner_item` VALUES ('7', null, '27', '1', '2019-08-01 00:41:41.000', '2019-09-20 01:03:22.441', null, '2', 'right-bottom');
INSERT INTO `banner_item` VALUES ('12', null, 't-2', '3', '2019-09-15 17:29:52.000', '2019-09-20 00:47:22.829', null, '1', null);
INSERT INTO `banner_item` VALUES ('13', null, '23', '1', '2019-07-28 04:39:22.000', '2019-09-18 21:27:20.349', null, '1', null);
INSERT INTO `banner_item` VALUES ('14', null, '24', '1', '2019-07-28 04:40:10.000', '2019-09-18 21:27:20.349', null, '1', null);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
