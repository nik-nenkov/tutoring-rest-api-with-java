package com.epam.training.dao;

import com.epam.training.DemoApplication;
import com.epam.training.model.Revision;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(
    classes = DemoApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@Rollback
public class RevisionRepositoryTest {

  @Autowired private RevisionRepository revisionRepository;

  @Test
  public void getRevisionsInTimeInterval() throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    List<Revision> testData =
        revisionRepository.getRevisionsInTimeInterval(
            new Timestamp(sdf.parse("1991-01-01").getTime()), new Timestamp(new Date().getTime()));
    testData.forEach(Assertions::assertNotNull);
  }
}
