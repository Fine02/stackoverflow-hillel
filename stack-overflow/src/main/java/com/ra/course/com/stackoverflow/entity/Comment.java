package com.ra.course.com.stackoverflow.entity;

import java.time.LocalDateTime;

public class Comment {
    private String text;
    private LocalDateTime creationDate;
    private int voteCount;
    private int flagCount;

    private Member author;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
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

    public Member getAuthor() {
        return author;
    }

    public void setAuthor(Member author) {
        this.author = author;
    }

    public Comment(String text, LocalDateTime creationDate, int voteCount, int flagCount, Member author) {
        this.text = text;
        this.creationDate = creationDate;
        this.voteCount = voteCount;
        this.flagCount = flagCount;
        this.author = author;
    }
}
