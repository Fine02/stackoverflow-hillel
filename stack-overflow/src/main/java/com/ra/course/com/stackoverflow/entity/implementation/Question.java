package com.ra.course.com.stackoverflow.entity.implementation;

import com.ra.course.com.stackoverflow.entity.Search;
import com.ra.course.com.stackoverflow.entity.enumeration.QuestionClosingRemark;
import com.ra.course.com.stackoverflow.entity.enumeration.QuestionStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Question implements Search {
    private final long id;
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
    private final List<Tag> tagList;


    private Question(Builder builder) {
        Objects.requireNonNull(builder.title, "field 'builder.title' must not be null");
        Objects.requireNonNull(builder.description, "field 'builder.description' must not be null");
        Objects.requireNonNull(builder.creationTime, "field 'builder.creationTime' must not be null");
        Objects.requireNonNull(builder.updateTime, "field 'builder.updateTime' must not be null");
        Objects.requireNonNull(builder.status, "field 'builder.status' must not be null");
        Objects.requireNonNull(builder.author, "field 'builder.author' must not be null");
        Objects.requireNonNull(builder.commentList, "field 'builder.commentList' must not be null");
        Objects.requireNonNull(builder.answerList, "field 'builder.answerList' must not be null");
        Objects.requireNonNull(builder.photoList, "field 'builder.photoList' must not be null");
        Objects.requireNonNull(builder.tagList, "field 'builder.tagList' must not be null");

        this.id = builder.id;
        this.title = builder.title;
        this.description = builder.description;
        this.viewCount = builder.viewCount;
        this.voteCount = builder.voteCount;
        this.creationTime = builder.creationTime;
        this.updateTime = builder.updateTime;
        this.status = builder.status;
        this.author = builder.author;
        this.bounty = builder.bounty;
        this.closingRemark = builder.closingRemark;
        this.commentList = builder.commentList;
        this.answerList = builder.answerList;
        this.photoList = builder.photoList;
        this.tagList = builder.tagList;
    }

    public static class Builder {
        private transient final long id;
        private transient final String title;
        private transient String description;
        private transient int viewCount;
        private transient int voteCount;
        private transient final LocalDateTime creationTime;
        private transient LocalDateTime updateTime;
        private transient QuestionStatus status;
        private transient QuestionClosingRemark closingRemark;
        private transient final Member author;
        private transient Bounty bounty;

        private transient final List<Comment> commentList;
        private transient final List<Answer> answerList;
        private transient final List<Photo> photoList;
        private transient final List<Tag> tagList;

        public Builder(Long id, String title, LocalDateTime creationTime, Member author) {
            this.id = id;
            this.title = title;
            this.creationTime = creationTime;
            this.author = author;
            this.updateTime = creationTime;
            this.commentList = new ArrayList<>();
            this.answerList = new ArrayList<>();
            this.photoList = new ArrayList<>();
            this.tagList = new ArrayList<>();

            this.description = "";
            this.viewCount = 0;
            this.voteCount = 0;
            this.status = QuestionStatus.OPEN;
            this.bounty = new Bounty(0, creationTime, author);
        }

        public Builder withDescription(final String description) {
            this.description = description;
            return this;
        }

        public Builder withViewCount(final int viewCount) {
            this.viewCount = viewCount;
            return this;
        }

        public Builder withVoteCount(final int voteCount) {
            this.voteCount = voteCount;
            return this;
        }

        public Builder withUpdateTime(final LocalDateTime updateTime) {
            this.updateTime = updateTime;
            return this;
        }

        public Builder withStatus(final QuestionStatus status) {
            this.status = status;
            return this;
        }

        public Builder withBounty(final Bounty bounty) {
            this.bounty = bounty;
            return this;
        }

        public Builder withClosingRemark(final QuestionClosingRemark remark) {
            this.closingRemark = remark;
            return this;
        }

        public Question build() {
            return new Question(this);
        }

    }

    public long getId() {
        return this.id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(final int viewCount) {
        this.viewCount = viewCount;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(final int voteCount) {
        this.voteCount = voteCount;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(final LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public QuestionStatus getStatus() {
        return status;
    }

    public void setStatus(final QuestionStatus status) {
        this.status = status;
    }

    public QuestionClosingRemark getClosingRemark() {
        return closingRemark;
    }

    public void setClosingRemark(final QuestionClosingRemark closingRemark) {
        this.closingRemark = closingRemark;
    }

    public Member getAuthor() {
        return author;
    }

    public Bounty getBounty() {
        return this.bounty;
    }

    public void setBounty(final Bounty bounty) {
        Objects.requireNonNull(bounty, "argument 'bounty' must not be null");

        this.bounty = bounty;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void addComment(final Comment comment) {
        Objects.requireNonNull(comment, "argument 'comment' must not be null");

        this.commentList.add(comment);
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void addAnswer(final Answer answer) {
        Objects.requireNonNull(answer, "argument 'answer' must not be null");

        this.answerList.add(answer);
    }

    public List<Photo> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(final Photo photo) {
        Objects.requireNonNull(photo, "argument 'photo' must not be null");

        this.photoList.add(photo);
    }

    public List<Tag> getTagList() {
        return tagList;
    }

    public void addTag(final Tag tag) {
        Objects.requireNonNull(tag, "argument 'tag' must not be null");

        this.tagList.add(tag);
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
        return this.id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
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
