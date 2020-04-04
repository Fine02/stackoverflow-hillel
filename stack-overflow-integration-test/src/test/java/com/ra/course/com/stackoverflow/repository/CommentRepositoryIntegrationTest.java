package com.ra.course.com.stackoverflow.repository;

import com.ra.course.com.stackoverflow.StackOverflowApplication;
import com.ra.course.com.stackoverflow.entity.*;
import com.ra.course.com.stackoverflow.exception.repository.AlreadySaveComment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {StackOverflowApplication.class, RepositoryTestConfiguration.class})
@ActiveProfiles("test")
@Sql({"classpath:schema-h2.sql", "classpath:data.sql"})
public class CommentRepositoryIntegrationTest {
    @Autowired
    private CommentRepository data;
    private Comment comment;
    private List<Comment> expectedList;
    private final long ID = 1;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @BeforeEach
    void setUp() {
        expectedList = new ArrayList<>();
        comment = Comment.builder().id(1).text("Some comment text").creationDate(LocalDateTime.parse("2020-03-19 13:32:37", formatter )).voteCount(3).authorId(3L).questionId(1L).build();
    }

    @Test
    public void whenCommentWithIdSaveToDBThenThrownException(){
        assertThrows(AlreadySaveComment.class, ()->
                data.save(comment));
    }

    @Test
    public void whenCommentSaveToDBThenReturnCommentWithIdAndThenDeleteItFromDB(){
        //save new comment in db
        var comment = Comment.builder().creationDate(LocalDateTime.now()).text("text").authorId(1L).questionId(2L).build();
        var newComment = data.save(comment);
        assertNotEquals(0, newComment.getId());
        //delete this comment from db
        data.delete(newComment);
        assertEquals(Optional.empty(), data.findById(newComment.getId()));
    }
    @Test
    public void whenFindCommentByIdThenReturnOptionalOfComment(){
        var expectedOptional = Optional.of(comment);
        assertEquals(expectedOptional, data.findById(ID));
    }
    @Test
    public void whenFindCommentByIdAndNoSuchCommentThenReturnOptionalEmpty(){
        assertEquals(Optional.empty(), data.findById(12635));
    }
    @Test
    public void whenUpdateCommentThenCheckCommentInDb(){
        comment.setVoteCount(123);
        comment.setText("new text");
        data.update(comment);
        assertEquals(comment, data.findById(ID).get());
    }
    @Test
    public void whenFindAllThenReturnListWithAllComments(){
        expectedList.add(comment);
        expectedList.add(Comment.builder().id(2).text("Another comment text").creationDate(LocalDateTime.parse("2020-03-19 14:55:14", formatter )).voteCount(2).authorId(1L).answerId(2L).build());
        expectedList.add(Comment.builder().id(3).text("Just comment text").creationDate(LocalDateTime.parse("2020-03-15 10:30:22", formatter )).voteCount(5).authorId(1L).answerId(3L).build());
        assertEquals(expectedList, data.findAll());
    }
    @Test
    public void whenFindByAuthorIdThenReturnListWithComments(){
        expectedList.add(Comment.builder().id(2).text("Another comment text").creationDate(LocalDateTime.parse("2020-03-19 14:55:14", formatter )).voteCount(2).authorId(1L).answerId(2L).build());
        expectedList.add(Comment.builder().id(3).text("Just comment text").creationDate(LocalDateTime.parse("2020-03-15 10:30:22", formatter )).voteCount(5).authorId(1L).answerId(3L).build());
        assertEquals(expectedList, data.findByMemberId(1));
    }
    @Test
    public void whenFindByQuestionIdThenReturnListWithComment(){
        expectedList.add(comment);
        assertEquals(expectedList, data.findByQuestionId(1));
    }
    @Test
    public void whenFindByAnswerIdThenReturnListWithComments(){
        expectedList.add(Comment.builder().id(2).text("Another comment text").creationDate(LocalDateTime.parse("2020-03-19 14:55:14", formatter )).voteCount(2).authorId(1L).answerId(2L).build());
        assertEquals(expectedList, data.findByAnswerId(2));
    }
    @Test
    public void whenFindBySomeIdButThereIsNotInDbThenReturnEmptyList(){
        assertThat(expectedList)
                .isEqualTo(data.findByAnswerId(666))
                .isEqualTo(data.findByQuestionId(666))
                .isEqualTo(data.findByMemberId(666));
    }

}
