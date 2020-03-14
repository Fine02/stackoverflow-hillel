package com.ra.course.com.stackoverflow.service.vote;

import com.ra.course.com.stackoverflow.entity.Member;

public interface VoteService<T> {

    T upVote(T t, Member member);

    T downVote (T t, Member member);

}
