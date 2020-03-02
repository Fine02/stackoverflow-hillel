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

    private static final String SERVER_ERR_MSG = "Unexpected error occurred: 500 Internal Server Error";
    private static final String NULL_WARNING_MSG = "argument must not be null";

    public MemberServiceImpl(final QuestionRepository questionRep,
                             final MemberRepository memberRep) {
        Objects.requireNonNull(questionRep, "questionRep' " + NULL_WARNING_MSG);
        Objects.requireNonNull(memberRep, "'memberRep' " + NULL_WARNING_MSG);

        this.questionRep = questionRep;
        this.memberRep = memberRep;
    }


    @Override
    public Question postQuestion(final Question question) throws InternalServerErrorException {
        Objects.requireNonNull(question, "'question' " + NULL_WARNING_MSG);

        final Question postedQuestion = saveQuestion(question);
        updateMember(postedQuestion);

        return postedQuestion;
    }

    private Question saveQuestion(final Question question) throws InternalServerErrorException {

        try {
            return questionRep.save(question);
        } catch (final DataBaseOperationException e) {
            throw (InternalServerErrorException)
                    new InternalServerErrorException(SERVER_ERR_MSG).initCause(e);
        }
    }

    private Member updateMember(final Question question) throws InternalServerErrorException {

        final Member member = question.getAuthor();

        member.setQuestions(Collections.singletonList(question));

        try {
            memberRep.update(member);
        } catch (final DataBaseOperationException e) {
            throw (InternalServerErrorException)
                    new InternalServerErrorException(SERVER_ERR_MSG).initCause(e);
        }

        return member;
    }
}
