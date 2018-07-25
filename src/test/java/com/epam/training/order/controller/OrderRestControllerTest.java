package com.epam.training.order.controller;

import com.epam.training.DemoApplication;
import com.epam.training.stock.repository.StockRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = DemoApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ComponentScan("com.epam")

public class OrderRestControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void clearTestDatabase(JdbcTemplate jdbcTemplate) {
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

    @Before
    public void prepare() {
        stockRepository.insertNewStock(8864408, BigDecimal.valueOf(5.5), 500);
//        orderRepository.createNewOrder(8864408, 20, BigDecimal.valueOf(110));
        jdbcTemplate.execute("INSERT INTO `stock` (`id`,`stock_id`,`quantity`,`price`) " +
                "VALUES (16,442,500,1.25);");
        jdbcTemplate.execute("INSERT INTO `order` (`order_id`,`stock_id`,`quantity`,`price`,`order_timestamp`) " +
                "VALUES (19,442,20,10.05,'2018-07-19 14:00:00');");
    }

    @Test
    public void getOrderById() {
        assertThat(this
                .restTemplate
                .getForObject("http://localhost:" + port + "/order/19",
                        String.class)
        ).isEqualTo("{\"orderId\":19," +
                "\"quantity\":20," +
                "\"price\":10.05," +
                "\"stock_id\":442," +
                "\"order_timestamp\":\"2018-07-19T11:00:00.000+0000\"}");
    }

    @Test
    public void createNewOrder() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("quantity", "10");
        map.add("stock_id", "8864408");
//        Timestamp timeNow = new Timestamp((new Date().getTime()/1000)*1000);
        assertThat(this
                .restTemplate
                .postForObject("http://localhost:" + port + "/order/new", map, String.class)
        ).contains("{" +
                "\"orderId\":20," +
                "\"quantity\":10," +
                "\"price\":55.00," +
                "\"stock_id\":8864408," +
                "\"order_timestamp\":"); //TODO : this fucks with timestamp precision
    }

    @Test
    public void failToCreateNewOrderOfNonExistingStock() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("quantity", "10");
        map.add("stock_id", "999999999");
        assertThat(this
                .restTemplate
                .postForObject("http://localhost:" + port + "/order/new", map, String.class)
        ).contains("Could not find s stock with id=999999999"); //TODO : this fucks with timestamp precision
    }

    @Test
    public void failToCreateNewOrderOfExceedingQuantity() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("quantity", "100000000");
        map.add("stock_id", "8864408");
        assertThat(this
                .restTemplate
                .postForObject("http://localhost:" + port + "/order/new", map, String.class)
        ).contains("Requested quantity should not exceed total stock quantity!");
    }

    @After
    public void clear() {
        clearTestDatabase(jdbcTemplate);
    }
}