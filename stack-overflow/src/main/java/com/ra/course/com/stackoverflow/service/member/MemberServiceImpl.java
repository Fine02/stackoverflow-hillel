package com.ra.course.com.stackoverflow.service.member;

import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.repository.MemberRepository;
import com.ra.course.com.stackoverflow.repository.QuestionRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService<Question> {

    @NonNull
    private transient final QuestionRepository questionRep;

    @NonNull
    private transient final MemberRepository memberRep;

    @Override
    public Question postQuestion(@NonNull final Question question) {
        final Question postedQuestion = questionRep.save(question);

        final Member member = question.getAuthor();

        member.getQuestions().add(postedQuestion);

        memberRep.update(member);

        return postedQuestion;
    }
}
