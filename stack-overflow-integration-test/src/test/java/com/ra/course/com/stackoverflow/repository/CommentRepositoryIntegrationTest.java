package com.ra.course.com.stackoverflow.repository;

import com.ra.course.com.stackoverflow.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class CommentRepositoryIntegrationTest {
    @Autowired
    private CommentRepository data;
    private Comment comment;
    private Comment comment2;
    private Comment comment3;
    private List<Comment> expectedList;
    private final Long ID = 1L;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @BeforeEach
    void setUp() {
        expectedList = new ArrayList<>();
        comment = createComment1();
        comment2 = createComment2();
        comment3 = createComment3();
    }

    @Test
    public void whenCommentSaveToDBThenReturnCommentWithIdAndThenDeleteItFromDB() {
        //save new comment in db
        var comment = Comment.builder().creationDate(LocalDateTime.now()).text("text").authorId(1L).questionId(2L).build();
        var newComment = data.save(comment);
        assertNotNull(newComment.getId());
        //delete this comment from db
        data.delete(newComment);
        assertEquals(Optional.empty(), data.findById(newComment.getId()));
    }

    @Test
    public void whenCommentWithIdSaveToDBThenReturnCommentWithNewIdAndThenDeleteItFromDB() {
        //save new comment in db
        var newComment = data.save(comment);
        assertNotEquals(comment.getId(), newComment.getId());
        //delete this comment from db
        data.delete(newComment);
        assertEquals(Optional.empty(), data.findById(newComment.getId()));
    }

    @Test
    public void whenFindCommentByIdThenReturnOptionalOfComment() {
        var expectedOptional = Optional.of(comment);
        assertEquals(expectedOptional, data.findById(ID));
    }

    @Test
    public void whenFindCommentByIdAndNoSuchCommentThenReturnOptionalEmpty() {
        assertEquals(Optional.empty(), data.findById(12635L));
        assertEquals(Optional.empty(), data.findById(null));
    }

    @Test
    public void whenUpdateCommentThenCheckCommentInDb() {
        comment.setVoteCount(123);
        comment.setText("new text");
        data.update(comment);
        assertEquals(comment, data.findById(ID).get());
        //rollback comment
        comment.setVoteCount(3);
        comment.setText("Some comment text");
        data.update(comment);
    }

    @Test
    public void whenFindAllThenReturnListWithAllComments() {
        expectedList.add(comment);
        expectedList.add(comment2);
        expectedList.add(comment3);
        assertEquals(expectedList, data.findAll());
    }

    @Test
    public void whenFindByAuthorIdThenReturnListWithComments() {
        expectedList.add(comment2);
        expectedList.add(comment3);
        assertEquals(expectedList, data.findByMemberId(1L));
    }

    @Test
    public void whenFindByQuestionIdThenReturnListWithComment() {
        expectedList.add(comment);
        assertEquals(expectedList, data.findByQuestionId(1L));
    }

    @Test
    public void whenFindByAnswerIdThenReturnListWithComments() {
        expectedList.add(comment2);
        assertEquals(expectedList, data.findByAnswerId(2L));
    }

    @Test
    public void whenFindBySomeIdButThereIsNotInDbThenReturnEmptyList() {
        assertThat(expectedList)
                .isEqualTo(data.findByAnswerId(666L))
                .isEqualTo(data.findByQuestionId(666L))
                .isEqualTo(data.findByMemberId(666L));
    }

    private Comment createComment1() {
        return Comment.builder()
                .id(ID)
                .text("Some comment text")
                .creationDate(LocalDateTime.parse("2020-03-19 13:32:37", formatter))
                .voteCount(3)
                .authorId(3L)
                .questionId(1L).build();
    }

    private Comment createComment2() {
        return Comment.builder()
                .id(2L)
                .text("Another comment text")
                .creationDate(LocalDateTime.parse("2020-03-19 14:55:14", formatter))
                .voteCount(2)
                .authorId(1L)
                .answerId(2L).build();
    }

    private Comment createComment3(){
        return Comment.builder()
                .id(3L)
                .text("Just comment text")
                .creationDate(LocalDateTime.parse("2020-03-15 10:30:22", formatter))
                .voteCount(5)
                .authorId(1L)
                .answerId(3L).build();
    }

}
