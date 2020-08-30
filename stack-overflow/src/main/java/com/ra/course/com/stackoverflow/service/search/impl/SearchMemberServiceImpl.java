package com.ra.course.com.stackoverflow.service.search.impl;

import com.ra.course.com.stackoverflow.dto.member.MemberDto;
import com.ra.course.com.stackoverflow.dto.mapper.MemberMapper;
import com.ra.course.com.stackoverflow.repository.MemberRepository;
import com.ra.course.com.stackoverflow.service.search.SearchMemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@AllArgsConstructor
@Service
public class SearchMemberServiceImpl implements SearchMemberService {

    private final MemberRepository memberRepository;

    @Override
    public List<MemberDto> findByMemberName(final String name) {
        final var memberList = memberRepository.findByMemberName(name.toLowerCase(Locale.US));
        return MemberMapper.MAPPER.toMemberDto(memberList);
    }

}
