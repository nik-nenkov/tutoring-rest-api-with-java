package com.epam.training.stock.service;


import com.epam.training.stock.Stock;
import com.epam.training.stock.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class StockService {

    private final StockRepository stockRepository;

    @Autowired
    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }


    @Transactional
    public void addQuantityToStock(final int stockId, final int quantity) {
        final Stock stockToUpdate = stockRepository.getStockById(stockId);

        int newQuantity = stockToUpdate.getQuantity() + quantity;

        stockRepository.updateStockQuantity(stockId, newQuantity);
    }
}
