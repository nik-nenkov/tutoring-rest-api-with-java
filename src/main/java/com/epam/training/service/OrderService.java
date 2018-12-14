package com.epam.training.service;

import com.epam.training.dao.OrderRepository;
import com.epam.training.dao.StockRepository;
import com.epam.training.exception.NoSuchStockException;
import com.epam.training.exception.QuantityExceedsStorageException;
import com.epam.training.model.Order;
import com.epam.training.model.Stock;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

  private final OrderRepository orderRepository;
  private final StockRepository stockRepository;

  @Autowired
  public OrderService(OrderRepository orderRepository, StockRepository stockRepository) {
    this.orderRepository = orderRepository;
    this.stockRepository = stockRepository;
  }

  public Order placeNewOrder(int stockId, int quantity)
      throws QuantityExceedsStorageException, NoSuchStockException {
    Stock currentStock;
    currentStock = stockRepository.getStockByStockId(stockId);
    if (currentStock == null) {
      throw new NoSuchStockException(stockId);
    }
    BigDecimal orderPrice = currentStock.getPrice().multiply(BigDecimal.valueOf(quantity));
    int newQuantity = currentStock.getQuantity() - quantity;
    if (newQuantity >= 0) {
      currentStock.setQuantity(newQuantity);
    } else {
      throw new QuantityExceedsStorageException();
    }
    orderRepository.createNewOrder(stockId, quantity, orderPrice);
    stockRepository.updateQuantityById(stockId, newQuantity);
    return orderRepository.getLastOrder();
  }

  public Order getInfoAboutOrder(int orderId) {
    return orderRepository.getOrderById(orderId);
  }
}
