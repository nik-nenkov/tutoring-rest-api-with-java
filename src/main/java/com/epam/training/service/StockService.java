package com.epam.training.service;

import com.epam.training.config.MyThread;
import com.epam.training.dao.StockRepository;
import com.epam.training.exception.StockAlreadyExistsException;
import com.epam.training.model.Stock;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class StockService {

  private final StockRepository stockRepository;

  private final TaskExecutor taskExecutor;

  @Autowired
  public StockService(StockRepository stockRepository, TaskExecutor taskExecutor) {
    this.stockRepository = stockRepository;
    this.taskExecutor = taskExecutor;
  }

  @Transactional
  public Stock increaseQuantityOfStock(final int stockId, final int quantity) {
    final Stock stockToUpdate = stockRepository.getStockByStockId(stockId);

    int newQuantity = stockToUpdate.getQuantity() + quantity;
    int id = stockRepository.updateQuantityById(stockId, newQuantity);

    return stockRepository.getStockById(id);
  }

  public Stock createStock(int stockId, BigDecimal price, int quantity)
      throws StockAlreadyExistsException {
    if (stockRepository.getStockByStockId(stockId) != null) {
      throw new StockAlreadyExistsException(stockId);
    }
    int newStockId = stockRepository.insertNewStock(stockId, price, quantity);
    return readStock(newStockId);
  }

  private Stock readStock(int id) {
    return stockRepository.getStockById(id);
  }

  public Stock readStockByStockId(int stockId) {

    MyThread myThread = new MyThread();
    taskExecutor.execute(myThread);
    return stockRepository.getStockByStockId(stockId);
  }
}
