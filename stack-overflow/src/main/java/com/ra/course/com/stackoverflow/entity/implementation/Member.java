package com.ra.course.com.stackoverflow.entity.implementation;

import java.util.*;


public class Member {
    final private long id;
    private Account account;

    final private List<Badge> badges;
    final private List<Question> questions;
    final private List<Answer> answers;
    final private List<Comment> comments;
    final private List<Notification> notifications;


    public Member(Account account) {
        Objects.requireNonNull(account, "argument 'account' must not be null");

        this.account = account;
        this.id = account.getId();
        this.badges = new ArrayList<>();
        this.questions = new ArrayList<>();
        this.answers = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.notifications = new ArrayList<>();
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(final Account account) {
        Objects.requireNonNull(account);

        this.account = account;
    }

    public int getReputation() {
        return this.account.getReputation();
    }

    public String getEmail() {
        return this.account.getEmail();
    }

    public long getId() {
        return id;
    }

    public List<Badge> getBadges() {
        return badges;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if ( !(o instanceof Member)) {
            return false;
        }
        Member that = (Member) o;

        return this.id == that.id;
    }



    @Override
    public String toString() {
        return this.account.toString() +
                ", { badges: " + badges.toString() +
                "} questions: " + questions.toString() +
                ", { answers: " + answers.toString() +
                "} comments: " + comments.toString() +
                ", { notifications: " + notifications.toString() + "}";
    }
}
