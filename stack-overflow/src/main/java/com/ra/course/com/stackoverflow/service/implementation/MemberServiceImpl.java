package com.ra.course.com.stackoverflow.service.implementation;

import com.ra.course.com.stackoverflow.entity.implementations.Member;
import com.ra.course.com.stackoverflow.entity.implementations.Question;
import com.ra.course.com.stackoverflow.repository.interfaces.MemberRepository;
import com.ra.course.com.stackoverflow.repository.interfaces.QuestionRepository;
import com.ra.course.com.stackoverflow.service.MemberService;

import java.util.Objects;

public class MemberServiceImpl implements MemberService<Question> {

    private transient final QuestionRepository<Question, Member> questionRep;
    private transient final MemberRepository<Member> memberRep;

    public MemberServiceImpl(final QuestionRepository<Question, Member> questionRep,
                             final MemberRepository<Member> memberRep) {
        Objects.requireNonNull(questionRep, "argument 'questionRep' must not be null");
        this.questionRep = questionRep;
        this.memberRep = memberRep;
    }

    @Override
    public void postQuestion(final Question question) {
        Objects.requireNonNull(question, "argument 'question' must not be null");

    }
}
