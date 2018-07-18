package com.epam.training.revision.service;

import com.epam.training.revision.Revision;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableScheduling
public class RevisionSchedulingService {

    private static final Logger log = LoggerFactory.getLogger(RevisionSchedulingService.class);
    private final RevisionService revisionService;

    @Autowired
    public RevisionSchedulingService(RevisionService revisionService) {
        this.revisionService = revisionService;
    }

    @Scheduled(fixedRate = 1800000) // 30 minutes == 1 800 000 milliseconds
    private void makeComputationAndLogResult() {
        List<Revision> revisionsFromLastThirtyMinutes = revisionService.revisionsByTimeIntervalBeforeNowMills(1800000);
        int totalQuantity = 0;
        float totalPrice = 0;
        for (Revision r : revisionsFromLastThirtyMinutes) {
            totalPrice += r.getTotalPrice();
            totalQuantity += r.getTotalQuantities();
        }
        log.info("{\nnumber_of_revisions:" + revisionsFromLastThirtyMinutes.size() +
                ",\ntotal_quantity:" + totalQuantity +
                ",\ntotal_price:" + totalPrice);
    }
}
