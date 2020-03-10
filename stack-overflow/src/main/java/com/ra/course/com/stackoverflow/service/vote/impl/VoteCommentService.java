package com.ra.course.com.stackoverflow.service.vote.impl;

import com.ra.course.com.stackoverflow.entity.Comment;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.exception.repository.DataBaseOperationException;
import com.ra.course.com.stackoverflow.exception.service.CommentNotFoundException;
import com.ra.course.com.stackoverflow.exception.service.InternalServerErrorException;
import com.ra.course.com.stackoverflow.exception.service.MemberNotFoundException;
import com.ra.course.com.stackoverflow.exception.service.AlreadyVotedException;
import com.ra.course.com.stackoverflow.exception.service.CannotVoteOwnPostException;
import com.ra.course.com.stackoverflow.repository.interfaces.CommentRepository;
import com.ra.course.com.stackoverflow.repository.interfaces.MemberRepository;
import com.ra.course.com.stackoverflow.service.vote.VoteService;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class VoteCommentService implements VoteService<Comment> {

    private transient final CommentRepository commentData;
    private transient final MemberRepository memberData;
    private transient final static int ADDED_REPUTATION = 5;
    private static final String SERVER_ERR_MSG = "Unexpected data base error occurred: ";

    @Override
    public Comment upVote(final Comment comment, final Member member) {

        final var commentFromDB = checkComment(comment);
        final var memberFromDB = checkMember(member);

        checkTheAuthorOfComment(commentFromDB, memberFromDB);
        checkIsAlreadyVoted(commentFromDB.getId(), memberFromDB.getUpVotedCommentsId());

        final var voteCount = commentFromDB.getVoteCount() + 1;
        commentFromDB.setVoteCount(voteCount);
        commentData.update(commentFromDB);

        memberFromDB.getUpVotedCommentsId().add(commentFromDB.getId());
        updateMemberWithNewReputation(memberFromDB);

        comment.setVoteCount(voteCount);
        return comment;
    }

    @Override
    public Comment downVote(final Comment comment, final Member member) {

        final var commentFromDB = checkComment(comment);
        final var memberFromDB = checkMember(member);

        checkTheAuthorOfComment(commentFromDB, memberFromDB);
        checkIsAlreadyVoted(commentFromDB.getId(), memberFromDB.getDownVotedCommentsId());

        final var voteCount = commentFromDB.getVoteCount() - 1;
        commentFromDB.setVoteCount(voteCount);
        commentData.update(commentFromDB);

        memberFromDB.getDownVotedCommentsId().add(commentFromDB.getId());
        updateMemberWithNewReputation(memberFromDB);

        comment.setVoteCount(voteCount);
        return comment;
    }

    private Comment checkComment(final Comment comment) {
        final var optionalComment = commentData.findById(comment.getId());
        return optionalComment.orElseThrow(
                ()-> new CommentNotFoundException("No such comment in DB"));
    }

    private Member checkMember(final Member member) {
        final var optionalMember = memberData.findById(member.getId());
        return optionalMember.orElseThrow(
                ()-> new MemberNotFoundException("No such member in DB"));

    }

    private void checkTheAuthorOfComment(final Comment comment, final Member member) {
        if (comment.getAuthor().getId() == member.getId()) {
            throw new CannotVoteOwnPostException("Can't vote your own comment");
        }
    }

    private void checkIsAlreadyVoted(final long commentId, final List<Long> votedComments) {
        if (votedComments.contains(commentId)) {
            throw new AlreadyVotedException("This comment is already voted");
        }
    }

    private void updateMemberWithNewReputation (final Member member) {
        final int reputation = member.getReputation() + ADDED_REPUTATION;
        member.getAccount().setReputation(reputation);
        try {
            memberData.update(member);
        } catch (DataBaseOperationException e) {
            throw (InternalServerErrorException)
                    new InternalServerErrorException(SERVER_ERR_MSG + e.getMessage()).initCause(e.getCause());
        }
    }
}
