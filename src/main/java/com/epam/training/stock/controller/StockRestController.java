package com.epam.training.stock.controller;

import com.epam.training.stock.Stock;
import com.epam.training.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;

@RestController
public class StockRestController {


    private final StockService stockService;

    @Autowired
    public StockRestController(StockService stockService) {
        this.stockService = stockService;
    }

    @RequestMapping(
            value = "/new_stock",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    public Stock newStock(@RequestBody Stock stockToAdd) {
        return stockService.createStock(stockToAdd.getSockId(), stockToAdd.getPrice(), stockToAdd.getQuantity());
    }

    @RequestMapping(
            value = "/add_stock",
            method = RequestMethod.PUT,
            produces = "application/json")
    public Stock increaseStock(
            @RequestParam("stock_id") int stockId,
            @RequestParam("quantity") int quantity) throws InvalidParameterException {
        return stockService.increaseQuantityOfStock(stockId, quantity);
    }

    @RequestMapping(
            value = "/stock",
            method = RequestMethod.GET,
            produces = "application/json")
    public Stock showStock(
            @RequestParam("id") int stockId) {
        return stockService.readStock(stockId);
    }

}
