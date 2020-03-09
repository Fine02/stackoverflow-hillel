package com.ra.course.com.stackoverflow.service.bounty;

import com.ra.course.com.stackoverflow.entity.Bounty;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.exception.repository.QuestionRepositoryException;

public interface BountyService {
    Bounty addBounty(final Question question, final Bounty bounty) throws QuestionRepositoryException;
}