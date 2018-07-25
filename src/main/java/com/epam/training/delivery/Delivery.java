package com.epam.training.delivery;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Delivery {
    private Integer id;
    private int stockId;
    private int quantity;
    private Timestamp date;
    private boolean scheduled;
    private Long timeInterval;

    public Delivery(Integer deliveryId, int stockId, int quantity, Timestamp date, boolean scheduled, Long timeInterval) {
        this.id = deliveryId;
        this.stockId = stockId;
        this.quantity = quantity;
        this.date = date;
        this.scheduled = scheduled;
        this.timeInterval = timeInterval;
    }

    public Delivery(int stockId, int quantity, long timeInterval) {
        this(null, stockId, quantity, null, true, timeInterval);
    }

    public Delivery(int stockId, int quantity, Timestamp firstDate) {
        this(null, stockId, quantity, firstDate, false, null);
    }
}
