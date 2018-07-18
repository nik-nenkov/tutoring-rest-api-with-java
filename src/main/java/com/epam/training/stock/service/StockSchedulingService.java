package com.epam.training.stock.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


@Service
@EnableScheduling
public class StockSchedulingService {

    private final int interval = 60000;
    private static final Logger log = LoggerFactory.getLogger(StockSchedulingService.class);
    private final StockService stockService;

    @Autowired
    public StockSchedulingService(StockService stockService) {
        this.stockService = stockService;
    }

    @PostConstruct
    private void initialize() {
        //TODO тук да заредя от базата позиции ? редовни доставки ?
    }

    @Scheduled(fixedRate = interval)   //  1 minute == 60 000 milliseconds
    private void addSomeStocks() {
        //Някъде ще трябва да задаваме кои стоки и с колко да бъдат увеличавани периодично
        // TODO use a settings file from which to load the stocks and their amounts
        stockService.addQuantityToStock(111, 20);
        log.info("Stock with id=111 was increased by quantity=20");
        stockService.addQuantityToStock(345, 80);
        log.info("Stock with id=345 was increased by quantity=80");
    }

}
