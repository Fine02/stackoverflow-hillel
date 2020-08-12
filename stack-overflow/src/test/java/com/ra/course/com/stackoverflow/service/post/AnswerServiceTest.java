package com.ra.course.com.stackoverflow.service.post;

import com.ra.course.com.stackoverflow.dto.post.CreateDto;
import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.entity.Answer;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.entity.enums.QuestionStatus;
import com.ra.course.com.stackoverflow.exception.service.AcceptedException;
import com.ra.course.com.stackoverflow.exception.service.security.QuestionStatusException;
import com.ra.course.com.stackoverflow.repository.AnswerRepository;
import com.ra.course.com.stackoverflow.service.RepositoryUtils;
import com.ra.course.com.stackoverflow.service.post.impl.AnswerServiceImpl;
import com.ra.course.com.stackoverflow.service.system.NotificationService;
import com.ra.course.com.stackoverflow.service.system.SecurityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.ra.course.com.stackoverflow.utils.Constants.ID;
import static com.ra.course.com.stackoverflow.utils.Constants.TEXT;
import static com.ra.course.com.stackoverflow.utils.DtoCreationUtils.getSessionMemberDto;
import static com.ra.course.com.stackoverflow.utils.EntityCreationUtil.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class AnswerServiceTest {

    private AnswerService service;

    private final AnswerRepository answerData = mock(AnswerRepository.class);
    private final RepositoryUtils utils = mock(RepositoryUtils.class);
    private final SecurityService securityService = mock(SecurityService.class);
    private final BountyService bountyService = mock(BountyService.class);
    private final NotificationService noteService = mock(NotificationService.class);

    private Answer answer;
    private CreateDto createDto;
    private SessionMemberDto sessionMemberDto;
    private Member member;
    private Question question;


    @BeforeEach
    void setUp() {
        service = new AnswerServiceImpl(answerData, utils, securityService, bountyService, noteService);

        answer = getAnswer();
        createDto = new CreateDto();
            createDto.setText(TEXT);
        sessionMemberDto = getSessionMemberDto();
        member = getMember();
        question = getQuestion();

        when(utils.getAnswerFromDB(ID)).thenReturn(answer);
        when(utils.getQuestionFromDB(ID)).thenReturn(question);
        when(securityService.checkRightOfMemberAndReturnQuestion(sessionMemberDto, ID)).thenReturn(question);
    }

    @Test
    void whenAddAnswer() {
        //given
        answer.setId(null);
        when(securityService.checkStatusAndReturnMember(sessionMemberDto)).thenReturn(member);
        //when
        service.addAnswer(createDto, ID, sessionMemberDto);
        //then
        verify(answerData).save(answer);
        verify(noteService).sendNotification(answer, "created");
    }

    @Test
    void whenAddAnswerAndQuestionIsDeletedThenThrownException() {
        //given
        question.setStatus(QuestionStatus.DELETED);
        when(securityService.checkStatusAndReturnMember(sessionMemberDto)).thenReturn(member);
        //then
        assertThatThrownBy(() -> service.addAnswer(createDto, ID, sessionMemberDto))
                .isInstanceOf(QuestionStatusException.class);
    }

    @Test
    void whenAcceptAnswerAndItIsAlreadyAcceptedThenThrowException() {
        //given
        answer.setAccepted(true);
        //then
        assertThatThrownBy(() -> service.acceptAnswer(ID, sessionMemberDto))
                .isInstanceOf(AcceptedException.class);
    }
    @Test
    void whenAcceptAnswerAndQuestionHasAcceptedThenThrowException() {
        //given
        var acceptedAnswer = getAnswer();
            acceptedAnswer.setAccepted(true);
        question.setAnswers(List.of(acceptedAnswer));
        //then
        assertThatThrownBy(() -> service.acceptAnswer(ID, sessionMemberDto))
                .isInstanceOf(AcceptedException.class);
    }

    @Test
    void whenAcceptAnswerAndQuestionIsDeletedThenThrowException() {
        //given
        question.setStatus(QuestionStatus.DELETED);
        //then
        assertThatThrownBy(() -> service.acceptAnswer(ID, sessionMemberDto))
                .isInstanceOf(QuestionStatusException.class);
    }


    @Test
    void whenAcceptAnswer() {
        //given
        when(answerData.findByQuestionId(ID)).thenReturn(List.of(answer));
        //when
        service.acceptAnswer(ID, sessionMemberDto);
        answer.setAccepted(true);
        //then
        verify(answerData).update(answer);
        verify(noteService).sendNotification(answer, "accepted");
    }

    @Test
    void whenDeleteAnswer() {
        //given
        when(securityService.checkRightOfMemberAndReturnAnswer(sessionMemberDto, ID)).thenReturn(answer);
        //when
        service.deleteAnswer(ID, sessionMemberDto);
        //then
        verify(answerData).delete(answer);
        verify(noteService).sendNotification(answer, "deleted");
    }
}
