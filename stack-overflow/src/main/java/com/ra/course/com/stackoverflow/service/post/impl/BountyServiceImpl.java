package com.ra.course.com.stackoverflow.service.post.impl;

import com.ra.course.com.stackoverflow.dto.bounty.CreateBountyDto;
import com.ra.course.com.stackoverflow.dto.mapper.BountyMapper;
import com.ra.course.com.stackoverflow.dto.mapper.QuestionMapper;
import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.dto.post.QuestionFullDto;
import com.ra.course.com.stackoverflow.entity.Answer;
import com.ra.course.com.stackoverflow.entity.enums.QuestionStatus;
import com.ra.course.com.stackoverflow.exception.service.BountyCreationException;
import com.ra.course.com.stackoverflow.exception.service.security.QuestionStatusException;
import com.ra.course.com.stackoverflow.repository.BountyRepository;
import com.ra.course.com.stackoverflow.repository.MemberRepository;
import com.ra.course.com.stackoverflow.repository.QuestionRepository;
import com.ra.course.com.stackoverflow.service.RepositoryUtils;
import com.ra.course.com.stackoverflow.service.post.BountyService;
import com.ra.course.com.stackoverflow.service.system.NotificationService;
import com.ra.course.com.stackoverflow.service.system.SecurityService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@AllArgsConstructor
@Slf4j
@Service
public class BountyServiceImpl implements BountyService {
    private final QuestionRepository questionRepo;
    private final BountyRepository bountyRepo;
    private final MemberRepository memberRepo;

    private final RepositoryUtils utils;
    private final SecurityService securityService;
    private final NotificationService noteService;

    @Override
    public QuestionFullDto addBounty(final Long questionId, final CreateBountyDto bountyDto,
                                     final SessionMemberDto sessionMemberDto) {

        final var question = utils.getQuestionFromDB(questionId);

        if(!question.getStatus().equals(QuestionStatus.OPEN)){
            throw  new QuestionStatusException("add bounty", question.getStatus());
        }
        if(Objects.nonNull(question.getBounty())){
            throw new BountyCreationException("Question already has a bounty");
        }

        final var member = securityService.checkStatusAndReturnMember(sessionMemberDto);

        if(member.getReputation() < bountyDto.getReputation()){
            throw new BountyCreationException("Member reputation is less then bounty reputation");
        }

        final var bounty = BountyMapper.MAPPER.toBounty(bountyDto);
            bounty.setCreatorId(member.getId());
            bounty.setQuestionId(question.getId());
        final var savedBounty = bountyRepo.save(bounty);

        question.setBounty(savedBounty);
        questionRepo.update(question);

        noteService.sendNotification(question, "updated with new bounty");

        return QuestionMapper.MAPPER.toQuestionFullDto(question);
    }

    @Override
    public void chargeBounty(final Answer answer){
        final var question = utils.getQuestionFromDB(answer.getQuestion());

        if(Objects.nonNull(question.getBounty())){
            final var bounty = utils.getBountyFromDB(question.getBounty().getId());

                final var creator = utils.getMemberFromDB(bounty.getCreatorId());
                var newReputation = creator.getReputation() - bounty.getReputation();
                creator.getAccount().setReputation(newReputation);
                memberRepo.update(creator);

                final var answerAuthor = utils.getMemberFromDB(answer.getAuthor());
                newReputation = answerAuthor.getReputation() + bounty.getReputation();
                answerAuthor.getAccount().setReputation(newReputation);
                memberRepo.update(answerAuthor);

                question.setBounty(null);
                questionRepo.update(question);

                bountyRepo.deleteById(bounty.getId());

                noteService.sendNotification(answer, "charge by bounty");
        }
    }

    @Override
    public void deleteBounty(final Long bountyId, final SessionMemberDto sessionMemberDto) {
        final var bounty = securityService.checkRightOfMemberAndReturnBounty(sessionMemberDto, bountyId);

        final var question = utils.getQuestionFromDB(bounty.getQuestionId());
        question.setBounty(null);
        questionRepo.update(question);

        bountyRepo.deleteById(bounty.getId());
    }
}
