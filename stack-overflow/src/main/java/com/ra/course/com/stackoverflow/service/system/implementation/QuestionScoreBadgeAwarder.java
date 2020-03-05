package com.ra.course.com.stackoverflow.service.system.implementation;

import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.entity.enums.Badge;
import com.ra.course.com.stackoverflow.exception.repository.DataBaseOperationException;
import com.ra.course.com.stackoverflow.exception.service.InternalServerErrorException;
import com.ra.course.com.stackoverflow.repository.interfaces.MemberRepository;
import com.ra.course.com.stackoverflow.service.system.BadgeAwardService;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.*;

@AllArgsConstructor
public class QuestionScoreBadgeAwarder implements BadgeAwardService<Question> {

    private transient final MemberRepository memberRepository;

    @Override
    public Member awardMember(@NonNull final Question question) throws InternalServerErrorException {

        final int score = question.getVoteCount();
        final Member author = question.getAuthor();
        final Map<Badge, List<Question>> memberBadges = author.getQuestionBadges();

        awardStudentBadge(memberBadges, score, question);
        awardNiceQuestionBadge(memberBadges, score, question);
        awardGoodQuestionBadge(memberBadges, score, question);
        awardGreatQuestionBadge(memberBadges, score, question);

        try {
            return memberRepository.update(author);
        } catch (DataBaseOperationException e) {
            throw (InternalServerErrorException)
                    new InternalServerErrorException("Unexpected server error: " + e.getMessage()).initCause(e.getCause());
        }
    }

    private void awardStudentBadge(@NonNull final Map<Badge, List<Question>> memberBadges, final int score,
                                   final Question question) {
        if (score < 1) {
            return;
        }

        memberBadges.putIfAbsent(Badge.STUDENT, new ArrayList<>(Collections.singletonList(question)));
    }

    private void awardNiceQuestionBadge(@NonNull final Map<Badge, List<Question>> memberBadges, final int score,
                                        final Question question) {

        if (score < 10) {
            return;
        }

        addBadgeToMap(memberBadges, question, Badge.NICE_QUESTION);
    }

    private void awardGoodQuestionBadge(@NonNull final Map<Badge, List<Question>> memberBadges, final int score,
                                        final Question question) {

        if (score < 25) {
            return;
        }

        addBadgeToMap(memberBadges, question, Badge.GOOD_QUESTION);

    }

    private void awardGreatQuestionBadge(@NonNull final Map<Badge, List<Question>> memberBadges, final int score,
                                         final Question question) {

        if (score < 100) {
            return;
        }

        addBadgeToMap(memberBadges, question, Badge.GREAT_QUESTION);
    }

    private void addBadgeToMap(final Map<Badge, List<Question>> memberBadges, final Question question,
                                       final Badge badge) {

        List<Question> questions = memberBadges.putIfAbsent(badge, new ArrayList<>(Arrays.asList(question)));
        if (questions != null) {
            questions.add(question);
        }
    }
}
