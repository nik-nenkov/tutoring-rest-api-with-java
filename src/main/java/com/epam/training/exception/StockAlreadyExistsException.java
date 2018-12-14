package com.epam.training.exception;

public class StockAlreadyExistsException extends Exception {

  private final int stockId;

  public StockAlreadyExistsException(int stockId) {
    this.stockId = stockId;
  }

  @Override
  public String getMessage() {
    return "Stock with stockId=" + stockId + " already exists!";
  }
}
