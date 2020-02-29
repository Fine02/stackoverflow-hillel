package com.ra.course.com.stackoverflow.repository.interfaces;

import com.ra.course.com.stackoverflow.entity.implementations.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository<T> {

    long save(T t);

    Optional<Comment> findById(long id);

    void delete(Comment comment);

    Comment update (Comment comment);

    List<Comment> findAll();

    List<Comment> findAllMemberComments();

}
