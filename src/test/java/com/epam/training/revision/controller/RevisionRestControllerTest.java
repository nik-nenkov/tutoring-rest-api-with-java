package com.epam.training.revision.controller;

import com.epam.training.application.DemoApplication;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(
        classes = DemoApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RevisionRestControllerTest {
    @Test
    public void createNewRevisionWithTimeInterval() {

    }
}