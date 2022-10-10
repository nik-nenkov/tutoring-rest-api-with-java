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

-- Dumping database structure for test_warehouse_db
DROP DATABASE IF EXISTS ` test_warehouse_db `;
CREATE DATABASE IF NOT EXISTS `test_warehouse_db` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;
USE `test_warehouse_db`;

-- Dumping structure for table test_warehouse_db.order
DROP TABLE IF EXISTS ` order `;
CREATE TABLE IF NOT EXISTS ` order `
(
    `order_id`          int(10) unsigned        NOT NULL AUTO_INCREMENT,
    ` stock_id `        int(10) unsigned        NOT NULL,
    ` quantity `        int(10) unsigned        NOT NULL,
    ` price `           decimal(14, 2) unsigned NOT NULL,
    ` order_timestamp ` timestamp               NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`order_id`),
    KEY ` FK_orders_stocks ` (`stock_id`),
    CONSTRAINT ` FK_orders_stocks ` FOREIGN KEY (`stock_id`) REFERENCES ` stock ` (`stock_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 45
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin;

-- Dumping data for table test_warehouse_db.order: ~44 rows (approximately)
/*!40000 ALTER TABLE `order`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `order`
    ENABLE KEYS */;

-- Dumping structure for table test_warehouse_db.revision
DROP TABLE IF EXISTS ` revision `;
CREATE TABLE IF NOT EXISTS ` revision `
(
    `revision_id`        int(10) unsigned        NOT NULL AUTO_INCREMENT,
    ` total_quantities ` int(10) unsigned        NOT NULL,
    ` total_price `      decimal(14, 2) unsigned NOT NULL,
    ` revision_started ` datetime                NOT NULL,
    ` revision_ended `   datetime                NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`revision_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 35
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin;

-- Dumping data for table test_warehouse_db.revision: ~34 rows (approximately)
/*!40000 ALTER TABLE `revision`
    DISABLE KEYS */;
INSERT INTO ` revision ` (` revision_id `, ` total_quantities `, ` total_price `, ` revision_started `, ` revision_ended
                          `)
VALUES (1, 236, 565.20, '2018-07-17 12:24:10', '2018-07-17 12:54:10'),
       (2, 236, 565.20, '2018-07-17 12:24:15', '2018-07-17 12:54:15'),
       (7, 296, 7465.20, '2018-07-17 12:31:54', '2018-07-17 13:01:54'),
       (8, 296, 7465.20, '2018-07-17 12:31:55', '2018-07-17 13:01:55'),
       (34, 296, 7465.20, '2018-07-17 16:49:53', '2018-07-17 17:19:53');
/*!40000 ALTER TABLE `revision`
    ENABLE KEYS */;

-- Dumping structure for table test_warehouse_db.stock
DROP TABLE IF EXISTS ` stock `;
CREATE TABLE IF NOT EXISTS ` stock `
(
    `id`         int(10) unsigned        NOT NULL AUTO_INCREMENT,
    ` stock_id ` int(10) unsigned        NOT NULL,
    ` quantity ` int(10) unsigned        NOT NULL,
    ` price `    decimal(14, 2) unsigned NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY ` stock_id ` (`stock_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 35
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin;

-- Dumping data for table test_warehouse_db.stock: ~0 rows (approximately)
/*!40000 ALTER TABLE `stock`
    DISABLE KEYS */;
INSERT INTO ` stock ` (` id `, ` stock_id `, ` quantity `, ` price `)
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
/*!40000 ALTER TABLE `stock`
    ENABLE KEYS */;

/*!40101 SET SQL_MODE = IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS = IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
