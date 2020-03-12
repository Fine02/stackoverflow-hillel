package com.ra.course.com.stackoverflow.repository;

import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository  {

    Question save(Question question);

    long getNextId();

    Optional<Question> findById(long id);

    void delete(Question question);

    Question update (Question question);

    List<Question> findAll();

    List<Question> findAllByTitle(String title);

    List<Question> findAllMemberQuestions(Member member);

    List<Question> findByTag(Tag tag);

    List<Question> findByTitle(String searchPhrase);

    List<Question> findByTitleAndTag(String searchPhrase, Tag tag);
}
