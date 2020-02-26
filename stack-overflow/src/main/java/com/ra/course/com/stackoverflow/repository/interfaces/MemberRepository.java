package com.ra.course.com.stackoverflow.repository.interfaces;


import com.ra.course.com.stackoverflow.dto.MemberSaveDto;
import com.ra.course.com.stackoverflow.entity.implementations.Member;

import java.util.Optional;

public interface MemberRepository extends GeneralRepository<MemberSaveDto, Member> {
    Optional<Member> findByMemberName(String name);
}
