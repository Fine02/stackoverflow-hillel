package com.ra.course.com.stackoverflow.service.vote.impl;

import com.ra.course.com.stackoverflow.entity.Comment;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.exception.service.CommentNotFoundException;
import com.ra.course.com.stackoverflow.exception.service.MemberNotFoundException;
import com.ra.course.com.stackoverflow.exception.service.AlreadyVotedException;
import com.ra.course.com.stackoverflow.exception.service.CannotVoteOwnPostException;
import com.ra.course.com.stackoverflow.repository.CommentRepository;
import com.ra.course.com.stackoverflow.repository.MemberRepository;
import com.ra.course.com.stackoverflow.service.vote.VoteService;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class VoteCommentService implements VoteService<Comment> {

    private transient final CommentRepository commentData;
    private transient final MemberRepository memberData;
    private transient final static int ADDED_REPUTATION = 5;

    @Override
    public Comment upVote(final Comment comment, final Member member) {
        voteComment(comment, member, 1);
        return comment;
    }

    @Override
    public Comment downVote(final Comment comment, final Member member) {
        voteComment(comment, member, -1);
        return comment;
    }

    private void voteComment(final Comment comment, final Member member, final int i) {
        final var commentFromDB = checkComment(comment);
        final var memberFromDB = checkMember(member);
        checkTheAuthorOfComment(commentFromDB, memberFromDB);
        checkIsAlreadyVoted(commentFromDB.getId(), i > 0
                ? memberFromDB.getUpVotedCommentsId()
                : memberFromDB.getDownVotedCommentsId());
        final var voteCount = commentFromDB.getVoteCount() + i;
        commentFromDB.setVoteCount(voteCount);
        commentData.update(commentFromDB);
        if (i > 0) {
            memberFromDB.getUpVotedCommentsId().add(commentFromDB.getId());
        } else {
            memberFromDB.getDownVotedCommentsId().add(commentFromDB.getId());
        }
        updateMemberWithNewReputation(memberFromDB);
        comment.setVoteCount(voteCount);
    }

    private Comment checkComment(final Comment comment) {
        final var optionalComment = commentData.findById(comment.getId());
        return optionalComment.orElseThrow(
                () -> new CommentNotFoundException("No such comment in DB"));
    }

    private Member checkMember(final Member member) {
        final var optionalMember = memberData.findById(member.getId());
        return optionalMember.orElseThrow(
                () -> new MemberNotFoundException("No such member in DB"));

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

    private void updateMemberWithNewReputation(final Member member) {
        final int reputation = member.getReputation() + ADDED_REPUTATION;
        member.getAccount().setReputation(reputation);
        memberData.update(member);
    }
}
