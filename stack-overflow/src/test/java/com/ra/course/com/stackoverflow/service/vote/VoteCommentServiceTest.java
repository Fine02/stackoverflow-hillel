package com.ra.course.com.stackoverflow.service.vote;

import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.entity.Comment;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.exception.service.AlreadyVotedException;
import com.ra.course.com.stackoverflow.exception.service.CannotVoteOwnPostException;
import com.ra.course.com.stackoverflow.repository.CommentRepository;
import com.ra.course.com.stackoverflow.repository.MemberRepository;
import com.ra.course.com.stackoverflow.service.RepositoryUtils;
import com.ra.course.com.stackoverflow.service.system.NotificationService;
import com.ra.course.com.stackoverflow.service.system.SecurityService;
import com.ra.course.com.stackoverflow.service.vote.impl.VoteCommentServiceImpl;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.ra.course.com.stackoverflow.utils.Constants.ID;
import static com.ra.course.com.stackoverflow.utils.DtoCreationUtils.getSessionMemberDto;
import static com.ra.course.com.stackoverflow.utils.EntityCreationUtil.getComment;
import static com.ra.course.com.stackoverflow.utils.EntityCreationUtil.getMember;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class VoteCommentServiceTest {

    private VoteService<Comment> service;

    private final CommentRepository commentData = mock(CommentRepository.class);
    private final MemberRepository memberData = mock(MemberRepository.class);
    private final NotificationService notificationService = mock(NotificationService.class);
    private final RepositoryUtils utils = mock(RepositoryUtils.class);
    private final SecurityService securityService = mock(SecurityService.class);

    private Member member;
    private SessionMemberDto sessionMember;
    private Comment comment;

    private ThrowableAssert.ThrowingCallable callable;

    @BeforeEach
    void setUp() {
        service = new VoteCommentServiceImpl(commentData, memberData, notificationService, utils, securityService);

        member = getMember();
        sessionMember = getSessionMemberDto();
        comment = getComment();
            comment.setAuthor(2L);

        when(utils.getCommentFromDB(ID)).thenReturn(comment);
        when(securityService.checkStatusAndReturnMember(sessionMember)).thenReturn(member);
    }

    @Test
    void whenUpVoteAndMemberIsAuthorThenThrownCannotVoteOwnPostException() {
        //given
        comment.setAuthor(ID);
        //then
        assertThatThrownBy(() -> service.upVote(ID, sessionMember))
                .isInstanceOf(CannotVoteOwnPostException.class);
    }

    @Test
    void whenUpVoteAndCommentIsVotedByMemberThenThrownAlreadyVotedException() {
        //given
        member.setUpVotedCommentsId(List.of(ID));
        //then
        assertThatThrownBy(() -> service.upVote(ID, sessionMember))
                .isInstanceOf(AlreadyVotedException.class);
    }

    @Test
    void whenUpVote() {
        //given
        member.setDownVotedCommentsId(new ArrayList<>(List.of(ID)));
        //when
        callable = () -> service.upVote(ID, sessionMember);

        //then
        assertThatCode(callable)
                .doesNotThrowAnyException();
        verify(memberData).update(member);
        verify(commentData).update(comment);
        verify(notificationService).sendNotification(comment, "voting up");
    }

    @Test
    void whenDownVoteAndMemberIsAuthorThenThrownCannotVoteOwnPostException() {
        //given
        comment.setAuthor(ID);
        //then
        assertThatThrownBy(() -> service.downVote(ID, sessionMember))
                .isInstanceOf(CannotVoteOwnPostException.class);
    }

    @Test
    void whenDownVoteAndCommentIsVotedByMemberThenThrownAlreadyVotedException() {
        //given
        member.setDownVotedCommentsId(List.of(ID));
        //then
        assertThatThrownBy(() -> service.downVote(ID, sessionMember))
                .isInstanceOf(AlreadyVotedException.class);
    }

    @Test
    void whenDownVote() {
        //given
        member.setUpVotedCommentsId(new ArrayList<>(List.of(ID)));
        //when
        callable = () -> service.downVote(ID, sessionMember);

        //then
        assertThatCode(callable)
                .doesNotThrowAnyException();
        verify(memberData).update(member);
        verify(commentData).update(comment);
        verify(notificationService).sendNotification(comment, "voting down");
    }
}
