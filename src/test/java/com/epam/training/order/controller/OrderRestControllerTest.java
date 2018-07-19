package com.epam.training.order.controller;

import com.epam.training.application.DemoApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = DemoApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderRestControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getOrderById() {
        assertThat(this
                .restTemplate
                .getForObject("http://localhost:" + port + "/order?id=8",
                        String.class)
        ).isEqualTo("{\"orderId\":8,\"quantity\":20,\"price\":62.8,\"stock_id\":442,\"order_timestamp\":\"2018-07-16T15:25:00.000+0000\"}");
    }
}