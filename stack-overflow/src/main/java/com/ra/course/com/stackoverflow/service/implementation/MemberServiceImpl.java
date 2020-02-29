package com.ra.course.com.stackoverflow.service.implementation;

import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.exception.repository.DataBaseOperationException;
import com.ra.course.com.stackoverflow.exception.repository.InternalServerErrorException;
import com.ra.course.com.stackoverflow.repository.interfaces.MemberRepository;
import com.ra.course.com.stackoverflow.repository.interfaces.QuestionRepository;
import com.ra.course.com.stackoverflow.service.MemberService;

import java.util.Collections;
import java.util.Objects;

public class MemberServiceImpl implements MemberService<Question> {

    private transient final QuestionRepository questionRep;
    private transient final MemberRepository memberRep;

    public MemberServiceImpl(final QuestionRepository questionRep,
                             final MemberRepository memberRep) {
        Objects.requireNonNull(questionRep, "argument 'questionRep' must not be null");
        Objects.requireNonNull(memberRep, "argument 'memberRep' must not be null");

        this.questionRep = questionRep;
        this.memberRep = memberRep;
    }


    @Override
    public void postQuestion(final Question question) throws InternalServerErrorException {
        Objects.requireNonNull(question, "argument 'question' must not be null");

        try {
            questionRep.save(question);
        } catch (DataBaseOperationException e) {
            throw (InternalServerErrorException) new InternalServerErrorException().initCause(e);
        }

        final Member member = question.getAuthor();

        member.setQuestions(Collections.singletonList(question));

        try {
            memberRep.update(member);
        } catch (DataBaseOperationException e) {
            throw (InternalServerErrorException) new InternalServerErrorException().initCause(e);
        }

    }
}
