package com.ra.course.com.stackoverflow.service.vote;

import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.exception.repository.DataBaseOperationException;
import com.ra.course.com.stackoverflow.exception.service.*;
import com.ra.course.com.stackoverflow.exception.vote_service.AlreadyVotedException;
import com.ra.course.com.stackoverflow.exception.vote_service.CannotVoteOwnPostException;

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
