package com.epam.training.exception;

public class StockAlreadyExistsException extends CouldNotCreateNewStock {
    public StockAlreadyExistsException(long stockId) {
        super(stockId);
    }

    @Override
    public String getMessage() {
        return "Stock with id=" + this.getStockId() + " already exists!";
    }
}
