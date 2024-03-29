package info.nenkov.warehouse.controller;

import info.nenkov.warehouse.exception.StockAlreadyExistsException;
import info.nenkov.warehouse.model.Stock;
import info.nenkov.warehouse.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stock")
@RequiredArgsConstructor
public class StockRestController {
  private final StockService stockService;

  @PostMapping(value = "/new", consumes = "application/json", produces = "application/json")
  public Stock newStock(@RequestBody Stock stockToAdd) throws StockAlreadyExistsException {
    return stockService.createStock(
        stockToAdd.getStockId(), stockToAdd.getPrice(), stockToAdd.getQuantity());
  }

  @PutMapping(value = "/increase", produces = "application/json")
  public Stock increaseStock(
      @RequestParam("stock_id") int stockId, @RequestParam("quantity") int quantity) {
    return stockService.increaseQuantityOfStock(stockId, quantity);
  }

  @GetMapping(value = "/show/{id}", produces = "application/json")
  public Stock showStock(@PathVariable("id") int stockId) {
    return stockService.readStockByStockId(stockId);
  }
}
