package com.epam.training.order.service;

import com.epam.training.order.Order;
import com.epam.training.order.repository.OrderRepository;
import com.epam.training.stock.Stock;
import com.epam.training.stock.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.InvalidParameterException;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final StockRepository stockRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, StockRepository stockRepository) {
        this.orderRepository = orderRepository;
        this.stockRepository = stockRepository;
    }

    public Order placeNewOrder(int stockId, int quantity) {
        Stock currentStock;
        currentStock = stockRepository.getStockByStockId(stockId);
        if (currentStock == null) throw new InvalidParameterException() {
            @Override
            public String getMessage() {
                return "Could not find s stock with id=" + stockId;
            }
        };
        BigDecimal orderPrice = currentStock.getPrice().multiply(BigDecimal.valueOf(quantity));
        int newQuantity = currentStock.getQuantity() - quantity;
        if (newQuantity >= 0) {
            currentStock.setQuantity(newQuantity);
        } else {
            throw new InvalidParameterException() {
                @Override
                public String getMessage() {
                    return "Requested quantity should not exceed total stock quantity!";
                }
            };
        }
        orderRepository.createNewOrder(stockId, quantity, orderPrice);
        stockRepository.updateQuantityById(stockId, newQuantity);
        return orderRepository.getLastOrder();
    }

    public Order getInfoAboutOrder(int orderId) {
        return orderRepository.getOrderById(orderId);
    }
}
