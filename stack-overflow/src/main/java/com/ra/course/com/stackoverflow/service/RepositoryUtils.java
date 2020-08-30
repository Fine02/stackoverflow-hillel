package com.ra.course.com.stackoverflow.service;

import com.ra.course.com.stackoverflow.entity.*;
import com.ra.course.com.stackoverflow.exception.repository.*;
import com.ra.course.com.stackoverflow.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RepositoryUtils {

    private final MemberRepository memberData;
    private final QuestionRepository questionData;
    private final AnswerRepository answerData;
    private final CommentRepository commentData;
    private final TagRepository tagData;
    private final NotificationRepository noteData;
    private final BountyRepository bountyData;


    public Member getMemberFromDB(final Long id) {
        return memberData.findById(id)
                .orElseThrow(MemberNotFoundException::new);
    }

    public Question getQuestionFromDB (final Long id){
        return questionData.findById(id)
                .orElseThrow(QuestionNotFoundException::new);
    }

    public Tag getTagFromDBByTagName(final String tagName) {
        return tagData.findByTagName(tagName)
                .orElseThrow(TagNotFoundException::new);
    }

    public Answer getAnswerFromDB (final Long id) {
        return answerData.findById(id)
                .orElseThrow(AnswerNotFoundException::new);
    }

    public Comment getCommentFromDB(final Long id) {
        return commentData.findById(id)
                .orElseThrow(CommentNotFoundException::new);
    }

    public Notification getNotificationFromDB(final Long id){
        return noteData.getById(id)
                .orElseThrow(NotificationNotFoundException::new);
    }

    public Bounty getBountyFromDB(final Long id){
        return bountyData.findById(id)
                .orElseThrow(BountyNotFoundException::new);
    }
}
