package com.ra.course.com.stackoverflow.service.vote;

import com.ra.course.com.stackoverflow.entity.Account;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.exception.service.MemberNotFoundException;
import com.ra.course.com.stackoverflow.exception.service.QuestionNotFoundException;
import com.ra.course.com.stackoverflow.exception.vote_service.AlreadyVotedException;
import com.ra.course.com.stackoverflow.exception.vote_service.CannotVoteOwnPostException;
import com.ra.course.com.stackoverflow.repository.interfaces.MemberRepository;
import com.ra.course.com.stackoverflow.repository.interfaces.QuestionRepository;
import com.ra.course.com.stackoverflow.service.vote.impl.VoteQuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UpVoteQuestionServiceTest {

    private VoteQuestionService voteQuestionService;
    private QuestionRepository questionData = mock(QuestionRepository.class);
    private MemberRepository memberData = mock(MemberRepository.class);

    private final long ID1 = 1L;
    private final long ID2 = 2L;

    @BeforeEach
    void setUp() {
        voteQuestionService = new VoteQuestionService(questionData, memberData);
    }

    @Test
    public void whenQuestionIsNotFoundThenThrowsQuestionNotFoundException(){
        //given
        var member = mockMember(ID1);
        var question = mockQuestion(member);
        when(questionData.findById(ID1)).thenReturn(Optional.empty());
        //when
        assertThatThrownBy(() -> voteQuestionService.upVote(question, member))
                .isInstanceOf(QuestionNotFoundException.class)
                .hasMessage("No such question in DB");
        //then
        verify(questionData).findById(ID1);
        verifyNoMoreInteractions(questionData);
        verifyNoInteractions(memberData);
    }

    @Test
    public void whenMemberIsNotFoundThenThrowsMemberNotFoundException(){
        //given
        var member = mockMember(ID1);
        var question = mockQuestion(member);
        when(questionData.findById(ID1)).thenReturn(Optional.of(question));
        when(memberData.findById(ID1)).thenReturn(Optional.empty());
        //when
        assertThatThrownBy(() -> voteQuestionService.upVote(question, member))
                .isInstanceOf(MemberNotFoundException.class)
                .hasMessage("No such member in DB");
        //then
        verify(questionData).findById(ID1);
        verify(memberData).findById(ID1);
        verifyNoMoreInteractions(questionData, memberData);
    }

    @Test
    public void whenMemberTryToVoteOwnQuestionThenThrowsCannotVoteOwnPostException() {
        //given
        var member = mockMember(ID1);
        var question = mockQuestion(member);
        when(questionData.findById(ID1)).thenReturn(Optional.of(question));
        when(memberData.findById(ID1)).thenReturn(Optional.of(member));
        //when
        assertThatThrownBy(() -> voteQuestionService.upVote(question, member))
                .isInstanceOf(CannotVoteOwnPostException.class)
                .hasMessage("Can't vote your own question");
        //then
        verify(questionData).findById(ID1);
        verify(memberData).findById(ID1);
        verifyNoMoreInteractions(questionData, memberData);
    }

    @Test
    public void whenMemberIsAlreadyVotedTheQuestionThenThrowsAlreadyVotedException() {
        //given
        var wantToVoteMember = mockMember(ID1);
        wantToVoteMember.getVotedQuestions().add(ID1);
        var author = mockMember(ID2);
        var question = mockQuestion(author);
        when(questionData.findById(ID1)).thenReturn(Optional.of(question));
        when(memberData.findById(ID1)).thenReturn(Optional.of(wantToVoteMember));
        //when
        assertThatThrownBy(() -> voteQuestionService.upVote(question, wantToVoteMember))
                .isInstanceOf(AlreadyVotedException.class)
                .hasMessage("This question is already voted");
        //then;
        verify(questionData).findById(ID1);
        verify(memberData).findById(ID1);
        verifyNoMoreInteractions(questionData, memberData);
    }
    @Test
    public void whenMemberVotesTheQuestionThenVoteCountIncrementAndAddReputation() throws Exception {
        //given
        var wantToVoteMember = mockMember(ID1);
        var author = mockMember(ID2);
        var question = mockQuestion(author);
        when(questionData.findById(ID1)).thenReturn(Optional.of(question));
        when(memberData.findById(ID1)).thenReturn(Optional.of(wantToVoteMember));
        //when
        var questionAfterVoting = voteQuestionService.upVote(question, wantToVoteMember);
        //then
        assertEquals(1, questionAfterVoting.getVoteCount());
        assertEquals(5, wantToVoteMember.getReputation());
        assertTrue(wantToVoteMember.getVotedQuestions().contains(ID1));

        verify(questionData).findById(ID1);
        verify(memberData).findById(ID1);
        verify(questionData).update(any());
        verify(memberData).update(any());
        verifyNoMoreInteractions(questionData, memberData);
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

