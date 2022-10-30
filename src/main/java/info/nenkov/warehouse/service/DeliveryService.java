package info.nenkov.warehouse.service;

import info.nenkov.warehouse.dao.DeliveryRepository;
import info.nenkov.warehouse.dao.StockRepository;
import info.nenkov.warehouse.model.Delivery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Service
public class DeliveryService {

  private final DeliveryRepository deliveryRepository;
  private final StockRepository stockRepository;

  public DeliveryService(DeliveryRepository deliveryRepository, StockRepository stockRepository) {
    this.deliveryRepository = deliveryRepository;
    this.stockRepository = stockRepository;
  }

  @Transactional
  public Delivery newScheduledDelivery(int stockId, int quantity, Long timeInterval) {
    stockRepository.createStockIfNotExists(stockId);
    int k =
        deliveryRepository.createNewScheduledDelivery(
            new Delivery(stockId, quantity, timeInterval));
    return deliveryRepository.getDeliveryById(k);
  }

  @Transactional
  public Delivery newSingleDelivery(int stockId, int quantity, Timestamp deliveryTime) {
    stockRepository.createStockIfNotExists(stockId);
    int k =
        deliveryRepository.createNewSingleDelivery(new Delivery(stockId, quantity, deliveryTime));
    return deliveryRepository.getDeliveryById(k);
  }
}
