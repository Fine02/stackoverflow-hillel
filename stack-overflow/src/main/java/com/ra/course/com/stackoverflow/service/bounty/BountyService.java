package com.ra.course.com.stackoverflow.service.bounty;

import com.ra.course.com.stackoverflow.entity.Bounty;
import com.ra.course.com.stackoverflow.entity.Question;

import java.util.Optional;

public interface BountyService {
    Optional<Bounty> addBounty(final Question question, final Bounty bounty);
}