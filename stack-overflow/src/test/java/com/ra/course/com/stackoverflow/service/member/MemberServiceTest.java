package com.ra.course.com.stackoverflow.service.member;

import com.ra.course.com.stackoverflow.dto.member.*;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.enums.AccountStatus;
import com.ra.course.com.stackoverflow.exception.service.AlreadyExistAccountException;
import com.ra.course.com.stackoverflow.exception.service.LoginMemberException;
import com.ra.course.com.stackoverflow.exception.service.WrongPasswordException;
import com.ra.course.com.stackoverflow.repository.MemberRepository;
import com.ra.course.com.stackoverflow.service.RepositoryUtils;
import com.ra.course.com.stackoverflow.service.member.impl.MemberServiceImpl;
import com.ra.course.com.stackoverflow.service.system.SecurityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.ra.course.com.stackoverflow.utils.Constants.*;
import static com.ra.course.com.stackoverflow.utils.DtoCreationUtils.*;
import static com.ra.course.com.stackoverflow.utils.EntityCreationUtil.getMember;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MemberServiceTest {

    private MemberService service;

    private final MemberRepository data = mock(MemberRepository.class);
    private final SecurityService securityService = mock(SecurityService.class);
    private final RepositoryUtils utils = mock(RepositoryUtils.class);

    private Member member;
    private SessionMemberDto sessionMember;
    private LogInDto logInDto;
    private UpdateDto updateDto;
    private RegisterDto registerDto;
    private MemberDto memberDto;

    @BeforeEach
    void setUp() {
        member = getMember();
        sessionMember = getSessionMemberDto();
        logInDto = getLogInDto();
        updateDto = getUpdateDto();
        registerDto = getRegisterDto();
        memberDto = getMemberDto();

        when(data.findByEmail(EMAIL)).thenReturn(Optional.of(member));
        when(securityService.checkStatusAndReturnMember(sessionMember)).thenReturn(member);
        service = new MemberServiceImpl(data, securityService, utils);
    }

    @Test
    void whenLoginMemberAndNoMemberFromDbThenThrownLoginException() {
        //given
        when(data.findByEmail(EMAIL)).thenReturn(Optional.empty());
        //then
        assertThatThrownBy(() -> service.loginMember(logInDto))
                .isInstanceOf(LoginMemberException.class)
                .hasMessage("No account with email " + EMAIL);
    }

    @Test
    void whenLoginMemberAndWrongStatusThenThrowLoginException() {
        //given
        member.getAccount().setStatus(AccountStatus.BANNED);
        //then
        assertThatThrownBy(() -> service.loginMember(logInDto))
                .isInstanceOf(LoginMemberException.class)
                .hasMessage("OoUPS! Account status is " + AccountStatus.BANNED.toString());
    }

    @Test
    void whenLoginMemberAndWrongPasswordThenThrowWrongPasswordException() {
        //given
        logInDto.setPassword("Wrong password");
        //then
        assertThatThrownBy(() -> service.loginMember(logInDto))
                .isInstanceOf(WrongPasswordException.class);
    }

    @Test
    void whenLoginThenReturnSessionDto() {
        //when
        var result = service.loginMember(logInDto);
        //then
        assertEquals(sessionMember, result);
    }

    @Test
    void whenDeleteMemberAndWrongPasswordThenThrownWrongPasswordException() {
        //then
        assertThatThrownBy(() -> service.deleteMember(sessionMember, "wrong password"))
                .isInstanceOf(WrongPasswordException.class);
    }

    @Test
    void whenDeleteMember() {
        //when
        service.deleteMember(sessionMember, PASSWORD);
        //then
        verify(data).delete(member);
    }

    @Test
    void whenUpdateMemberAndWrongPasswordThenThrownWrongPasswordException() {
        //then
        assertThatThrownBy(() -> service.updateMember(updateDto, sessionMember,"wrong password"))
                .isInstanceOf(WrongPasswordException.class);
    }

    @Test
    void whenUpdateMember() {
        //given
        updateDto.setName("New name");
        //when
        service.updateMember(updateDto, sessionMember, PASSWORD);
        //then
        verify(data).update(member);
    }

    @Test
    void whenRegisterMemberAndSuchEmailAlreadyExistThenThrownAlreadyExistException() {
        //then
        assertThatThrownBy(() -> service.registerMember(registerDto))
                .isInstanceOf(AlreadyExistAccountException.class);
    }

    @Test
    void whenRegisterMemberThenReturnSessionDto() {
        //given
        var newMember = getMember();
        newMember.getAccount().setId(null);

        when(data.findByEmail(EMAIL)).thenReturn(Optional.empty());
        when(data.save(newMember)).thenReturn(member);
        //when
        var result = service.registerMember(registerDto);
        //then
        assertEquals(sessionMember, result);
    }


    @Test
    void whenGetMemberByIdThenReturnMember() {
        //given
        when(utils.getMemberFromDB(ID)).thenReturn(member);
        //when
        var result = service.getMemberById(ID);
        //then
        assertEquals(memberDto, result);
    }
}


