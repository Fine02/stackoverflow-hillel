package com.ra.course.com.stackoverflow.service.vote.impl;

import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.entity.enums.QuestionClosingRemark;
import com.ra.course.com.stackoverflow.entity.enums.QuestionStatus;
import com.ra.course.com.stackoverflow.exception.service.AlreadyVotedException;
import com.ra.course.com.stackoverflow.exception.service.security.QuestionStatusException;
import com.ra.course.com.stackoverflow.repository.QuestionRepository;
import com.ra.course.com.stackoverflow.service.RepositoryUtils;
import com.ra.course.com.stackoverflow.service.system.NotificationService;
import com.ra.course.com.stackoverflow.service.system.SecurityService;
import com.ra.course.com.stackoverflow.service.vote.VoteToCloseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class VoteToCloseServiceImpl implements VoteToCloseService {

    private final QuestionRepository questionData;
    private final SecurityService securityService;
    private final RepositoryUtils utils;
    private final NotificationService noteService;

    @Override
    public void voteToClose(final Long questionId, final SessionMemberDto sessionMemberDto,
                                final QuestionClosingRemark remark) {
        final var member = securityService.checkStatusAndReturnMember(sessionMemberDto);
        final var question = checkQuestion(questionId);

        if (question.getMembersIdsWhoVotedQuestionToClose().containsKey(member.getId())){
            throw new AlreadyVotedException("This question is already vote to close");
        }

        question.getMembersIdsWhoVotedQuestionToClose().put(member.getId(), remark);

        questionData.update(question);
        noteService.sendNotification(question, "voting to close because " + remark.toString());
    }

    private Question checkQuestion(final Long questionId){
        final var question = utils.getQuestionFromDB(questionId);
            if(!question.getStatus().equals(QuestionStatus.OPEN)){
                throw new QuestionStatusException(question.getStatus());
            }
        return question;
    }
}
