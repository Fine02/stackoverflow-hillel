package com.ra.course.com.stackoverflow.entity;

import java.time.LocalDateTime;
import java.util.List;

public class Question {
    private String title;
    private String description;
    private int viewCount;
    private int voteCount;
    private LocalDateTime creationTime;
    private LocalDateTime updateTime;
    private QuestionStatus status;
    private QuestionClosingRemark closingRemark;

    private Member author;
    private List<Comment> commentList;
    private List<Answer> answerList;
    private List<Bounty> bountyList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public QuestionStatus getStatus() {
        return status;
    }

    public void setStatus(QuestionStatus status) {
        this.status = status;
    }

    public QuestionClosingRemark getClosingRemark() {
        return closingRemark;
    }

    public void setClosingRemark(QuestionClosingRemark closingRemark) {
        this.closingRemark = closingRemark;
    }

    public Member getAuthor() {
        return author;
    }

    public void setAuthor(Member author) {
        this.author = author;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
    }

    public List<Bounty> getBountyList() {
        return bountyList;
    }

    public void setBountyList(List<Bounty> bountyList) {
        this.bountyList = bountyList;
    }

    public List<Photo> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<Photo> photoList) {
        this.photoList = photoList;
    }

    public Question(String title, String description, int viewCount, int voteCount, LocalDateTime creationTime, LocalDateTime updateTime, QuestionStatus status, QuestionClosingRemark closingRemark, Member author, List<Comment> commentList, List<Answer> answerList, List<Bounty> bountyList, List<Photo> photoList) {
        this.title = title;
        this.description = description;
        this.viewCount = viewCount;
        this.voteCount = voteCount;
        this.creationTime = creationTime;
        this.updateTime = updateTime;
        this.status = status;
        this.closingRemark = closingRemark;
        this.author = author;
        this.commentList = commentList;
        this.answerList = answerList;
        this.bountyList = bountyList;
        this.photoList = photoList;
    }

    private List<Photo> photoList;
}
