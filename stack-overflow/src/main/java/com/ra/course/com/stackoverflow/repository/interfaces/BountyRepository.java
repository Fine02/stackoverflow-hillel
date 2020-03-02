package com.ra.course.com.stackoverflow.repository.interfaces;

import com.ra.course.com.stackoverflow.entity.Bounty;

import java.util.Optional;

public interface BountyRepository {
    Bounty findByQuestionId(long id);

    Bounty save(Bounty bounty);

    Optional<Bounty> findById(long id);
}
