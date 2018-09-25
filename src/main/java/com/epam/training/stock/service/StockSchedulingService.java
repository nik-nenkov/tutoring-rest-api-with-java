package com.epam.training.stock.service;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Log4j
@EnableScheduling
public class StockSchedulingService {

    private static final int INTERVAL = 60000;
    private final StockService stockService;

    @Autowired
    public StockSchedulingService(StockService stockService) {
        this.stockService = stockService;
    }

    @Scheduled(fixedRate = INTERVAL)   //  1 minute == 60 000 milliseconds
    private void addSomeStocks() {
        // to maybe do : use a settings file from which to load the stocks and their amounts
        stockService.increaseQuantityOfStock(111, 20);
        log.info("Stock with id=111 was increased by quantity=20");
        stockService.increaseQuantityOfStock(345, 80);
        log.info("Stock with id=345 was increased by quantity=80");
    }

}
