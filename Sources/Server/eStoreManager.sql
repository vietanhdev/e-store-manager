-- MySQL dump 10.16  Distrib 10.1.34-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: eStoreManager
-- ------------------------------------------------------
-- Server version	10.1.34-MariaDB-0ubuntu0.18.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `buy_items`
--

DROP TABLE IF EXISTS `buy_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `buy_items` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `price` float DEFAULT NULL,
  `product_id` bigint(20) DEFAULT NULL,
  `quantities` float DEFAULT NULL,
  `supplier_id` bigint(20) DEFAULT NULL,
  `buy_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrstk31yqs8npq0nbj17num501` (`buy_id`),
  CONSTRAINT `FKrstk31yqs8npq0nbj17num501` FOREIGN KEY (`buy_id`) REFERENCES `buys` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `buy_items`
--

LOCK TABLES `buy_items` WRITE;
/*!40000 ALTER TABLE `buy_items` DISABLE KEYS */;
INSERT INTO `buy_items` VALUES (4,'2018-12-06 08:14:43','2018-12-06 08:14:43',12000,1,2,7,7),(5,'2018-12-06 08:14:43','2018-12-06 08:14:43',12000,4,3,2,7),(6,'2018-12-06 08:14:43','2018-12-06 08:14:43',1000,4,12,5,7),(7,'2018-12-07 01:48:16','2018-12-07 01:48:16',12000,5,2,1,6),(8,'2018-12-07 02:03:09','2018-12-07 02:03:09',1,1,1,2,1),(9,'2018-12-07 02:03:09','2018-12-07 02:03:09',1,2,1,1,1),(10,'2018-12-09 08:15:18','2018-12-09 08:15:18',12000,7,4,3,8),(11,'2018-12-09 08:15:18','2018-12-09 08:15:18',15000,6,34,2,8),(12,'2018-12-09 08:15:18','2018-12-09 08:15:18',12000,3,34,9,8);
/*!40000 ALTER TABLE `buy_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `buys`
--

DROP TABLE IF EXISTS `buys`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `buys` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `active` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `buys`
--

