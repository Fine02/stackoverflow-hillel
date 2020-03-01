package com.ra.course.com.stackoverflow.service.interfaces;

/*Members should be able to add an answer to an open question.*/

public interface AnswerService<T, K> {

    T addAnswerToQuestion(T t, K k);
}
