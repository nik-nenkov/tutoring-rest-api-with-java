package com.epam.training.service;


import com.epam.training.dao.DeliveryRepository;
import com.epam.training.dao.StockRepository;
import com.epam.training.model.Delivery;
import java.sql.Timestamp;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    int k = deliveryRepository
        .createNewScheduledDelivery(new Delivery(stockId, quantity, timeInterval));
    return deliveryRepository.getDeliveryById(k);
  }


  @Transactional
  public Delivery newSingleDelivery(int stockId, int quantity, Timestamp deliveryTime) {
    stockRepository.createStockIfNotExists(stockId);
    int k = deliveryRepository
        .createNewSingleDelivery(new Delivery(stockId, quantity, deliveryTime));
    return deliveryRepository.getDeliveryById(k);
  }
}
