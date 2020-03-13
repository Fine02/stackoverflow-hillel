package com.ra.course.com.stackoverflow.service.bounty;

import com.ra.course.com.stackoverflow.entity.Bounty;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.exception.repository.QuestionRepositoryException;
import com.ra.course.com.stackoverflow.exception.service.InternalServerErrorException;
import com.ra.course.com.stackoverflow.repository.BountyRepository;
import com.ra.course.com.stackoverflow.repository.QuestionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Service
public class BountyServiceImpl implements BountyService {
    private final QuestionRepository questionRepo;
    private final BountyRepository bountyRepo;

    @Override
    public Optional<Bounty> addBounty(final Question question, final Bounty bounty) {
        final Question questionById = questionRepo.findById(question.getId())
                                                  .orElseThrow(() -> new QuestionRepositoryException("Question with id " + question.getId() + " not found in DB"));

        final Bounty savedBounty = bountyRepo.findById(bounty.getId())
                                             .orElseGet(() -> bountyRepo.save(bounty));

        questionById.setBounty(Optional.of(savedBounty));

        try {
            questionRepo.update(questionById);
        } catch (InternalServerErrorException e) {
            bountyRepo.deleteById(savedBounty.getId());
            return Optional.empty();
        }

        return Optional.of(savedBounty);
    }
}
