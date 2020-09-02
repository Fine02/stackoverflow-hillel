package com.ra.course.com.stackoverflow.service.post.impl;

import com.ra.course.com.stackoverflow.dto.post.CreateDto;
import com.ra.course.com.stackoverflow.dto.mapper.AnswerMapper;
import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.entity.Answer;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.entity.enums.QuestionStatus;
import com.ra.course.com.stackoverflow.exception.service.AcceptedException;
import com.ra.course.com.stackoverflow.exception.service.security.QuestionStatusException;
import com.ra.course.com.stackoverflow.repository.AnswerRepository;
import com.ra.course.com.stackoverflow.service.RepositoryUtils;
import com.ra.course.com.stackoverflow.service.post.AnswerService;
import com.ra.course.com.stackoverflow.service.post.BountyService;
import com.ra.course.com.stackoverflow.service.system.NotificationService;
import com.ra.course.com.stackoverflow.service.system.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerData;
    private final RepositoryUtils utils;
    private final SecurityService securityService;
    private final BountyService bountyService;
    private final NotificationService noteService;


    /*Any member can add answer to open question*/
    @Override
    public void addAnswer(final CreateDto createDto, final Long questionId,
                          final SessionMemberDto sessionMemberDto) {
        final var member = securityService.checkStatusAndReturnMember(sessionMemberDto);

        final var question = utils.getQuestionFromDB(questionId);
            checkQuestionStatus(question);

        final var answer = AnswerMapper.MAPPER.toAnswer(createDto);
            answer.setQuestion(question.getId());
            answer.setAuthor(member.getId());

        final var savedAnswer = answerData.save(answer);

        noteService.sendNotification(savedAnswer, "created");
    }

    /*User can accepted the answer to his own question. Moder/admin - any*/
    @Override
    public void acceptAnswer(final Long answerId, final  SessionMemberDto sessionMemberDto) {
        final var answer = utils.getAnswerFromDB(answerId);

        if(answer.isAccepted()){
            throw new AcceptedException("Answer already accepted");
        }

        final var question = securityService.checkRightOfMemberAndReturnQuestion(sessionMemberDto, answer.getQuestion());
            checkQuestionStatus(question);

        if(question.getAnswers().stream().anyMatch(Answer::isAccepted)){
            throw new AcceptedException("Question already has accepted answer");
        }

        bountyService.chargeBounty(answer);

        answer.setAccepted(true);
        answerData.update(answer);

        noteService.sendNotification(answer, "accepted");
    }

    /*User can delete own answer. Moder/admin - any*/
    @Override
    public void deleteAnswer(final Long answerId, final SessionMemberDto sessionMemberDto) {

        final var answer = securityService.checkRightOfMemberAndReturnAnswer(sessionMemberDto, answerId);

        answerData.delete(answer);

        noteService.sendNotification(answer, "deleted");
    }

    private void checkQuestionStatus(final Question question){
        if(!question.getStatus().equals(QuestionStatus.OPEN)){
            throw new QuestionStatusException(question.getStatus());
        }
    }

}
