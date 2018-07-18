package com.epam.training.stock.controller;

import com.epam.training.stock.Stock;
import com.epam.training.stock.repository.StockRepository;
import com.epam.training.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;


@RestController

public class StockRestController {

    private final StockRepository stockRepository;
    private final StockService stockService;

    @Autowired
    public StockRestController(StockRepository stockRepository, StockService stockService) {
        this.stockRepository = stockRepository;
        this.stockService = stockService;

    }

    @RequestMapping(
            value = "/new_stock",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    public Stock newStock(@RequestBody Stock stockToAdd) {
        stockRepository.insertNewStock(stockToAdd.getSockId(), stockToAdd.getPrice(), stockToAdd.getQuantity());
        return stockRepository.getStockById(stockToAdd.getSockId());
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
