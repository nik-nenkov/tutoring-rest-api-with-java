package com.epam.training.delivery.service;


import com.epam.training.delivery.Delivery;
import com.epam.training.delivery.repository.DeliveryRepository;
import com.epam.training.stock.repository.StockRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final StockRepository stockRepository;

    public DeliveryService(DeliveryRepository deliveryRepository, StockRepository stockRepository) {
        this.deliveryRepository = deliveryRepository;
        this.stockRepository = stockRepository;
    }

    public Delivery newScheduledDelivery(int stockId, int quantity, Long timeInterval) {
        stockRepository.createStockIfNotExists(stockId);

        deliveryRepository.createNewScheduledDelivery(new Delivery(stockId, quantity, timeInterval));

        return deliveryRepository.lastEnteredDelivery();
    }

    public Delivery newSingleDelivery(int stockId, int quantity, Timestamp deliveryTime) {
        stockRepository.createStockIfNotExists(stockId);

        deliveryRepository.createNewSingleDelivery(new Delivery(stockId, quantity, deliveryTime));

        return deliveryRepository.lastEnteredDelivery();
    }
}
