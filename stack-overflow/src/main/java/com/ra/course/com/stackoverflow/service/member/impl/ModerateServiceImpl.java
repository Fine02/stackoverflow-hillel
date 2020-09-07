package com.ra.course.com.stackoverflow.service.member.impl;

import com.ra.course.com.stackoverflow.dto.mapper.QuestionMapper;
import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.dto.post.QuestionFullDto;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.entity.enums.QuestionStatus;
import com.ra.course.com.stackoverflow.repository.QuestionRepository;
import com.ra.course.com.stackoverflow.service.RepositoryUtils;
import com.ra.course.com.stackoverflow.service.member.ModerateService;
import com.ra.course.com.stackoverflow.service.system.NotificationService;
import com.ra.course.com.stackoverflow.service.system.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.ra.course.com.stackoverflow.entity.enums.QuestionStatus.CLOSE;
import static com.ra.course.com.stackoverflow.entity.enums.QuestionStatus.ON_HOLD;
import static com.ra.course.com.stackoverflow.entity.enums.QuestionStatus.OPEN;

@AllArgsConstructor
@Service
public class ModerateServiceImpl implements ModerateService {

    private final QuestionRepository questionRepo;
    private final RepositoryUtils utils;
    private final SecurityService securityService;
    private final NotificationService noteService;

    @Override
    public QuestionFullDto closeQuestion(final Long questionId, final SessionMemberDto memberDto) {
        securityService.checkModer(memberDto);
        final var question = getQuestionAndUpdateStatus(questionId, CLOSE);

        noteService.sendNotification(question, "closed");
        return QuestionMapper.MAPPER.toQuestionFullDto(question);
    }

    @Override
    public QuestionFullDto undeleteQuestion(final Long questionId, final SessionMemberDto memberDto) {
        securityService.checkModer(memberDto);
        final var question = getQuestionAndUpdateStatus(questionId, ON_HOLD);

        noteService.sendNotification(question, "on hold");
        return QuestionMapper.MAPPER.toQuestionFullDto(question);
    }

    @Override
    public QuestionFullDto reopenQuestion(final Long questionId, final SessionMemberDto memberDto) {
        securityService.checkModer(memberDto);
        final var question = getQuestionAndUpdateStatus(questionId, OPEN);

        noteService.sendNotification(question, "opened");
        return QuestionMapper.MAPPER.toQuestionFullDto(question);
    }

    private Question getQuestionAndUpdateStatus(final Long id, final QuestionStatus status) {
        final var question = utils.getQuestionFromDB(id);
        question.setStatus(status);
        question.setUpdateTime(LocalDateTime.now());
        questionRepo.update(question);
        return question;
    }
}
