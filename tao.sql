-- phpMyAdmin SQL Dump
-- version 4.4.10
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 2017-02-18 08:48:46
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
  `id` varchar(36) NOT NULL COMMENT '业务ID',
  `pId` varchar(36) DEFAULT NULL COMMENT '上级代理ID，为空则表示没有上级',
  `reach` int(1) DEFAULT '0' COMMENT '是否就代理达成一致意见，1达成，其他不达成',
  `mark` varchar(12) NOT NULL COMMENT '显示ID',
  `pMark` varchar(12) DEFAULT NULL COMMENT '上级的显示ID',
  `cellphone` varchar(11) NOT NULL COMMENT '手机号码',
  `password` varchar(64) NOT NULL COMMENT '登录密码',
  `name` varchar(36) DEFAULT NULL COMMENT '姓名',
  `named` varchar(36) DEFAULT NULL COMMENT '被命名',
  `levelId` varchar(36) NOT NULL DEFAULT '0' COMMENT '代理的级别ID',
  `weight` int(1) NOT NULL DEFAULT '0' COMMENT '排序，数据权重',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '代理的状态，-1删除，0禁用，1正常',
  `haveCodes` int(11) NOT NULL DEFAULT '0' COMMENT '累计的购买授权码数量',
  `spendCodes` int(11) NOT NULL DEFAULT '0' COMMENT '累计的授权码数量',
  `leftCodes` int(11) NOT NULL DEFAULT '0' COMMENT '当前可用的授权码数量',
  `trueName` varchar(36) DEFAULT NULL COMMENT '真实姓名',
  `zhifubao` varchar(36) DEFAULT NULL COMMENT '支付宝账户',
  `pIdTime` timestamp NULL DEFAULT NULL COMMENT '申请加入代理体系的时间',
  `reachTime` timestamp NULL DEFAULT NULL COMMENT '达成上下级代理的时间',
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
  `toId` varchar(36) DEFAULT NULL COMMENT '操作流向ID，如果类型为1，则此ID为后来补充',
  `toMark` varchar(12) DEFAULT NULL COMMENT '流向的显示ID',
  `type` int(11) NOT NULL COMMENT '1激活码，2批量激活码',
  `actCode` varchar(12) DEFAULT NULL COMMENT '如果类型为1，则是向单个用激活；如果类型为2，则表示交易为数量',
  `codeNum` int(11) NOT NULL COMMENT '如果类型为2，则是代理之间交易，记录数量',
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
  `description` text NOT NULL COMMENT '描述',
  `minNum` int(11) NOT NULL COMMENT '一次的最少取码量',
  `price` float NOT NULL COMMENT '每码价格',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `level`
--

INSERT INTO `level` (`id`, `name`, `description`, `minNum`, `price`, `createTime`, `updateTime`) VALUES
('694d50b0-d96d-11e6-9335-5d7f657e4af7', '联合创始人', '联合创始人', 1000, 48, '2017-01-14 02:43:22', '2017-01-14 00:00:00'),
('694dddfa-d96d-11e6-9335-5d7f657e4af7', '总代', '总代', 500, 55, '2017-01-14 02:43:26', '2017-01-05 00:00:00'),
('8f2e8e0c-d96d-11e6-9335-5d7f657e4af7', '一级代理', '一级代理', 20, 65, '2017-01-14 02:43:30', '2017-01-05 00:00:00'),
('8f2ea536-d96d-11e6-9335-5d7f657e4af7', '分销商', '分销商', 3, 78, '2017-01-14 02:43:35', '2017-01-18 00:00:00');

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
('9965aad0-f19c-11e6-87a2-adf5c4f88bd2', 'administrator110', 'admin', '18511694468', 'B9416C80E3045C9929C3AE9B7B352124', -99, -67, '["200","201","202"]', '2017-02-13 03:29:17', '2017-02-15 10:01:11');

-- --------------------------------------------------------

--
-- 表的结构 `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` varchar(36) NOT NULL COMMENT '业务ID，不能暴露给客户端',
  `mark` varchar(12) NOT NULL COMMENT '用户ID',
  `cellphone` varchar(15) NOT NULL COMMENT '手机号码',
  `password` varchar(64) NOT NULL COMMENT '登录密码',
  `name` varchar(36) DEFAULT NULL COMMENT '备注姓名',
  `actCode` varchar(36) DEFAULT NULL COMMENT '激活码',
  `actTime` timestamp NULL DEFAULT NULL COMMENT '激活时间',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '0禁用，1正常',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

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
-- 转存表中的数据 `version`
--

INSERT INTO `version` (`versionId`, `platform`, `type`, `versionCode`, `versionName`, `appName`, `information`, `url`, `upgrade`, `createTime`, `updateTime`) VALUES
('7975f9dc-d48c-11e6-802a-6ef16e545ac5', 'Android', 2, 5, 'snapshot-1.0.3', 'Agent', '1:增加了什么功能；\r\n2:增加了什么特色；\r\n3:增加了什么玩意；\r\n4:修改了什么东西；\r\n5:优化了什么路径；', NULL, 3, '2017-01-07 03:50:47', '2017-01-14 07:00:06'),
('ac6d1fbe-d4aa-11e6-802a-6ef16e545ac5', 'Android', 1, 4, 'snapshot-1.0.3', 'Seller', '1:故用兵之法，十则围之，五则攻之，倍则分之，敌则能战之，少则能逃之，不若则能避之。故小敌之坚，大敌之擒也。\r\n2:夫将者，国之辅也,辅周则国必强，辅隙则国必弱。', NULL, 1, '2017-01-07 07:26:58', '2017-02-16 02:49:13');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `agent`
--
ALTER TABLE `agent`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `cellphone` (`cellphone`),
  ADD UNIQUE KEY `mark` (`mark`);

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
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `mark` (`mark`);

--
-- Indexes for table `version`
--
ALTER TABLE `version`
  ADD PRIMARY KEY (`versionId`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
