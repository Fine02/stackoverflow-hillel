package com.ra.course.com.stackoverflow.service.system;

import com.ra.course.com.stackoverflow.entity.Account;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.entity.enums.Badge;
import com.ra.course.com.stackoverflow.exception.repository.DataBaseOperationException;
import com.ra.course.com.stackoverflow.exception.service.InternalServerErrorException;
import com.ra.course.com.stackoverflow.repository.interfaces.MemberRepository;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class QuestionScoreBadgeAwarderTest {
    private BadgeAwardService<Question> badgeAwardService;
    private MemberRepository mockedMemberRepository;
    private Member expectedMember;
    private Member givenMember;
    private Question expectedQuestion;
    private Question givenQuestion;
    private Question questionForMap;

    @BeforeEach
    void setUp() throws DataBaseOperationException {
        mockedMemberRepository = mock(MemberRepository.class);
        badgeAwardService = new QuestionScoreBadgeAwarder(mockedMemberRepository);

        givenMember = Member.builder()
                            .id(42L)
                            .account(Account.builder()
                                        .id(24L)
                                        .name("Test")
                                        .password("test12345")
                                        .email("test@gmail.com")
                                        .build())
                            .build();

        givenQuestion = Question.builder()
                            .id(1L)
                            .title("test")
                            .author(givenMember)
                            .build();

        expectedMember = Member.builder()
                            .id(42L)
                            .account(Account.builder()
                                    .id(24L)
                                    .name("Test")
                                    .password("test12345")
                                    .email("test@gmail.com")
                                    .build())
                            .build();

        expectedQuestion = Question.builder()
                            .id(1L)
                            .title("test")
                            .author(expectedMember)
                            .build();

        questionForMap = Question.builder()
                            .id(2L)
                            .title("Question for Map")
                            .author(givenMember)
                            .build();

        when(mockedMemberRepository.update(givenMember)).thenReturn(expectedMember);
    }

    @Test
    public void whenQuestionWithScoreOfOnePassedThenReturnMemberWithStudentBadge() throws InternalServerErrorException,
                                                                                            DataBaseOperationException {
        //given
        givenQuestion.setVoteCount(1);
        //givenMember.getQuestionBadges().put(Badge.NICE_QUESTION, new ArrayList<>(Arrays.asList(questionForMap)));

        expectedQuestion.setVoteCount(1);
        expectedMember.getQuestionBadges().put(Badge.STUDENT, new ArrayList<>(Arrays.asList(expectedQuestion)));
        expectedMember.getQuestionBadges().put(Badge.NICE_QUESTION, new ArrayList<>(Arrays.asList(questionForMap)));

        //when
        Member actualMember = badgeAwardService.awardMember(givenQuestion);

        //then
        System.out.println(expectedMember.getQuestionBadges());
        System.out.println(actualMember.getQuestionBadges());

        assertEquals(expectedMember.getQuestions().size(), actualMember.getQuestions().size());
        assertTrue(actualMember.getQuestionBadges().get(Badge.STUDENT).contains(expectedQuestion));

        verify(mockedMemberRepository).update(givenMember);
        verifyNoMoreInteractions(mockedMemberRepository);
    }

    /*@Test
    public void whenQuestionWithScoreOfZeroPassedThenReturnMemberWithStudentBadge() throws InternalServerErrorException,
            DataBaseOperationException {
        //given
        givenQuestion.setVoteCount(0);
        givenMember.getQuestionBadges().put(Badge.NICE_QUESTION, new ArrayList<>(Arrays.asList(questionForMap)));

        expectedQuestion.setVoteCount(0);
        expectedMember.getQuestionBadges().put(Badge.STUDENT, new ArrayList<>(Arrays.asList(expectedQuestion)));

        //when
        Member actualMember = badgeAwardService.awardMember(givenQuestion);

        //then
        assertTrue(actualMember.getQuestionBadges().get(Badge.STUDENT).contains(expectedQuestion));
        verify(mockedMemberRepository).update(givenMember);
    }*/
}
