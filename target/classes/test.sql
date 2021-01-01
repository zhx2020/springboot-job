/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50560
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50560
File Encoding         : 65001

Date: 2020-12-24 11:08:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tab_job
-- ----------------------------
DROP TABLE IF EXISTS `tab_job`;
CREATE TABLE `tab_job` (
  `job_id` varchar(255) NOT NULL,
  `job_name` varchar(255) DEFAULT NULL,
  `job_href` varchar(255) DEFAULT NULL,
  `job_area` varchar(255) DEFAULT NULL,
  `job_welf` varchar(255) DEFAULT NULL,
  `job_msg` varchar(8192) DEFAULT NULL,
  `job_year` varchar(255) DEFAULT NULL,
  `job_education` varchar(255) DEFAULT NULL,
  `job_demand` varchar(255) DEFAULT NULL,
  `job_attr` varchar(255) DEFAULT NULL,
  `salary_min` int(11) DEFAULT NULL,
  `salary_max` int(11) DEFAULT NULL,
  `issue_date` varchar(255) DEFAULT NULL,
  `company_name` varchar(255) DEFAULT NULL,
  `company_href` varchar(255) DEFAULT NULL,
  `company_area` varchar(255) DEFAULT NULL,
  `company_type` varchar(255) DEFAULT NULL,
  `company_size` varchar(255) DEFAULT NULL,
  `company_ind` varchar(255) DEFAULT NULL,
  `company_attr` varchar(255) DEFAULT NULL,
  `company_msg` varchar(8192) DEFAULT NULL,
  PRIMARY KEY (`job_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tab_job
-- ----------------------------

-- ----------------------------
-- Table structure for tab_store
-- ----------------------------
DROP TABLE IF EXISTS `tab_store`;
CREATE TABLE `tab_store` (
  `job_id` varchar(255) NOT NULL,
  `job_name` varchar(255) DEFAULT NULL,
  `create_time` date DEFAULT NULL,
  PRIMARY KEY (`job_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tab_store
-- ----------------------------
INSERT INTO `tab_store` VALUES ('125194162', 'Java初级开发工程师', '2020-12-24');
