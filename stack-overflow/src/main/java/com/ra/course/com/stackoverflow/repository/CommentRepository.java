package com.ra.course.com.stackoverflow.repository;

import com.ra.course.com.stackoverflow.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    Comment save(Comment comment);

    long getNextId();

    Optional<Comment> findById(Long id);

    void delete(Comment comment);

    void update (Comment comment);

    List<Comment> findAll();

    List<Comment> findByMemberId(Long id);

    List<Comment> findByQuestionId(Long id);

    List<Comment> findByAnswerId(Long id);
}
