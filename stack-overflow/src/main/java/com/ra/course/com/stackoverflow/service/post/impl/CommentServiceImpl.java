package com.ra.course.com.stackoverflow.service.post.impl;

import com.ra.course.com.stackoverflow.dto.post.CreateDto;
import com.ra.course.com.stackoverflow.dto.mapper.CommentMapper;
import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;

import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.entity.enums.QuestionStatus;
import com.ra.course.com.stackoverflow.exception.service.security.QuestionStatusException;
import com.ra.course.com.stackoverflow.repository.CommentRepository;


import com.ra.course.com.stackoverflow.service.RepositoryUtils;
import com.ra.course.com.stackoverflow.service.post.CommentService;
import com.ra.course.com.stackoverflow.service.system.NotificationService;
import com.ra.course.com.stackoverflow.service.system.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepo;

    private final RepositoryUtils utils;
    private final SecurityService securityService;
    private final NotificationService noteService;


    /**Member can add comments to any open question.**/
    @Override
    public void addCommentToQuestion(final CreateDto commentDto, final Long questionId,
                                     final SessionMemberDto sessionMemberDto) {

        final var member = securityService.checkStatusAndReturnMember(sessionMemberDto);

        final var question = utils.getQuestionFromDB(questionId);
            checkQuestionStatus(question);

        final var comment = CommentMapper.MAPPER.toComment(commentDto);
            comment.setQuestion(question.getId());
            comment.setAuthor(member.getId());

        commentRepo.save(comment);

        noteService.sendNotification(question, "commented");
    }


    /**Member can add comments to any answer to open question.**/
    @Override
    public void addCommentToAnswer(final CreateDto commentDto, final Long answerId,
                                   final SessionMemberDto sessionMemberDto) {

        final var member = securityService.checkStatusAndReturnMember(sessionMemberDto);

        final var answer = utils.getAnswerFromDB(answerId);

        final var question = utils.getQuestionFromDB(answer.getQuestion());
            checkQuestionStatus(question);

        final var comment = CommentMapper.MAPPER.toComment(commentDto);
            comment.setAnswer(answer.getId());
            comment.setAuthor(member.getId());

        commentRepo.save(comment);

        noteService.sendNotification(question, "commented");
    }

    /**User can delete own comment, moder/admin - any**/
    @Override
    public void deleteComment(final Long commentId, final SessionMemberDto sessionMemberDto) {

        final var comment = securityService.checkRightOfMemberAndReturnComment(sessionMemberDto, commentId);

        commentRepo.delete(comment);

        noteService.sendNotification(comment, "deleted");
    }

    private void checkQuestionStatus(final Question question){
        if(!question.getStatus().equals(QuestionStatus.OPEN)){
            throw new QuestionStatusException(question.getStatus());
        }
    }

}
