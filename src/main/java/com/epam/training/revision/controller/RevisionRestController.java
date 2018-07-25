package com.epam.training.revision.controller;

import com.epam.training.revision.Revision;
import com.epam.training.revision.service.RevisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/revision")
public class RevisionRestController {

    private final RevisionService revisionService;

    @Autowired
    public RevisionRestController(RevisionService revisionService) {
        this.revisionService = revisionService;
    }

    @GetMapping("/last")
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

    @GetMapping("/interval")
    public Revision makeRevisionByGivenTimeInterval(
            @RequestParam("from") String startDate,
            @RequestParam("to") String endDate) throws ParseException {

        return revisionService.revisionFromToStringYyyyMmDd(startDate, endDate);
    }
}
