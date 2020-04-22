package com.ra.course.com.stackoverflow.service.vote;

import com.ra.course.com.stackoverflow.entity.Account;
import com.ra.course.com.stackoverflow.entity.Answer;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.exception.service.AnswerNotFoundException;
import com.ra.course.com.stackoverflow.exception.service.MemberNotFoundException;
import com.ra.course.com.stackoverflow.exception.service.AlreadyVotedException;
import com.ra.course.com.stackoverflow.exception.service.CannotVoteOwnPostException;
import com.ra.course.com.stackoverflow.repository.AnswerRepository;
import com.ra.course.com.stackoverflow.repository.MemberRepository;
import com.ra.course.com.stackoverflow.service.vote.impl.VoteAnswerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UpVoteAnswerServiceTest {
    private final AnswerRepository answerData = mock(AnswerRepository.class);
    private final MemberRepository memberData = mock(MemberRepository.class);
    private final long ID1 = 1;
    private final long ID2 = 2;

    private VoteAnswerService voteAnswerService;
    private Member member;
    private Answer answer;

    @BeforeEach
    void setUp() {
        voteAnswerService = new VoteAnswerService(answerData, memberData);
        member = mockMember(ID1);
        answer = mockAnswer(mockMember(ID2));
    }

    @Test
    public void whenAnswerIsNotFoundThenThrowsAnswerNotFoundException(){
        //given
        when(answerData.findById(ID1)).thenReturn(Optional.empty());
        //when
        //then
        assertThatThrownBy(() -> voteAnswerService.upVote(answer, member))
                .isInstanceOf(AnswerNotFoundException.class)
                .hasMessage("No such answer in DB");
    }

    @Test
    public void whenMemberIsNotFoundThenThrowsMemberNotFoundException(){
        //given
        when(answerData.findById(ID1)).thenReturn(Optional.of(answer));
        when(memberData.findById(ID1)).thenReturn(Optional.empty());
        //when
        //then
        assertThatThrownBy(() -> voteAnswerService.upVote(answer, member))
                .isInstanceOf(MemberNotFoundException.class)
                .hasMessage("No such member in DB");
    }

    @Test
    public void whenMemberTryToVoteOwnAnswerThenThrowsCannotVoteOwnPostException() {
        //given
        var answerWithTheSameAuthor = mockAnswer(member);
        when(answerData.findById(ID1)).thenReturn(Optional.of(answerWithTheSameAuthor));
        when(memberData.findById(ID1)).thenReturn(Optional.of(member));
        //when
        //then
        assertThatThrownBy(() -> voteAnswerService.upVote(answer, member))
                .isInstanceOf(CannotVoteOwnPostException.class)
                .hasMessage("Can't vote your own answer");
    }

    @Test
    public void whenMemberIsAlreadyVotedTheAnswerThenThrowsAlreadyVotedException() {
        //given
        member.getUpVotedAnswersId().add(ID1);
        when(answerData.findById(ID1)).thenReturn(Optional.of(answer));
        when(memberData.findById(ID1)).thenReturn(Optional.of(member));
        //when
        //then
        assertThatThrownBy(() -> voteAnswerService.upVote(answer, member))
                .isInstanceOf(AlreadyVotedException.class)
                .hasMessage("This answer is already voted");
    }
    @Test
    public void whenMemberVotesTheAnswerThenVoteCountIncrementAndAddReputation() {
        //given
        when(answerData.findById(ID1)).thenReturn(Optional.of(answer));
        when(memberData.findById(ID1)).thenReturn(Optional.of(member));
        //when
        var answerAfterVoting = voteAnswerService.upVote(answer, member);
        //then
        assertEquals(1, answerAfterVoting.getVoteCount());
        assertEquals(10, member.getReputation());
        assertTrue(member.getUpVotedAnswersId().contains(answer.getId()));
    }

    private Answer mockAnswer(Member member){ ;
        return Answer.builder()
                .id(ID1)
                .answerText("Some answer")
                .creationDate(LocalDateTime.now())
                .authorId(member.getId())
                .questionId(ID2).build();
    }

    private Member mockMember(long idMember){
        var account = Account.builder()
                .id(idMember)
                .password("password")
                .email("email")
                .name("name").build();
        return Member.builder()
                .id(idMember)
                .account(account).build();
    }
}
