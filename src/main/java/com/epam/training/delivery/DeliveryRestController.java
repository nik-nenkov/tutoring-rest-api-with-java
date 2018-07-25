package com.epam.training.delivery;


import com.epam.training.delivery.service.DeliveryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

@RestController
public class DeliveryRestController {

    private final DeliveryService deliveryService;

    public DeliveryRestController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @RequestMapping(value = "/new_delivery", method = RequestMethod.POST)
    public Delivery addNewDelivery(
            @RequestParam int stockId,
            @RequestParam int quantity,
            @RequestParam boolean scheduled,
            @RequestParam Long timeInterval,
            @RequestParam Timestamp deliveryTime
    ) {
        if (scheduled) {
            return deliveryService.newScheduledDelivery(stockId, quantity, timeInterval); //TODO добавяне на начална дата?
        } else {
            return deliveryService.newSingleDelivery(stockId, quantity, deliveryTime);
        }
    }
}
