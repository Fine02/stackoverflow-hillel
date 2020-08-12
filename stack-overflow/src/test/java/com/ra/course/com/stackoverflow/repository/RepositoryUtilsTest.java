package com.ra.course.com.stackoverflow.repository;

import com.ra.course.com.stackoverflow.entity.*;
import com.ra.course.com.stackoverflow.exception.repository.*;
import com.ra.course.com.stackoverflow.service.RepositoryUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.ra.course.com.stackoverflow.utils.EntityCreationUtil.*;
import static com.ra.course.com.stackoverflow.utils.Constants.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RepositoryUtilsTest {

    private static RepositoryUtils utils;

    private static final Long ID = 2L;
    private static final Member member = getMember();
    private static final Question question = getQuestion();
    private static final Answer answer = getAnswer();
    private static final Comment comment = getComment();
    private static final Tag tag = getTag();
    private static final Notification notification = getNotification();
    private static final Bounty bounty = getBounty();

    //mock inner repositories
    private MemberRepository memberData = mock(MemberRepository.class);
    private QuestionRepository questionData = mock(QuestionRepository.class);
    private AnswerRepository answerData = mock(AnswerRepository.class);
    private CommentRepository commentData = mock(CommentRepository.class);
    private TagRepository tagData = mock(TagRepository.class);
    private NotificationRepository notificationData = mock(NotificationRepository.class);
    private BountyRepository bountyData = mock(BountyRepository.class);

    @BeforeEach
    void setUp() {
        utils = new RepositoryUtils(memberData, questionData, answerData, commentData, tagData, notificationData, bountyData);
    }

    @BeforeAll
    static void beforeAll() {
    
        //mock return entities


    }

    @Test
    void whenMemberDataReturnEmptyOptionalThenThrownException() {
        //given
        when(memberData.findById(ID)).thenReturn(Optional.empty());
        //when
        assertThatThrownBy(() -> utils.getMemberFromDB(ID))
                            .isInstanceOf(MemberNotFoundException.class);
    }
    @Test
    void whenCommentDataReturnEmptyOptionalThenThrownException() {
        //given
        when(commentData.findById(ID)).thenReturn(Optional.empty());
        //when
        assertThatThrownBy(() -> utils.getCommentFromDB(ID))
                            .isInstanceOf(CommentNotFoundException.class);
    }
    @Test
    void whenQuestionDataReturnEmptyOptionalThenThrownException() {
        //given
        when(questionData.findById(ID)).thenReturn(Optional.empty());
        //when
        assertThatThrownBy(() -> utils.getQuestionFromDB(ID))
                            .isInstanceOf(QuestionNotFoundException.class);
    }
    @Test
    void whenAnswerDataReturnEmptyOptionalThenThrownException() {
        //given
        when(answerData.findById(ID)).thenReturn(Optional.empty());
        //when
        assertThatThrownBy(() -> utils.getAnswerFromDB(ID))
                            .isInstanceOf(AnswerNotFoundException.class);
    }
    @Test
    void whenNotificationDataReturnEmptyOptionalThenThrownException() {
        //given
        when(notificationData.getById(ID)).thenReturn(Optional.empty());
        //when
        assertThatThrownBy(() -> utils.getNotificationFromDB(ID))
                            .isInstanceOf(NotificationNotFoundException.class);
    }
    @Test
    void whenTagDataReturnEmptyOptionalThenThrownException() {
        //given
        when(tagData.findByTagName(TITLE)).thenReturn(Optional.empty());
        //when
        assertThatThrownBy(() -> utils.getTagFromDBByTagName(TITLE))
                            .isInstanceOf(TagNotFoundException.class);
    }
    @Test
    void whenBountyDataReturnEmptyOptionalThenThrownException() {
        //given
        when(bountyData.findById(ID)).thenReturn(Optional.empty());
        //when
        assertThatThrownBy(() -> utils.getBountyFromDB(ID))
                            .isInstanceOf(BountyNotFoundException.class);
    }

    @Test
    void whenMemberDataReturnOptionalOfMember() {
        //given
        when(memberData.findById(ID)).thenReturn(Optional.of(member));
        //when
        var result = utils.getMemberFromDB(ID);
        //then
        assertEquals(member, result);
    }
    @Test
    void whenQuestionDataReturnOptionalOfQuestion() {
        //given
        when(questionData.findById(ID)).thenReturn(Optional.of(question));
        //when
        var result = utils.getQuestionFromDB(ID);
        //then
        assertEquals(question, result);
    }
    @Test
    void whenAnswerDataReturnOptionalOfAnswer() {
        //given
        when(answerData.findById(ID)).thenReturn(Optional.of(answer));
        //when
        var result = utils.getAnswerFromDB(ID);
        //then
        assertEquals(answer, result);
    }
    @Test
    void whenCommentDataReturnOptionalOfComment() {
        //given
        when(commentData.findById(ID)).thenReturn(Optional.of(comment));
        //when
        var result = utils.getCommentFromDB(ID);
        //then
        assertEquals(comment, result);
    }
    @Test
    void whenTagDataReturnOptionalOfTag() {
        //given
        when(tagData.findByTagName(TITLE)).thenReturn(Optional.of(tag));
        //when
        var result = utils.getTagFromDBByTagName(TITLE);
        //then
        assertEquals(tag, result);
    }
    @Test
    void whenNotificationDataReturnOptionalOfNotification() {
        //given
        when(notificationData.getById(ID)).thenReturn(Optional.of(notification));
        //when
        var result = utils.getNotificationFromDB(ID);
        //then
        assertEquals(notification, result);
    }
    @Test
    void whenBountyDataReturnOptionalOfBounty() {
        //given
        when(bountyData.findById(ID)).thenReturn(Optional.of(bounty));
        //when
        var result = utils.getBountyFromDB(ID);
        //then
        assertEquals(bounty, result);
    }
}
