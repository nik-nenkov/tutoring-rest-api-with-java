package info.nenkov.warehouse.exception;

public class QuantityExceedsStorageException extends Exception {

  @Override
  public String getMessage() {
    return "Requested quantity should not exceed total stock quantity!";
  }
}
