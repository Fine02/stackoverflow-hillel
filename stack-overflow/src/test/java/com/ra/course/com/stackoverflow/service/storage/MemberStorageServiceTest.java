package com.ra.course.com.stackoverflow.service.storage;

import com.ra.course.com.stackoverflow.dto.MemberDto;
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
    private MemberRepository mockedMemberRepository;
    private MemberMapper mapper;

    @BeforeEach
    void setUp() {
        mockedMemberRepository = mock(MemberRepository.class);
        mapper = mock(MemberMapper.class);
        memberStorageService = new MemberStorageServiceImpl(mockedMemberRepository, mapper);
        member = mockMember(1L);
        dto = mockDto(1L);
        when(mapper.dtoFromEntity(member)).thenReturn(dto);
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
        when(mockedMemberRepository.findByEmail("email")).thenReturn(Optional.of(member));
        //when
        var result = memberStorageService.loginMember("email", "password");
        //then
        assertEquals(dto, result);
    }
    @Test
    @DisplayName("MemberStorageService throws LoginException when not data in repository")
    public void whenLoginWithWrongEmailThenThrowsLoginException(){
        //given
        when(mockedMemberRepository.findByEmail("wrong email")).thenReturn(Optional.empty());
        //when
        //then
        assertThatThrownBy(() -> memberStorageService.loginMember("wrong email", "password"))
                .isInstanceOf(LoginException.class)
        .hasMessage("No account with email wrong email");
    }
    @Test
    @DisplayName("MemberStorageService throws LoginException when wrong password")
    public void whenLoginWithWrongPasswordThenThrowsLoginException(){
        //given
        when(mockedMemberRepository.findByEmail("email")).thenReturn(Optional.of(member));
        //when
        //then
        assertThatThrownBy(() -> memberStorageService.loginMember("email", "wrong password"))
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
        var newDto = mockDto(0);
        var newMember = mockMember(0);
        when(mapper.entityFromDto(newDto)).thenReturn(newMember);
        when(mockedMemberRepository.findByEmail(member.getAccount().getEmail())).thenReturn(Optional.empty());
        when(mockedMemberRepository.save(newMember)).thenReturn(member);
        //when
        var result = memberStorageService.saveMemberToDB(newDto);
        //then
        assertEquals(dto,result);
    }
    @Test
    @DisplayName("MemberStorageService save member then throws AlreadyExistException")
    public void whenSaveMemberThenThrowsAlreadyExistException(){
        //given
        when(mapper.entityFromDto(dto)).thenReturn(member);
        when(mockedMemberRepository.findByEmail(member.getAccount().getEmail())).thenReturn(Optional.of(member));
        //when
        //then
        assertThatThrownBy(() -> memberStorageService.saveMemberToDB(dto))
                .isInstanceOf(AlreadyExistAccountException.class);
    }
    @Test
    @DisplayName("MemberStorageService find member by name then return list")
    public void whenFindByNameThenReturnListOfMembers(){
        //given
        var listMember = new ArrayList<Member>(){{ add(member); }};
        var listDto = new ArrayList<MemberDto>(){{ add(dto); }};
        when(mockedMemberRepository.findByMemberName("name")).thenReturn(listMember);
        when(mapper.dtoFromEntity(listMember)).thenReturn(listDto);

        //when
        var listMemberFromDb = memberStorageService.findByMemberName("name");
        //then
        assertEquals(listDto, listMemberFromDb);
    }



    private Member mockMember(long idMember) {
        var account = Account.builder()
                .id(idMember)
                .password("password")
                .email("email")
                .name("name").build();
        return Member.builder()
                .account(account).build();
    }
    private MemberDto mockDto(long idMember) {
        return MemberDto.builder()
                .id(idMember)
                .password("password")
                .email("email")
                .name("name").build();
    }
}
