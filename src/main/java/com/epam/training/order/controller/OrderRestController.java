package com.epam.training.order.controller;

import com.epam.training.order.Order;
import com.epam.training.order.repository.OrderRepository;
import com.epam.training.stock.Stock;
import com.epam.training.stock.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.InvalidParameterException;

@RestController

@ComponentScan("com.epam.training.*")
public class OrderRestController {
    private final OrderRepository orderRepository;
    private final StockRepository stockRepository;

    @Autowired
    public OrderRestController(OrderRepository orderRepository, StockRepository stockRepository) {
        this.orderRepository = orderRepository;
        this.stockRepository = stockRepository;
    }

    @RequestMapping(value = "/new_order",
            produces = "application/json")
    public Order createOrder(
            @RequestParam("stock_id") int stockId,
            @RequestParam("quantity") int quantity) {

        //Търсим в базата стока с посоченият ID номер:
        Stock currentStock = stockRepository.getStockById(stockId);

        //В случай че не е намерена такава стока приключваме с подходящо съобщение:
        if (currentStock == null) throw new InvalidParameterException() {
            @Override
            public String getMessage() {
                return "Could not find s stock with id=" + stockId;
            }
        };

        //Правим изчисления са цена на поръчката и промяна на количеството в склада:
        float orderPrice = currentStock.getPrice() * quantity;
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
        //Съхраняваме поръчката в базата данни:
        orderRepository.createNewOrder(stockId, quantity, orderPrice);

        //Променяме количеството на стоката в базата данни:
        stockRepository.updateQuantityById(stockId, newQuantity);

        //Ако искаме да изведем цялата информация за новата поръчка правим последно търсене в базата
        //където обекта е полъчил уникален ID номер и TIMESTAMP на въвеждането си:
        return orderRepository.getLastOrder();
    }
}
