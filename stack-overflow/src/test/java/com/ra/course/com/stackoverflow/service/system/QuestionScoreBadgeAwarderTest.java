package com.ra.course.com.stackoverflow.service.system;

import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.entity.enums.Badge;
import com.ra.course.com.stackoverflow.repository.MemberRepository;
import com.ra.course.com.stackoverflow.service.RepositoryUtils;
import com.ra.course.com.stackoverflow.service.system.impl.QuestionScoreBadgeAwarder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.ra.course.com.stackoverflow.utils.Constants.ID;
import static com.ra.course.com.stackoverflow.utils.EntityCreationUtil.getMember;
import static com.ra.course.com.stackoverflow.utils.EntityCreationUtil.getQuestion;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class QuestionScoreBadgeAwarderTest {

    private BadgeAwardService<Question> badgeAwardService;

    private final MemberRepository memberRepository = mock(MemberRepository.class);
    private final RepositoryUtils utils = mock(RepositoryUtils.class);

    private Question question;
    private Member member;

    @BeforeEach
    void setUp() {
        badgeAwardService = new QuestionScoreBadgeAwarder(memberRepository, utils);

        question = getQuestion();
        member = getMember();

        when(utils.getMemberFromDB(ID)).thenReturn(member);
    }


    @Test
    public void whenQuestionWithLessThanStudentScorePassedThenAuthorShouldNotGetBadge() {
        //when
        var result = badgeAwardService.awardMember(question);
        //then
        assertTrue(result.getQuestionBadges().isEmpty());
    }

    @Test
    public void whenQuestionWithMoreThenFirstScorePassedThenAuthorShouldGetStudentBadge() {
        //given
        question.setVoteCount(1);
        var expectedMap = Map.of(Badge.STUDENT, Set.of(question));
        //when
        var result = badgeAwardService.awardMember(question);
        //then
        assertThat(expectedMap).containsExactlyInAnyOrderEntriesOf(result.getQuestionBadges());
    }

    @Test
    public void whenQuestionWithMoreThenSecondScorePassedThenAuthorShouldGetNiceQuestionBadge() {
        //given
        question.setVoteCount(10);
        var expectedMap = Map.of(Badge.STUDENT, Set.of(question),
                Badge.NICE_QUESTION, Set.of(question));
        //when
        var result = badgeAwardService.awardMember(question);
        //then
        assertThat(expectedMap).containsExactlyInAnyOrderEntriesOf(result.getQuestionBadges());
    }

    @Test
    public void whenQuestionWithMoreThanThirdScorePassedThenAuthorShouldGetGoodQuestionBadge() {
        //given
        question.setVoteCount(25);
        var expectedMap = Map.of(Badge.STUDENT, Set.of(question),
                Badge.NICE_QUESTION, Set.of(question),
                Badge.GOOD_QUESTION,Set.of(question));
        //when
        var result = badgeAwardService.awardMember(question);
        //then
        assertThat(expectedMap).containsExactlyInAnyOrderEntriesOf(result.getQuestionBadges());
    }

    @Test
    public void whenQuestionWithMoreThanGreatScorePassedThenAuthorShouldGetFourBadges() {
        //given
        question.setVoteCount(Integer.MAX_VALUE);
        var expectedMap = Map.of(Badge.STUDENT, Set.of(question),
                Badge.NICE_QUESTION, Set.of(question),
                Badge.GOOD_QUESTION,Set.of(question),
                Badge.GREAT_QUESTION, Set.of(question));
        //when
        var result = badgeAwardService.awardMember(question);
        //then
        assertThat(expectedMap).containsExactlyInAnyOrderEntriesOf(result.getQuestionBadges());
    }
    @Test
    public void whenQuestionWithMoreThanGreatScorePassedThenAuthorWithAnotherBadgesShouldGetMoreFourBadges() {
        //given
        var questionFirst = getQuestion();
            questionFirst.setText("Another question");
        member.setQuestionBadges(new HashMap<>(Map.of(
                Badge.STUDENT, new HashSet<>(Set.of(questionFirst)),
                Badge.NICE_QUESTION, new HashSet<>(Set.of(questionFirst)),
                Badge.GOOD_QUESTION, new HashSet<>(Set.of(questionFirst)),
                Badge.GREAT_QUESTION, new HashSet<>(Set.of(questionFirst)))));

        question.setVoteCount(Integer.MAX_VALUE);

        var expectedMap = Map.of(Badge.STUDENT, Set.of(question, questionFirst),
                Badge.NICE_QUESTION, Set.of(question, questionFirst),
                Badge.GOOD_QUESTION,Set.of(question, questionFirst),
                Badge.GREAT_QUESTION, Set.of(question, questionFirst));
        //when
        var result = badgeAwardService.awardMember(question);
        //then
        assertThat(expectedMap).containsExactlyInAnyOrderEntriesOf(result.getQuestionBadges());
    }

}
