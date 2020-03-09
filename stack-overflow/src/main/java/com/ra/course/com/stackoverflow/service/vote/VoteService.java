package com.ra.course.com.stackoverflow.service.vote;

import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.exception.service.*;

public interface VoteService<T> {

    T upVote(T t, Member member) throws InternalServerErrorException,
            CannotVoteOwnPostException, AlreadyVotedException,
            QuestionNotFoundException, AnswerNotFoundException,
            CommentNotFoundException, MemberNotFoundException;

    T downVote (T t, Member member) throws InternalServerErrorException,
            CannotVoteOwnPostException, AlreadyVotedException,
            QuestionNotFoundException, AnswerNotFoundException,
            CommentNotFoundException, MemberNotFoundException;

}
