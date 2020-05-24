package com.ra.course.com.stackoverflow.service.storage;

import com.ra.course.com.stackoverflow.dto.LogInDto;
import com.ra.course.com.stackoverflow.dto.MemberDto;
import com.ra.course.com.stackoverflow.dto.RegisterDto;
import com.ra.course.com.stackoverflow.dto.UpdateDto;
import com.ra.course.com.stackoverflow.dto.mapper.impl.MemberMapper;
import com.ra.course.com.stackoverflow.entity.Account;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.enums.AccountStatus;
import com.ra.course.com.stackoverflow.exception.service.AlreadyExistAccountException;
import com.ra.course.com.stackoverflow.exception.service.LoginMemberException;
import com.ra.course.com.stackoverflow.exception.service.MemberNotFoundException;
import com.ra.course.com.stackoverflow.exception.service.WrongPasswordException;
import com.ra.course.com.stackoverflow.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MemberStorageServiceTest {

    private MemberStorageService memberStorageService;

    private Member member;
    private MemberDto dto;
    private LogInDto logInDto;
    private RegisterDto registerDto;
    private UpdateDto updateDto;
    private MemberRepository mockedMemberRepository;
    private MemberMapper memberMapper;

    private final String EMAIL = "email@gmail.com";
    private final String PASSWORD = "Password!111";
    private final String NAME = "Member name";
    private final Long ID = 1L;

    @BeforeEach
    void setUp() {
        mockedMemberRepository = mock(MemberRepository.class);
        memberMapper = mock(MemberMapper.class);
        memberStorageService = new MemberStorageServiceImpl(mockedMemberRepository, memberMapper);
        member = mockMember(ID);
        dto = mockDto(ID);
        logInDto = new LogInDto(EMAIL, PASSWORD);
        registerDto = new RegisterDto(NAME, EMAIL, PASSWORD);
        updateDto = new UpdateDto(ID, NAME, PASSWORD);
        when(mockedMemberRepository.findById(ID)).thenReturn(Optional.of(member));
        when(mockedMemberRepository.findById(99)).thenReturn(Optional.empty());
        when(mockedMemberRepository.findByEmail(EMAIL)).thenReturn(Optional.of(member));
        when(memberMapper.dtoFromEntity(member)).thenReturn(dto);
        when(memberMapper.entityFromRegisterDto(registerDto)).thenReturn(member);
    }

    @Test
    @DisplayName("MemberStorageService return dto of member when find by id")
    public void whenFindByIdThenReturnMemberDto(){
        assertEquals(dto, memberStorageService.findMemberById(ID));
    }

    @Test
    @DisplayName("MemberStorageService return dto of member when login is true")
    public void whenLoginThenReturnDto(){
        assertEquals(dto, memberStorageService.loginMember(logInDto));
    }

    @Test
    @DisplayName("MemberStorageService throws LoginException when not data in repository")
    public void whenLoginWithWrongEmailThenThrowsLoginException(){
        //given
        logInDto.setEmail("wrong@gmail.com");
        when(mockedMemberRepository.findByEmail("wrong@gmail.com")).thenReturn(Optional.empty());
        //when
        //then
        assertThatThrownBy(() -> memberStorageService.loginMember(logInDto))
                .isInstanceOf(LoginMemberException.class)
        .hasMessage("No account with email wrong@gmail.com");
    }
    @Test
    @DisplayName("MemberStorageService throws LoginException when status of account in not active")
    public void whenLoginWithNonActiveStatusThenThrowsLoginException(){
        //given
        member.getAccount().setStatus(AccountStatus.BANNED);
        when(mockedMemberRepository.findByEmail(EMAIL)).thenReturn(Optional.of(member));
        //then
        assertThatThrownBy(() -> memberStorageService.loginMember(logInDto))
                .isInstanceOf(LoginMemberException.class)
        .hasMessage("OoUPS! Account status is BANNED");
    }
    @Test
    @DisplayName("MemberStorageService login throws WrongPasswordException")
    public void whenLoginWithWrongPasswordThenThrowsWrongPasswordException(){
        //given
        logInDto.setPassword("wrongPassword!111");
        //then
        assertThatThrownBy(() -> memberStorageService.loginMember(logInDto))
                .isInstanceOf(WrongPasswordException.class);
    }

    @Test
    @DisplayName("MemberStorageService delete throws WrongPasswordException")
    public void whenDeleteWithWrongPasswordThenThrowsWrongPasswordException(){

        assertThatThrownBy(() -> memberStorageService.deleteMember(ID, "wrongPassword!111"))
                .isInstanceOf(WrongPasswordException.class);
    }
    @Test
    @DisplayName("MemberStorageService delete member")
    public void whenDeleteMember(){
        //when
        memberStorageService.deleteMember(ID, PASSWORD);
        //then
        verify(mockedMemberRepository, times(1)).delete(member);
    }
    @Test
    @DisplayName("MemberStorageService update throws WrongPasswordException")
    public void whenUpdateWithWrongPasswordThenThrowsWrongPasswordException(){

        assertThatThrownBy(() -> memberStorageService.updateMember(updateDto, "wrongPassword!111"))
                .isInstanceOf(WrongPasswordException.class);
    }

    @Test
    @DisplayName("MemberStorageService update member then ok")
    public void whenUpdateMember(){
        //when
        memberStorageService.updateMember(updateDto, PASSWORD);
        //then
        verify(mockedMemberRepository, times(1)).update(member);
    }

    @Test
    @DisplayName("MemberStorageService save member then return member  dto")
    public void whenSaveMemberThenReturnMemberDto(){
        //given
        when(mockedMemberRepository.findByEmail(member.getAccount().getEmail())).thenReturn(Optional.empty());
        when(mockedMemberRepository.save(member)).thenReturn(member);
        //when
        var result = memberStorageService.registerMember(registerDto);
        //then
        assertEquals(dto,result);
    }

    @Test
    @DisplayName("MemberStorageService save member then throws AlreadyExistException")
    public void whenSaveMemberThenThrowsAlreadyExistException(){

        assertThatThrownBy(() -> memberStorageService.registerMember(registerDto))
                .isInstanceOf(AlreadyExistAccountException.class);
    }

    @Test
    @DisplayName("MemberStorageService find member by name then return list")
    public void whenFindByNameThenReturnListOfMembers(){
        //given
        var listMember = new ArrayList<Member>(){{ add(member); }};
        var listDto = new ArrayList<MemberDto>(){{ add(dto); }};
        when(mockedMemberRepository.findByMemberName("name")).thenReturn(listMember);
        when(memberMapper.dtoFromEntity(listMember)).thenReturn(listDto);

        //when
        var listMemberFromDb = memberStorageService.findByMemberName("name");
        //then
        assertEquals(listDto, listMemberFromDb);
    }
    @Test
    @DisplayName("MemberStorageService find by id throws MemberNotFoundException")
    public void whenFindByIdThrowsMemberNotFoundException(){
        assertThatThrownBy(() -> memberStorageService.findMemberById(99))
                .isInstanceOf(MemberNotFoundException.class);
    }
    @Test
    @DisplayName("MemberStorageService delete throws MemberNotFoundException")
    public void whenDeleteThrowsMemberNotFoundException(){
        assertThatThrownBy(() -> memberStorageService.deleteMember(99, PASSWORD))
                .isInstanceOf(MemberNotFoundException.class);
    }
    @Test
    @DisplayName("MemberStorageService update throws MemberNotFoundException")
    public void whenUpdateThrowsMemberNotFoundException(){
        //given
        updateDto.setId(99L);
        assertThatThrownBy(() -> memberStorageService.updateMember(updateDto, PASSWORD))
                .isInstanceOf(MemberNotFoundException.class);
    }


    private Member mockMember(long idMember) {
        var account = Account.builder()
                .id(idMember)
                .password(PASSWORD)
                .email(EMAIL)
                .name(NAME).build();
        return Member.builder()
                .account(account).build();
    }
    private MemberDto mockDto(long idMember) {
        return MemberDto.builder()
                .id(idMember)
                .email(EMAIL)
                .name(NAME).build();
    }
}
