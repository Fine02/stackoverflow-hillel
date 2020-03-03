package com.ra.course.com.stackoverflow.service.vote.impl;

import com.ra.course.com.stackoverflow.entity.Comment;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.exception.repository.DataBaseOperationException;
import com.ra.course.com.stackoverflow.exception.service.CommentNotFoundException;
import com.ra.course.com.stackoverflow.exception.service.MemberNotFoundException;
import com.ra.course.com.stackoverflow.exception.vote_service.AlreadyVotedException;
import com.ra.course.com.stackoverflow.exception.vote_service.CannotVoteOwnPostException;
import com.ra.course.com.stackoverflow.repository.interfaces.CommentRepository;
import com.ra.course.com.stackoverflow.repository.interfaces.MemberRepository;
import com.ra.course.com.stackoverflow.service.vote.VoteService;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class VoteCommentService implements VoteService<Comment> {

    private transient final CommentRepository commentData;
    private transient final MemberRepository memberData;

    @Override
    public Comment upVote(final Comment comment, final Member member)
            throws DataBaseOperationException,
            CannotVoteOwnPostException, AlreadyVotedException,
            CommentNotFoundException, MemberNotFoundException{

        final var commentFromDB = checkComment(comment);
        final var memberFromDB = checkMember(member);

        checkTheAuthorOfComment(commentFromDB, memberFromDB);
        checkIsAlreadyVoted(commentFromDB.getId(), memberFromDB.getVotedComments());

        final var voteCount = commentFromDB.getVoteCount() + 1;
        commentFromDB.setVoteCount(voteCount);
        commentData.update(commentFromDB);

        memberFromDB.getVotedComments().add(commentFromDB.getId());
        final int reputation = memberFromDB.getReputation() + 5;
        memberFromDB.getAccount().setReputation(reputation);
        memberData.update(memberFromDB);

        comment.setVoteCount(voteCount);
        return comment;
    }

    @Override
    public Comment downVote(final Comment comment, final Member member)
            throws DataBaseOperationException,
            CannotVoteOwnPostException, AlreadyVotedException,
            CommentNotFoundException, MemberNotFoundException {

        final var commentFromDB = checkComment(comment);
        final var memberFromDB = checkMember(member);

        checkTheAuthorOfComment(commentFromDB, memberFromDB);
        checkIsAlreadyVoted(commentFromDB.getId(), memberFromDB.getDownVotedComments());

        final var voteCount = commentFromDB.getVoteCount() - 1;
        commentFromDB.setVoteCount(voteCount);
        commentData.update(commentFromDB);

        memberFromDB.getDownVotedComments().add(commentFromDB.getId());
        final int reputation = memberFromDB.getReputation() + 5;
        memberFromDB.getAccount().setReputation(reputation);
        memberData.update(memberFromDB);

        comment.setVoteCount(voteCount);
        return comment;
    }

    private Comment checkComment(final Comment comment) throws CommentNotFoundException {
        final var optionalComment = commentData.findById(comment.getId());
        return optionalComment.orElseThrow(
                ()-> new CommentNotFoundException("No such comment in DB"));
    }

    private Member checkMember(final Member member) throws MemberNotFoundException {
        final var optionalMember = memberData.findById(member.getId());
        return optionalMember.orElseThrow(
                ()-> new MemberNotFoundException("No such member in DB"));

    }

    private void checkTheAuthorOfComment(final Comment comment, final Member member) throws CannotVoteOwnPostException {
        if (comment.getAuthor().getId() == member.getId()) {
            throw new CannotVoteOwnPostException("Can't vote your own comment");
        }
    }

    private void checkIsAlreadyVoted(final long commentId, final List<Long> votedComments) throws AlreadyVotedException {
        if (votedComments.contains(commentId)) {
            throw new AlreadyVotedException("This comment is already voted");
        }
    }
}
