-- phpMyAdmin SQL Dump
-- version 4.4.10
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 2017-03-06 08:46:14
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
-- 表的结构 `agent`
--

DROP TABLE IF EXISTS `agent`;
CREATE TABLE IF NOT EXISTS `agent` (
  `id` int(11) NOT NULL COMMENT '代理的ID，从100000起始',
  `pId` int(11) DEFAULT NULL COMMENT '上级代理ID，为空则表示没有上级',
  `reach` int(1) DEFAULT '0' COMMENT '是否就代理达成一致意见，1达成，其他不达成',
  `cellphone` varchar(11) NOT NULL COMMENT '手机号码',
  `password` varchar(64) NOT NULL COMMENT '登录密码',
  `name` varchar(36) DEFAULT NULL COMMENT '姓名',
  `named` varchar(36) DEFAULT NULL COMMENT '被命名',
  `level` int(4) NOT NULL DEFAULT '0' COMMENT '代理的级别，顶级为1，依次加一',
  `weight` int(1) NOT NULL DEFAULT '0' COMMENT '排序，数据权重',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '代理的状态，-1删除，0禁用，1正常',
  `haveCodes` int(11) NOT NULL DEFAULT '0' COMMENT '累计的购买授权码数量',
  `spendCodes` int(11) NOT NULL DEFAULT '0' COMMENT '累计的授权码数量',
  `leftCodes` int(11) NOT NULL DEFAULT '0' COMMENT '当前可用的授权码数量',
  `weChat` varchar(36) DEFAULT NULL COMMENT '微信号',
  `trueName` varchar(36) DEFAULT NULL COMMENT '真实姓名',
  `zhifubao` varchar(36) DEFAULT NULL COMMENT '支付宝账户',
  `pIdTime` timestamp NULL DEFAULT NULL COMMENT '申请加入代理体系的时间',
  `reachTime` timestamp NULL DEFAULT NULL COMMENT '达成上下级代理的时间',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB AUTO_INCREMENT=100003 DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `backup`
--

DROP TABLE IF EXISTS `backup`;
CREATE TABLE IF NOT EXISTS `backup` (
  `id` varchar(36) NOT NULL,
  `name` varchar(36) DEFAULT NULL COMMENT '名称',
  `filePath` varchar(128) NOT NULL COMMENT '文件路径',
  `type` int(1) NOT NULL DEFAULT '1' COMMENT '1数据库，2日志',
  `auto` int(1) NOT NULL DEFAULT '1' COMMENT '1：自动备份，2：手动备份',
  `counter` int(11) NOT NULL DEFAULT '0' COMMENT '下载次数',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `history`
--

DROP TABLE IF EXISTS `history`;
CREATE TABLE IF NOT EXISTS `history` (
  `id` varchar(36) NOT NULL COMMENT '业务ID',
  `fromId` varchar(36) NOT NULL COMMENT '操作来源ID',
  `toId` varchar(36) DEFAULT NULL COMMENT '操作流向ID，如果类型为空，则此ID为后来补充',
  `type` int(11) NOT NULL COMMENT '1激活码，2批量激活码',
  `actCode` varchar(12) DEFAULT NULL COMMENT '如果类型为1，则是向单个用激活；如果类型为2，则表示交易为数量',
  `codeNum` int(11) NOT NULL COMMENT '如果类型为2，则是代理之间交易，记录数量',
  `price` float DEFAULT NULL COMMENT '成交价格',
  `codeDeal` float DEFAULT NULL COMMENT '成交价格',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `level`
--

DROP TABLE IF EXISTS `level`;
CREATE TABLE IF NOT EXISTS `level` (
  `id` varchar(36) NOT NULL COMMENT '业务ID',
  `name` varchar(36) NOT NULL COMMENT '名称',
  `description` text COMMENT '描述',
  `minNum` int(11) NOT NULL COMMENT '一次的最少取码量',
  `price` float NOT NULL COMMENT '每码价格',
  `level` int(11) NOT NULL COMMENT '等级',
  `weight` int(11) NOT NULL DEFAULT '0' COMMENT '权重，数字越大，权重越小',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `manager`
--

DROP TABLE IF EXISTS `manager`;
CREATE TABLE IF NOT EXISTS `manager` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `loginName` varchar(22) NOT NULL COMMENT '登录名称',
  `nickName` varchar(64) NOT NULL COMMENT '用户名称',
  `cellphone` varchar(11) NOT NULL COMMENT '电话号码',
  `password` varchar(128) NOT NULL COMMENT '登录密码',
  `level` int(1) NOT NULL DEFAULT '2' COMMENT '用户级别',
  `status` int(1) NOT NULL DEFAULT '2' COMMENT '-1删除，1禁用，2正常',
  `access` varchar(256) DEFAULT NULL COMMENT '权限表,使用,分割',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `manager`
--

INSERT INTO `manager` (`id`, `loginName`, `nickName`, `cellphone`, `password`, `level`, `status`, `access`, `createTime`, `updateTime`) VALUES
('9965aad0-f19c-11e6-87a2-adf5c4f88bd2', 'administrator110', 'admin', '18511694468', 'B9416C80E3045C9929C3AE9B7B352124', -99, -67, '["200","201","204","202","203","300","301","302"]', '2017-02-13 03:29:17', '2017-03-03 01:35:04');

-- --------------------------------------------------------

--
-- 表的结构 `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL,
  `cellphone` varchar(15) NOT NULL COMMENT '手机号码',
  `password` varchar(64) NOT NULL COMMENT '登录密码',
  `name` varchar(36) DEFAULT NULL COMMENT '备注姓名',
  `actCode` varchar(36) DEFAULT NULL COMMENT '激活码',
  `actTime` timestamp NULL DEFAULT NULL COMMENT '激活时间',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '0禁用，1正常',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间'
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8 COMMENT='用户表';

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
  `upgrade` int(1) DEFAULT NULL COMMENT '是否强制升级，0不强制升级 其他强制',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `agent`
--
ALTER TABLE `agent`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `cellphone` (`cellphone`);

--
-- Indexes for table `backup`
--
ALTER TABLE `backup`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `history`
--
ALTER TABLE `history`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `level`
--
ALTER TABLE `level`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `manager`
--
ALTER TABLE `manager`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `version`
--
ALTER TABLE `version`
  ADD PRIMARY KEY (`versionId`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `agent`
--
ALTER TABLE `agent`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '代理的ID，从100000起始',AUTO_INCREMENT=100003;
--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=51;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
