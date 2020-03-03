package com.ra.course.com.stackoverflow.service.vote;

import com.ra.course.com.stackoverflow.entity.Account;
import com.ra.course.com.stackoverflow.entity.Answer;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.exception.service.AnswerNotFoundException;
import com.ra.course.com.stackoverflow.exception.service.MemberNotFoundException;
import com.ra.course.com.stackoverflow.exception.vote_service.AlreadyVotedException;
import com.ra.course.com.stackoverflow.exception.vote_service.CannotVoteOwnPostException;
import com.ra.course.com.stackoverflow.repository.interfaces.AnswerRepository;
import com.ra.course.com.stackoverflow.repository.interfaces.MemberRepository;
import com.ra.course.com.stackoverflow.service.vote.impl.VoteAnswerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DownVoteAnswerServiceTest {
    private VoteAnswerService voteAnswerService;
    private AnswerRepository answerData = mock(AnswerRepository.class);
    private MemberRepository memberData = mock(MemberRepository.class);

    private final long ID1 = 1L;
    private final long ID2 = 2L;

    @BeforeEach
    void setUp() {
        voteAnswerService = new VoteAnswerService(answerData, memberData);
    }

    @Test
    public void whenAnswerIsNotFoundThenThrowsAnswerNotFoundException(){
        //given
        var member = mockMember(ID1);
        var answer = mockAnswer(member);
        when(answerData.findById(ID1)).thenReturn(Optional.empty());
        //when
        assertThatThrownBy(() -> voteAnswerService.downVote(answer, member))
                .isInstanceOf(AnswerNotFoundException.class)
                .hasMessage("No such answer in DB");
        //then
        verify(answerData).findById(ID1);
        verifyNoMoreInteractions(answerData);
        verifyNoInteractions(memberData);
    }

    @Test
    public void whenMemberIsNotFoundThenThrowsMemberNotFoundException(){
        //given
        var member = mockMember(ID1);
        var answer = mockAnswer(member);
        when(answerData.findById(ID1)).thenReturn(Optional.of(answer));
        when(memberData.findById(ID1)).thenReturn(Optional.empty());
        //when
        assertThatThrownBy(() -> voteAnswerService.downVote(answer, member))
                .isInstanceOf(MemberNotFoundException.class)
                .hasMessage("No such member in DB");
        //then
        verify(answerData).findById(ID1);
        verify(memberData).findById(ID1);
        verifyNoMoreInteractions(answerData, memberData);
    }

    @Test
    public void whenMemberTryToVoteOwnAnswerThenThrowsCannotVoteOwnPostException() {
        //given
        var member = mockMember(ID1);
        var answer = mockAnswer(member);
        when(answerData.findById(ID1)).thenReturn(Optional.of(answer));
        when(memberData.findById(ID1)).thenReturn(Optional.of(member));
        //when
        assertThatThrownBy(() -> voteAnswerService.downVote(answer, member))
                .isInstanceOf(CannotVoteOwnPostException.class)
                .hasMessage("Can't vote your own answer");
        //then
        verify(answerData).findById(ID1);
        verify(memberData).findById(ID1);
        verifyNoMoreInteractions(answerData, memberData);
    }

    @Test
    public void whenMemberIsAlreadyVotedTheAnswerThenThrowsAlreadyVotedException() {
        //given
        var wantToVoteMember = mockMember(ID1);
        wantToVoteMember.getDownVotedAnswers().add(ID1);
        var author = mockMember(ID2);
        var answer = mockAnswer(author);
        when(answerData.findById(ID1)).thenReturn(Optional.of(answer));
        when(memberData.findById(ID1)).thenReturn(Optional.of(wantToVoteMember));
        //when
        assertThatThrownBy(() -> voteAnswerService.downVote(answer, wantToVoteMember))
                .isInstanceOf(AlreadyVotedException.class)
                .hasMessage("This answer is already voted");
        //then
        verify(answerData).findById(ID1);
        verify(memberData).findById(ID1);
        verifyNoMoreInteractions(answerData, memberData);
    }
    @Test
    public void whenMemberVotesTheAnswerThenVoteCountDecrementAndAddReputation() throws Exception {
        //given
        var wantToVoteMember = mockMember(ID1);
        var author = mockMember(ID2);
        var answer = mockAnswer(author);
        when(answerData.findById(ID1)).thenReturn(Optional.of(answer));
        when(memberData.findById(ID1)).thenReturn(Optional.of(wantToVoteMember));
        //when
        var answerAfterVoting = voteAnswerService.downVote(answer, wantToVoteMember);
        //then
        assertEquals(-1, answerAfterVoting.getVoteCount());
        assertEquals(10, wantToVoteMember.getReputation());
        assertTrue(wantToVoteMember.getDownVotedAnswers().contains(ID1));

        verify(answerData).findById(ID1);
        verify(memberData).findById(ID1);
        verify(answerData).update(any());
        verify(memberData).update(any());
        verifyNoMoreInteractions(answerData, memberData);
    }



    private Answer mockAnswer(Member member){
        return Answer.builder()
                .id(ID1)
                .author(member)
                .question(mockQuestion(member)).build();
    }
    private Question mockQuestion(Member member){
        return Question.builder()
                .id(ID1)
                .title("title")
                .author(member).build();
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
