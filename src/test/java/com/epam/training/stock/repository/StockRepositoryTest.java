package com.epam.training.stock.repository;

import com.epam.training.BasicRepositoryTest;
import com.epam.training.stock.Stock;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class StockRepositoryTest extends BasicRepositoryTest {


    @Autowired
    private StockRepository stockRepository;

    private List<Stock> stocks = new ArrayList<>();

    @Before
    public void createStocksForTesting() {
        stockRepository.insertNewStock(43, BigDecimal.valueOf(14.30), 156);
        stocks.add(new Stock(43, BigDecimal.valueOf(14.30), 156));
        stocks.add(new Stock(22, BigDecimal.valueOf(15.60), 133));
        stocks.add(new Stock(43, BigDecimal.valueOf(14.3), 956));
    }

    @Test
    public void getStockById() {
        Stock s1 = stockRepository.getStockById(43);
        assertThat(s1).isEqualTo(stocks.get(0));
    }

    @Test
    public void insertNewStock() {
//        try {
        stockRepository.insertNewStock(22, BigDecimal.valueOf(15.6), 133);
//        } finally {
        assertThat(stockRepository.getLastInsertedStock()).isEqualTo(stocks.get(1));
//        }
    }

    @Test
    public void updateQuantityById() {
//        try {
        stockRepository.updateQuantityById(43, 956);
//        } finally {
        Stock s2 = stockRepository.getStockById(43);
        assertThat(s2).isEqualTo(stocks.get(2));
//        }
    }
}