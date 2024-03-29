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
DROP DATABASE IF EXISTS warehouse_db;
CREATE DATABASE IF NOT EXISTS warehouse_db /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;
USE `demo_warehouse_db`;

-- Dumping structure for table demo_warehouse_db.order
DROP TABLE IF EXISTS orders;
CREATE TABLE IF NOT EXISTS orders
(
    `order_id`        int(10) unsigned        NOT NULL AUTO_INCREMENT,
    `stock_id`        int(10) unsigned        NOT NULL,
    `quantity`        int(10) unsigned        NOT NULL,
    `price`           decimal(14, 2) unsigned NOT NULL,
    `order_timestamp` timestamp               NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`order_id`),
    KEY `FK_orders_stocks` (`stock_id`),
    CONSTRAINT `FK_orders_stocks` FOREIGN KEY (`stock_id`) REFERENCES `stock` (`stock_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 45
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin;

-- Dumping data for table demo_warehouse_db.order: ~39 rows (approximately)
/*!40000 ALTER TABLE `order`
    DISABLE KEYS */;
INSERT INTO `
order`(`order_id`, `stock_id`, `quantity`, `price`, `order_timestamp`)
VALUES (1, 11111, 56, 0.00, '2018-07-16 17:47:17'),
       (2, 442, 20, 62.80, '2018-07-16 18:23:17'),
       (3, 442, 20, 62.80, '2018-07-16 18:23:53'),
       (4, 442, 20, 62.80, '2018-07-16 18:24:53'),
       (5, 442, 20, 62.80, '2018-07-16 18:24:57'),
       (6, 442, 20, 62.80, '2018-07-16 18:24:58'),
       (7, 442, 20, 62.80, '2018-07-16 18:24:59'),
       (8, 442, 20, 62.80, '2018-07-16 18:25:00'),
       (9, 442, 20, 62.80, '2018-07-17 12:41:01'),
       (10, 442, 20, 62.80, '2018-07-17 12:41:03'),
       (11, 1768, 20, 2300.00, '2018-07-17 13:01:46'),
       (12, 1768, 20, 2300.00, '2018-07-17 13:01:48'),
       (13, 1768, 20, 2300.00, '2018-07-17 13:01:49'),
       (14, 345, 2000, 262220.00, '2018-07-17 17:29:28'),
       (15, 345, 2000, 262220.00, '2018-07-17 17:29:33'),
       (16, 345, 2000, 262220.00, '2018-07-17 17:29:34'),
       (17, 345, 2000, 262220.00, '2018-07-17 17:29:38'),
       (18, 345, 2000, 262220.00, '2018-07-18 12:02:50'),
       (19, 345, 2000, 262220.00, '2018-07-18 12:07:25'),
       (20, 345, 2000, 262220.00, '2018-07-18 12:08:29'),
       (21, 345, 2000, 262220.00, '2018-07-18 12:10:14'),
       (22, 345, 2000, 262220.00, '2018-07-18 12:14:21'),
       (23, 345, 2000, 262220.00, '2018-07-18 12:16:09'),
       (24, 345, 2000, 262220.00, '2018-07-18 12:16:15'),
       (25, 345, 2000, 262220.00, '2018-07-18 12:16:17'),
       (26, 345, 2000, 262220.00, '2018-07-18 12:16:18'),
       (27, 345, 2000, 262220.00, '2018-07-18 12:16:18'),
       (28, 345, 2000, 262220.00, '2018-07-18 12:16:19'),
       (29, 345, 2000, 262220.00, '2018-07-18 12:16:19'),
       (30, 345, 2000, 262220.00, '2018-07-18 12:16:20'),
       (31, 345, 2000, 262220.00, '2018-07-18 12:16:21'),
       (32, 345, 2000, 262220.00, '2018-07-18 12:16:21'),
       (33, 345, 2000, 262220.00, '2018-07-18 12:16:22'),
       (34, 345, 2000, 262220.00, '2018-07-18 12:16:22'),
       (35, 345, 2000, 262220.00, '2018-07-18 12:16:23'),
       (36, 345, 2000, 262220.00, '2018-07-18 12:16:23'),
       (37, 345, 2000, 262220.00, '2018-07-18 12:16:24'),
       (38, 345, 2000, 262220.00, '2018-07-18 12:16:24'),
       (39, 345, 2000, 262220.00, '2018-07-18 12:16:25'),
       (40, 345, 20000, 2622200.00, '2018-07-18 12:16:54'),
       (41, 345, 20000, 2622200.00, '2018-07-18 12:19:10'),
       (42, 11111111, 20, 2300.00, '2018-07-18 14:17:48'),
       (43, 11111111, 20, 2300.00, '2018-07-18 14:18:06'),
       (44, 11111111, 20, 2300.00, '2018-07-18 14:18:08');
/*!40000 ALTER TABLE `order`
    ENABLE KEYS */;

-- Dumping structure for table demo_warehouse_db.revision
DROP TABLE IF EXISTS `revision`;
CREATE TABLE IF NOT EXISTS `revision`
(
    `revision_id`      int(10) unsigned        NOT NULL AUTO_INCREMENT,
    `total_quantities` int(10) unsigned        NOT NULL,
    `total_price`      decimal(14, 2) unsigned NOT NULL,
    `revision_started` datetime                NOT NULL,
    `revision_ended`   datetime                NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`revision_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 35
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin;

-- Dumping data for table demo_warehouse_db.revision: ~31 rows (approximately)
/*!40000 ALTER TABLE `revision`
    DISABLE KEYS */;
INSERT INTO `revision`(`revision_id`, `total_quantities`, `total_price`, `revision_started`, `revision_ended
                         `)
VALUES (1, 236, 565.20, '2018-07-17 12:24:10', '2018-07-17 12:54:10'),
       (2, 236, 565.20, '2018-07-17 12:24:15', '2018-07-17 12:54:15'),
       (3, 236, 565.20, '2018-07-17 12:24:16', '2018-07-17 12:54:16'),
       (4, 236, 565.20, '2018-07-17 12:24:17', '2018-07-17 12:54:17'),
       (5, 236, 565.20, '2018-07-17 12:24:18', '2018-07-17 12:54:18'),
       (6, 236, 565.20, '2018-07-17 12:30:50', '2018-07-17 13:00:50'),
       (7, 296, 7465.20, '2018-07-17 12:31:54', '2018-07-17 13:01:54'),
       (8, 296, 7465.20, '2018-07-17 12:31:55', '2018-07-17 13:01:55'),
       (9, 296, 7465.20, '2018-07-17 13:00:18', '2018-07-17 13:30:18'),
       (10, 296, 7465.20, '2018-07-17 13:00:45', '2018-07-17 13:30:45'),
       (11, 296, 7465.20, '2018-07-17 13:02:14', '2018-07-17 13:32:15'),
       (12, 296, 7465.20, '2018-07-17 13:15:25', '2018-07-17 13:45:25'),
       (13, 296, 7465.20, '2018-07-17 13:30:42', '2018-07-17 14:00:42'),
       (14, 296, 7465.20, '2018-07-17 13:34:10', '2018-07-17 14:04:10'),
       (15, 296, 7465.20, '2018-07-17 13:35:36', '2018-07-17 14:05:36'),
       (16, 296, 7465.20, '2018-07-17 13:40:25', '2018-07-17 14:10:25'),
       (17, 296, 7465.20, '2018-07-17 13:45:30', '2018-07-17 14:15:30'),
       (18, 296, 7465.20, '2018-07-17 13:56:17', '2018-07-17 14:26:18'),
       (19, 296, 7465.20, '2018-07-17 13:56:18', '2018-07-17 14:26:18'),
       (20, 296, 7465.20, '2018-07-17 14:04:55', '2018-07-17 14:34:55'),
       (21, 296, 7465.20, '2018-07-17 14:15:29', '2018-07-17 14:45:30'),
       (22, 296, 7465.20, '2018-07-17 14:45:29', '2018-07-17 15:15:29'),
       (23, 296, 7465.20, '2018-07-17 15:47:42', '2018-07-17 16:17:42'),
       (24, 296, 7465.20, '2018-07-17 15:58:31', '2018-07-17 16:28:32'),
       (25, 296, 7465.20, '2018-07-17 15:58:47', '2018-07-17 16:28:47'),
       (26, 296, 7465.20, '2018-07-17 15:58:48', '2018-07-17 16:28:48'),
       (27, 296, 7465.20, '2018-07-17 16:00:25', '2018-07-17 16:30:25'),
       (28, 296, 7465.20, '2018-07-17 16:01:17', '2018-07-17 16:31:17'),
       (29, 296, 7465.20, '2018-07-17 16:02:01', '2018-07-17 16:32:02'),
       (30, 296, 7465.20, '2018-07-17 16:08:55', '2018-07-17 16:38:56'),
       (31, 296, 7465.20, '2018-07-17 16:09:39', '2018-07-17 16:39:39'),
       (32, 296, 7465.20, '2018-07-17 16:19:02', '2018-07-17 16:49:02'),
       (33, 296, 7465.20, '2018-07-17 16:19:53', '2018-07-17 16:49:53'),
       (34, 296, 7465.20, '2018-07-17 16:49:53', '2018-07-17 17:19:53');
/*!40000 ALTER TABLE `revision`
    ENABLE KEYS */;

-- Dumping structure for table demo_warehouse_db.stock
DROP TABLE IF EXISTS `stock`;
CREATE TABLE IF NOT EXISTS `stock`
(
    `id`       int(10) unsigned        NOT NULL AUTO_INCREMENT,
    `stock_id` int(10) unsigned        NOT NULL,
    `quantity` int(10) unsigned        NOT NULL,
    `price`    decimal(14, 2) unsigned NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `stock_id` (`stock_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 35
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin;

-- Dumping data for table demo_warehouse_db.stock: ~20 rows (approximately)
/*!40000 ALTER TABLE `stock`
    DISABLE KEYS */;
INSERT INTO `stock`(`id`, `stock_id`, `quantity`, `price`)
VALUES (1, 324324, 86, 4.56),
       (3, 11111, 980, 7.86),
       (5, 2445, 980, 7.86),
       (6, 879879, 980, 7.86),
       (7, 5555, 500, 5.55),
       (9, 1000, 500, 5.55),
       (10, 6666, 600, 6.60),
       (11, 999, 0, 6.60),
       (14, 111, 20560, 6.60),
       (16, 442, 10, 3.14),
       (18, 111442, 50, 3.14),
       (19, 345, 23850, 131.11),
       (21, 888, 80, 0.15),
       (22, 222, 80, 0.15),
       (27, 768, 80, 0.15),
       (29, 1768, 120, 115.00),
       (31, 565, 180, 115.00),
       (32, 11111111, 120, 115.00),
       (33, 1991, 35, 4.14),
       (34, 1992, 35, 4.14);
/*!40000 ALTER TABLE `stock`
    ENABLE KEYS */;

/*!40101 SET SQL_MODE = IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS = IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
