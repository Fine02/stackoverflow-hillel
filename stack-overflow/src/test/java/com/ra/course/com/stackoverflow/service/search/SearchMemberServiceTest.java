package com.ra.course.com.stackoverflow.service.search;

import com.ra.course.com.stackoverflow.dto.member.MemberDto;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.repository.MemberRepository;
import com.ra.course.com.stackoverflow.service.RepositoryUtils;
import com.ra.course.com.stackoverflow.service.search.impl.SearchMemberServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.ra.course.com.stackoverflow.utils.Constants.ID;
import static com.ra.course.com.stackoverflow.utils.Constants.NAME;
import static com.ra.course.com.stackoverflow.utils.DtoCreationUtils.getMemberDto;
import static com.ra.course.com.stackoverflow.utils.EntityCreationUtil.getMember;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SearchMemberServiceTest {

    private SearchMemberService service;

    private Member member;
    private MemberDto memberDto;

    //mock inner classes
    private final MemberRepository memberData = mock(MemberRepository.class);

    @BeforeEach
    void setUp() {
        member = getMember();
        memberDto = getMemberDto();
        service = new SearchMemberServiceImpl(memberData);
    }

    @Test
    void whenFindByMemberNameThenReturnListWithMember() {
        //given
        when(memberData.findByMemberName(NAME.toLowerCase())).thenReturn(List.of(member));
        //when
        var result = service.findByMemberName(NAME);
        //then
        assertEquals(List.of(memberDto), result);
    }

    @Test
    void whenReturnEmptyList() {
        //given
        when(memberData.findByMemberName(NAME.toLowerCase())).thenReturn(new ArrayList<>());
        //when
        var result = service.findByMemberName(NAME);
        //then
        assertTrue(result.isEmpty());
    }
}
