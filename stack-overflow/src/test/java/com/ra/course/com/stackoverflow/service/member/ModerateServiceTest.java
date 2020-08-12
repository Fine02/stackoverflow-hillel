package com.ra.course.com.stackoverflow.service.member;

import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.dto.post.QuestionFullDto;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.entity.enums.QuestionStatus;
import com.ra.course.com.stackoverflow.repository.QuestionRepository;
import com.ra.course.com.stackoverflow.service.RepositoryUtils;
import com.ra.course.com.stackoverflow.service.member.impl.ModerateServiceImpl;
import com.ra.course.com.stackoverflow.service.system.NotificationService;
import com.ra.course.com.stackoverflow.service.system.SecurityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.ra.course.com.stackoverflow.utils.Constants.ID;
import static com.ra.course.com.stackoverflow.utils.DtoCreationUtils.getQuestionFullDto;
import static com.ra.course.com.stackoverflow.utils.DtoCreationUtils.getSessionMemberDto;
import static com.ra.course.com.stackoverflow.utils.EntityCreationUtil.getQuestion;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ModerateServiceTest {

    private ModerateService moderateService;

    private final QuestionRepository data = mock(QuestionRepository.class);
    private final RepositoryUtils utils = mock(RepositoryUtils.class);
    private final SecurityService securityService = mock(SecurityService.class);
    private final NotificationService noteService = mock(NotificationService.class);

    private Question question;
    private QuestionFullDto questionFullDto;
    private SessionMemberDto memberDto;

    @BeforeEach
    void setUp() {
        moderateService = new ModerateServiceImpl(data, utils, securityService, noteService);

        question = getQuestion();
        questionFullDto = getQuestionFullDto();
        memberDto = getSessionMemberDto();

        when(utils.getQuestionFromDB(ID)).thenReturn(question);
    }

    @Test
    public void shouldCloseQuestion() {
        //given
        questionFullDto.setStatus(QuestionStatus.CLOSE);
        //when
        var result = moderateService.closeQuestion(ID,memberDto);
        //then
        assertEquals(questionFullDto, result);
        verify(data).update(question);
        verify(noteService).sendNotification(question, "closed");
    }
    @Test
    public void shouldReopenQuestion() {
        //given
        questionFullDto.setStatus(QuestionStatus.OPEN);
        //when
        var result = moderateService.reopenQuestion(ID,memberDto);
        //then
        assertEquals(questionFullDto, result);
        verify(data).update(question);
        verify(noteService).sendNotification(question, "opened");
    }
    @Test
    public void shouldUndeleteQuestion() {
        //given
        questionFullDto.setStatus(QuestionStatus.ON_HOLD);
        //when
        var result = moderateService.undeleteQuestion(ID,memberDto);
        //then
        assertEquals(questionFullDto, result);
        verify(data).update(question);
        verify(noteService).sendNotification(question, "on hold");
    }
}