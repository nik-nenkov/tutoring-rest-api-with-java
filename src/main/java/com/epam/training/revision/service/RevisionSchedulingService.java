package com.epam.training.revision.service;

import com.epam.training.revision.Revision;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

//import com.epam.training.revision.repository.RevisionRepository;
//import java.sql.Timestamp;

@Service
@EnableScheduling
public class RevisionSchedulingService {

    private static final Logger log = LoggerFactory.getLogger(RevisionSchedulingService.class);
    private final RevisionService revisionService;
//    private final RevisionRepository revisionRepository;

    @Autowired
    public RevisionSchedulingService(RevisionService revisionService) {
        this.revisionService = revisionService;
//        this.revisionRepository = revisionRepository;
    }

    @Scheduled(fixedRate = 180000) // 30 minutes == 1 800 000 milliseconds
    private void makeComputationAndLogResult() {

        //TODO this should return a single Revision of all Orders in the last 30 minutes !!!

        List<Revision> revisionsFromLastThirtyMinutes = revisionService.revisionsByTimeIntervalBeforeNowMills(1800000);
        int totalQuantity = 0;
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (Revision r : revisionsFromLastThirtyMinutes) {
            totalPrice = totalPrice.add(r.getTotalPrice());
            totalQuantity += r.getTotalQuantities();
        }

        final String sfm = String.format(
                "\n{\nnumber_of_revisions:%d,\ntotal_quantity:%d,\ntotal_price:%f\n}",
                revisionsFromLastThirtyMinutes.size(),
                totalQuantity,
                totalPrice);

        log.info(sfm);
    }
}
