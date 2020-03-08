package com.ra.course.com.stackoverflow.service.bounty;

import com.ra.course.com.stackoverflow.entity.Bounty;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.exception.service.QuestionRepositoryException;
import com.ra.course.com.stackoverflow.repository.interfaces.BountyRepository;
import com.ra.course.com.stackoverflow.repository.interfaces.QuestionRepository;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class BountyServiceImpl implements BountyService {
    private final QuestionRepository questionRepo;
    private final BountyRepository bountyRepo;

    @Override
    public Bounty addBounty(final Question question, final Bounty bounty) throws QuestionRepositoryException {
        final Bounty savedBounty = bountyRepo.findById(bounty.getId())
                                       .orElseGet(() -> createBounty(bounty));

        final Question questionById = questionRepo.findById(question.getId())
                                            .orElseThrow(() -> new QuestionRepositoryException("Question with id " + question.getId() + " not found in DB"));

        questionById.setBounty(Optional.of(savedBounty));
        questionRepo.update(questionById);

        return savedBounty;
    }

    private Bounty createBounty(final Bounty bounty) {
        return bountyRepo.save(bounty);
    }
}
