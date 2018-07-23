package com.epam.training.order.controller;

import com.epam.training.order.Order;
import com.epam.training.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ComponentScan("com.epam.training.*")
public class OrderRestController {


    private final OrderService orderService;

    @Autowired
    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping(value = "/order",
            produces = "application/json",
            method = RequestMethod.GET)
    public Order showOrder(
            @RequestParam("id") int orderId) {
        return orderService.getInfoAboutOrder(orderId);
    }

    @RequestMapping(value = "/new_order",
            produces = "application/json",
            method = RequestMethod.POST)
    public Order createOrder(
            @RequestParam("stock_id") int stockId,
            @RequestParam("quantity") int quantity) {
        return orderService.placeNewOrder(stockId, quantity);
    }
}
