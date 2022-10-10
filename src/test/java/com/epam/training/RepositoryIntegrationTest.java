package com.epam.training;

import org.springframework.jdbc.core.JdbcTemplate;

public interface RepositoryIntegrationTest {

  default void clearTestDatabase(JdbcTemplate jdbcTemplate) {
    jdbcTemplate.execute("drop database if exists test_warehouse_db");
    jdbcTemplate.execute("create database if not exists test_warehouse_db");
    jdbcTemplate.execute("use `test_warehouse_db`");
    jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS=0");
    jdbcTemplate.execute(
        "CREATE TABLE `stock` ("
            + "`id` int(10) unsigned NOT NULL AUTO_INCREMENT,"
            + "`stock_id` int(10) unsigned NOT NULL,"
            + "`quantity` int(10) unsigned NOT NULL,"
            + "`price` decimal(14,2) unsigned NOT NULL,"
            + "PRIMARY KEY (`id`),"
            + "UNIQUE KEY `stock_id` (`stock_id`)"
            + ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COLLATE=utf8_bin");
    jdbcTemplate.execute(
        "CREATE TABLE `order` ("
            + "`order_id` int(10) unsigned NOT NULL AUTO_INCREMENT,"
            + "`stock_id` int(10) unsigned NOT NULL,"
            + "`quantity` int(10) unsigned NOT NULL,"
            + "`price` decimal(14,2) unsigned NOT NULL,"
            + "`order_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,"
            + "PRIMARY KEY (`order_id`),"
            + "KEY `FK_orders_stocks` (`stock_id`),"
            + "CONSTRAINT `FK_orders_stocks` FOREIGN KEY (`stock_id`) REFERENCES `stock` (`stock_id`)"
            + ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COLLATE=utf8_bin");
    jdbcTemplate.execute(
        "CREATE TABLE `revision` ("
            + "`revision_id` int(10) unsigned NOT NULL AUTO_INCREMENT,"
            + "`total_quantities` int(10) unsigned NOT NULL,"
            + "`total_price` decimal(14,2) unsigned NOT NULL,"
            + "`revision_started` datetime NOT NULL,"
            + "`revision_ended` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,"
            + "PRIMARY KEY (`revision_id`)"
            + ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;");
    jdbcTemplate.execute(
        "CREATE TABLE `delivery` ("
            + "  `id` int(11) NOT NULL AUTO_INCREMENT,"
            + "  `stock_id` int(11) NOT NULL,"
            + "  `quantity` int(11) DEFAULT NULL,"
            + "  `first_date` timestamp NULL DEFAULT NULL,"
            + "  `scheduled` bit(1) DEFAULT NULL,"
            + "  `time_interval` int(11) DEFAULT NULL,"
            + "  PRIMARY KEY (`id`)"
            + ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;");
    jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS=1");
  }
}
