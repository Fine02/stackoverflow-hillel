package com.ra.course.com.stackoverflow.dto;

import com.ra.course.com.stackoverflow.entity.enumeration.QuestionClosingRemark;
import com.ra.course.com.stackoverflow.entity.enumeration.QuestionStatus;
import com.ra.course.com.stackoverflow.entity.implementation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QuestionDto {
    private final String title;
    private String description;
    private int viewCount;
    private int voteCount;
    private final LocalDateTime creationTime;
    private LocalDateTime updateTime;
    private QuestionStatus status;
    private QuestionClosingRemark closingRemark;
    private final Member author;
    private Bounty bounty;

    private final List<Comment> commentList;
    private final List<Answer> answerList;
    private final List<Photo> photoList;


    private QuestionDto(QuestionDto.Builder builder) {
        Objects.requireNonNull(builder.title, "field 'builder.title' must not be null");
        Objects.requireNonNull(builder.description, "field 'builder.description' must not be null");
        Objects.requireNonNull(builder.creationTime, "field 'builder.creationTime' must not be null");
        Objects.requireNonNull(builder.updateTime, "field 'builder.updateTime' must not be null");
        Objects.requireNonNull(builder.status, "field 'builder.status' must not be null");
        Objects.requireNonNull(builder.author, "field 'builder.author' must not be null");
        Objects.requireNonNull(builder.commentList, "field 'builder.commentList' must not be null");
        Objects.requireNonNull(builder.answerList, "field 'builder.answerList' must not be null");
        Objects.requireNonNull(builder.photoList, "field 'builder.photoList' must not be null");

        this.title = builder.title;
        this.description = builder.description;
        this.viewCount = builder.viewCount;
        this.voteCount = builder.voteCount;
        this.creationTime = builder.creationTime;
        this.updateTime = builder.updateTime;
        this.status = builder.status;
        this.author = builder.author;
        this.bounty = builder.bounty;
        this.commentList = builder.commentList;
        this.answerList = builder.answerList;
        this.photoList = builder.photoList;
    }

    public static class Builder {
        private final String title;
        private String description;
        private int viewCount;
        private int voteCount;
        private final LocalDateTime creationTime;
        private LocalDateTime updateTime;
        private QuestionStatus status;
        private final Member author;
        private Bounty bounty;

        private final List<Comment> commentList = new ArrayList<>();
        private final List<Answer> answerList = new ArrayList<>();
        private final List<Photo> photoList = new ArrayList<>();

        public Builder(String title, LocalDateTime creationTime, Member author) {
            this.title = title;
            this.creationTime = creationTime;
            this.author = author;
            this.updateTime = creationTime;

            this.description = "";
            this.viewCount = 0;
            this.voteCount = 0;
            this.status = QuestionStatus.OPEN;
            this.bounty = new Bounty(0, creationTime, author);
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder viewCount(int viewCount) {
            this.viewCount = viewCount;
            return this;
        }

        public Builder voteCount(int voteCount) {
            this.voteCount = voteCount;
            return this;
        }

        public Builder updateTime(LocalDateTime updateTime) {
            this.updateTime = updateTime;
            return this;
        }

        public Builder status(QuestionStatus status) {
            this.status = status;
            return this;
        }

        public Builder bounty(Bounty bounty) {
            this.bounty = bounty;
            return this;
        }

        public QuestionDto build() {
            return new QuestionDto(this);
        }

    }

    public String getTitle() {
        return title;
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

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void addComment(Comment comment) {
        Objects.requireNonNull(comment, "argument 'comment' must not be null");

        this.commentList.add(comment);
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void addAnswer(Answer answer) {
        Objects.requireNonNull(answer, "argument 'answer' must not be null");

        this.answerList.add(answer);
    }

    public Bounty getBounty() {
        return this.bounty;
    }

    public void setBounty(Bounty bounty) {
        Objects.requireNonNull(bounty, "argument 'bounty' must not be null");

        this.bounty = bounty;
    }

    public List<Photo> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(Photo photo) {
        Objects.requireNonNull(photo, "argument 'photo' must not be null");

        this.photoList.add(photo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Question)) {
            return false;
        }

        Question that = (Question) o;
        return this.title.equals(that.getTitle()) &&
                this.description.equals(that.getDescription()) &&
                this.author.equals(that.getAuthor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, author);
    }

    @Override
    public String toString() {
        return "Question{" +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", viewCount=" + viewCount +
                ", voteCount=" + voteCount +
                ", creationTime=" + creationTime +
                ", updateTime=" + updateTime +
                ", status=" + status +
                ", closingRemark=" + closingRemark +
                ", author=" + author +
                ", bounty=" + bounty +
                ", commentList=" + commentList +
                ", answerList=" + answerList +
                ", photoList=" + photoList +
                '}';
    }
}
