package com.epam.training.stock.controller;

import com.epam.training.stock.Stock;
import com.epam.training.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;

@RestController
@RequestMapping("/stock")
public class StockRestController {

    private final StockService stockService;

    @Autowired
    public StockRestController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping(
            value = "/new",
            consumes = "application/json",
            produces = "application/json")
    public Stock newStock(@RequestBody Stock stockToAdd) throws Exception {
        return stockService.createStock(stockToAdd.getStockId(), stockToAdd.getPrice(), stockToAdd.getQuantity());
    }

    @PutMapping(
            value = "/increase",
            produces = "application/json")
    public Stock increaseStock(
            @RequestParam("stock_id") int stockId,
            @RequestParam("quantity") int quantity) throws InvalidParameterException {
        return stockService.increaseQuantityOfStock(stockId, quantity);
    }

    @GetMapping(
            value = "/show/{id}",
            produces = "application/json")
    public Stock showStock(
            @PathVariable("id") int stockId) {
        return stockService.readStockByStockId(stockId);
    }

}
