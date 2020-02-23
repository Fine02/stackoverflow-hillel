package com.ra.course.com.stackoverflow.entity.implementations;

import java.time.LocalDateTime;
import java.util.Objects;

public class Notification {
    final private long id;
    final private LocalDateTime createdON;
    private String content;

    public Notification(long id, LocalDateTime createdOn, String content) {
        Objects.requireNonNull(createdOn, "argument 'createdOn' must not be null");
        Objects.requireNonNull(content, "argument 'content' must not be null");

        this.id = id;
        this.createdON = createdOn;
        this.content = content;
    }

    public long getId() {
        return this.id;
    }

    public LocalDateTime getCreatedON() {
        return this.createdON;
    }

    public String getContent() {
        return content;
    }

    public void setContent(final String content) {
        Objects.requireNonNull(content, "argument 'content' must not be null");

        this.content = content;
    }
}
