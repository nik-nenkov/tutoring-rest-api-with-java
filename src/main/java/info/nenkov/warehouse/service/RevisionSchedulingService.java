package info.nenkov.warehouse.service;

import info.nenkov.warehouse.model.Revision;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@EnableScheduling
@Log4j
public class RevisionSchedulingService {

  private final RevisionService revisionService;

  @Autowired
  public RevisionSchedulingService(RevisionService revisionService) {
    this.revisionService = revisionService;
  }

  @Scheduled(fixedRate = 180000) // 30 minutes == 1 800 000 milliseconds
  private void makeComputationAndLogResult() {

    Revision fromLastHalfHour =
        revisionService.revisionFromToTimestamp(
            new Timestamp(System.currentTimeMillis() - 1800000),
            new Timestamp(System.currentTimeMillis()));

    final String sfm =
        String.format(
            "%n%n total_quantity:%d%n total_price:%f%n",
            fromLastHalfHour.getTotalQuantities(), fromLastHalfHour.getTotalPrice());
    // suspicious line separator
    log.info(sfm);
  }
}
