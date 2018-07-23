package com.epam.training;

import com.epam.training.application.DemoApplication;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@Transactional
//@Rollback
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ComponentScan("com.epam")
public abstract class BasicRepositoryTest {


    @Autowired
    protected static JdbcTemplate jdbcTemplate;

    @BeforeAll
    public static void prepareTestDatabase() {
        jdbcTemplate.execute("drop database if exists test_warehouse_db");
        jdbcTemplate.execute("create database if not exists test_warehouse_db");
        jdbcTemplate.execute("use `test_warehouse_db`");
        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS=0");
        jdbcTemplate.execute("CREATE TABLE `stock` (" +
                "`id` int(10) unsigned NOT NULL AUTO_INCREMENT," +
                "`stock_id` int(10) unsigned NOT NULL," +
                "`quantity` int(10) unsigned NOT NULL," +
                "`price` decimal(14,2) unsigned NOT NULL," +
                "PRIMARY KEY (`id`)," +
                "UNIQUE KEY `stock_id` (`stock_id`)" +
                ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COLLATE=utf8_bin");
        jdbcTemplate.execute("CREATE TABLE `order` (" +
                "`order_id` int(10) unsigned NOT NULL AUTO_INCREMENT," +
                "`stock_id` int(10) unsigned NOT NULL," +
                "`quantity` int(10) unsigned NOT NULL," +
                "`price` decimal(14,2) unsigned NOT NULL," +
                "`order_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP," +
                "PRIMARY KEY (`order_id`)," +
                "KEY `FK_orders_stocks` (`stock_id`)," +
                "CONSTRAINT `FK_orders_stocks` FOREIGN KEY (`stock_id`) REFERENCES `stock` (`stock_id`)" +
                ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COLLATE=utf8_bin");
        jdbcTemplate.execute("CREATE TABLE `revision` (" +
                "`revision_id` int(10) unsigned NOT NULL AUTO_INCREMENT," +
                "`total_quantities` int(10) unsigned NOT NULL," +
                "`total_price` decimal(14,2) unsigned NOT NULL," +
                "`revision_started` datetime NOT NULL," +
                "`revision_ended` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP," +
                "PRIMARY KEY (`revision_id`)" +
                ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;");
        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS=1");
    }

    @Test
    public void dummyTest() {
        //do nothing
    }
}
