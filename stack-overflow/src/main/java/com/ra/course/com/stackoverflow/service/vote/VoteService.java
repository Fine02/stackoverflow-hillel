package com.ra.course.com.stackoverflow.service.vote;

import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.exception.repository.DataBaseOperationException;
import com.ra.course.com.stackoverflow.exception.service.AnswerNotFoundException;
import com.ra.course.com.stackoverflow.exception.service.CommentNotFoundException;
import com.ra.course.com.stackoverflow.exception.service.MemberNotFoundException;
import com.ra.course.com.stackoverflow.exception.service.QuestionNotFoundException;
import com.ra.course.com.stackoverflow.exception.vote_service.AlreadyVotedException;
import com.ra.course.com.stackoverflow.exception.vote_service.CannotVoteOwnPostException;

public interface VoteService<T> {

    T upVote(T t, Member member) throws DataBaseOperationException,
            CannotVoteOwnPostException, AlreadyVotedException,
            QuestionNotFoundException, AnswerNotFoundException,
            CommentNotFoundException, MemberNotFoundException;

    T downVote (T t, Member member) throws DataBaseOperationException,
            CannotVoteOwnPostException, AlreadyVotedException,
            QuestionNotFoundException, AnswerNotFoundException,
            CommentNotFoundException, MemberNotFoundException;

}
