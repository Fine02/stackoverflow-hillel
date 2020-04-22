package com.ra.course.com.stackoverflow.repository;

import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.entity.Tag;

public interface TagQuestionRepository {

    void save(Tag tag, Question question);
}
