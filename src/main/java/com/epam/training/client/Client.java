package com.epam.training.client;

import com.epam.training.order.Order;
import com.epam.training.stock.Stock;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
abstract class Client {
    private final Long clientId;
    private final String name;
    private String email;
    private Long phoneNumber;
    private List<Order> orders;
    private List<Stock> favoriteStocks;
    private Sex sex;
    private Ethnicity ethnicity;
}
