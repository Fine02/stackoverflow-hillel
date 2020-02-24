package com.ra.course.com.stackoverflow.service;

import com.ra.course.com.stackoverflow.dto.QuestionDto;
import com.ra.course.com.stackoverflow.entity.implementation.Member;
import com.ra.course.com.stackoverflow.exception.service.ServiceException;

public interface MemberService {
    void createQuestion(QuestionDto questionDto, Member member) throws ServiceException;
}
