package com.ra.course.com.stackoverflow.utils;

import com.ra.course.com.stackoverflow.entity.*;

import java.time.LocalDateTime;

import static com.ra.course.com.stackoverflow.utils.Constants.*;

public class EntityCreationUtil {

    public static Member getMember() {
        var account = new Account();
            account.setId(ID);
            account.setEmail(EMAIL);
            account.setName(NAME);
            account.setPassword(PASSWORD);

        var member = new Member();
            member.setAccount(account);

        return member;
    }

    public static Question getQuestion() {
        var question = new Question();
            question.setId(ID);
            question.setTitle(TITLE);
            question.setText(TEXT);
            question.setCreationTime(LocalDateTime.MIN);
            question.setUpdateTime(LocalDateTime.MIN);
            question.setAuthor(ID);
        return question;
    }

    public static Answer getAnswer() {
        var answer = new Answer();
            answer.setId(ID);
            answer.setText(TEXT);
            answer.setCreationTime(LocalDateTime.MIN);
            answer.setAuthor(ID);
            answer.setQuestion(ID);
        return answer;
    }

    public static Notification getNotification(){
        var notification = new Notification();
            notification.setId(ID);
            notification.setMember(ID);
            notification.setCreationTime(LocalDateTime.MIN);
        return notification;
    }

    public static Comment getComment(){
        var comment = new Comment();
            comment.setId(ID);
            comment.setText(TEXT);
            comment.setCreationTime(LocalDateTime.MIN);
            comment.setAuthor(ID);
        return comment;
    }

    public static Tag getTag(){
        var tag = new Tag();
            tag.setId(ID);
            tag.setName(TITLE);
            tag.setDescription(TEXT);
        return tag;
    }

    public static Bounty getBounty(){
        var bounty = new Bounty();
            bounty.setCreatorId(ID);
            bounty.setId(ID);
            bounty.setExpiry(LocalDateTime.MAX);
        return bounty;
    }
}
