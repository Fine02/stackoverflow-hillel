package com.ra.course.com.stackoverflow.repository;

import com.ra.course.com.stackoverflow.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    Comment save(Comment comment);

    long getNextId();

    Optional<Comment> findById(long id);

    void delete(Comment comment);

    Comment update (Comment comment);

    List<Comment> findAll();

    List<Comment> findAllMemberComments();

    List<Comment> findByQuestionId(long id);
}
