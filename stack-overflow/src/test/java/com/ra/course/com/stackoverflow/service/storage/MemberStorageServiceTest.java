package com.ra.course.com.stackoverflow.service.storage;

import com.ra.course.com.stackoverflow.dto.LogInDto;
import com.ra.course.com.stackoverflow.dto.MemberDto;
import com.ra.course.com.stackoverflow.dto.RegisterDto;
import com.ra.course.com.stackoverflow.dto.mapper.impl.MemberMapper;
import com.ra.course.com.stackoverflow.entity.Account;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.exception.service.AlreadyExistAccountException;
import com.ra.course.com.stackoverflow.exception.service.LoginException;
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
    private MemberRepository mockedMemberRepository;
    private MemberMapper memberMapper;

    @BeforeEach
    void setUp() {
        mockedMemberRepository = mock(MemberRepository.class);
        memberMapper = mock(MemberMapper.class);
        memberStorageService = new MemberStorageServiceImpl(mockedMemberRepository, memberMapper);
        member = mockMember(1L);
        dto = mockDto(1L);
        registerDto = new RegisterDto("Member Name", "email@gmail.com", "Password!111");
        when(memberMapper.dtoFromEntity(member)).thenReturn(dto);
        when(memberMapper.entityFromRegisterDto(registerDto)).thenReturn(member);
    }

    @Test
    @DisplayName("MemberStorageService return dto of member when find by id")
    public void whenFindByIdThenReturnMemberDto(){
        //given
        when(mockedMemberRepository.findById(1L)).thenReturn(Optional.of(member));
        //when
        var result = memberStorageService.findMemberById(1L);
        //then
        assertEquals(dto, result);
    }

    @Test
    @DisplayName("MemberStorageService return dto of member when login is true")
    public void whenLoginThenReturnDto(){
        //given
        logInDto = new LogInDto("email@gmail.com", "Password!111");
        when(mockedMemberRepository.findByEmail("email@gmail.com")).thenReturn(Optional.of(member));
        //when
        var result = memberStorageService.loginMember(logInDto);
        //then
        assertEquals(dto, result);
    }
    @Test
    @DisplayName("MemberStorageService throws LoginException when not data in repository")
    public void whenLoginWithWrongEmailThenThrowsLoginException(){
        //given
        logInDto = new LogInDto("wrong@gmail.com", "Password!111");
        when(mockedMemberRepository.findByEmail("wrong@gmail.com")).thenReturn(Optional.empty());
        //when
        //then
        assertThatThrownBy(() -> memberStorageService.loginMember(logInDto))
                .isInstanceOf(LoginException.class)
        .hasMessage("No account with email wrong@gmail.com");
    }
    @Test
    @DisplayName("MemberStorageService throws LoginException when wrong password")
    public void whenLoginWithWrongPasswordThenThrowsLoginException(){
        //given
        logInDto = new LogInDto("email@gmail.com", "wrongPassword!111");
        when(mockedMemberRepository.findByEmail("email@gmail.com")).thenReturn(Optional.of(member));
        //when
        //then
        assertThatThrownBy(() -> memberStorageService.loginMember(logInDto))
                .isInstanceOf(LoginException.class)
                .hasMessage("Wrong password, try once more!");
    }

    @Test
    @DisplayName("MemberStorageService delete member")
    public void whenDeleteMember(){
        //when
        memberStorageService.deleteMember(member);
        //then
        verify(mockedMemberRepository, times(1)).delete(member);
    }
    @Test
    @DisplayName("MemberStorageService update member")
    public void whenUpdateMember(){
        //when
        memberStorageService.updateMember(member);
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
        var result = memberStorageService.saveMemberToDB(registerDto);
        //then
        assertEquals(dto,result);
    }
    @Test
    @DisplayName("MemberStorageService save member then throws AlreadyExistException")
    public void whenSaveMemberThenThrowsAlreadyExistException(){
        //given
        when(mockedMemberRepository.findByEmail(member.getAccount().getEmail())).thenReturn(Optional.of(member));
        //when
        //then
        assertThatThrownBy(() -> memberStorageService.saveMemberToDB(registerDto))
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



    private Member mockMember(long idMember) {
        var account = Account.builder()
                .id(idMember)
                .password("Password!111")
                .email("email@gmail.com")
                .name("Member name").build();
        return Member.builder()
                .account(account).build();
    }
    private MemberDto mockDto(long idMember) {
        return MemberDto.builder()
                .id(idMember)
                .email("email@gmail.com")
                .name("Member name").build();
    }
}
