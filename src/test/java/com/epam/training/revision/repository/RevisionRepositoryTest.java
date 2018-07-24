package com.epam.training.revision.repository;

import com.epam.training.application.DemoApplication;
import com.epam.training.revision.Revision;
import org.junit.Test;
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

//import org.springframework.context.annotation.ComponentScan;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = DemoApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ComponentScan("com.epam")
@Transactional
@Rollback
public class RevisionRepositoryTest {

    @Autowired
    private RevisionRepository revisionRepository;

    @Test
    public void getLastRevisionEntered() {
    }

    @Test
    public void getRevisionsInTimeInterval() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Revision> testData = revisionRepository.getRevisionsInTimeInterval(
                new Timestamp(sdf.parse("1991-01-01").getTime()),
                new Timestamp(new Date().getTime())
        );
        testData.forEach(System.out::println);
    }

    @Test
    public void insertNewRevision() {
    }
}