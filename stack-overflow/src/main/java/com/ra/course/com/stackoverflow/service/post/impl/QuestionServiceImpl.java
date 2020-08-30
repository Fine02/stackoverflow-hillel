package com.ra.course.com.stackoverflow.service.post.impl;

import com.ra.course.com.stackoverflow.dto.post.CreateQuestionDto;
import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.dto.post.QuestionFullDto;
import com.ra.course.com.stackoverflow.dto.post.UpdateQuestionDto;
import com.ra.course.com.stackoverflow.dto.mapper.QuestionMapper;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.entity.enums.QuestionStatus;
import com.ra.course.com.stackoverflow.exception.service.security.QuestionStatusException;
import com.ra.course.com.stackoverflow.repository.*;

import com.ra.course.com.stackoverflow.service.RepositoryUtils;
import com.ra.course.com.stackoverflow.service.post.QuestionService;
import com.ra.course.com.stackoverflow.service.system.NotificationService;
import com.ra.course.com.stackoverflow.service.system.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@AllArgsConstructor
@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepo;
    private final RepositoryUtils utils;
    private final SecurityService securityService;
    private final NotificationService noteService;


    @Override
    public QuestionFullDto getQuestionById(final Long id) {
        final var question = utils.getQuestionFromDB(id);
        return QuestionMapper.MAPPER.toQuestionFullDto(question);
    }

    /*User can update own open question, moder/admin - any*/
    @Override
    public QuestionFullDto updateQuestion(final UpdateQuestionDto updateQuestionDto,
                               final Long questionId, final SessionMemberDto sessionMember) {
        final var question = securityService.checkRightOfMemberAndReturnQuestion(sessionMember, questionId);
            checkQuestionStatus(question);

        question.setUpdateTime(LocalDateTime.now());
        question.setTitle(updateQuestionDto.getTitle());
        question.setText(updateQuestionDto.getText());
        questionRepo.update(question);

        noteService.sendNotification(question, "updated");
        return QuestionMapper.MAPPER.toQuestionFullDto(question);
    }

    /*User can delete own question, moder/admin - any*/
    @Override
    public QuestionFullDto deleteQuestion(final Long questionId, final SessionMemberDto sessionMember) {
        final var question = securityService.checkRightOfMemberAndReturnQuestion(sessionMember, questionId);
        if(question.getStatus().equals(QuestionStatus.DELETED)){
            throw  new QuestionStatusException("delete", question.getStatus());
        }

        question.setStatus(QuestionStatus.DELETED);
        question.setUpdateTime(LocalDateTime.now());
        questionRepo.update(question);

        noteService.sendNotification(question, "deleted");
        return QuestionMapper.MAPPER.toQuestionFullDto(question);
    }

    /*User can close own open question, moder/admin - any*/
    @Override
    public QuestionFullDto closeQuestion(final Long questionId, final SessionMemberDto sessionMember) {
        final var question = securityService.checkRightOfMemberAndReturnQuestion(sessionMember, questionId);
            checkQuestionStatus(question);

        question.setStatus(QuestionStatus.CLOSE);
        question.setUpdateTime(LocalDateTime.now());
        questionRepo.update(question);

        noteService.sendNotification(question, "closed");
        return QuestionMapper.MAPPER.toQuestionFullDto(question);
    }

    /*Any active user can create new question*/
    @Override
    public QuestionFullDto createQuestion(final CreateQuestionDto createQuestionDto,
                                      final SessionMemberDto sessionMemberDto) {

        final var member = securityService.checkStatusAndReturnMember(sessionMemberDto);

        final var newQuestion = QuestionMapper.MAPPER.toQuestion(createQuestionDto);
            newQuestion.setAuthor(member.getId());

        final var postedQuestion = questionRepo.save(newQuestion);
        createQuestionDto.getTags().stream()
                .map(utils::getTagFromDBByTagName)
                .forEach(tag  -> questionRepo.addTagToQuestion(tag, postedQuestion));
        final var savedQuestion = utils.getQuestionFromDB(postedQuestion.getId());

        noteService.sendNotification(savedQuestion, "created");
        return QuestionMapper.MAPPER.toQuestionFullDto(savedQuestion);
    }

    private void checkQuestionStatus(final Question question){
        if(!question.getStatus().equals(QuestionStatus.OPEN)){
            throw new QuestionStatusException(question.getStatus());
        }
    }

}
