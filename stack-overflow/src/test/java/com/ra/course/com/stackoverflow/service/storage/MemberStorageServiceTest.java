package com.ra.course.com.stackoverflow.service.storage;

import com.ra.course.com.stackoverflow.entity.Account;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.exception.service.AlreadyExistAccount;
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
    private MemberRepository mockedMemberRepository;

    @BeforeEach
    void setUp() {
        mockedMemberRepository = mock(MemberRepository.class);
        memberStorageService = new MemberStorageServiceImpl(mockedMemberRepository);
        member = mockMember(1L);
    }

    @Test
    @DisplayName("MemberStorageService return optional of member when find by id")
    public void whenFindByIdThenReturnOptional(){
        //given
        when(mockedMemberRepository.findById(1L)).thenReturn(Optional.of(member));
        //when
        var optional = memberStorageService.findMemberById(1L);
        //then
        assertEquals(Optional.of(member), optional);
    }

    @Test
    @DisplayName("MemberStorageService return optional of member when find by email")
    public void whenFindByEmailThenReturnOptional(){
        //given
        when(mockedMemberRepository.findByEmail("email")).thenReturn(Optional.of(member));
        //when
        var optional = memberStorageService.findMemberByEmail("email");
        //then
        assertEquals(Optional.of(member), optional);
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
    @DisplayName("MemberStorageService save member then return member with id")
    public void whenSaveMemberThenReturnMemberWithId(){
        //given
        var newMember = mockMember(0);
        when(mockedMemberRepository.save(newMember)).thenReturn(member);
        //when
        var memberFromDb = memberStorageService.saveMemberToDB(newMember);
        //then
        assertEquals(1L, memberFromDb.getAccount().getId());
    }
    @Test
    @DisplayName("MemberStorageService save member then return member with id")
    public void whenSaveMemberThenThrowsAlreadyExistException(){
        //given
        when(mockedMemberRepository.findByEmail(member.getAccount().getEmail())).thenReturn(Optional.of(member));
        //when
        //then
        assertThatThrownBy(() -> memberStorageService.saveMemberToDB(member))
                .isInstanceOf(AlreadyExistAccount.class);
    }
    @Test
    @DisplayName("MemberStorageService find member by name then return list")
    public void whenFindByNameThenReturnListOfMembers(){
        //given
        var listMember = new ArrayList<Member>(){{ add(member); }};

        when(mockedMemberRepository.findByMemberName("name")).thenReturn(listMember);
        //when
        var listMemberFromDb = memberStorageService.findByMemberName("name");
        //then
        assertEquals(listMember, listMemberFromDb);
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
}
