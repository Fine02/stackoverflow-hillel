package com.ra.course.com.stackoverflow.repository;

import com.ra.course.com.stackoverflow.entity.Bounty;

import java.util.Optional;

public interface BountyRepository {
    Bounty save(Bounty bounty);

    Optional<Bounty> findById(Long id);

    void deleteById(long id);
}
