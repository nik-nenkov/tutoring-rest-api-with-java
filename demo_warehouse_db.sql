-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.1.32-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win32
-- HeidiSQL Version:             9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping database structure for demo_warehouse_db
DROP DATABASE IF EXISTS `demo_warehouse_db`;
CREATE DATABASE IF NOT EXISTS `demo_warehouse_db` /*!40100 DEFAULT CHARACTER SET utf8
  COLLATE utf8_bin */;
USE `demo_warehouse_db`;

-- Dumping structure for table demo_warehouse_db.order
DROP TABLE IF EXISTS `order`;
CREATE TABLE IF NOT EXISTS `order` (
  `order_id`        int(10) unsigned        NOT NULL AUTO_INCREMENT,
  `stock_id`        int(10) unsigned        NOT NULL,
  `quantity`        int(10) unsigned        NOT NULL,
  `price`           decimal(14, 2) unsigned NOT NULL,
  `order_timestamp` timestamp               NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`order_id`),
  KEY `FK_orders_stocks` (`stock_id`),
  CONSTRAINT `FK_orders_stocks` FOREIGN KEY (`stock_id`) REFERENCES `stock` (`stock_id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 9
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin;

-- Dumping data for table demo_warehouse_db.order: ~8 rows (approximately)
/*!40000 ALTER TABLE `order`
  DISABLE KEYS */;
INSERT INTO `order` (`order_id`, `stock_id`, `quantity`, `price`, `order_timestamp`) VALUES
  (1, 11111, 56, 0.00, '2018-07-16 17:47:17'),
  (2, 442, 20, 62.80, '2018-07-16 18:23:17'),
  (3, 442, 20, 62.80, '2018-07-16 18:23:53'),
  (4, 442, 20, 62.80, '2018-07-16 18:24:53'),
  (5, 442, 20, 62.80, '2018-07-16 18:24:57'),
  (6, 442, 20, 62.80, '2018-07-16 18:24:58'),
  (7, 442, 20, 62.80, '2018-07-16 18:24:59'),
  (8, 442, 20, 62.80, '2018-07-16 18:25:00');
/*!40000 ALTER TABLE `order`
  ENABLE KEYS */;

-- Dumping structure for table demo_warehouse_db.revision
DROP TABLE IF EXISTS `revision`;
CREATE TABLE IF NOT EXISTS `revision` (
  `revision_id`      int(10) unsigned        NOT NULL AUTO_INCREMENT,
  `total_quantities` int(10) unsigned        NOT NULL,
  `total_price`      decimal(14, 2) unsigned NOT NULL,
  `revision_started` datetime                NOT NULL,
  `revision_ended`   datetime                NOT NULL,
  PRIMARY KEY (`revision_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin;

-- Dumping data for table demo_warehouse_db.revision: ~0 rows (approximately)
/*!40000 ALTER TABLE `revision`
  DISABLE KEYS */;
/*!40000 ALTER TABLE `revision`
  ENABLE KEYS */;

-- Dumping structure for table demo_warehouse_db.stock
DROP TABLE IF EXISTS `stock`;
CREATE TABLE IF NOT EXISTS `stock` (
  `id`       int(10) unsigned        NOT NULL AUTO_INCREMENT,
  `stock_id` int(10) unsigned        NOT NULL,
  `quantity` int(10) unsigned        NOT NULL,
  `price`    decimal(14, 2) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `stock_id` (`stock_id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 28
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin;

-- Dumping data for table demo_warehouse_db.stock: ~15 rows (approximately)
/*!40000 ALTER TABLE `stock`
  DISABLE KEYS */;
INSERT INTO `stock` (`id`, `stock_id`, `quantity`, `price`) VALUES
  (1, 324324, 86, 4.56),
  (3, 11111, 980, 7.86),
  (5, 2445, 980, 7.86),
  (6, 879879, 980, 7.86),
  (7, 5555, 500, 5.55),
  (9, 1000, 500, 5.55),
  (10, 6666, 600, 6.60),
  (11, 999, 0, 6.60),
  (14, 111, 11300, 6.60),
  (16, 442, 50, 3.14),
  (18, 111442, 50, 3.14),
  (19, 345, 14970, 131.11),
  (21, 888, 80, 0.15),
  (22, 222, 80, 0.15),
  (27, 768, 80, 0.15);
/*!40000 ALTER TABLE `stock`
  ENABLE KEYS */;

/*!40101 SET SQL_MODE = IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS = IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
