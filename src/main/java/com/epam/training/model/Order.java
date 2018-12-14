package com.epam.training.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import lombok.Data;

@Data
public class Order {

  @JsonPropertyOrder("order_id")
  private Integer orderId;
  @JsonProperty("stock_id")
  private int stockId;
  private int quantity;
  private BigDecimal price;
  @JsonProperty("order_timestamp")
  private Timestamp timestamp;

  public Order(Integer orderId, int stockId, int quantity, BigDecimal orderPrice,
      Timestamp timestamp) {
    setOrderId(orderId);
    setStockId(stockId);
    setQuantity(quantity);
    setPrice(orderPrice.setScale(2, RoundingMode.CEILING));
    setTimestamp(timestamp);
  }
}
