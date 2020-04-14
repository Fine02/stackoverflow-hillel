package com.ra.course.com.stackoverflow.service.vote;

import com.ra.course.com.stackoverflow.entity.Account;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.entity.enums.QuestionClosingRemark;
import com.ra.course.com.stackoverflow.entity.enums.QuestionStatus;
import com.ra.course.com.stackoverflow.exception.service.MemberNotFoundException;
import com.ra.course.com.stackoverflow.exception.service.QuestionNotFoundException;
import com.ra.course.com.stackoverflow.exception.service.AlreadyVotedException;
import com.ra.course.com.stackoverflow.repository.MemberRepository;
import com.ra.course.com.stackoverflow.repository.QuestionRepository;
import com.ra.course.com.stackoverflow.service.vote.impl.VoteWithRemarkService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class VoteToCloseServiceTest {
    private final QuestionRepository questionData = mock(QuestionRepository.class);
    private final MemberRepository memberData = mock(MemberRepository.class);
    private final QuestionClosingRemark remark = QuestionClosingRemark.SPAM;
    private final long ID = 1;

    private VoteWithRemarkService voteQuestionService;
    private Member member;
    private Question question;

    @BeforeEach
    void setUp() {
        voteQuestionService = new VoteWithRemarkService(questionData, memberData);
        member = mockMember();
        question = mockQuestion(member);
    }

    @Test
    public void whenQuestionIsNotFoundThenThrowsQuestionNotFoundException() {
        //given
        when(questionData.findById(ID)).thenReturn(Optional.empty());
        //when
        //then
        assertThatThrownBy(() -> voteQuestionService.voteToClose(question, member, remark))
                .isInstanceOf(QuestionNotFoundException.class)
                .hasMessage("No such question in DB");
    }

    @Test
    public void whenMemberIsNotFoundThenThrowsMemberNotFoundException() {
        //given
        when(questionData.findById(ID)).thenReturn(Optional.of(question));
        when(memberData.findById(ID)).thenReturn(Optional.empty());
        //when
        //then
        assertThatThrownBy(() -> voteQuestionService.voteToClose(question, member, remark))
                .isInstanceOf(MemberNotFoundException.class)
                .hasMessage("No such member in DB");
    }

    @Test
    public void whenMemberIsAlreadyVotedToCloseTheQuestionThenThrowsAlreadyVotedException() {
        //given
        question.getMembersIdsWhoVotedQuestionToClose().put(member.getId(), remark);
        when(questionData.findById(ID)).thenReturn(Optional.of(question));
        when(memberData.findById(ID)).thenReturn(Optional.of(member));
        //when
        //then
        assertThatThrownBy(() -> voteQuestionService.voteToClose(question, member, remark))
                .isInstanceOf(AlreadyVotedException.class)
                .hasMessage("This question is already vote to close/delete");
    }

    @Test
    public void whenMemberVotesToCloseTheQuestionThenUpdateQuestion() {
        //given
        var expectedMap = new HashMap<>() {{
            put(ID, remark);
        }};
        when(questionData.findById(ID)).thenReturn(Optional.of(question));
        when(memberData.findById(ID)).thenReturn(Optional.of(member));
        //when
        var questionAfterVoting = voteQuestionService.voteToClose(question, member, remark);
        //then
        assertThat(expectedMap).containsAllEntriesOf(questionAfterVoting.getMembersIdsWhoVotedQuestionToClose());
    }

    private Question mockQuestion(Member member) {
        return Question.builder()
                .id(ID)
                .title("title")
                .authorId(member.getId())
                .description("Some description")
                .creationTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .status(QuestionStatus.OPEN)
                .closingRemark(QuestionClosingRemark.NOT_MARKED_FOR_CLOSING)
                .membersIdsWhoVotedQuestionToClose(new HashMap<>())
                .build();
    }

    private Member mockMember() {
        var account = Account.builder()
                .id(ID)
                .password("password")
                .email("email")
                .name("name").build();
        return Member.builder()
                .id(ID)
                .account(account).build();
    }
}
