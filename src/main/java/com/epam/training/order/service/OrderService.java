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
        //Търсим в базата стока с посоченият ID номер:
        Stock currentStock = stockRepository.getStockById(stockId);
        //В случай че не е намерена такава стока приключваме с подходящо съобщение:
        if (currentStock == null) throw new InvalidParameterException() {
            @Override
            public String getMessage() {
                return "Could not find s stock with id=" + stockId;
            }
        };
        //Правим изчисления за цена на поръчката и промяна на количеството в склада:
        BigDecimal orderPrice = currentStock.getPrice().multiply(BigDecimal.valueOf(quantity));
        int newQuantity = currentStock.getQuantity() - quantity;
        //Променяме количеството на стоката или извеждаме съобщение за грешка ако то е отрицателно:
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
        //След проверки и изчисления, можем да създадем новата поръчка:
        orderRepository.createNewOrder(stockId, quantity, orderPrice);
        //Променяме количеството на стоката в базата данни:
        stockRepository.updateQuantityById(stockId, newQuantity);
        //Ако искаме да изведем цялата информация за новата поръчка правим последно търсене в базата
        //където обекта е получил уникален ID номер и TIMESTAMP на въвеждането си:
        return orderRepository.getLastOrder();
    }

    public Order getInfoAboutOrder(int orderId) {
        return orderRepository.getOrderById(orderId);
    }
}
