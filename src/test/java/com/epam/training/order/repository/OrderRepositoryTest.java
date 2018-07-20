package com.epam.training.order.repository;

import com.epam.training.BasicRepositoryTest;
import com.epam.training.order.Order;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderRepositoryTest extends BasicRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;


    @Test
    public void getLastOrder() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:m:s");
        Date date = dateFormat.parse("2018-07-17 17:29:28");
        long time = date.getTime();

        Assertions.assertEquals(
                new Order(4, 442, 20, 62.8F, new Timestamp(time)),
                orderRepository.getOrderById(4));
    }

    @Test
    public void ordersAfterTimestamp() {
    }

    @Test
    public void createNewOrder() {

    }
}