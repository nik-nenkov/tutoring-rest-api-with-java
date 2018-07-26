package com.epam.training.exception;


import lombok.Data;

@Data
public class CouldNotCreateNewStock extends Exception {
    private final long stockId;

    public CouldNotCreateNewStock(long stockId) {
        this.stockId = stockId;
    }

    @Override
    public String getMessage() {
        return "Could not create new stock with id=" + stockId;
    }
}
