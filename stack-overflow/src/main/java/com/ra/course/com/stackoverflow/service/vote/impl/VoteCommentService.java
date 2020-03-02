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
import java.util.Optional;

@AllArgsConstructor
public class VoteCommentService implements VoteService<Comment> {

    private transient final CommentRepository commentData;
    private transient final MemberRepository memberData;

    @Override
    public Comment upVote(final Comment comment, final Member member)
            throws DataBaseOperationException,
            CannotVoteOwnPostException, AlreadyVotedException,
            CommentNotFoundException, MemberNotFoundException{
        final var optionalComment = commentData.findById(comment.getId());
        final var commentFromDB = checkCommentFromDB(optionalComment);


        final var optionalMember = memberData.findById(member.getId());
        final var memberFromDB = checkMemberFromDB(optionalMember);

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

        final var optionalComment = commentData.findById(comment.getId());
        final var commentFromDB = checkCommentFromDB(optionalComment);


        final var optionalMember = memberData.findById(member.getId());
        final var memberFromDB = checkMemberFromDB(optionalMember);

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

    private Comment checkCommentFromDB(final Optional<Comment> optionalComment) throws CommentNotFoundException {
        if (optionalComment.isEmpty()) {
            throw new CommentNotFoundException("No such comment in DB");
        } else {
            return optionalComment.get();
        }
    }
    private Member checkMemberFromDB(final Optional<Member> optionalMember) throws MemberNotFoundException {
        if(optionalMember.isEmpty()){
            throw new MemberNotFoundException("No such member in DB");
        } else{
            return optionalMember.get();
        }
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
