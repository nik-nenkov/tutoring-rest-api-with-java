package com.epam.training.application;

import com.epam.training.stock.Stock;
import com.epam.training.stock.controller.StockRestController;
import com.epam.training.stock.repository.StockRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTest {

    @Autowired
    StockRepository stockRepository;

    @Autowired
    StockRestController stockRestController;

    @Test
    public void contextLoads() {

    }

    @Test
    public void repositoryIsNotNull() {
        Assertions.assertNotNull(stockRepository);
    }

    @Test
    public void controllerIsNotNull() {
        Assertions.assertNotNull(stockRestController);
    }

    @Test
    public void checkPriceOfExistingStockById() {
        Stock testStock = stockRepository.getStockById(1992);
        Assertions.assertEquals(BigDecimal.valueOf(4.14), testStock.getPrice());
    }
}