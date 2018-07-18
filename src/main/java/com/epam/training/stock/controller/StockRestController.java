package com.epam.training.stock.controller;

import com.epam.training.stock.Stock;
import com.epam.training.stock.repository.StockRepository;
import com.epam.training.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;


@RestController

public class StockRestController {

    private final JdbcTemplate jdbcTemplate;
    private final StockRepository stockRepository;
    private final StockService stockService;


    @Autowired
    public StockRestController(JdbcTemplate jdbcTemplate,
                               StockRepository stockRepository,
                               StockService stockService) {
        this.jdbcTemplate = jdbcTemplate;
        this.stockRepository = stockRepository;
        this.stockService = stockService;

    }


    @RequestMapping(
            value = "/new_stock",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    public Stock newStock(@RequestBody Stock stockToAdd) {
        jdbcTemplate.update(
                "INSERT INTO stock(stock_id, price, quantity) VALUES (?, ?, ?)",
                stockToAdd.getSockId(), stockToAdd.getPrice(), stockToAdd.getQuantity());
        return stockToAdd;
    }

    @RequestMapping(
            value = "/add_stock",
            method = RequestMethod.PUT,
            produces = "application/json")
    public Stock increaseStock(
            @RequestParam("stock_id") int stockId,
            @RequestParam("quantity") int quantity) throws InvalidParameterException {
        stockService.addQuantityToStock(stockId, quantity);
        return stockRepository.getStockById(stockId);
    }


}
