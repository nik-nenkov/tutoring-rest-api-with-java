package com.epam.training.application;

import com.epam.training.stock.Stock;
import com.epam.training.stock.controller.StockRestController;
import com.epam.training.stock.repository.StockRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DemoApplicationTest {

    @Autowired
    StockRepository stockRepository;

    @Autowired
    StockRestController stockRestController;

//    @Test
//    public void main() {
//        DemoApplication.main(new String[]{});
//    }

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
        Assertions.assertEquals(new Stock(999, BigDecimal.valueOf(6.6), 0), stockRestController.showStock(999));
    }

    @Test
    public void checkPriceOfExistingStockById() {
        Stock testStock = stockRepository.getStockById(1992);
        Assertions.assertEquals(BigDecimal.valueOf(4.14), testStock.getPrice());
    }
}