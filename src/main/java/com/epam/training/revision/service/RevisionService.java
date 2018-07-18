package com.epam.training.revision.service;

import com.epam.training.revision.Revision;
import com.epam.training.revision.repository.RevisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class RevisionService {

    private final RevisionRepository revisionRepository;

    @Autowired
    public RevisionService(RevisionRepository revisionRepository) {
        this.revisionRepository = revisionRepository;
    }

    @Transactional
    public List<Revision> revisionsByTimeIntervalBeforeNowMills(long mills) {
        Timestamp startingTime = new Timestamp(new Date().getTime() - mills);
        Timestamp timeNow = new Timestamp(new Date().getTime());
        return revisionRepository.getRevisionsInTimeInterval(startingTime, timeNow);
    }
}
