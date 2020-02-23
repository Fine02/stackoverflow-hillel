package com.ra.course.com.stackoverflow.entity.implementations;

import java.time.LocalDateTime;

public class Photo {

    private int photoId;
    private String photoPath;
    private LocalDateTime creationDate;

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Photo(int photoId, String photoPath, LocalDateTime creationDate) {
        this.photoId = photoId;
        this.photoPath = photoPath;
        this.creationDate = creationDate;
    }
}
