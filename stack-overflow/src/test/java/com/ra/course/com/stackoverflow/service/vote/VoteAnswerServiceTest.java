package com.ra.course.com.stackoverflow.service.vote;

import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.entity.Answer;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.exception.service.AlreadyVotedException;
import com.ra.course.com.stackoverflow.exception.service.CannotVoteOwnPostException;
import com.ra.course.com.stackoverflow.repository.AnswerRepository;
import com.ra.course.com.stackoverflow.repository.MemberRepository;
import com.ra.course.com.stackoverflow.service.RepositoryUtils;
import com.ra.course.com.stackoverflow.service.system.NotificationService;
import com.ra.course.com.stackoverflow.service.system.SecurityService;
import com.ra.course.com.stackoverflow.service.vote.impl.VoteAnswerServiceImpl;
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

public class VoteAnswerServiceTest {

    private VoteService<Answer> service;

    private final AnswerRepository answerData = mock(AnswerRepository.class);
    private final MemberRepository memberData = mock(MemberRepository.class);
    private final NotificationService notificationService = mock(NotificationService.class);
    private final RepositoryUtils utils = mock(RepositoryUtils.class);
    private final SecurityService securityService = mock(SecurityService.class);

    private Member member;
    private SessionMemberDto sessionMember;
    private Answer answer;

    private ThrowableAssert.ThrowingCallable callable;

    @BeforeEach
    void setUp() {
        service = new VoteAnswerServiceImpl(answerData, memberData, notificationService, utils, securityService);

        member = getMember();
        sessionMember = getSessionMemberDto();
        answer = getAnswer();
            answer.setAuthor(2L);

        when(utils.getAnswerFromDB(ID)).thenReturn(answer);
        when(securityService.checkStatusAndReturnMember(sessionMember)).thenReturn(member);
    }

    @Test
    void whenUpVoteAndMemberIsAuthorThenThrownCannotVoteOwnPostException() {
        //given
        answer.setAuthor(ID);
        //then
        assertThatThrownBy(() -> service.upVote(ID, sessionMember))
                .isInstanceOf(CannotVoteOwnPostException.class);
    }

    @Test
    void whenUpVoteAndAnswerIsVotedByMemberThenThrownAlreadyVotedException() {
        //given
        member.setUpVotedAnswersId(List.of(ID));
        //then
        assertThatThrownBy(() -> service.upVote(ID, sessionMember))
                .isInstanceOf(AlreadyVotedException.class);
    }

    @Test
    void whenUpVote() {
        //given
        member.setDownVotedAnswersId(new ArrayList<>(List.of(ID)));
        //when
        callable = () -> service.upVote(ID, sessionMember);

        //then
        assertThatCode(callable)
                .doesNotThrowAnyException();
        verify(memberData).update(member);
        verify(answerData).update(answer);
        verify(notificationService).sendNotification(answer, "voting up");
    }

    @Test
    void whenDownVoteAndMemberIsAuthorThenThrownCannotVoteOwnPostException() {
        //given
        answer.setAuthor(ID);
        //then
        assertThatThrownBy(() -> service.downVote(ID, sessionMember))
                .isInstanceOf(CannotVoteOwnPostException.class);
    }

    @Test
    void whenDownVoteAndAnswerIsVotedByMemberThenThrownAlreadyVotedException() {
        //given
        member.setDownVotedAnswersId(List.of(ID));
        //then
        assertThatThrownBy(() -> service.downVote(ID, sessionMember))
                .isInstanceOf(AlreadyVotedException.class);
    }

    @Test
    void whenDownVote() {
        //given
        member.setUpVotedAnswersId(new ArrayList<>(List.of(ID)));
        //when
        callable = () -> service.downVote(ID, sessionMember);

        //then
        assertThatCode(callable)
                .doesNotThrowAnyException();
        verify(memberData).update(member);
        verify(answerData).update(answer);
        verify(notificationService).sendNotification(answer, "voting down");
    }
}
