package com.ra.course.com.stackoverflow.service.vote.impl;

import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.entity.Comment;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.exception.service.AlreadyVotedException;
import com.ra.course.com.stackoverflow.exception.service.CannotVoteOwnPostException;
import com.ra.course.com.stackoverflow.repository.CommentRepository;
import com.ra.course.com.stackoverflow.repository.MemberRepository;
import com.ra.course.com.stackoverflow.service.RepositoryUtils;
import com.ra.course.com.stackoverflow.service.system.NotificationService;
import com.ra.course.com.stackoverflow.service.system.SecurityService;
import com.ra.course.com.stackoverflow.service.vote.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class VoteCommentServiceImpl implements VoteService<Comment> {

    private final CommentRepository commentData;
    private final MemberRepository memberData;
    private final NotificationService noteService;

    private final RepositoryUtils utils;
    private final SecurityService security;
    private final static int ADDED_REPUTATION = 5;

    @Override
    public void upVote(final Long postId, final SessionMemberDto sessionMemberDto) {

        final var member = security.checkStatusAndReturnMember(sessionMemberDto);
        final var comment = utils.getCommentFromDB(postId);

        checkAuthor(comment, member);
        checkIsAlreadyVoted(comment, member.getUpVotedCommentsId());

        member.getUpVotedCommentsId().add(comment.getId());
        member.getDownVotedCommentsId().remove(comment.getId());

        updateMemberWithNewReputation(member);
        updateCommentWithNewVoteCount(comment, 1);

        noteService.sendNotification(comment, "voting up");
    }

    @Override
    public void downVote(final Long postId, final SessionMemberDto sessionMemberDto) {

        final var member = security.checkStatusAndReturnMember(sessionMemberDto);
        final var comment = utils.getCommentFromDB(postId);

        checkAuthor(comment, member);
        checkIsAlreadyVoted(comment, member.getDownVotedCommentsId());

        member.getDownVotedCommentsId().add(comment.getId());
        member.getUpVotedCommentsId().remove(comment.getId());

        updateMemberWithNewReputation(member);
        updateCommentWithNewVoteCount(comment, - 1);

        noteService.sendNotification(comment, "voting down");
    }

    private void checkAuthor(final Comment comment, final Member member ){
        if(comment.getAuthor().equals(member.getId())){
            throw new CannotVoteOwnPostException();
        }
    }

    private void checkIsAlreadyVoted(final Comment comment, final List<Long> votedComments) {
        if (votedComments.contains(comment.getId())) {
            throw new AlreadyVotedException("Comment");
        }
    }

    private void updateMemberWithNewReputation(final Member member) {
        final int reputation = member.getReputation() + ADDED_REPUTATION;
        member.getAccount().setReputation(reputation);
        memberData.update(member);
    }

    private void updateCommentWithNewVoteCount(final Comment comment, final Integer countChanges){
        final var voteCount = comment.getVoteCount() + countChanges;
        comment.setVoteCount(voteCount);
        commentData.update(comment);
    }
}
