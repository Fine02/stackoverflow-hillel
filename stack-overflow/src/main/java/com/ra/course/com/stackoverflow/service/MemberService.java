package com.ra.course.com.stackoverflow.service;

import com.ra.course.com.stackoverflow.exception.repository.InternalServerErrorException;

public interface MemberService<Q> {
    void postQuestion(Q questionDto) throws InternalServerErrorException;
}
