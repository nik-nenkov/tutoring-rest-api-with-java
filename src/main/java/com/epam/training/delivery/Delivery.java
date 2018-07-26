package com.epam.training.delivery;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class Delivery {
    private Integer id;
    private int stockId;
    private int quantity;
    private Timestamp date;
    private boolean scheduled;
    private Long timeInterval;

    public Delivery(int stockId, int quantity, long timeInterval) {
        this(null, stockId, quantity, null, true, timeInterval);
    }

    public Delivery(int id, int stockId, int quantity, long timeInterval) {
        this(id, stockId, quantity, null, true, timeInterval);
    }

    public Delivery(int stockId, int quantity, Timestamp firstDate) {
        this(null, stockId, quantity, firstDate, false, null);
    }

    public Delivery(int id, int stockId, int quantity, Timestamp firstDate) {
        this(id, stockId, quantity, firstDate, false, 0L);
    }
}
