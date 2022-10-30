package info.nenkov.warehouse.exception;

public class NoSuchStockException extends Exception {

  private final int stockId;

  public NoSuchStockException(int stockId) {
    this.stockId = stockId;
  }

  @Override
  public String getMessage() {
    return "Could not find s stock with id=" + stockId;
  }
}
