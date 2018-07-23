package com.epam.training.order.repository;


import com.epam.training.application.DemoApplication;
import com.epam.training.order.Order;
import com.epam.training.order.controller.OrderRestControllerTest;
import com.epam.training.stock.Stock;
import com.epam.training.stock.repository.StockRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = DemoApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ComponentScan("com.epam")
@Transactional
@Rollback
public class OrderRepositoryTest {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Before
    public void prepareTestDatabase() {
        OrderRestControllerTest.clearTestDatabase(jdbcTemplate);
    }

    @Test
    @Rollback
    public void addSomeEntries() {
        jdbcTemplate.execute("INSERT INTO `test_warehouse_db`.`stock` (`stock_id`, `quantity`, `price`) VALUES ('442', '40', '3.4');");
        jdbcTemplate.execute("INSERT INTO `test_warehouse_db`.`stock` (`stock_id`, `quantity`, `price`) VALUES ('3232', '50', '7.8');");

//        stockRepository.insertNewStock(22, BigDecimal.valueOf(15.6), 133);
        assertThat(stockRepository.getLastInsertedStock()).isEqualTo(new Stock(3232, BigDecimal.valueOf(7.8), 50));
    }

//    @Autowired
//    private OrderRepository orderRepository;
//
//    @Test
//    public void getAnyOrderById() throws ParseException {
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:m:s");
//        Date date = dateFormat.parse("2018-07-19 14:00:00.0");
//        long time = date.getTime();
//
//        Assertions.assertEquals(
//                new Order(8, 442, 20, 10.05F, new Timestamp(time)),
//                orderRepository.getOrderById(8));
//    }

    @Test
    @Rollback
    public void getLastOrdersLimitFifty() {

        stockRepository.insertNewStock(111, BigDecimal.valueOf(0.5), 125);
        orderRepository.createNewOrder(111, 11, BigDecimal.valueOf(5.5));

        stockRepository.insertNewStock(3232, BigDecimal.valueOf(8.5), 20);
        orderRepository.createNewOrder(3232, 10, BigDecimal.valueOf(85));

        stockRepository.insertNewStock(484, BigDecimal.valueOf(1.5), 80);
        orderRepository.createNewOrder(484, 40, BigDecimal.valueOf(60));

        List<Order> result = orderRepository.getLastOrdersLimitFifty();


        Assertions.assertEquals(
                new Order(2, 3232, 10, 85F, new Timestamp((new Date().getTime() / 1000) * 1000)),
                result.get(1));
    }

    @Test
    @Rollback
    public void createNewOrder() {
        stockRepository.insertNewStock(3232, BigDecimal.valueOf(8.5), 20);
        orderRepository.createNewOrder(
                3232,
                10,
                BigDecimal.valueOf(85)
        );
        Assertions.assertEquals(
                new Order(1, 3232, 10, 85F, new Timestamp((new Date().getTime() / 1000) * 1000)),
                orderRepository.getLastOrder());
    }

    @Test
    @Rollback
    public void getOrdersAfterSpecificTimestamp() throws ParseException {
        jdbcTemplate.execute("INSERT INTO `test_warehouse_db`.`stock` (`stock_id`, `quantity`, `price`) VALUES ('442', '40', '3.4');");
        jdbcTemplate.execute("INSERT INTO `test_warehouse_db`.`stock` (`stock_id`, `quantity`, `price`) VALUES ('3232', '50', '7.8');");
        jdbcTemplate.execute("insert into `test_warehouse_db`.`order` " +
                "(stock_id, quantity, price, order_timestamp) VALUES " +
                "(442,10,3.4,'1999-12-03 16:46:30')," +
                "(3232,10,34.5,'2000-12-03 16:46:30')," +
                "(442,20,13.4,'2007-12-03 16:46:30')," +
                "(3232,20,134.5,'2013-12-03 16:46:30')");
        Timestamp specifiedTime = new Timestamp(
                new SimpleDateFormat("yyyy-MM-dd")
                        .parse("2003-12-02")
                        .getTime()
        );

        List<Order> result = orderRepository.ordersAfterTimestamp(specifiedTime);

        StringBuilder resultToString = new StringBuilder();
        result.forEach(x -> resultToString.append(x.toString().concat("\n")));

        Assertions.assertEquals("Order(orderId=3, stockId=442, quantity=20, price=13.4, timestamp=2007-12-03 16:46:30.0)\n" +
                "Order(orderId=4, stockId=3232, quantity=20, price=134.5, timestamp=2013-12-03 16:46:30.0)\n", resultToString.toString());
    }

    @Test
    @Rollback
    public void getOrdersBetweenTwoSpecifiedTimestamps() throws ParseException {
        jdbcTemplate.execute("INSERT INTO `test_warehouse_db`.`stock` (`stock_id`, `quantity`, `price`) VALUES ('442', '40', '3.4');");
        jdbcTemplate.execute("INSERT INTO `test_warehouse_db`.`stock` (`stock_id`, `quantity`, `price`) VALUES ('3232', '50', '7.8');");
        jdbcTemplate.execute("insert into `test_warehouse_db`.`order` " +
                "(stock_id, quantity, price, order_timestamp) VALUES " +
                "(442,10,3.4,'1999-12-03 16:46:30')," +
                "(3232,10,34.5,'2000-12-03 16:46:30')," +
                "(442,20,13.4,'2007-12-03 16:46:30')," +
                "(3232,20,134.5,'2013-12-03 16:46:30')");
        Timestamp specifiedTimeStart = new Timestamp(
                new SimpleDateFormat("yyyy-MM-dd")
                        .parse("2000-12-02")
                        .getTime()
        );
        Timestamp specifiedTimeEnd = new Timestamp(
                new SimpleDateFormat("yyyy-MM-dd")
                        .parse("2010-12-02")
                        .getTime()
        );
        List<Order> result = orderRepository.ordersBetweenTwoTimestamps(specifiedTimeStart, specifiedTimeEnd);

        StringBuilder resultToString = new StringBuilder();
        result.forEach(x -> resultToString.append(x.toString().concat("\n")));

        Assertions.assertEquals("Order(orderId=2, stockId=3232, quantity=10, price=34.5, timestamp=2000-12-03 16:46:30.0)\n" +
                "Order(orderId=3, stockId=442, quantity=20, price=13.4, timestamp=2007-12-03 16:46:30.0)\n", resultToString.toString());
    }


}