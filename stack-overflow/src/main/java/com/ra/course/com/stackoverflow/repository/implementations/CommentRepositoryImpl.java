package com.ra.course.com.stackoverflow.repository.implementations;

import com.ra.course.com.stackoverflow.entity.implementations.Comment;
import com.ra.course.com.stackoverflow.entity.implementations.Member;
import com.ra.course.com.stackoverflow.exception.repository.CommentNotFoundException;
import com.ra.course.com.stackoverflow.repository.interfaces.CommentRepository;

import java.util.List;
import java.util.Optional;

public class CommentRepositoryImpl implements CommentRepository {

    private final transient List<Comment> comments;

    public CommentRepositoryImpl(final List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public Comment save(final Comment comment) {

        comments.add(comment);

        return comments.get((int) comment.getId());
    }

    @Override
    public long getNextId() {
        return comments.size() + 1;
    }

    @Override
    public Optional<Comment> findById(final long id) {
        return Optional.empty();
    }

    @Override
    public void delete(final Comment comment) {
        comments.remove((int) comment.getId());
    }

    @Override
    public Comment update(final Comment comment) {
        final int id = (int) comment.getId();

        if (comments.get(id) == null) {
            throw new CommentNotFoundException("Can't find comment in CommentRepository");
        }

        comments.add(id, comment);

        return comments.get(id);
    }

    @Override
    public List<Comment> findAll() {
        return comments;
    }

    @Override
    public List<Comment> findAllMemberComments(final Member member) {
        return member.getComments();
    }
}
