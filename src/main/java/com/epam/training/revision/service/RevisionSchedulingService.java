package com.epam.training.revision.service;

import com.epam.training.revision.Revision;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@EnableScheduling
public class RevisionSchedulingService {

    private static final Logger log = LoggerFactory.getLogger(RevisionSchedulingService.class);
    private final RevisionService revisionService;

    @Autowired
    public RevisionSchedulingService(RevisionService revisionService) {
        this.revisionService = revisionService;
    }

    @Scheduled(fixedRate = 180000) // 30 minutes == 1 800 000 milliseconds
    private void makeComputationAndLogResult() {

        Revision fromLastHalfHour = revisionService.revisionFromToTimestamp(
                new Timestamp(System.currentTimeMillis() - 1800000),
                new Timestamp(System.currentTimeMillis())
        );

        final String sfm = String.format(
                "\n\ntotal_quantity:%d\ntotal_price:%f\n",
                fromLastHalfHour.getTotalQuantities(),
                fromLastHalfHour.getTotalPrice());

        log.info(sfm);
    }
}
