package com.epam.training.stock.service;

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
        final Stock stockToUpdate = stockRepository.getStockByStockId(stockId);

        int newQuantity = stockToUpdate.getQuantity() + quantity;
        int id = stockRepository.updateQuantityById(stockId, newQuantity);

        return stockRepository.getStockById(id);
    }

    public Stock createStock(int stockId, BigDecimal price, int quantity) throws Exception {
        if (stockRepository.getStockByStockId(stockId) != null) {
            throw new Exception() {
                @Override
                public String getMessage() {
                    return "Stock with stockId=" + stockId + " already exists!";
                }
            };
        }
        int newStockId = stockRepository.insertNewStock(stockId, price, quantity);
        return readStock(newStockId);
    }

    private Stock readStock(int id) {
        return stockRepository.getStockById(id);
    }

    public Stock readStockByStockId(int stockId) {
        return stockRepository.getStockByStockId(stockId);
    }
}
