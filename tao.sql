-- phpMyAdmin SQL Dump
-- version 4.4.10
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 2017-01-08 07:16:31
-- 服务器版本： 5.6.22
-- PHP Version: 5.5.20

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `tao`
--
CREATE DATABASE IF NOT EXISTS `tao` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `tao`;

-- --------------------------------------------------------

--
-- 表的结构 `mate`
--

DROP TABLE IF EXISTS `agent`;
CREATE TABLE IF NOT EXISTS `agent` (
  `agentId` varchar(36) NOT NULL COMMENT '代理的ID',
  `agentPId` varchar(36) DEFAULT NULL COMMENT '上级代理ID',
  `cellphone` varchar(11) NOT NULL COMMENT '手机号码',
  `password1` varchar(128) DEFAULT NULL COMMENT '业务类型1的密码',
  `password2` varchar(36) DEFAULT NULL COMMENT '业务类型2的密码',
  `name` varchar(36) DEFAULT NULL COMMENT '姓名',
  `named` varchar(36) DEFAULT NULL COMMENT '被命名',
  `grantCode` varchar(11) DEFAULT NULL COMMENT '自己的授权码',
  `level` int(1) NOT NULL DEFAULT '9' COMMENT '高级代理的级别',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '代理的状态，-1删除，0禁用，1正常',
  `haveCodes` int(11) NOT NULL DEFAULT '0' COMMENT '累计的购买授权码数量',
  `spendCodes` int(11) NOT NULL DEFAULT '0' COMMENT '累计的授权码数量',
  `leftCodes` int(11) NOT NULL DEFAULT '0' COMMENT '当前可用的授权码数量',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `grantTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '被授权的时间',
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `version`
--

DROP TABLE IF EXISTS `version`;
CREATE TABLE IF NOT EXISTS `version` (
  `versionId` varchar(36) NOT NULL COMMENT 'ID',
  `platform` varchar(12) NOT NULL COMMENT '应用平台',
  `type` int(11) NOT NULL COMMENT '应用类型',
  `versionCode` int(11) NOT NULL COMMENT '应用版本',
  `versionName` varchar(36) DEFAULT NULL COMMENT '应用名称',
  `appName` varchar(36) DEFAULT NULL COMMENT '应用名称',
  `information` text COMMENT '备注信息',
  `url` varchar(256) DEFAULT NULL COMMENT '下载地址',
  `upgrade` int(11) DEFAULT NULL COMMENT '是否强制升级',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `mate`
--
ALTER TABLE `agent`
  ADD PRIMARY KEY (`agentId`),
  ADD UNIQUE KEY `cellphone` (`cellphone`);

--
-- Indexes for table `version`
--
ALTER TABLE `version`
  ADD PRIMARY KEY (`versionId`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
