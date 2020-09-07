package com.ra.course.com.stackoverflow.service.member;

import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.repository.MemberRepository;
import com.ra.course.com.stackoverflow.service.member.impl.AdminServiceImpl;
import com.ra.course.com.stackoverflow.service.system.SecurityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.ra.course.com.stackoverflow.utils.Constants.ID;
import static com.ra.course.com.stackoverflow.utils.DtoCreationUtils.getSessionMemberDto;
import static com.ra.course.com.stackoverflow.utils.EntityCreationUtil.getMember;
import static org.mockito.Mockito.*;

public class AdminServiceTest {

    private AdminService service;

    private final MemberRepository data = mock(MemberRepository.class);
    private final SecurityService securityService = mock(SecurityService.class);

    private Member member;
    private SessionMemberDto sessionMemberDto;

    @BeforeEach
    void setUp() {
        service = new AdminServiceImpl(data, securityService);
        member = getMember();
        sessionMemberDto = getSessionMemberDto();
        when(securityService.checkAdminAndReturnMember(sessionMemberDto)).thenReturn(member);
    }

    @Test
    void whenBlockMember() {
        //when
        service.blockMember(ID, sessionMemberDto);
        //then
        verify(data).update(member);
    }

    @Test
    void whenUnblockMember() {
        //when
        service.unblockMember(ID, sessionMemberDto);
        //then
        verify(data).update(member);
    }

    @Test
    void whenSetMemberAsModer() {
        //when
        service.setMemberAsModer(ID, sessionMemberDto);
        //then
        verify(data).update(member);
    }

    @Test
    void whenSetMemberAsAdmin() {
        //when
        service.setMemberAsAdmin(ID, sessionMemberDto);
        //then
        verify(data).update(member);
    }
    @Test
    void whenSetMemberAsUser() {
        //when
        service.setMemberAsUser(ID, sessionMemberDto);
        //then
        verify(data).update(member);
    }
}
