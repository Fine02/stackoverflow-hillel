package com.ra.course.com.stackoverflow.service.search;

import com.ra.course.com.stackoverflow.dto.member.MemberDto;

import java.util.List;

public interface SearchMemberService {

    List<MemberDto> findByMemberName(String name);

}