LOCK TABLES `buys` WRITE;
/*!40000 ALTER TABLE `buys` DISABLE KEYS */;
INSERT INTO `buys` VALUES (1,'2018-12-06 04:14:37','2018-12-07 02:03:09',1,'\0'),(6,'2018-12-06 06:36:53','2018-12-07 01:48:16',1,'\0'),(7,'2018-12-06 08:14:43','2018-12-06 08:14:43',1,''),(8,'2018-12-09 08:15:18','2018-12-09 08:15:18',1,'');
/*!40000 ALTER TABLE `buys` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customers` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `address` varchar(100) DEFAULT NULL,
  `email` varchar(40) DEFAULT NULL,
  `mobile_no` varchar(100) DEFAULT NULL,
  `name` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES (1,'2018-12-06 03:14:59','2018-12-06 03:14:59','Tam Ky','va@gmail.com','03482034234','Nguyen Viet Anh'),(2,'2018-12-06 03:15:24','2018-12-06 03:15:24','Hòa Bình','syan@gmail.com','032842304234','Nguyễn Sỹ An'),(3,'2018-12-06 03:15:50','2018-12-07 02:03:40','France','theo@fr.com','03248238439','Theo'),(4,'2018-12-06 03:16:13','2018-12-06 03:16:51','Hello Steet','v@gg.com','04382934892343','Vương Vật Vờ'),(5,'2018-12-06 03:16:42','2018-12-06 03:16:42','Dũng Street','d@xx.com','023489234234','Dũng Vui vẻ'),(6,'2018-12-06 03:17:16','2018-12-06 03:17:16','Bách Khoa KTX','ttt@gmail.com','034234234545','Tuấn Tiền Tỉ'),(7,'2018-12-06 03:17:56','2018-12-06 03:17:56','Hồ Tiền Street','hhh@gmail.com','0234832840823904','Hải Hống Hách '),(8,'2018-12-06 03:18:27','2018-12-06 03:18:27','Paris City','q@xx.com','0348234354545','Quang QQ'),(10,'2018-12-06 03:19:20','2018-12-06 03:19:20','Ngõ Gốc Đề Minh Khai','vietanh@vietanhdev.com','0348923840982','Việt Anh ĐT'),(11,'2018-12-06 03:20:09','2018-12-06 03:20:09','Quang Trung Street','tds@ddd.com','034584938545','Quang Trung Company'),(12,'2018-12-07 02:50:17','2018-12-07 02:50:17','vietanhdev.com','vietanh@vietanhdev.com','023849023498','VietAnhDev');
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoices`
--

DROP TABLE IF EXISTS `invoices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invoices` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `amount` float DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `purpose` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoices`
--

LOCK TABLES `invoices` WRITE;
/*!40000 ALTER TABLE `invoices` DISABLE KEYS */;
INSERT INTO `invoices` VALUES (1,'2018-12-08 16:25:52','2018-12-08 16:29:34',50000,'Buy 25k for bull dog and 25k for curry dog.','Buy candy for dogs'),(2,'2018-12-10 09:32:21','2018-12-10 09:32:21',500000,'Buy 10 bottles of Lavie for office','Lavie water for office'),(3,'2018-12-10 09:33:03','2018-12-10 09:33:03',5000,'Electricity For November - HD993','Electricity'),(4,'2018-12-10 09:33:21','2018-12-10 09:33:21',9000,'Buy pens for office','Buy Pens');
/*!40000 ALTER TABLE `invoices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `products` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `barcode` varchar(40) DEFAULT NULL,
  `name` varchar(40) DEFAULT NULL,
  `price` float NOT NULL,
  `quantities` float DEFAULT NULL,
  `unit` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'2018-12-05 04:35:53','2018-12-09 07:23:55','jdfnu34f34f4','Sữa Vinamilk 350ml',6000,323,'Pack'),(2,'2018-12-05 04:36:10','2018-12-09 05:56:37','333dff23f','Vinasold Sữa Đậu Nành',12000,37,'Bottle'),(3,'2018-12-06 03:28:04','2018-12-09 08:15:18','5sdf45dfwe','Bàn Phím Dell 1202',120000,236,'Piece'),(4,'2018-12-06 03:29:12','2018-12-09 05:56:37','h23ryh78ff4','Nước Lavie 1L',8000,260,'Bottle'),(5,'2018-12-06 03:30:01','2018-12-09 05:56:37','difojs90ere','Chuột Dell 1201',60000,425,'Piece '),(6,'2018-12-06 03:30:43','2018-12-09 08:15:18','ịdsdfsdf444','Máy tính Casio FX570ES',200000,542,'Piece'),(7,'2018-12-07 02:19:04','2018-12-09 08:15:18','dfj98fj2r828305','Thuốc lá Vinatabe',50000,322,'Pack');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_nb4h0p6txrmfc0xbrd1kglp9t` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'ROLE_ADMIN'),(3,'ROLE_CASHIER'),(2,'ROLE_MANAGER');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sell_items`
--

DROP TABLE IF EXISTS `sell_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sell_items` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `price` float DEFAULT NULL,
  `product_id` bigint(20) DEFAULT NULL,
  `quantities` float DEFAULT NULL,
  `sell_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKlwt8a3anlo177puw0gfpfytqm` (`sell_id`),
  CONSTRAINT `FKlwt8a3anlo177puw0gfpfytqm` FOREIGN KEY (`sell_id`) REFERENCES `sells` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sell_items`
--

LOCK TABLES `sell_items` WRITE;
/*!40000 ALTER TABLE `sell_items` DISABLE KEYS */;
INSERT INTO `sell_items` VALUES (27,'2018-12-09 03:30:57','2018-12-09 03:30:57',8000,4,1,13),(28,'2018-12-09 03:30:57','2018-12-09 03:30:57',120000,3,2,13),(29,'2018-12-09 03:30:57','2018-12-09 03:30:57',6000,1,2,13),(30,'2018-12-09 03:30:57','2018-12-09 03:30:57',12000,2,1,13),(31,'2018-12-09 03:30:57','2018-12-09 03:30:57',60000,5,1,13),(32,'2018-12-09 03:36:36','2018-12-09 03:36:36',12000,2,2,14),(33,'2018-12-09 03:36:36','2018-12-09 03:36:36',60000,5,2,14),(34,'2018-12-09 03:36:36','2018-12-09 03:36:36',120000,3,1,14),(35,'2018-12-09 03:36:36','2018-12-09 03:36:36',8000,4,1,14),(36,'2018-12-09 03:38:46','2018-12-09 03:38:46',50000,7,1,15),(37,'2018-12-09 03:38:46','2018-12-09 03:38:46',200000,6,3,15),(38,'2018-12-09 03:38:46','2018-12-09 03:38:46',120000,3,2,15),(39,'2018-12-09 03:38:46','2018-12-09 03:38:46',60000,5,2,15),(40,'2018-12-09 05:56:37','2018-12-09 05:56:37',50000,7,2,16),(41,'2018-12-09 05:56:37','2018-12-09 05:56:37',8000,4,1,16),(42,'2018-12-09 05:56:37','2018-12-09 05:56:37',12000,2,1,16),(43,'2018-12-09 05:56:37','2018-12-09 05:56:37',60000,5,1,16),(45,'2018-12-09 11:35:21','2018-12-09 11:35:21',6000,1,1,17);
/*!40000 ALTER TABLE `sell_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sells`
--

DROP TABLE IF EXISTS `sells`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sells` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `active` bit(1) DEFAULT NULL,
  `customer_id` bigint(20) DEFAULT NULL,
  `tax` float DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `total` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sells`
--

LOCK TABLES `sells` WRITE;
/*!40000 ALTER TABLE `sells` DISABLE KEYS */;
INSERT INTO `sells` VALUES (13,'2018-12-09 03:30:57','2018-12-09 03:30:57','',NULL,0.08,1,358560),(14,'2018-12-09 03:36:36','2018-12-09 03:36:36','',NULL,0.08,1,293760),(15,'2018-12-09 03:38:46','2018-12-09 03:38:46','',NULL,0.08,1,1090800),(16,'2018-12-09 05:56:37','2018-12-09 05:56:37','',NULL,0.08,1,194400),(17,'2018-12-09 07:23:55','2018-12-09 11:35:21','\0',NULL,0.08,1,6480);
/*!40000 ALTER TABLE `sells` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `suppliers`
--

DROP TABLE IF EXISTS `suppliers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `suppliers` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `address` varchar(100) DEFAULT NULL,
  `email` varchar(40) DEFAULT NULL,
  `mobile_no` varchar(255) DEFAULT NULL,
  `name` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `suppliers`
--

LOCK TABLES `suppliers` WRITE;
/*!40000 ALTER TABLE `suppliers` DISABLE KEYS */;
INSERT INTO `suppliers` VALUES (1,'2018-12-05 04:42:21','2018-12-06 03:09:51','Hai Ha Street','hh@vn.com','0234023525454','Hai Ha Company'),(2,'2018-12-05 04:42:31','2018-12-06 03:10:12','Truong Dinh Street','dd@c.com','023892354543545','Hunadas Co'),(3,'2018-12-05 04:43:48','2018-12-06 03:09:30','Quang Trung Street','helloman@gmail.com','03240923748233','Hello Man'),(4,'2018-12-05 04:43:55','2018-12-06 03:10:42','Hai Ba Trung District','HH@cc.com','0485943543545','Vuong Gia Group'),(5,'2018-12-05 04:44:14','2018-12-06 03:11:19','Hanoi University of Science and Technology','ss.com@cc.com','0458943859435435','Bach Khoa ICT'),(6,'2018-12-06 03:11:43','2018-12-06 03:11:43','Silicon Valley','apache@sis.com','035235352445','Apache'),(7,'2018-12-06 03:12:06','2018-12-06 03:12:06','KTX Bach Khoa','tuan@hust.com','023849234234','Dinh Tuan Co'),(8,'2018-12-06 03:13:03','2018-12-06 03:13:03','Goc De ngo','dung@gmail.com','0238472384','Dung Store'),(9,'2018-12-06 03:13:45','2018-12-06 03:13:45','Le Thanh Nghi Street','bk@gmail.com','02348923434','Bach Khoa Computer'),(10,'2018-12-06 03:22:19','2018-12-06 03:22:19','Silicon Valley','intel@gmail.com','04358435435','Intel Company'),(11,'2018-12-06 03:22:48','2018-12-06 03:22:48','Không Rõ Ở Đâu','ms@vietanh.com','032840234324','Microsoft'),(12,'2018-12-07 02:20:43','2018-12-07 02:20:43','2 Đại Cồ Việt','hh@hongha.com','0238432849','Hồng Hà Co');
/*!40000 ALTER TABLE `suppliers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_roles` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKh8ciramu9cc9q3qcqiv4ue8a6` (`role_id`),
  CONSTRAINT `FKh8ciramu9cc9q3qcqiv4ue8a6` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `FKhfh9dx7w3ubf1co1vdev94g3f` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (1,1),(2,1),(3,3),(4,2);
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `address` varchar(100) DEFAULT NULL,
  `email` varchar(40) DEFAULT NULL,
  `mobile_no` varchar(255) DEFAULT NULL,
  `name` varchar(40) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `salary` bigint(20) DEFAULT NULL,
  `username` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'2018-12-05 02:17:13','2018-12-06 03:23:29','Ngõ Gốc Đề Minh Khai','admin@gmail.com','03482934892384','Việt Anh','$2a$10$cWLQfTbxmrcpHNUTqNzy4ubs2yw2/APxdIwB2C41cw3O.ofIsEnvi',2000000,'admin'),(2,'2018-12-06 03:24:15','2018-12-06 03:24:15','Hòa Bình','an@vietanhdev.com','02384234023434','Sỹ An','$2a$10$p/Vm/OIvEGoAlWYqMlE46e7cDqCyNtwC3DhGZfDB/f3EpDwjqyfU6',1000000,'syan'),(3,'2018-12-06 03:24:46','2018-12-06 03:24:46','Theo Street','theo@gmail.com','032492034838','Theo','$2a$10$ogefSSa6nyWbgvOaz/YUf.cV/ZbknjxgxI0fzBXPCnazzcEH/TnkS',500000,'theo'),(4,'2018-12-06 03:25:47','2018-12-06 03:25:47','Tạ Quang Bửu','keeper@gmail.com','0384032840823','Warehouse keepper','$2a$10$cQ1eiwaWa8eAt0gQBs1qb.lPGH.pg1oAXFKHDbH06bMlbPCgUbvzG',5000000,'keeper');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-12-10 17:45:41
