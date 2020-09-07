package com.ra.course.com.stackoverflow.repository;

import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository  {

    Question save(Question question);

    Optional<Question> findById(Long id);

    void delete(Question question);

    void update (Question question);

    List<Question> findByMemberId(Long id);

    List<Question> findByTag(Tag tag);

    List<Question> findByTitle(String searchPhrase);

    List<Question> findByTitleAndTag(String searchPhrase, Tag tag);

    void addTagToQuestion(Tag tag, Question question);

}
