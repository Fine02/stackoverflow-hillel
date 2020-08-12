package com.ra.course.com.stackoverflow.service.post;

import com.ra.course.com.stackoverflow.dto.post.CreateDto;
import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.entity.Comment;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.entity.enums.QuestionStatus;
import com.ra.course.com.stackoverflow.exception.service.security.QuestionStatusException;
import com.ra.course.com.stackoverflow.repository.CommentRepository;
import com.ra.course.com.stackoverflow.service.RepositoryUtils;
import com.ra.course.com.stackoverflow.service.post.impl.CommentServiceImpl;
import com.ra.course.com.stackoverflow.service.system.NotificationService;
import com.ra.course.com.stackoverflow.service.system.SecurityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.ra.course.com.stackoverflow.utils.Constants.ID;
import static com.ra.course.com.stackoverflow.utils.Constants.TEXT;
import static com.ra.course.com.stackoverflow.utils.DtoCreationUtils.getSessionMemberDto;
import static com.ra.course.com.stackoverflow.utils.EntityCreationUtil.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class CommentServiceTest {

    private CommentService service;

    private final CommentRepository commentRepo = mock(CommentRepository.class);
    private final RepositoryUtils utils = mock(RepositoryUtils.class);
    private final SecurityService securityService = mock(SecurityService.class);
    private final NotificationService noteService = mock(NotificationService.class);

    private Comment comment;
    private Question question;
    private SessionMemberDto sessionMemberDto;
    private CreateDto createDto;


    @BeforeEach
    void setUp() {
        service = new CommentServiceImpl(commentRepo, utils, securityService, noteService);

        var member = getMember();
        var answer = getAnswer();
        question = getQuestion();
        comment = getComment();
        sessionMemberDto = getSessionMemberDto();
        createDto = new CreateDto();
            createDto.setText(TEXT);

        when(securityService.checkStatusAndReturnMember(sessionMemberDto)).thenReturn(member);
        when(utils.getAnswerFromDB(ID)).thenReturn(answer);

        when(utils.getQuestionFromDB(ID)).thenReturn(question);
    }

    @Test
    void whenAddCommentToAnswer() {
        //given
        comment.setAnswer(ID);
        //when
        service.addCommentToAnswer(createDto, ID, sessionMemberDto);
        //then
        verify(commentRepo).save(comment);
        verify(noteService).sendNotification(question, "commented");
    }

    @Test
    void whenAddCommentToAnswerAndQuestionIsDeletedThenThrownException() {
        //given
        comment.setAnswer(ID);
        question.setStatus(QuestionStatus.DELETED);
        //then
        assertThatThrownBy(() -> service.addCommentToAnswer(createDto, ID, sessionMemberDto))
                .isInstanceOf(QuestionStatusException.class);
    }

    @Test
    void whenAddCommentToQuestion() {
        //given
        comment.setQuestion(ID);
        //when
        service.addCommentToQuestion(createDto, ID, sessionMemberDto);
        //then
        verify(commentRepo).save(comment);
        verify(noteService).sendNotification(question, "commented");
    }

    @Test
    void whenAddCommentToQuestionAndQuestionIsDeletedThenThrownException() {
        //given
        comment.setQuestion(ID);
        question.setStatus(QuestionStatus.DELETED);
        //then
        assertThatThrownBy(() -> service.addCommentToQuestion(createDto, ID, sessionMemberDto))
                .isInstanceOf(QuestionStatusException.class);
    }

    @Test
    void whenDeleteComment() {
        //given
        when(securityService.checkRightOfMemberAndReturnComment(sessionMemberDto, ID)).thenReturn(comment);
        //when
        service.deleteComment(ID, sessionMemberDto);
        //then
        verify(commentRepo).delete(comment);
        verify(noteService).sendNotification(comment, "deleted");
    }
}
