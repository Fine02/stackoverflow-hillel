package com.ra.course.com.stackoverflow.entity.implementations;

import java.time.LocalDateTime;
import java.util.List;

public class Answer {
    private String answerText;
    private boolean accepted;
    private int voteCount;
    private int flagCount;
    private LocalDateTime creationDate;

    private Member author;
    private List<Photo> photos;
    private List<Comment> comments;

    public Answer(String answerText, boolean accepted, int voteCount, int flagCount, LocalDateTime creationDate, Member author, List<Photo> photos, List<Comment> comments) {
        this.answerText = answerText;
        this.accepted = accepted;
        this.voteCount = voteCount;
        this.flagCount = flagCount;
        this.creationDate = creationDate;
        this.author = author;
        this.photos = photos;
        this.comments = comments;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public int getFlagCount() {
        return flagCount;
    }

    public void setFlagCount(int flagCount) {
        this.flagCount = flagCount;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Member getAuthor() {
        return author;
    }

    public void setAuthor(Member author) {
        this.author = author;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
