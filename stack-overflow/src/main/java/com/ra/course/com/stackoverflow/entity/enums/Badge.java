package com.ra.course.com.stackoverflow.entity.enums;

public enum Badge {
    STUDENT("First question with score of 1 or more ", "BRONZE"),
    TEACHER("Answer a question with score of 1 or more", "BRONZE"),
    COMMENTATOR("Leave 10 comments", "BRONZE"),
    CRITIC("First down vote", "BRONZE"),
    SUPPORTER("First up vote", "BRONZE"),
    BENEFACTOR("First bounty you manually award on your own question", "BRONZE"),
    NICE_QUESTION("Question score of 10 or more", "BRONZE"),
    GOOD_QUESTION("Question score of 25 or more", "SILVER"),
    GREAT_QUESTION("Question score of 100 or more", "GOLD");


    private String description;
    private String level;
    
    Badge(String description, String level) {
        this.description = description;
        this.level = level;
    }

    public String getDescription() {
        return description;
    }

    public String getLevel() {
        return level;
    }
}
