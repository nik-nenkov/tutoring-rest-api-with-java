package com.epam.training.revision.controller;

import com.epam.training.order.Order;
import com.epam.training.order.repository.OrderRepository;
import com.epam.training.revision.Revision;
import com.epam.training.revision.repository.RevisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

@ComponentScan("com.epam.training.*")
public class RevisionRestController {

    private final OrderRepository orderRepository;
    private final RevisionRepository revisionRepository;

    @Autowired
    public RevisionRestController(
            OrderRepository orderRepository,
            RevisionRepository revisionRepository) {
        this.orderRepository = orderRepository;
        this.revisionRepository = revisionRepository;
    }

    @RequestMapping("/revise")
    public Revision reviseLastThirtyMinutes() {

        java.sql.Timestamp startingTime = new java.sql.Timestamp(System.currentTimeMillis() - 1800000);

        Float sumOfPrice = 0F;
        Integer sumQuantity = 0;

        List<Order> ordersFromLastThirtyMinutes = orderRepository.ordersAfterTimestamp(startingTime);


        for (Order order : ordersFromLastThirtyMinutes) {
            sumQuantity += order.getQuantity();
            sumOfPrice += order.getPrice();
        }

        revisionRepository.insertNewRevision(sumQuantity, sumOfPrice, startingTime);

        return revisionRepository.getLastRevisionEntered();
    }
}
