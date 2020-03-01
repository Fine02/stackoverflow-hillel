package com.ra.course.com.stackoverflow.repository.interfaces;

import com.ra.course.com.stackoverflow.entity.implementations.Comment;
import com.ra.course.com.stackoverflow.entity.implementations.Member;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    Comment save(Comment comment);

    long getNextId();

    Optional<Comment> findById(long id);

    void delete(Comment comment);

    Comment update (Comment comment);

    List<Comment> findAll();

    List<Comment> findAllMemberComments(Member member);

}
