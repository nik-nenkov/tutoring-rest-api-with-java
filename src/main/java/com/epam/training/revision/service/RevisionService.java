package com.epam.training.revision.service;

import com.epam.training.order.Order;
import com.epam.training.order.repository.OrderRepository;
import com.epam.training.revision.Revision;
import com.epam.training.revision.repository.RevisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class RevisionService {

    private final RevisionRepository revisionRepository;

    private final OrderRepository orderRepository;

    @Autowired
    public RevisionService(RevisionRepository revisionRepository, OrderRepository orderRepository) {
        this.revisionRepository = revisionRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public List<Revision> revisionsByTimeIntervalBeforeNowMills(long mills) {
        Timestamp startingTime = new Timestamp(new Date().getTime() - mills);
        Timestamp timeNow = new Timestamp(new Date().getTime());
        return revisionRepository.getRevisionsInTimeInterval(startingTime, timeNow);
    }

    @Transactional
    public Revision makeRevisionFromTimeUntilNow(Timestamp startingTime) {


        List<Order> ordersFromLastThirtyMinutes = orderRepository.ordersAfterTimestamp(startingTime);


        Float sumOfPrice = 0F;
        Integer sumQuantity = 0;

        for (Order order : ordersFromLastThirtyMinutes) {
            sumQuantity += order.getQuantity();
            sumOfPrice += order.getPrice();
        }

        revisionRepository.insertNewRevision(sumQuantity, sumOfPrice, startingTime, new Timestamp(System.currentTimeMillis()));

        return revisionRepository.getLastRevisionEntered();
    }

    @Transactional
    public Revision revisionFromTo(String start, String end) throws ParseException {

        DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
        Date startDate = df.parse(start);
        Date endDate = df.parse(end);
        Timestamp startingTime = new Timestamp(startDate.getTime());
        Timestamp endingTime = new Timestamp(endDate.getTime());

        List<Order> orders = orderRepository.ordersBetweenTwoTimestamps(startingTime, endingTime);

        Float sumOfPrice = 0F;
        Integer sumQuantity = 0;

        for (Order order : orders) {
            sumQuantity += order.getQuantity();
            sumOfPrice += order.getPrice();
        }

        revisionRepository.insertNewRevision(sumQuantity, sumOfPrice, startingTime, endingTime);

        return revisionRepository.getLastRevisionEntered();
    }
}
