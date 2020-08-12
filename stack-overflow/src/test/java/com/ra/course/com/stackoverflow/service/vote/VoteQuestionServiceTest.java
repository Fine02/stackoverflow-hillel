package com.ra.course.com.stackoverflow.service.vote;

import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.entity.enums.QuestionStatus;
import com.ra.course.com.stackoverflow.exception.service.AlreadyVotedException;
import com.ra.course.com.stackoverflow.exception.service.CannotVoteOwnPostException;
import com.ra.course.com.stackoverflow.exception.service.security.QuestionStatusException;
import com.ra.course.com.stackoverflow.repository.MemberRepository;
import com.ra.course.com.stackoverflow.repository.QuestionRepository;
import com.ra.course.com.stackoverflow.service.RepositoryUtils;
import com.ra.course.com.stackoverflow.service.system.BadgeAwardService;
import com.ra.course.com.stackoverflow.service.system.NotificationService;
import com.ra.course.com.stackoverflow.service.system.SecurityService;
import com.ra.course.com.stackoverflow.service.vote.impl.VoteQuestionServiceImpl;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.ra.course.com.stackoverflow.utils.Constants.ID;
import static com.ra.course.com.stackoverflow.utils.DtoCreationUtils.getSessionMemberDto;
import static com.ra.course.com.stackoverflow.utils.EntityCreationUtil.*;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class VoteQuestionServiceTest {

    private VoteService<Question> service;

    private final QuestionRepository questionData = mock(QuestionRepository.class);
    private final MemberRepository memberData = mock(MemberRepository.class);
    private final NotificationService notificationService = mock(NotificationService.class);
    private final RepositoryUtils utils = mock(RepositoryUtils.class);
    private final SecurityService securityService = mock(SecurityService.class);
    private final BadgeAwardService<Question> badgeService = mock(BadgeAwardService.class);

    private Member member;
    private SessionMemberDto sessionMember;
    private Question question;

    private ThrowableAssert.ThrowingCallable callable;

    @BeforeEach
    void setUp() {
        service = new VoteQuestionServiceImpl(questionData, memberData, notificationService,
                                                utils, securityService, badgeService);
        member = getMember();
        sessionMember = getSessionMemberDto();
        question = getQuestion();
            question.setAuthor(2L);

        when(utils.getQuestionFromDB(ID)).thenReturn(question);
        when(securityService.checkStatusAndReturnMember(sessionMember)).thenReturn(member);
    }

    @Test
    void whenUpVoteAndQuestionIdsClosedThenThrownQuestionStatusException() {
        //given
        question.setStatus(QuestionStatus.CLOSE);
        //then
        assertThatThrownBy(() -> service.upVote(ID, sessionMember))
                .isInstanceOf(QuestionStatusException.class);
    }

    @Test
    void whenUpVoteAndMemberIsAuthorThenThrownCannotVoteOwnPostException() {
        //given
        question.setAuthor(ID);
        //then
        assertThatThrownBy(() -> service.upVote(ID, sessionMember))
                .isInstanceOf(CannotVoteOwnPostException.class);
    }

    @Test
    void whenUpVoteAndQuestionIsVotedByMemberThenThrownAlreadyVotedException() {
        //given
        member.setUpVotedQuestionsId(List.of(ID));
        //then
        assertThatThrownBy(() -> service.upVote(ID, sessionMember))
                .isInstanceOf(AlreadyVotedException.class);
    }

    @Test
    void whenUpVote() {
        //given
        member.setDownVotedQuestionsId(new ArrayList<>(List.of(ID)));
        //when
        callable = () -> service.upVote(ID, sessionMember);

        //then
        assertThatCode(callable)
                .doesNotThrowAnyException();
        verify(memberData).update(member);
        verify(questionData).update(question);
        verify(badgeService).awardMember(question);
        verify(notificationService).sendNotification(question, "voting up");
    }

    @Test
    void whenDownVoteAndQuestionIdsClosedThenThrownQuestionStatusException() {
        //given
        question.setStatus(QuestionStatus.CLOSE);
        //then
        assertThatThrownBy(() -> service.downVote(ID, sessionMember))
                .isInstanceOf(QuestionStatusException.class);
    }

    @Test
    void whenDownVoteAndMemberIsAuthorThenThrownCannotVoteOwnPostException() {
        //given
        question.setAuthor(ID);
        //then
        assertThatThrownBy(() -> service.downVote(ID, sessionMember))
                .isInstanceOf(CannotVoteOwnPostException.class);
    }

    @Test
    void whenDownVoteAndAnswerIsVotedByMemberThenThrownAlreadyVotedException() {
        //given
        member.setDownVotedQuestionsId(List.of(ID));
        //then
        assertThatThrownBy(() -> service.downVote(ID, sessionMember))
                .isInstanceOf(AlreadyVotedException.class);
    }

    @Test
    void whenDownVote() {
        //given
        member.setUpVotedQuestionsId(new ArrayList<>(List.of(ID)));
        //when
        callable = () -> service.downVote(ID, sessionMember);

        //then
        assertThatCode(callable)
                .doesNotThrowAnyException();
        verify(memberData).update(member);
        verify(questionData).update(question);
        verify(notificationService).sendNotification(question, "voting down");
    }
}
