package info.nenkov.warehouse.controller;

import info.nenkov.warehouse.model.Delivery;
import info.nenkov.warehouse.service.DeliveryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

@RestController
@RequestMapping("/delivery")
public class DeliveryRestController {

  private final DeliveryService deliveryService;

  public DeliveryRestController(DeliveryService deliveryService) {
    this.deliveryService = deliveryService;
  }

  @PostMapping("/new")
  public Delivery addNewDelivery(
      @RequestParam(value = "stock_id") int stockId,
      @RequestParam int quantity,
      @RequestParam(required = false, defaultValue = "false") boolean scheduled,
      @RequestParam(value = "time_interval", required = false, defaultValue = "0")
          Long timeInterval,
      @RequestParam(value = "delivery_time") Timestamp deliveryTime) {
    if (scheduled) {
      return deliveryService.newScheduledDelivery(stockId, quantity, timeInterval);
    } else {
      return deliveryService.newSingleDelivery(stockId, quantity, deliveryTime);
    }
  }
}
