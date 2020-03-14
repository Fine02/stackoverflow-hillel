package com.ra.course.com.stackoverflow.service.member;

import com.ra.course.com.stackoverflow.entity.Question;

public interface MemberService<Q> {
    Question postQuestion(Q question);
}
