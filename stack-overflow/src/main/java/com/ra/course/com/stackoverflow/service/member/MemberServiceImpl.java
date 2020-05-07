package com.ra.course.com.stackoverflow.service.member;

import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.exception.service.MemberNotFoundException;
import com.ra.course.com.stackoverflow.repository.MemberRepository;
import com.ra.course.com.stackoverflow.repository.QuestionRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MemberServiceImpl implements MemberService<Question> {

    private transient final QuestionRepository questionRep;
    private transient final MemberRepository memberRep;

    @Override
    public Question postQuestion(@NonNull final Question question) {
        final Question postedQuestion = questionRep.save(question);

        final Member member = memberRep.findById(question.getAuthorId())
                .orElseThrow(() -> new MemberNotFoundException("Member with id " + question.getAuthorId() + " not found in DB"));

        member.getQuestions().add(postedQuestion);

        return postedQuestion;
    }
}
