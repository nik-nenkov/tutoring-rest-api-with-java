package com.epam.training.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.Data;

@Data
public class Stock {

  @JsonProperty("stock_id")
  private int stockId;
  private BigDecimal price;
  private int quantity;

  public Stock(int stockId, BigDecimal price, int quantity) {
    this.stockId = stockId;
    this.price = price.setScale(2, RoundingMode.CEILING);
    this.quantity = quantity;
  }

}
