package com.ra.course.com.stackoverflow.service.system;

import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.entity.*;
import com.ra.course.com.stackoverflow.entity.enums.AccountRole;
import com.ra.course.com.stackoverflow.entity.enums.AccountStatus;
import com.ra.course.com.stackoverflow.exception.service.security.MemberRoleException;
import com.ra.course.com.stackoverflow.exception.service.security.MemberStatusException;
import com.ra.course.com.stackoverflow.exception.service.security.NotAuthorOfThePostException;
import com.ra.course.com.stackoverflow.service.RepositoryUtils;
import com.ra.course.com.stackoverflow.service.system.impl.SecurityServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.ra.course.com.stackoverflow.utils.Constants.ID;
import static com.ra.course.com.stackoverflow.utils.DtoCreationUtils.getSessionMemberDto;
import static com.ra.course.com.stackoverflow.utils.EntityCreationUtil.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SecurityServiceTest {

    private SecurityService service;

    private final RepositoryUtils utils = mock(RepositoryUtils.class);

    private Member member;
    private SessionMemberDto sessionMemberDto;
    private Question question;
    private Answer answer;
    private Comment comment;
    private Bounty bounty;

    @BeforeEach
    void setUp() {
        service = new SecurityServiceImpl(utils);

        member = getMember();
        sessionMemberDto = getSessionMemberDto();
        question = getQuestion();
            question.setAuthor(2L);
        answer = getAnswer();
            answer.setAuthor(2L);
        comment = getComment();
            comment.setAuthor(2L);
        bounty = getBounty();
            bounty.setCreatorId(2L);

        when(utils.getMemberFromDB(ID)).thenReturn(member);
        when(utils.getQuestionFromDB(ID)).thenReturn(question);
        when(utils.getAnswerFromDB(ID)).thenReturn(answer);
        when(utils.getCommentFromDB(ID)).thenReturn(comment);
        when(utils.getBountyFromDB(ID)).thenReturn(bounty);

    }

//    Tests for checkModer()

    @Test
    void whenCheckModerAndAccountIsNotActiveThenThrownMemberStatusException() {
        //given
        member.getAccount().setStatus(AccountStatus.BANNED);
        //then
        assertThatThrownBy(() -> service.checkModer(sessionMemberDto))
                .isInstanceOf(MemberStatusException.class);
    }

    @Test
    void whenCheckModerAndRoleIsUserThenThrownMemberRoleException() {
        //given
        member.getAccount().setRole(AccountRole.USER);
        //then
        assertThatThrownBy(() -> service.checkModer(sessionMemberDto))
                .isInstanceOf(MemberRoleException.class);
    }

    @Test
    void whenCheckModerAndRoleIsModer() {
        //given
        member.getAccount().setRole(AccountRole.MODERATOR);
        //then
        assertThatCode(() -> service.checkModer(sessionMemberDto))
                .doesNotThrowAnyException();
    }

    @Test
    void whenCheckModerAndRoleIsAdmin() {
        //given
        member.getAccount().setRole(AccountRole.ADMIN);
        //then
        assertThatCode(() -> service.checkModer(sessionMemberDto))
                .doesNotThrowAnyException();
    }


//  Tests for checkAdminAndReturnMember()

    @Test
    void whenCheckAdminAndAccountIsNotActiveThenThrownMemberStatusException() {
        //given
        member.getAccount().setStatus(AccountStatus.BANNED);
        //then
        assertThatThrownBy(() -> service.checkAdminAndReturnMember(sessionMemberDto))
                .isInstanceOf(MemberStatusException.class);
    }

    @Test
    void whenCheckAdminAndRoleIsUserThenThrownMemberRoleException() {
        //given
        member.getAccount().setRole(AccountRole.USER);
        //then
        assertThatThrownBy(() -> service.checkAdminAndReturnMember(sessionMemberDto))
                .isInstanceOf(MemberRoleException.class);
    }

    @Test
    void whenCheckAdminThenReturnMember() {
        //given
        member.getAccount().setRole(AccountRole.ADMIN);
        //when
        var result = service.checkAdminAndReturnMember(sessionMemberDto);
        //then
        assertEquals(member, result);
    }

//  Tests for checkStatusAndReturnMember()

    @Test
    void whenCheckMemberStatusAndAccountIsNotActiveThenThrownMemberStatusException() {
        //given
        member.getAccount().setStatus(AccountStatus.BANNED);
        //then
        assertThatThrownBy(() -> service.checkStatusAndReturnMember(sessionMemberDto))
                .isInstanceOf(MemberStatusException.class);
    }

    @Test
    void whenCheckMemberStatusThenReturnMember() {
        //when
        var result = service.checkStatusAndReturnMember(sessionMemberDto);
        //then
        assertEquals(member, result);
    }

//  Tests for checkRightOfMemberAndReturnQuestion()

    @Test
    void whenCheckRightForQuestionAndMemberIsNotActiveThenThrownMemberStatusException() {
        //given
        member.getAccount().setStatus(AccountStatus.BANNED);
        //then
        assertThatThrownBy(() -> service.checkRightOfMemberAndReturnQuestion(sessionMemberDto, ID))
                .isInstanceOf(MemberStatusException.class);
    }

    @Test
    void whenCheckRightForQuestionAndAuthorIsAnotherThenThrownNotAuthorOfThePostException() {
        //then
        assertThatThrownBy(() -> service.checkRightOfMemberAndReturnQuestion(sessionMemberDto, ID))
                .isInstanceOf(NotAuthorOfThePostException.class);
    }

    @Test
    void whenCheckRightForQuestionAndMemberIsModerThenReturnQuestion() {
        //given
        member.getAccount().setRole(AccountRole.MODERATOR);
        //when
        var result = service.checkRightOfMemberAndReturnQuestion(sessionMemberDto, ID);
        //then
        assertEquals(question, result);
    }

    @Test
    void whenCheckRightForQuestionAndMemberIsAdminThenReturnQuestion() {
        //given
        member.getAccount().setRole(AccountRole.ADMIN);
        //when
        var result = service.checkRightOfMemberAndReturnQuestion(sessionMemberDto, ID);
        //then
        assertEquals(question, result);
    }

    @Test
    void whenCheckRightForQuestionAndMemberIsAuthorThenReturnQuestion() {
        //given
        question.setAuthor(ID);
        //when
        var result = service.checkRightOfMemberAndReturnQuestion(sessionMemberDto, ID);
        //then
        assertEquals(question, result);
    }

//    Tests for checkRightOfMemberAndReturnComment()

    @Test
    void whenCheckRightForCommentAndMemberIsNotActiveThenThrownMemberStatusException() {
        //given
        member.getAccount().setStatus(AccountStatus.BANNED);
        //then
        assertThatThrownBy(() -> service.checkRightOfMemberAndReturnComment(sessionMemberDto, ID))
                .isInstanceOf(MemberStatusException.class);
    }

    @Test
    void whenCheckRightForCommentAndAuthorIsAnotherThenThrownNotAuthorOfThePostException() {
        //then
        assertThatThrownBy(() -> service.checkRightOfMemberAndReturnComment(sessionMemberDto, ID))
                .isInstanceOf(NotAuthorOfThePostException.class);
    }

    @Test
    void whenCheckRightForCommentAndMemberIsModerThenReturnComment() {
        //given
        member.getAccount().setRole(AccountRole.MODERATOR);
        //when
        var result = service.checkRightOfMemberAndReturnComment(sessionMemberDto, ID);
        //then
        assertEquals(comment, result);
    }

    @Test
    void whenCheckRightForCommentAndMemberIsAdminThenReturnComment() {
        //given
        member.getAccount().setRole(AccountRole.ADMIN);
        //when
        var result = service.checkRightOfMemberAndReturnComment(sessionMemberDto, ID);
        //then
        assertEquals(comment, result);
    }

    @Test
    void whenCheckRightForCommentAndMemberIsAuthorThenReturnComment() {
        //given
        comment.setAuthor(ID);
        //when
        var result = service.checkRightOfMemberAndReturnComment(sessionMemberDto, ID);
        //then
        assertEquals(comment, result);
    }

//    Tests for checkRightOfMemberAndReturnAnswer()

    @Test
    void whenCheckRightForAnswerAndMemberIsNotActiveThenThrownMemberStatusException() {
        //given
        member.getAccount().setStatus(AccountStatus.BANNED);
        //then
        assertThatThrownBy(() -> service.checkRightOfMemberAndReturnAnswer(sessionMemberDto, ID))
                .isInstanceOf(MemberStatusException.class);
    }

    @Test
    void whenCheckRightForAnswerAndAuthorIsAnotherThenThrownNotAuthorOfThePostException() {
        //then
        assertThatThrownBy(() -> service.checkRightOfMemberAndReturnAnswer(sessionMemberDto, ID))
                .isInstanceOf(NotAuthorOfThePostException.class);
    }

    @Test
    void whenCheckRightForAnswerAndMemberIsModerThenReturnAnswer() {
        //given
        member.getAccount().setRole(AccountRole.MODERATOR);
        //when
        var result = service.checkRightOfMemberAndReturnAnswer(sessionMemberDto, ID);
        //then
        assertEquals(answer, result);
    }

    @Test
    void whenCheckRightForAnswerAndMemberIsAdminThenReturnAnswer() {
        //given
        member.getAccount().setRole(AccountRole.ADMIN);
        //when
        var result = service.checkRightOfMemberAndReturnAnswer(sessionMemberDto, ID);
        //then
        assertEquals(answer, result);
    }

    @Test
    void whenCheckRightForAnswerAndMemberIsAuthorThenReturnAnswer() {
        //given
        answer.setAuthor(ID);
        //when
        var result = service.checkRightOfMemberAndReturnAnswer(sessionMemberDto, ID);
        //then
        assertEquals(answer, result);
    }

//    Tests for checkRightOfMemberAndReturnBounty()

    @Test
    void whenCheckRightForBountyAndMemberIsNotActiveThenThrownMemberStatusException() {
        //given
        member.getAccount().setStatus(AccountStatus.BANNED);
        //then
        assertThatThrownBy(() -> service.checkRightOfMemberAndReturnBounty(sessionMemberDto, ID))
                .isInstanceOf(MemberStatusException.class);
    }

    @Test
    void whenCheckRightForBountyAndAuthorIsAnotherThenThrownNotAuthorOfThePostException() {
        //then
        assertThatThrownBy(() -> service.checkRightOfMemberAndReturnBounty(sessionMemberDto, ID))
                .isInstanceOf(NotAuthorOfThePostException.class);
    }

    @Test
    void whenCheckRightForBountyAndMemberIsModerThenReturnBounty() {
        //given
        member.getAccount().setRole(AccountRole.MODERATOR);
        //when
        var result = service.checkRightOfMemberAndReturnBounty(sessionMemberDto, ID);
        //then
        assertEquals(bounty, result);
    }

    @Test
    void whenCheckRightForBountyAndMemberIsAdminThenReturnBounty() {
        //given
        member.getAccount().setRole(AccountRole.ADMIN);
        //when
        var result = service.checkRightOfMemberAndReturnBounty(sessionMemberDto, ID);
        //then
        assertEquals(bounty, result);
    }

    @Test
    void whenCheckRightForBountyAndMemberIsAuthorThenReturnBounty() {
        //given
        bounty.setCreatorId(ID);
        //when
        var result = service.checkRightOfMemberAndReturnBounty(sessionMemberDto, ID);
        //then
        assertEquals(bounty, result);
    }
}
