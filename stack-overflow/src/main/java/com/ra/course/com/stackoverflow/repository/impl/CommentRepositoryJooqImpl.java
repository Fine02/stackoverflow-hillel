package com.ra.course.com.stackoverflow.repository.impl;

import com.ra.course.com.stackoverflow.entity.Comment;
import com.ra.course.com.stackoverflow.entity.jooq.tables.records.CommentRecord;
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
public class CommentRepositoryJooqImpl implements CommentRepository {

    private transient final DSLContext dslContext;

    @Override
    public Comment save(final Comment comment) {
        final var record = dslContext.insertInto(COMMENT_TABLE)
                .columns(COMMENT_TEXT, CREATION_DATE, VOTE_COUNT, AUTHOR_ID, ANSWER_ID, QUESTION_ID)
                .values(comment.getText(), Timestamp.valueOf(comment.getCreationTime()), 0, comment.getAuthor(), comment.getAnswer(), comment.getQuestion())
                .returning()
                .fetchOne();
        return intoComment(record);
    }

    @Override
    public Optional<Comment> findById(final Long id) {
        final var commentFromDb = dslContext.fetchOne(COMMENT_TABLE, ID.eq(id));
        return Optional.ofNullable(intoComment(commentFromDb));

    }

    @Override
    public void delete(final Comment comment) {
        dslContext.deleteFrom(COMMENT_TABLE)
                .where(ID.eq(comment.getId())).execute();
    }

    @Override
    public void update(final Comment comment) {
        dslContext.update(COMMENT_TABLE)
                .set(row(COMMENT_TEXT, VOTE_COUNT),
                        row(comment.getText(), comment.getVoteCount()))
                .where(ID.eq(comment.getId()))
                .execute();
    }

    @Override
    public List<Comment> findAll() {
        return dslContext.selectFrom(COMMENT_TABLE)
                .fetchStream()
                .map(this::intoComment)
                .collect(Collectors.toList());
    }

    @Override
    public List<Comment> findByMemberId(final Long id) {
        return listCommentFromDB(id, AUTHOR_ID);
    }

    @Override
    public List<Comment> findByQuestionId(final Long id) {
        return listCommentFromDB(id, QUESTION_ID);
    }

    @Override
    public List<Comment> findByAnswerId(final Long id) {
        return listCommentFromDB(id, ANSWER_ID);
    }

    private List<Comment> listCommentFromDB(final Long id, final TableField<CommentRecord, Long> tableField) {
        return dslContext.selectFrom(COMMENT_TABLE)
                .where(tableField.eq(id))
                .fetchStream()
                .map(this::intoComment)
                .collect(Collectors.toList());
    }

     private Comment intoComment(final CommentRecord record){
         if (Objects.isNull(record)){
             return null;
         }
        final var comment = new Comment();
            comment.setId(record.getId());
            comment.setText(record.getCommentText());
            comment.setVoteCount(record.getVoteCount());
            comment.setCreationTime(record.getCreationDate().toLocalDateTime());
            comment.setAuthor(record.getAuthorId());
            comment.setAnswer(record.getAnswerId());
            comment.setQuestion(record.getQuestionId());
        return comment;
     }

}
