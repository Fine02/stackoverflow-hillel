package com.ra.course.com.stackoverflow.repository.impl;

import com.ra.course.com.stackoverflow.entity.Comment;
import com.ra.course.com.stackoverflow.entity.jooq.Sequences;
import com.ra.course.com.stackoverflow.entity.jooq.tables.records.CommentRecord;
import com.ra.course.com.stackoverflow.exception.repository.AlreadySaveComment;
import com.ra.course.com.stackoverflow.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.jooq.*;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


import static com.ra.course.com.stackoverflow.entity.jooq.Tables.COMMENT_TABLE;
import static com.ra.course.com.stackoverflow.entity.jooq.tables.CommentTable.*;
import static org.jooq.impl.DSL.row;

@Repository
@AllArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {

    private transient final DSLContext defaultDSLContext;

    @Override
    public Comment save(final Comment comment) {
        if (comment.getId() != 0) {
            throw new AlreadySaveComment("Comment is already exist");
        }
        final var commentId = defaultDSLContext.insertInto(COMMENT_TABLE)
                .columns(COMMENT_TEXT, CREATION_DATE, VOTE_COUNT, AUTHOR_ID, ANSWER_ID, QUESTION_ID)
                .values(comment.getText(), Timestamp.valueOf(comment.getCreationDate()), 0, comment.getAuthorId(), comment.getAnswerId(), comment.getQuestionId())
                .returning(ID)
                .fetchOne()
                .get(ID);
        comment.setId(commentId);
        return comment;
    }

    @Override
    public long getNextId() {
        return defaultDSLContext.currval(Sequences.COMMENT_ID_SEQ) + 1;
    }

    @Override
    public Optional<Comment> findById(final long id) {
        final var list = listCommentFromD(id, ID);
        return list.isEmpty() ? Optional.empty()
                : Optional.of(list.get(0));
    }

    @Override
    public void delete(final Comment comment) {
        defaultDSLContext.deleteFrom(COMMENT_TABLE)
                .where(ID.eq(comment.getId())).execute();
    }

    @Override
    public void update(final Comment comment) {
        defaultDSLContext.update(COMMENT_TABLE)
                .set(row(COMMENT_TEXT, VOTE_COUNT),
                        row(comment.getText(), comment.getVoteCount()))
                .where(ID.eq(comment.getId()))
                .execute();
    }

    @Override
    public List<Comment> findAll() {
        return defaultDSLContext.selectFrom(COMMENT_TABLE)
                .fetch()
                .stream()
                .filter(Objects::nonNull)
                .map(this::getCommentFromCommentRecord)
                .collect(Collectors.toList());
    }

    @Override
    public List<Comment> findByMemberId(final long id) {
        return listCommentFromD(id, AUTHOR_ID);
    }

    @Override
    public List<Comment> findByQuestionId(final long id) {
        return listCommentFromD(id, QUESTION_ID);
    }

    @Override
    public List<Comment> findByAnswerId(final long id) {
        return listCommentFromD(id, ANSWER_ID);
    }

    private List<Comment> listCommentFromD(final long id, final TableField<CommentRecord, Long> tableField) {
        return defaultDSLContext.selectFrom(COMMENT_TABLE)
                .where(tableField.eq(id))
                .fetch()
                .stream()
                .filter(Objects::nonNull)
                .map(this::getCommentFromCommentRecord)
                .collect(Collectors.toList());
    }

    private Comment getCommentFromCommentRecord(final CommentRecord record) {
        return Comment.builder()
                .id(record.getId())
                .text(record.getCommentText())
                .creationDate(record.getCreationDate().toLocalDateTime())
                .voteCount(record.getVoteCount())
                .authorId(record.getAuthorId())
                .answerId(record.getAnswerId())
                .questionId(record.getQuestionId()).build();
    }
}
