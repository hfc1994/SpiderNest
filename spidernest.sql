/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50709
Source Host           : localhost:3306
Source Database       : spidernest

Target Server Type    : MYSQL
Target Server Version : 50709
File Encoding         : 65001

Date: 2019-01-20 17:24:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` varchar(32) NOT NULL COMMENT '豆瓣的用户唯一标识符，可能是数字可能是字符串',
  `nickname` varchar(16) NOT NULL COMMENT '豆瓣昵称',
  `signature` varchar(64) DEFAULT NULL COMMENT '个性签名',
  `join_date` date NOT NULL COMMENT '加入豆瓣的日期',
  `place` varchar(64) DEFAULT NULL COMMENT '长居地',
  `intro` text COMMENT '介绍',
  `follow` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '关注数',
  `follower` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '被关注数',
  `follow_group` mediumint(8) unsigned DEFAULT '0' COMMENT '常去的小组数',
  `user_url` varchar(64) DEFAULT NULL COMMENT '个人主页',
  `avatar_url` varchar(64) DEFAULT NULL COMMENT '头像链接',
  `watching` mediumint(8) unsigned DEFAULT '0' COMMENT '在看的影视数量',
  `wish_watch` mediumint(8) unsigned DEFAULT '0' COMMENT '想看的影视数量',
  `watched` mediumint(8) unsigned DEFAULT '0' COMMENT '看过的影视数量',
  `wish_listen` mediumint(8) unsigned DEFAULT '0' COMMENT '想听的音乐数',
  `listened` mediumint(8) unsigned DEFAULT '0' COMMENT '听过的音乐数',
  `reading` mediumint(8) unsigned DEFAULT '0' COMMENT '在读的书籍数量',
  `wish_read` mediumint(8) unsigned DEFAULT '0' COMMENT '想读的书籍数量',
  `readed` mediumint(8) unsigned DEFAULT '0' COMMENT '已读书籍数量',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING BTREE,
  UNIQUE KEY `user_id` (`user_id`) USING BTREE,
  KEY `joindate` (`join_date`) USING BTREE,
  KEY `follow` (`follow`) USING BTREE,
  KEY `follower` (`follower`) USING BTREE,
  KEY `watched` (`watched`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for reply
-- ----------------------------
DROP TABLE IF EXISTS `reply`;
CREATE TABLE `reply` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `topic_id` varchar(32) NOT NULL COMMENT '帖子的id',
  `replier_id` varchar(32) DEFAULT NULL COMMENT '回复者id',
  `replier_name` varchar(16) NOT NULL COMMENT '回复者昵称',
  `reply_src` tinyint(1) DEFAULT '0' COMMENT '回应来源，0为网页，1为app',
  `reply_text` text COMMENT '回复的内容',
  `quote_text` text COMMENT '引用的内容',
  `quoted_name` varchar(16) DEFAULT NULL COMMENT '被引用者的昵称',
  `topicer` tinyint(1) DEFAULT '0' COMMENT '是否为楼主，0为不是，1为是',
  `likes` mediumint(8) unsigned DEFAULT '0' COMMENT '赞数',
  `reply_time` datetime DEFAULT NULL COMMENT '回复时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING BTREE,
  KEY `replier_id` (`replier_id`) USING BTREE,
  KEY `replier_name` (`replier_name`) USING BTREE,
  KEY `reply_time` (`reply_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for topic
-- ----------------------------
DROP TABLE IF EXISTS `topic`;
CREATE TABLE `topic` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(64) NOT NULL COMMENT '帖子名称',
  `url` varchar(64) NOT NULL COMMENT '帖子链接',
  `author_id` varchar(32) DEFAULT NULL COMMENT '作者id',
  `author_name` varchar(16) NOT NULL COMMENT '作者昵称',
  `reply_count` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '回复数',
  `modify_time` datetime NOT NULL COMMENT '最后回应时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING BTREE,
  KEY `author_id` (`author_id`) USING BTREE,
  KEY `author_name` (`author_name`) USING BTREE,
  KEY `reply_count` (`reply_count`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
