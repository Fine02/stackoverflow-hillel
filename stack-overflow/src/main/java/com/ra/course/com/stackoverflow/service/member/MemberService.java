package com.ra.course.com.stackoverflow.service.member;

import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.exception.service.InternalServerErrorException;

public interface MemberService<Q> {
    Question postQuestion(Q question) throws InternalServerErrorException;
}
