package com.epam.training.controller;

import com.epam.training.exception.NoSuchStockException;
import com.epam.training.exception.QuantityExceedsStorageException;
import com.epam.training.model.Order;
import com.epam.training.service.OrderService;
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

  @GetMapping(value = "/show/{id}", produces = "application/json")
  public Order showOrder(@PathVariable("id") int orderId) {
    return orderService.getInfoAboutOrder(orderId);
  }

  @PostMapping(value = "/new", produces = "application/json")
  public Order createOrder(
      @RequestParam("stock_id") int stockId, @RequestParam("quantity") int quantity)
      throws QuantityExceedsStorageException, NoSuchStockException {
    return orderService.placeNewOrder(stockId, quantity);
  }
}
