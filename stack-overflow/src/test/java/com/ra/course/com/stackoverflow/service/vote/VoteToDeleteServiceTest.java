package com.ra.course.com.stackoverflow.service.vote;

import com.ra.course.com.stackoverflow.entity.Account;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.entity.enums.QuestionClosingRemark;
import com.ra.course.com.stackoverflow.exception.service.MemberNotFoundException;
import com.ra.course.com.stackoverflow.exception.service.QuestionNotFoundException;
import com.ra.course.com.stackoverflow.exception.vote_service.AlreadyVotedException;
import com.ra.course.com.stackoverflow.repository.interfaces.MemberRepository;
import com.ra.course.com.stackoverflow.repository.interfaces.QuestionRepository;
import com.ra.course.com.stackoverflow.service.vote.impl.VoteQuestionService;
import com.ra.course.com.stackoverflow.service.vote.impl.VoteToCloseOrDeleteQuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class VoteToDeleteServiceTest {
    private VoteToCloseOrDeleteQuestionService voteQuestionService;
    private QuestionRepository questionData = mock(QuestionRepository.class);
    private MemberRepository memberData = mock(MemberRepository.class);

    private final long ID1 = 1L;
    private final QuestionClosingRemark remark = QuestionClosingRemark.SPAM;

    @BeforeEach
    void setUp() {
        voteQuestionService = new VoteToCloseOrDeleteQuestionService(questionData, memberData);
    }

    @Test
    public void whenQuestionIsNotFoundThenThrowsQuestionNotFoundException(){
        //given
        var member = mockMember(ID1);
        var question = mockQuestion(member);
        when(questionData.findById(ID1)).thenReturn(Optional.empty());
        //when
        assertThatThrownBy(() -> voteQuestionService.voteToDelete(question, member, remark))
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
        assertThatThrownBy(() -> voteQuestionService.voteToDelete(question, member, remark))
                .isInstanceOf(MemberNotFoundException.class)
                .hasMessage("No such member in DB");
        //then
        verify(questionData).findById(ID1);
        verify(memberData).findById(ID1);
        verifyNoMoreInteractions(questionData, memberData);
    }

    @Test
    public void whenMemberIsAlreadyVotedToDeleteTheQuestionThenThrowsAlreadyVotedException() {
        //given
        var member = mockMember(ID1);
        var question = mockQuestion(member);
        question.getMembersIdsWhoVotedQuestionToDelete().put(member.getId(), remark);
        when(questionData.findById(ID1)).thenReturn(Optional.of(question));
        when(memberData.findById(ID1)).thenReturn(Optional.of(member));
        //when
        assertThatThrownBy(() -> voteQuestionService.voteToDelete(question, member, remark))
                .isInstanceOf(AlreadyVotedException.class)
                .hasMessage("This question is already vote to close/delete");
        //then;
        verify(questionData).findById(ID1);
        verify(memberData).findById(ID1);
        verifyNoMoreInteractions(questionData, memberData);
    }
    @Test
    public void whenMemberVotesToDeleteTheQuestionThenUpdateQuestion() throws Exception {
        //given
        var member = mockMember(ID1);
        var question = mockQuestion(member);
        when(questionData.findById(ID1)).thenReturn(Optional.of(question));
        when(memberData.findById(ID1)).thenReturn(Optional.of(member));
        //when
        var questionAfterVoting = voteQuestionService.voteToDelete(question, member, remark);
        //then
        Map<Long, QuestionClosingRemark> expectedMap = new HashMap<>(){{
            put(ID1, remark);
        }};
        assertThat(expectedMap).containsAllEntriesOf(questionAfterVoting.getMembersIdsWhoVotedQuestionToDelete());

        verify(questionData).findById(ID1);
        verify(memberData).findById(ID1);
        verify(questionData).update(any());
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
