package com.ra.course.com.stackoverflow.service.system;

import com.ra.course.com.stackoverflow.entity.Account;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.entity.enums.Badge;
import com.ra.course.com.stackoverflow.exception.repository.DataBaseOperationException;
import com.ra.course.com.stackoverflow.exception.service.InternalServerErrorException;
import com.ra.course.com.stackoverflow.repository.interfaces.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class QuestionScoreBadgeAwarderTest {
    private BadgeAwardService<Question> badgeAwardService;
    private MemberRepository mockedMemberRepository;

    private static final int SCR_FOR_STDNT_BDG = 1;
    private static final int SCR_FOR_NICE_BDG = 10;
    private static final int SCR_FOR_GOOD_BDG = 25;
    private static final int SCR_FOR_GREAT_BDG = 100;

    @BeforeEach
    void setUp() {
        mockedMemberRepository = mock(MemberRepository.class);
        badgeAwardService = new QuestionScoreBadgeAwarder(mockedMemberRepository);
    }

    @Test
    public void whenQuestionWithGreatScorePassedThenAuthorShouldGetFourBadges() throws InternalServerErrorException,
            DataBaseOperationException {
        //given
        Badge studentBadge = Badge.STUDENT;
        Badge niceQuestionBadge = Badge.NICE_QUESTION;
        Badge goodQuestionBadge = Badge.GOOD_QUESTION;
        Badge greatQuestionBadge = Badge.GREAT_QUESTION;

        Question givenQuestion = setUpQuestionBuilder(1L, setUpMemberBuilder(1L).build())
                .voteCount(SCR_FOR_GREAT_BDG)
                .build();

        Member expectedAuthor = setUpMemberBuilder(1L).build();
        Question expectedQuestion = setUpQuestionBuilder(1L, expectedAuthor)
                .voteCount(SCR_FOR_GREAT_BDG)
                .build();

        //when
        Member actualAuthor = givenQuestion.getAuthor();
        when(mockedMemberRepository.update(actualAuthor)).thenReturn(actualAuthor);

        badgeAwardService.awardMember(givenQuestion);

        //then
        assertNotNull(actualAuthor.getQuestionBadges().get(studentBadge));
        assertNotNull(actualAuthor.getQuestionBadges().get(niceQuestionBadge));
        assertNotNull(actualAuthor.getQuestionBadges().get(goodQuestionBadge));
        assertNotNull(actualAuthor.getQuestionBadges().get(greatQuestionBadge));

        assertEquals(4, actualAuthor.getQuestionBadges().size());

        assertTrue(actualAuthor.getQuestionBadges().get(studentBadge).contains(expectedQuestion));
        assertTrue(actualAuthor.getQuestionBadges().get(niceQuestionBadge).contains(expectedQuestion));
        assertTrue(actualAuthor.getQuestionBadges().get(goodQuestionBadge).contains(expectedQuestion));
        assertTrue(actualAuthor.getQuestionBadges().get(greatQuestionBadge).contains(expectedQuestion));
    }

    @Test
    public void whenQuestionWithMoreThanGreatScorePassedThenAuthorShouldGetFourBadges() throws InternalServerErrorException,
            DataBaseOperationException {
        //given
        Badge studentBadge = Badge.STUDENT;
        Badge niceQuestionBadge = Badge.NICE_QUESTION;
        Badge goodQuestionBadge = Badge.GOOD_QUESTION;
        Badge greatQuestionBadge = Badge.GREAT_QUESTION;

        Question givenQuestion = setUpQuestionBuilder(1L, setUpMemberBuilder(1L).build())
                .voteCount(Integer.MAX_VALUE)
                .build();

        Member expectedAuthor = setUpMemberBuilder(1L).build();
        Question expectedQuestion = setUpQuestionBuilder(1L, expectedAuthor)
                .voteCount(Integer.MAX_VALUE)
                .build();

        //when
        Member actualAuthor = givenQuestion.getAuthor();
        when(mockedMemberRepository.update(actualAuthor)).thenReturn(actualAuthor);

        badgeAwardService.awardMember(givenQuestion);

        //then
        assertNotNull(actualAuthor.getQuestionBadges().get(studentBadge));
        assertNotNull(actualAuthor.getQuestionBadges().get(niceQuestionBadge));
        assertNotNull(actualAuthor.getQuestionBadges().get(goodQuestionBadge));
        assertNotNull(actualAuthor.getQuestionBadges().get(greatQuestionBadge));

        assertEquals(4, actualAuthor.getQuestionBadges().size());

        assertTrue(actualAuthor.getQuestionBadges().get(studentBadge).contains(expectedQuestion));
        assertTrue(actualAuthor.getQuestionBadges().get(niceQuestionBadge).contains(expectedQuestion));
        assertTrue(actualAuthor.getQuestionBadges().get(goodQuestionBadge).contains(expectedQuestion));
        assertTrue(actualAuthor.getQuestionBadges().get(greatQuestionBadge).contains(expectedQuestion));
    }



    @Test
    public void whenQuestionWithLessThanStudentScorePassedThenAuthorShouldGetOnlyStudentBadge() throws InternalServerErrorException,
            DataBaseOperationException {
        //given
        Question givenQuestion = setUpQuestionBuilder(1L, setUpMemberBuilder(1L).build())
                .voteCount(SCR_FOR_STDNT_BDG - 1)
                .build();

        //when
        Member actualAuthor = givenQuestion.getAuthor();
        when(mockedMemberRepository.update(actualAuthor)).thenReturn(actualAuthor);

        badgeAwardService.awardMember(givenQuestion);

        //then
        assertNull(actualAuthor.getQuestionBadges().get(Badge.STUDENT));
    }

    @Test
    public void whenQuestionWithLessThanNiceScorePassedThenAuthorShouldGetOneBadge() throws InternalServerErrorException,
            DataBaseOperationException {
        //given
        Badge studentBadge = Badge.STUDENT;
        Badge niceQuestionBadge = Badge.NICE_QUESTION;

        Question givenQuestion = setUpQuestionBuilder(1L, setUpMemberBuilder(1L).build())
                .voteCount(SCR_FOR_NICE_BDG - 1)
                .build();

        Member expectedAuthor = setUpMemberBuilder(1L).build();
        Question expectedQuestion = setUpQuestionBuilder(1L, expectedAuthor)
                .voteCount(SCR_FOR_NICE_BDG - 1)
                .build();

        //when
        Member actualAuthor = givenQuestion.getAuthor();
        when(mockedMemberRepository.update(actualAuthor)).thenReturn(actualAuthor);

        badgeAwardService.awardMember(givenQuestion);

        //then
        assertNotNull(actualAuthor.getQuestionBadges().get(studentBadge));
        assertNull(actualAuthor.getQuestionBadges().get(niceQuestionBadge));

        assertEquals(1, actualAuthor.getQuestionBadges().size());

        assertTrue(actualAuthor.getQuestionBadges().get(studentBadge).contains(expectedQuestion));
    }


    @Test
    public void whenQuestionWithLessThanGoodScorePassedThenAuthorShouldGetTwoBadges() throws InternalServerErrorException,
            DataBaseOperationException {
        //given
        Badge studentBadge = Badge.STUDENT;
        Badge niceQuestionBadge = Badge.NICE_QUESTION;
        Badge goodQuestionBadge = Badge.GOOD_QUESTION;

        Question givenQuestion = setUpQuestionBuilder(1L, setUpMemberBuilder(1L).build())
                .voteCount(SCR_FOR_GOOD_BDG - 1)
                .build();

        Member expectedAuthor = setUpMemberBuilder(1L).build();
        Question expectedQuestion = setUpQuestionBuilder(1L, expectedAuthor)
                .voteCount(SCR_FOR_GOOD_BDG - 1)
                .build();

        //when
        Member actualAuthor = givenQuestion.getAuthor();
        when(mockedMemberRepository.update(actualAuthor)).thenReturn(actualAuthor);

        badgeAwardService.awardMember(givenQuestion);

        //then
        assertNotNull(actualAuthor.getQuestionBadges().get(studentBadge));
        assertNotNull(actualAuthor.getQuestionBadges().get(niceQuestionBadge));
        assertNull(actualAuthor.getQuestionBadges().get(goodQuestionBadge));

        assertEquals(2, actualAuthor.getQuestionBadges().size());

        assertTrue(actualAuthor.getQuestionBadges().get(studentBadge).contains(expectedQuestion));
        assertTrue(actualAuthor.getQuestionBadges().get(niceQuestionBadge).contains(expectedQuestion));
    }

    @Test
    public void whenQuestionWithLessThanGreatScorePassedThenAuthorShouldGetThreeBadges() throws InternalServerErrorException,
            DataBaseOperationException {
        //given
        Badge studentBadge = Badge.STUDENT;
        Badge niceQuestionBadge = Badge.NICE_QUESTION;
        Badge goodQuestionBadge = Badge.GOOD_QUESTION;
        Badge greatQuestionBadge = Badge.GREAT_QUESTION;

        Question givenQuestion = setUpQuestionBuilder(1L, setUpMemberBuilder(1L).build())
                .voteCount(SCR_FOR_GREAT_BDG - 1)
                .build();

        Member expectedAuthor = setUpMemberBuilder(1L).build();
        Question expectedQuestion = setUpQuestionBuilder(1L, expectedAuthor)
                .voteCount(SCR_FOR_GREAT_BDG - 1)
                .build();

        //when
        Member actualAuthor = givenQuestion.getAuthor();
        when(mockedMemberRepository.update(actualAuthor)).thenReturn(actualAuthor);

        badgeAwardService.awardMember(givenQuestion);

        //then
        assertNotNull(actualAuthor.getQuestionBadges().get(studentBadge));
        assertNotNull(actualAuthor.getQuestionBadges().get(niceQuestionBadge));
        assertNotNull(actualAuthor.getQuestionBadges().get(goodQuestionBadge));
        assertNull(actualAuthor.getQuestionBadges().get(greatQuestionBadge));

        assertEquals(3, actualAuthor.getQuestionBadges().size());

        assertTrue(actualAuthor.getQuestionBadges().get(studentBadge).contains(expectedQuestion));
        assertTrue(actualAuthor.getQuestionBadges().get(niceQuestionBadge).contains(expectedQuestion));
        assertTrue(actualAuthor.getQuestionBadges().get(goodQuestionBadge).contains(expectedQuestion));
    }

    @Test
    public void whenMemberRepositoryThrowsExceptionThenThrowInternalServerErrorException()
                                                                                    throws DataBaseOperationException {
        //given
        Member givenAuthor = setUpMemberBuilder(1L).build();
        Question givenQuestion = setUpQuestionBuilder(1L, givenAuthor).build();

        //when
        when(mockedMemberRepository.update(givenAuthor)).thenThrow(DataBaseOperationException.class);
        Throwable actualException = assertThrows(InternalServerErrorException.class,
                () -> badgeAwardService.awardMember(givenQuestion)
        );

        //then
        assertTrue(actualException.getMessage().contains("Unexpected data base error occurred: "));
    }


    private Member.MemberBuilder<?, ?> setUpMemberBuilder(long id) {
        return Member.builder()
                .id(id)
                .account(Account.builder()
                        .id(24L)
                        .name("Test")
                        .password("test12345")
                        .email("test@gmail.com")
                        .build());
    }

    private Question.QuestionBuilder<?, ?> setUpQuestionBuilder(long id, Member author) {
        return Question.builder()
                .id(id)
                .title("test")
                .author(author);
    }
}
