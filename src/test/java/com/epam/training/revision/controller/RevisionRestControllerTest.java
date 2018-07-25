package com.epam.training.revision.controller;

import com.epam.training.DemoApplication;
import com.epam.training.order.controller.OrderRestControllerTest;
import com.epam.training.order.repository.OrderRepository;
import com.epam.training.revision.repository.RevisionRepository;
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

import java.math.BigDecimal;
import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = DemoApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ComponentScan("com.epam")

public class RevisionRestControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RevisionRepository revisionRepository;


    @Before
    public void setupStocksAndOrdersForRevision() {

        stockRepository.insertNewStock(701, BigDecimal.valueOf(6.5), 200);
        stockRepository.insertNewStock(801, BigDecimal.valueOf(7.4), 300);
        stockRepository.insertNewStock(901, BigDecimal.valueOf(8.3), 400);

        orderRepository.createNewOrder(701, 20, BigDecimal.valueOf(130));
        orderRepository.createNewOrder(701, 50, BigDecimal.valueOf(325));
        orderRepository.createNewOrder(801, 20, BigDecimal.valueOf(148));
        orderRepository.createNewOrder(901, 50, BigDecimal.valueOf(415));

        revisionRepository.insertNewRevision(900,
                BigDecimal.valueOf(546.45),
                new Timestamp(System.currentTimeMillis() - 45000),
                new Timestamp(System.currentTimeMillis())
        );

        revisionRepository.insertNewRevision(100,
                BigDecimal.valueOf(53.55),
                new Timestamp(System.currentTimeMillis() - 333000),
                new Timestamp(System.currentTimeMillis())
        );

        revisionRepository.insertNewRevision(400,
                BigDecimal.valueOf(86.3),
                new Timestamp(System.currentTimeMillis() - 150000),
                new Timestamp(System.currentTimeMillis())
        );
    }

    @Test
    public void createNewRevisionWithTimeInterval() {
        assertThat(this
                .restTemplate
                .getForObject("http://localhost:" + port + "/revision/interval?from=2012-01-01&to=2020-01-01",
                        String.class)
        ).isEqualTo("{" +
                "\"revisionId\":4," +
                "\"total_quantities\":140," +
                "\"total_price\":1018.00," +
                "\"revision_started\":\"2011-12-31T22:01:00.000+0000\"," +
                "\"revision_ended\":\"2019-12-31T22:01:00.000+0000\"" +
                "}");
    }

    @Test
    public void checkTheSumOfAllRevisionsInTheLastThirtyMinutes() {
        assertThat(
                this.restTemplate
                        .getForObject("http://localhost:" + port + "revision/last",
                                String.class)
        ).contains("{\"revisionId\":4," +
                "\"total_quantities\":1400," +
                "\"total_price\":686.30," +
                "\"revision_started\":");
    }

    @After
    public void clearDatabase() {
        OrderRestControllerTest.clearTestDatabase(jdbcTemplate);
    }
}