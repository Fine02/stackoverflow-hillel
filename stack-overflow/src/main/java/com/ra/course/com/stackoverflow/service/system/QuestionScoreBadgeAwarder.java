package com.ra.course.com.stackoverflow.service.system;

import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.entity.enums.Badge;
import com.ra.course.com.stackoverflow.exception.repository.DataBaseOperationException;
import com.ra.course.com.stackoverflow.exception.service.InternalServerErrorException;
import com.ra.course.com.stackoverflow.repository.interfaces.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.*;

@AllArgsConstructor
public class QuestionScoreBadgeAwarder implements BadgeAwardService<Question> {

    private transient final MemberRepository memberRepository;

    private static final int SCR_FOR_STDNT_BDG = 1;
    private static final int SCR_FOR_NICE_BDG = 10;
    private static final int SCR_FOR_GOOD_BDG = 25;
    private static final int SCR_FOR_GREAT_BDG = 100;
    private static final String SERVER_ERR_MSG = "Unexpected data base error occurred: ";

    @Override
    public Member awardMember(@NonNull final Question question) throws InternalServerErrorException {

        final int score = question.getVoteCount();
        final Member author = question.getAuthor();
        final Map<Badge, Set<Question>> memberBadges = author.getQuestionBadges();

        awardStudentBadge(memberBadges, score, question);
        awardNiceQuestionBadge(memberBadges, score, question);
        awardGoodQuestionBadge(memberBadges, score, question);
        awardGreatQuestionBadge(memberBadges, score, question);

        try {
            return memberRepository.update(author);
        } catch (DataBaseOperationException e) {
            throw (InternalServerErrorException)
                    new InternalServerErrorException(SERVER_ERR_MSG + e.getMessage()).initCause(e.getCause());
        }
    }

    private void awardStudentBadge(@NonNull final Map<Badge, Set<Question>> memberBadges, final int score,
                                   final Question question) {
        if (score < SCR_FOR_STDNT_BDG) {
            return;
        }

        memberBadges.putIfAbsent(Badge.STUDENT, new HashSet<>(Collections.singletonList(question)));
    }

    private void awardNiceQuestionBadge(@NonNull final Map<Badge, Set<Question>> memberBadges, final int score,
                                        final Question question) {

        if (score < SCR_FOR_NICE_BDG) {
            return;
        }

        addBadgeToMap(memberBadges, question, Badge.NICE_QUESTION);
    }

    private void awardGoodQuestionBadge(@NonNull final Map<Badge, Set<Question>> memberBadges, final int score,
                                        final Question question) {

        if (score < SCR_FOR_GOOD_BDG) {
            return;
        }

        addBadgeToMap(memberBadges, question, Badge.GOOD_QUESTION);

    }

    private void awardGreatQuestionBadge(@NonNull final Map<Badge, Set<Question>> memberBadges, final int score,
                                         final Question question) {

        if (score < SCR_FOR_GREAT_BDG) {
            return;
        }

        addBadgeToMap(memberBadges, question, Badge.GREAT_QUESTION);
    }

    private void addBadgeToMap(final Map<Badge, Set<Question>> memberBadges, final Question question,
                                       final Badge badge) {

        final Set<Question> questions = memberBadges.putIfAbsent(badge, new HashSet<>(Arrays.asList(question)));
        if (questions != null) {
            questions.add(question);
        }
    }
}
