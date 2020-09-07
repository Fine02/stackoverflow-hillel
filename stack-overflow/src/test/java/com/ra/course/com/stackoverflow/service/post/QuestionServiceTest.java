package com.ra.course.com.stackoverflow.service.post;

import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.dto.post.CreateQuestionDto;
import com.ra.course.com.stackoverflow.dto.post.QuestionFullDto;
import com.ra.course.com.stackoverflow.dto.post.UpdateQuestionDto;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.entity.enums.QuestionStatus;
import com.ra.course.com.stackoverflow.exception.service.security.QuestionStatusException;
import com.ra.course.com.stackoverflow.repository.QuestionRepository;
import com.ra.course.com.stackoverflow.service.RepositoryUtils;
import com.ra.course.com.stackoverflow.service.post.impl.QuestionServiceImpl;
import com.ra.course.com.stackoverflow.service.system.NotificationService;
import com.ra.course.com.stackoverflow.service.system.SecurityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.ra.course.com.stackoverflow.utils.Constants.*;
import static com.ra.course.com.stackoverflow.utils.DtoCreationUtils.*;
import static com.ra.course.com.stackoverflow.utils.EntityCreationUtil.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class QuestionServiceTest {

    private QuestionService service;

    private final QuestionRepository data = mock(QuestionRepository.class);
    private final RepositoryUtils utils = mock(RepositoryUtils.class);
    private final SecurityService securityService = mock(SecurityService.class);
    private final NotificationService noteService = mock(NotificationService.class);

    private Question question;
    private SessionMemberDto sessionMember;
    private UpdateQuestionDto updateDto;
    private CreateQuestionDto createQuestionDto;
    private QuestionFullDto questionFullDto;

    @BeforeEach
    void setUp() {
        service = new QuestionServiceImpl(data, utils, securityService, noteService);

        question = getQuestion();
        sessionMember = getSessionMemberDto();
        updateDto = getUpdateQuestionDto();
        createQuestionDto = getCreateQuestionDto();
        questionFullDto = getQuestionFullDto();

        when(utils.getMemberFromDB(ID)).thenReturn(getMember());
        when(securityService.checkRightOfMemberAndReturnQuestion(sessionMember, ID)).thenReturn(question);
    }

    @Test
    void whenGetByQuestionIdThenReturnFullDto() {
        //given
        when(utils.getQuestionFromDB(ID)).thenReturn(question);
        //when
        var result = service.getQuestionById(ID);
        //then
        assertEquals(questionFullDto, result);
    }

    @Test
    void whenUpdateQuestionThenReturnQuestionFullDto() {
        //given
        questionFullDto.setText(NEW_TEXT);
        questionFullDto.setTitle(NEW_TITLE);
        //when
        var result = service.updateQuestion(updateDto, ID, sessionMember);
        //then
        verify(noteService).sendNotification(question, "updated");
        assertEquals(result, questionFullDto);
    }

    @Test
    void whenUpdateAndQuestionIsClosedThenThrownException() {
        //given
        question.setStatus(QuestionStatus.CLOSE);
        //then
        assertThatThrownBy(() -> service.updateQuestion(updateDto, ID, sessionMember))
                .isInstanceOf(QuestionStatusException.class);
    }

    @Test
    void whenDeleteQuestionThenReturnQuestionFullDto() {
        //given
        questionFullDto.setStatus(QuestionStatus.DELETED);
        //when
        var result = service.deleteQuestion(ID, sessionMember);
        //then
        verify(noteService).sendNotification(question, "deleted");
        assertEquals(result, questionFullDto);
    }

    @Test
    void whenCloseAndQuestionIsDeletedThenThrownException() {
        //given
        question.setStatus(QuestionStatus.DELETED);
        //then
        assertThatThrownBy(() -> service.closeQuestion(ID, sessionMember))
                .isInstanceOf(QuestionStatusException.class);
    }

    @Test
    void whenDeleteAndQuestionIsDeletedThenThrownException() {
        //given
        question.setStatus(QuestionStatus.DELETED);
        //then
        assertThatThrownBy(() -> service.deleteQuestion(ID, sessionMember))
                .isInstanceOf(QuestionStatusException.class);
    }

    @Test
    void whenCloseQuestionThenReturnQuestionFullDto() {
        //given
        questionFullDto.setStatus(QuestionStatus.CLOSE);
        //when
        var result = service.closeQuestion(ID, sessionMember);
        //then
        verify(noteService).sendNotification(question, "closed");
        assertEquals(result, questionFullDto);
    }

    @Test
    void whenCreateQuestionThenReturnQuestionFullDto() {
        //given
        when(securityService.checkStatusAndReturnMember(sessionMember)).thenReturn(getMember());

        var newQuestion = getQuestion();
        newQuestion.setId(null);
        when(data.save(newQuestion)).thenReturn(question);

        var tag = getTag();
        when(utils.getTagFromDBByTagName(TITLE)).thenReturn(tag);

        question.setTags(List.of(tag));
        when(utils.getQuestionFromDB(ID)).thenReturn(question);

        questionFullDto.setTags(List.of(getTagDto()));

        //when
        var result = service.createQuestion(createQuestionDto, sessionMember);
        //then
        verify(noteService).sendNotification(question, "created");
        assertEquals(questionFullDto, result);
    }
}
