package com.ra.course.com.stackoverflow.service.vote;

import com.ra.course.com.stackoverflow.entity.Account;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.entity.enums.QuestionClosingRemark;
import com.ra.course.com.stackoverflow.entity.enums.QuestionStatus;
import com.ra.course.com.stackoverflow.exception.service.MemberNotFoundException;
import com.ra.course.com.stackoverflow.exception.service.QuestionNotFoundException;
import com.ra.course.com.stackoverflow.exception.service.AlreadyVotedException;
import com.ra.course.com.stackoverflow.exception.service.CannotVoteOwnPostException;
import com.ra.course.com.stackoverflow.repository.MemberRepository;
import com.ra.course.com.stackoverflow.repository.QuestionRepository;
import com.ra.course.com.stackoverflow.service.system.BadgeAwardService;
import com.ra.course.com.stackoverflow.service.system.QuestionScoreBadgeAwarder;
import com.ra.course.com.stackoverflow.service.vote.impl.VoteQuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UpVoteQuestionServiceTest {
    private final QuestionRepository questionData = mock(QuestionRepository.class);
    private final MemberRepository memberData = mock(MemberRepository.class);
    private final long ID1 = 1;
    private final long ID2 = 2;

    private VoteQuestionService voteQuestionService;
    private Member member;
    private Question question;

    @BeforeEach
    void setUp() {
        BadgeAwardService<Question> badgeAwardService = new QuestionScoreBadgeAwarder(memberData);
        voteQuestionService = new VoteQuestionService(questionData, memberData, badgeAwardService);
        member = mockMember(ID1);
        question = mockQuestion(mockMember(ID2));
    }

    @Test
    public void whenQuestionIsNotFoundThenThrowsQuestionNotFoundException(){
        //given
        when(questionData.findById(ID1)).thenReturn(Optional.empty());
        //when
        //then
        assertThatThrownBy(() -> voteQuestionService.upVote(question, member))
                .isInstanceOf(QuestionNotFoundException.class)
                .hasMessage("No such question in DB");
    }

    @Test
    public void whenMemberIsNotFoundThenThrowsMemberNotFoundException(){
        //given
        when(questionData.findById(ID1)).thenReturn(Optional.of(question));
        when(memberData.findById(ID1)).thenReturn(Optional.empty());
        //when
        //then
        assertThatThrownBy(() -> voteQuestionService.upVote(question, member))
                .isInstanceOf(MemberNotFoundException.class)
                .hasMessage("No such member in DB");
    }

    @Test
    public void whenMemberTryToVoteOwnQuestionThenThrowsCannotVoteOwnPostException() {
        //given
        var questionWithTheSameAuthor = mockQuestion(member);
        when(questionData.findById(ID1)).thenReturn(Optional.of(questionWithTheSameAuthor));
        when(memberData.findById(ID1)).thenReturn(Optional.of(member));
        //when
        //then
        assertThatThrownBy(() -> voteQuestionService.upVote(questionWithTheSameAuthor, member))
                .isInstanceOf(CannotVoteOwnPostException.class)
                .hasMessage("Can't vote your own question");
    }

    @Test
    public void whenMemberIsAlreadyVotedTheQuestionThenThrowsAlreadyVotedException() {
        //given
        member.getUpVotedQuestionsId().add(ID1);
        when(questionData.findById(ID1)).thenReturn(Optional.of(question));
        when(memberData.findById(ID1)).thenReturn(Optional.of(member));
        //then
        //when
        assertThatThrownBy(() -> voteQuestionService.upVote(question, member))
                .isInstanceOf(AlreadyVotedException.class)
                .hasMessage("This question is already voted");
    }
    @Test
    public void whenMemberVotesTheQuestionThenVoteCountIncrementAndAddReputation() {
        //given
        when(questionData.findById(ID1)).thenReturn(Optional.of(question));
        when(memberData.findById(ID1)).thenReturn(Optional.of(member));
        when(memberData.findById(ID2)).thenReturn(Optional.of(member));
        //when
        var questionAfterVoting = voteQuestionService.upVote(question, member);
        //then
        assertEquals(1, questionAfterVoting.getVoteCount());
        assertEquals(5, member.getAccount().getReputation());
        assertTrue(member.getUpVotedQuestionsId().contains(question.getId()));
    }

    private Question mockQuestion(Member member){
        return Question.builder()
                .id(ID1)
                .title("title")
                .authorId(member.getAccount().getId())
                .description("Some description")
                .creationTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .status(QuestionStatus.OPEN)
                .closingRemark(QuestionClosingRemark.NOT_MARKED_FOR_CLOSING)
                .build();
    }
    private Member mockMember(long idMember){
        var account = Account.builder()
                .id(idMember)
                .password("password")
                .email("email")
                .name("name").build();
        return Member.builder()
                .account(account).build();
    }
}

