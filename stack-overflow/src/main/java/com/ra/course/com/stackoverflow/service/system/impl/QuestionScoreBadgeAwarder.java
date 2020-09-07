package com.ra.course.com.stackoverflow.service.system.impl;

import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.entity.enums.Badge;
import com.ra.course.com.stackoverflow.repository.MemberRepository;
import com.ra.course.com.stackoverflow.service.RepositoryUtils;
import com.ra.course.com.stackoverflow.service.system.BadgeAwardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@AllArgsConstructor
@Service
public class QuestionScoreBadgeAwarder implements BadgeAwardService<Question> {

    private final MemberRepository memberRepository;
    private final RepositoryUtils utils;

    private static final int SCR_FOR_STDNT_BDG = 1;
    private static final int SCR_FOR_NICE_BDG = 10;
    private static final int SCR_FOR_GOOD_BDG = 25;
    private static final int SCR_FOR_GREAT_BDG = 100;

    @Override
    public Member awardMember(final Question question) {

        final int score = question.getVoteCount();
        final Member author = utils.getMemberFromDB(question.getAuthor());
        final Map<Badge, Set<Question>> memberBadges = author.getQuestionBadges();

        awardStudentBadge(memberBadges, score, question);
        awardNiceQuestionBadge(memberBadges, score, question);
        awardGoodQuestionBadge(memberBadges, score, question);
        awardGreatQuestionBadge(memberBadges, score, question);

        memberRepository.update(author);
        return author;
    }

    private void awardStudentBadge(final Map<Badge, Set<Question>> memberBadges, final int score,
                                   final Question question) {
        if (score < SCR_FOR_STDNT_BDG) {
            return;
        }

        addBadgeToMap(memberBadges, question, Badge.STUDENT);

    }

    private void awardNiceQuestionBadge(final Map<Badge, Set<Question>> memberBadges, final int score,
                                        final Question question) {

        if (score < SCR_FOR_NICE_BDG) {
            return;
        }

        addBadgeToMap(memberBadges, question, Badge.NICE_QUESTION);
    }

    private void awardGoodQuestionBadge(final Map<Badge, Set<Question>> memberBadges, final int score,
                                        final Question question) {

        if (score < SCR_FOR_GOOD_BDG) {
            return;
        }

        addBadgeToMap(memberBadges, question, Badge.GOOD_QUESTION);

    }

    private void awardGreatQuestionBadge(final Map<Badge, Set<Question>> memberBadges, final int score,
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
