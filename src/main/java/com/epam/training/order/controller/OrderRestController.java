package com.epam.training.order.controller;

import com.epam.training.order.Order;
import com.epam.training.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderRestController {

    private final OrderService orderService;

    @Autowired
    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(value = "/{id}",
            produces = "application/json")
    public Order showOrder(
            @PathVariable("id") int orderId) {
        return orderService.getInfoAboutOrder(orderId);
    }

    @PostMapping(value = "/new",
            produces = "application/json")
    public Order createOrder(
            @RequestParam("stock_id") int stockId,
            @RequestParam("quantity") int quantity) {
        return orderService.placeNewOrder(stockId, quantity);
    }
}
