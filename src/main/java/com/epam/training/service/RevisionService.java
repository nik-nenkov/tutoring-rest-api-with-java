package com.epam.training.service;

import com.epam.training.dao.OrderRepository;
import com.epam.training.dao.RevisionRepository;
import com.epam.training.model.Order;
import com.epam.training.model.Revision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
  public Revision sumOfRevisionsFromTimestamp(Timestamp startingTime) {

    Timestamp timeNow = new Timestamp(System.currentTimeMillis());

    List<Revision> revisionsInTimeInterval =
        revisionRepository.getRevisionsInTimeInterval(startingTime, timeNow);

    BigDecimal sumOfPrice = BigDecimal.ZERO;
    Integer sumQuantity = 0;

    for (Revision r : revisionsInTimeInterval) {
      sumQuantity += r.getTotalQuantities();
      sumOfPrice = sumOfPrice.add(r.getTotalPrice());
    }

    revisionRepository.insertNewRevision(sumQuantity, sumOfPrice, startingTime, timeNow);

    return revisionRepository.getLastRevisionEntered();
  }

  @Transactional
  public Revision revisionFromToStringYyyyMmDd(String start, String end) throws ParseException {

    DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
    Date startDate = df.parse(start);
    Date endDate = df.parse(end);
    Timestamp startingTime = new Timestamp(startDate.getTime());
    Timestamp endingTime = new Timestamp(endDate.getTime());

    return revisionFromToTimestamp(startingTime, endingTime);
  }

  @Transactional
  public Revision revisionFromToTimestamp(Timestamp start, Timestamp end) {

    List<Order> orders = orderRepository.ordersBetweenTwoTimestamps(start, end);

    BigDecimal sumOfPrice = BigDecimal.ZERO;
    Integer sumQuantity = 0;

    for (Order order : orders) {
      sumQuantity += order.getQuantity();
      sumOfPrice = sumOfPrice.add(order.getPrice());
    }

    revisionRepository.insertNewRevision(sumQuantity, sumOfPrice, start, end);

    return revisionRepository.getLastRevisionEntered();
  }
}
