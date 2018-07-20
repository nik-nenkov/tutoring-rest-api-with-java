package com.epam.training.stock;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
public class Stock {
    @JsonProperty("stock_id")
    private int sockId;
    private BigDecimal price;
    private int quantity;

    public Stock(int sockId, BigDecimal price, int quantity) {
        this.sockId = sockId;
        this.price = price.setScale(2, RoundingMode.CEILING);
        this.quantity = quantity;
    }

}
