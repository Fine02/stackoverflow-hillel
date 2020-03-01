package com.ra.course.com.stackoverflow.repository.interfaces;

import com.ra.course.com.stackoverflow.entity.Bounty;

public interface BountyRepository {
    Bounty findByQuestionId(long id);
}
