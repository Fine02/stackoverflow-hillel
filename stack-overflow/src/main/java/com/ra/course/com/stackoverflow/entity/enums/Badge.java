package com.ra.course.com.stackoverflow.entity.enums;

public enum Badge {
    STUDENT("First question with score of 1 or more "),
    TEACHER("Answer a question with score of 1 or more"),
    COMMENTATOR("Leave 10 comments"),
    CRITIC("First down vote"),
    SUPPORTER("First up vote"),
    BENEFACTOR("First bounty you manually award on your own question"),
    NICE_QUESTION("Question score of 10 or more"),
    GOOD_QUESTION("Question score of 25 or more"),
    GREAT_QUESTION("Question score of 100 or more");


    private String description;
    
    Badge(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
