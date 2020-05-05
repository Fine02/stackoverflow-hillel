package com.ra.course.com.stackoverflow.repository.impl;

import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.entity.Tag;
import com.ra.course.com.stackoverflow.repository.TagQuestionRepository;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import static com.ra.course.com.stackoverflow.entity.jooq.tables.TagQuestionTable.TAG_QUESTION_TABLE;

@Repository
@AllArgsConstructor
public class TagQuestionRepositoryImpl implements TagQuestionRepository {

    private final DSLContext dslContext;

    @Override
    public void save(final Tag tag, final Question question) {
        dslContext.insertInto(TAG_QUESTION_TABLE, TAG_QUESTION_TABLE.TAG_ID, TAG_QUESTION_TABLE.QUESTION_ID)
                .values(tag.getId(), question.getId())
                .execute();
    }

}
