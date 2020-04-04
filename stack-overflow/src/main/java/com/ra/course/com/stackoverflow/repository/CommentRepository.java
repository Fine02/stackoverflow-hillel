package com.ra.course.com.stackoverflow.repository;

import com.ra.course.com.stackoverflow.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    Comment save(Comment comment);

    long getNextId();

    Optional<Comment> findById(long id);

    void delete(Comment comment);

    void update (Comment comment);

    List<Comment> findAll();

    List<Comment> findByMemberId(long id);

    List<Comment> findByQuestionId(long id);

    List<Comment> findByAnswerId(long id);
}
