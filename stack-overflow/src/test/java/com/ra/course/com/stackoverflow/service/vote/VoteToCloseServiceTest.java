package com.ra.course.com.stackoverflow.service.vote;

import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.entity.enums.QuestionClosingRemark;
import com.ra.course.com.stackoverflow.entity.enums.QuestionStatus;
import com.ra.course.com.stackoverflow.exception.service.AlreadyVotedException;
import com.ra.course.com.stackoverflow.exception.service.security.QuestionStatusException;
import com.ra.course.com.stackoverflow.repository.QuestionRepository;
import com.ra.course.com.stackoverflow.service.RepositoryUtils;
import com.ra.course.com.stackoverflow.service.system.NotificationService;
import com.ra.course.com.stackoverflow.service.system.SecurityService;
import com.ra.course.com.stackoverflow.service.vote.impl.VoteToCloseServiceImpl;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.ra.course.com.stackoverflow.utils.Constants.ID;
import static com.ra.course.com.stackoverflow.utils.DtoCreationUtils.getSessionMemberDto;
import static com.ra.course.com.stackoverflow.utils.EntityCreationUtil.getMember;
import static com.ra.course.com.stackoverflow.utils.EntityCreationUtil.getQuestion;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class VoteToCloseServiceTest {

    private VoteToCloseService service;

    private final QuestionRepository questionData = mock(QuestionRepository.class);
    private final SecurityService securityService = mock(SecurityService.class);
    private final RepositoryUtils utils = mock(RepositoryUtils.class);
    private final NotificationService noteService = mock(NotificationService.class);

    private Question question;
    private SessionMemberDto sessionMemberDto;
    private final QuestionClosingRemark remark = QuestionClosingRemark.SPAM;

    @BeforeEach
    void setUp() {
        service = new VoteToCloseServiceImpl(questionData, securityService, utils, noteService);

        var member = getMember();
        question = getQuestion();
        sessionMemberDto = getSessionMemberDto();

        when(securityService.checkStatusAndReturnMember(sessionMemberDto)).thenReturn(member);
        when(utils.getQuestionFromDB(ID)).thenReturn(question);
    }

    @Test
    void whenVoteToCloseAndQuestionIsCloseThenThrownQuestionStatusException() {
        //given
        question.setStatus(QuestionStatus.CLOSE);
        //then
        assertThatThrownBy(() -> service.voteToClose(ID, sessionMemberDto, remark))
                .isInstanceOf(QuestionStatusException.class);
    }

    @Test
    public void whenMemberIsAlreadyVotedToCloseTheQuestionThenThrowsAlreadyVotedException() {
        //given
        question.getMembersIdsWhoVotedQuestionToClose().put(ID, remark);
        //then
        assertThatThrownBy(() -> service.voteToClose(ID, sessionMemberDto, remark))
                .isInstanceOf(AlreadyVotedException.class);
    }

    @Test
    public void whenMemberVotesToCloseTheQuestionThenUpdateQuestion() {
        //then
        assertThatCode(() -> service.voteToClose(ID, sessionMemberDto, remark))
                .doesNotThrowAnyException();
        verify(questionData).update(question);
        verify(noteService).sendNotification(question, "voting to close because " + remark.toString());
    }

}
