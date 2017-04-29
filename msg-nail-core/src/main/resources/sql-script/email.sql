-- MySQL dump 10.13  Distrib 5.7.14, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: ipos-document-centers
-- ------------------------------------------------------
-- Server version	5.7.14-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `business_information`
--

DROP DATABASE IF EXISTS `ipos-document-centers`;
CREATE DATABASE IF NOT EXISTS `ipos-document-centers`;

USE `ipos-document-centers`; 

DROP TABLE IF EXISTS `business_information`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `business_information` (
  `uuid` binary(16) NOT NULL,
  `attachment_type` varchar(255) DEFAULT NULL,
  `detail` varchar(255) DEFAULT NULL,
  `document_name` varchar(255) DEFAULT NULL,
  `document_type` varchar(255) DEFAULT NULL,
  `is_attachted` bit(1) DEFAULT NULL,
  `is_gen_by_system` bit(1) DEFAULT NULL,
  `is_required` bit(1) DEFAULT NULL,
  `is_sign_required` bit(1) DEFAULT NULL,
  `is_signed` bit(1) DEFAULT NULL,
  `module_id` varchar(255) DEFAULT NULL,
  `owner_id` varchar(255) DEFAULT NULL,
  `profile_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `document_model`
--

DROP TABLE IF EXISTS `document_model`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `document_model` (
  `uuid` binary(16) NOT NULL,
  `content` longblob,
  `version` bigint(20) DEFAULT NULL,
  `business_information_uuid` binary(16) DEFAULT NULL,
  `system_information_uuid` binary(16) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  KEY `FKqhxwq01c6daqiu5lpjybds45j` (`business_information_uuid`),
  KEY `FK8equ1n3ic9mk8r93pcjtg0c2w` (`system_information_uuid`),
  CONSTRAINT `FK8equ1n3ic9mk8r93pcjtg0c2w` FOREIGN KEY (`system_information_uuid`) REFERENCES `system_information` (`uuid`),
  CONSTRAINT `FKqhxwq01c6daqiu5lpjybds45j` FOREIGN KEY (`business_information_uuid`) REFERENCES `business_information` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `system_information`
--

DROP TABLE IF EXISTS `system_information`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_information` (
  `uuid` binary(16) NOT NULL,
  `attached_date` datetime DEFAULT NULL,
  `content_size` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `detached_date` datetime DEFAULT NULL,
  `file_name` varchar(255) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `signed_date` datetime DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-11-23 17:04:13
