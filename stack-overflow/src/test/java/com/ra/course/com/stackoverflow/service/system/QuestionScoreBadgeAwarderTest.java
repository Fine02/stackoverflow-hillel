package com.ra.course.com.stackoverflow.service.system;

import com.ra.course.com.stackoverflow.entity.Account;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.entity.enums.Badge;
import com.ra.course.com.stackoverflow.entity.enums.QuestionClosingRemark;
import com.ra.course.com.stackoverflow.entity.enums.QuestionStatus;
import com.ra.course.com.stackoverflow.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class QuestionScoreBadgeAwarderTest {
    private BadgeAwardService<Question> badgeAwardService;
    private Set<Badge> expectedBadges;
    private Member author;

    private static final int SCR_FOR_STDNT_BDG = 1;
    private static final int SCR_FOR_NICE_BDG = 10;
    private static final int SCR_FOR_GOOD_BDG = 25;
    private static final int SCR_FOR_GREAT_BDG = 100;

    @BeforeEach
    void setUp() {
        var mockedMemberRepository = mock(MemberRepository.class);
        badgeAwardService = new QuestionScoreBadgeAwarder(mockedMemberRepository);

        author = setUpMemberBuilder(1L).build();
        when(mockedMemberRepository.findById(1L)).thenReturn(Optional.of(author));
        expectedBadges = new HashSet<>();
        expectedBadges.add(Badge.STUDENT);
        expectedBadges.add(Badge.NICE_QUESTION);
        expectedBadges.add(Badge.GOOD_QUESTION);
        expectedBadges.add(Badge.GREAT_QUESTION);
    }

    @Test
    public void whenQuestionWithMoreThanGreatScorePassedThenAuthorShouldGetFourBadges() {
        //given
        Question givenQuestion = setUpQuestionBuilder(1L, author)
                .voteCount(Integer.MAX_VALUE)
                .build();


        Question expectedQuestion = setUpQuestionBuilder(1L, author)
                .voteCount(Integer.MAX_VALUE)
                .build();

        Map<Badge, Set<Question>> actualBadges = author.getQuestionBadges();

        //when
        badgeAwardService.awardMember(givenQuestion);

        //then
        assertEquals(4, actualBadges.size());

        for (Badge expectedBadge : expectedBadges) {
            assertTrue(actualBadges.get(expectedBadge).contains(expectedQuestion));
        }
    }


    @Test
    public void whenQuestionWithLessThanStudentScorePassedThenAuthorShouldGetOnlyStudentBadge() {
        //given
        Question givenQuestion = setUpQuestionBuilder(1L, author)
                .voteCount(SCR_FOR_STDNT_BDG - 1)
                .build();

        //when
        badgeAwardService.awardMember(givenQuestion);

        //then
        assertNull(author.getQuestionBadges().get(Badge.STUDENT));
    }

    @Test
    public void whenQuestionWithLessThanNiceScorePassedThenAuthorShouldGetOneBadge() {
        //given
        Badge studentBadge = Badge.STUDENT;
        Badge niceQuestionBadge = Badge.NICE_QUESTION;

        Question givenQuestion = setUpQuestionBuilder(1L, author)
                .voteCount(SCR_FOR_NICE_BDG - 1)
                .build();

        Question expectedQuestion = setUpQuestionBuilder(1L, author)
                .voteCount(SCR_FOR_NICE_BDG - 1)
                .build();

        //when
        badgeAwardService.awardMember(givenQuestion);

        //then
        assertNull(author.getQuestionBadges().get(niceQuestionBadge));

        assertEquals(1, author.getQuestionBadges().size());

        assertTrue(author.getQuestionBadges().get(studentBadge).contains(expectedQuestion));
    }


    @Test
    public void whenQuestionWithLessThanGoodScorePassedThenAuthorShouldGetTwoBadges() {
        //given
        expectedBadges.remove(Badge.GREAT_QUESTION);
        expectedBadges.remove(Badge.GOOD_QUESTION);

        Question givenQuestion = setUpQuestionBuilder(1L, author)
                .voteCount(SCR_FOR_GOOD_BDG - 1)
                .build();

        Question expectedQuestion = setUpQuestionBuilder(1L, author)
                .voteCount(SCR_FOR_GOOD_BDG - 1)
                .build();

        Map<Badge, Set<Question>> actualBadges = author.getQuestionBadges();

        //when
        badgeAwardService.awardMember(givenQuestion);

        //then
        assertNull(actualBadges.get(Badge.GOOD_QUESTION));

        assertEquals(2, actualBadges.size());

        for (Badge expectedBadge : expectedBadges) {
            assertTrue(actualBadges.get(expectedBadge).contains(expectedQuestion));
        }
    }

    @Test
    public void whenQuestionWithLessThanGreatScorePassedThenAuthorShouldGetThreeBadges() {
        //given
        expectedBadges.remove(Badge.GREAT_QUESTION);

        Question givenQuestion = setUpQuestionBuilder(1L, author)
                .voteCount(SCR_FOR_GREAT_BDG - 1)
                .build();

        Question expectedQuestion = setUpQuestionBuilder(1L, author)
                .voteCount(SCR_FOR_GREAT_BDG - 1)
                .build();

        Map<Badge, Set<Question>> actualBadges = author.getQuestionBadges();

        //when
        badgeAwardService.awardMember(givenQuestion);

        //then
        assertNull(actualBadges.get(Badge.GREAT_QUESTION));

        assertEquals(3, actualBadges.size());

        for (Badge expectedBadge : expectedBadges) {
            assertTrue(actualBadges.get(expectedBadge).contains(expectedQuestion));
        }
    }

    @Test
    public void whenNullArgumentPassedThenThrowNpe() {
        assertThrows(NullPointerException.class, () -> badgeAwardService.awardMember(null));
    }

    private Member.MemberBuilder<?, ?> setUpMemberBuilder(long id) {
        return Member.builder()
                .account(Account.builder()
                        .id(1L)
                        .name("Test")
                        .password("test12345")
                        .email("test@gmail.com")
                        .build());
    }

    private Question.QuestionBuilder<?, ?> setUpQuestionBuilder(long id, Member author) {
        return Question.builder()
                .id(id)
                .title("test")
                .authorId(author.getAccount().getId())
                .description("Some description")
                .creationTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .status(QuestionStatus.OPEN)
                .closingRemark(QuestionClosingRemark.NOT_MARKED_FOR_CLOSING);
    }
}
