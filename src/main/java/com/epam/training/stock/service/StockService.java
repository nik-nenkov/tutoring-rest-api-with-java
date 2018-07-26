package com.epam.training.stock.service;


import com.epam.training.exception.CouldNotCreateNewStock;
import com.epam.training.exception.StockAlreadyExistsException;
import com.epam.training.stock.Stock;
import com.epam.training.stock.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;


@Service
public class StockService {

    private final StockRepository stockRepository;

    @Autowired
    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Transactional
    public Stock increaseQuantityOfStock(final int stockId, final int quantity) {

        final Stock stockToUpdate = readStock(stockId);
        int newQuantity = stockToUpdate.getQuantity() + quantity;

        stockRepository.updateQuantityById(stockId, newQuantity);
        return readStock(stockId);
    }

    public Stock createStock(int id, BigDecimal price, int quantity) throws CouldNotCreateNewStock {
        if (stockRepository.getStockById(id) != null) {
            throw new StockAlreadyExistsException(id);
        }
        stockRepository.insertNewStock(id, price, quantity);
        return readStock(id);
    }

    public Stock readStock(int id) {
        return stockRepository.getStockById(id);
    }
}
