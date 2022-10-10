-- --------------------------------------------------------
-- Host:                         localhost
-- Server version:               8.0.27 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.1.0.6537
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;


-- Dumping database structure for warehouse_db
DROP DATABASE IF EXISTS `warehouse_db`;
CREATE DATABASE warehouse_db;
USE `warehouse_db`;

-- Dumping structure for table warehouse_db.order
DROP TABLE IF EXISTS `order`;
CREATE TABLE IF NOT EXISTS `order`
(
    `order_id`        int unsigned            NOT NULL AUTO_INCREMENT,
    `stock_id`        int unsigned            NOT NULL,
    `quantity`        int unsigned            NOT NULL,
    `price`           decimal(14, 2) unsigned NOT NULL,
    `order_timestamp` timestamp               NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`order_id`),
    KEY `FK_orders_stocks` (`stock_id`),
    CONSTRAINT `FK_orders_stocks` FOREIGN KEY (`stock_id`) REFERENCES `stock` (`stock_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 45
  DEFAULT CHARSET = utf8mb3
  COLLATE = utf8_bin;

-- Dumping data for table warehouse_db.order: ~0 rows (approximately)
DELETE
FROM `order`;

-- Dumping structure for table warehouse_db.revision
DROP TABLE IF EXISTS `revision`;
CREATE TABLE IF NOT EXISTS `revision`
(
    `revision_id`      int unsigned            NOT NULL AUTO_INCREMENT,
    `total_quantities` int unsigned            NOT NULL,
    `total_price`      decimal(14, 2) unsigned NOT NULL,
    `revision_started` datetime                NOT NULL,
    `revision_ended`   datetime                NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`revision_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 35
  DEFAULT CHARSET = utf8mb3
  COLLATE = utf8_bin;

-- Dumping data for table warehouse_db.revision: ~5 rows (approximately)
DELETE
FROM `revision`;
INSERT INTO `revision` (`revision_id`, `total_quantities`, `total_price`, `revision_started`, `revision_ended`)
VALUES (1, 236, 565.20, '2018-07-17 12:24:10', '2018-07-17 12:54:10'),
       (2, 236, 565.20, '2018-07-17 12:24:15', '2018-07-17 12:54:15'),
       (7, 296, 7465.20, '2018-07-17 12:31:54', '2018-07-17 13:01:54'),
       (8, 296, 7465.20, '2018-07-17 12:31:55', '2018-07-17 13:01:55'),
       (34, 296, 7465.20, '2018-07-17 16:49:53', '2018-07-17 17:19:53');

-- Dumping structure for table warehouse_db.stock
DROP TABLE IF EXISTS `stock`;
CREATE TABLE IF NOT EXISTS `stock`
(
    `id`       int unsigned            NOT NULL AUTO_INCREMENT,
    `stock_id` int unsigned            NOT NULL,
    `quantity` int unsigned            NOT NULL,
    `price`    decimal(14, 2) unsigned NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `stock_id` (`stock_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 35
  DEFAULT CHARSET = utf8mb3
  COLLATE = utf8_bin;

-- Dumping data for table warehouse_db.stock: ~12 rows (approximately)
DELETE
FROM `stock`;
INSERT INTO `stock` (`id`, `stock_id`, `quantity`, `price`)
VALUES (1, 324324, 86, 4.56),
       (3, 11111, 980, 7.86),
       (9, 1000, 500, 5.55),
       (11, 999, 0, 6.60),
       (14, 111, 20580, 6.60),
       (16, 442, 10, 3.14),
       (19, 345, 23930, 131.11),
       (21, 888, 80, 0.15),
       (29, 1768, 120, 115.00),
       (31, 565, 180, 115.00),
       (32, 11111111, 120, 115.00),
       (33, 1991, 35, 4.14);

/*!40103 SET TIME_ZONE = IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE = IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS = IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES = IFNULL(@OLD_SQL_NOTES, 1) */;
