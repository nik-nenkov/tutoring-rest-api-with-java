package com.epam.training.revision.controller;

import com.epam.training.revision.Revision;
import com.epam.training.revision.service.RevisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController

@ComponentScan("com.epam.training.*")
public class RevisionRestController {

    private final RevisionService revisionService;

    @Autowired
    public RevisionRestController(RevisionService revisionService) {
        this.revisionService = revisionService;
    }

    @RequestMapping(value = "/revise_last", method = RequestMethod.GET)
    public Revision reviseLastThirtyMinutes(
            @RequestParam(
                    value = "minutes",
                    required = false,
                    defaultValue = "30") int minutes
    ) {
        int milliseconds = minutes * 30000;
        java.sql.Timestamp startingTime = new java.sql.Timestamp(System.currentTimeMillis() - milliseconds);
        return revisionService.sumOfRevisionsFromTimestamp(startingTime);
    }

    @RequestMapping(value = "/revision", method = RequestMethod.GET)
    public Revision makeRevisionByGivenTimeInterval(
            @RequestParam("from") String startDate,
            @RequestParam("to") String endDate) throws ParseException {

        return revisionService.revisionFromToStringYyyyMmDd(startDate, endDate);
    }
}
