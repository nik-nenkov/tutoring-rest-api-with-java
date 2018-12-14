package com.epam.training.exception;

public class QuantityExceedsStorageException extends Exception {

  @Override
  public String getMessage() {
    return "Requested quantity should not exceed total stock quantity!";
  }
}
