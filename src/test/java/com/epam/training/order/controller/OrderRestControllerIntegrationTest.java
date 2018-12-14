package com.epam.training.order.controller;

import com.epam.training.DemoApplication;
import com.epam.training.RepositoryIntegrationTest;
import com.epam.training.dao.StockRepository;
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

public class OrderRestControllerIntegrationTest implements RepositoryIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

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
                .getForObject("http://localhost:" + port + "/order/show/19",
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