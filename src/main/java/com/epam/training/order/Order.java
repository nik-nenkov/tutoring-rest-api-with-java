package com.epam.training.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class Order {
    @JsonPropertyOrder("order_id")
    private Integer orderId;
    @JsonProperty("stock_id")
    private int stockId;
    private int quantity;
    private float price;
    @JsonProperty("order_timestamp")
    private Timestamp timestamp;

    public Order(Integer orderId, int stockId, int quantity, float orderPrice, Timestamp timestamp) {
        setOrderId(orderId);
        setStockId(stockId);
        setQuantity(quantity);
        setPrice(orderPrice);
        setTimestamp(timestamp);
    }

//    public Order(int stockId, int quantity, float orderPrice) {
//        this(null, stockId, quantity, orderPrice, null);
//    }

}
