package com.ra.course.com.stackoverflow.entity.enums;

public enum Badge {
    STUDENT("First question with score of 1 or more", BadgeLevel.BRONZE),
    TEACHER("Answer a question with score of 1 or more", BadgeLevel.BRONZE),
    COMMENTATOR("Leave 10 comments", BadgeLevel.BRONZE),
    CRITIC("First down vote", BadgeLevel.BRONZE),
    SUPPORTER("First up vote", BadgeLevel.BRONZE),
    BENEFACTOR("First bounty you manually award on your own question", BadgeLevel.BRONZE),
    NICE_QUESTION("Question score of 10 or more", BadgeLevel.BRONZE),
    GOOD_QUESTION("Question score of 25 or more", BadgeLevel.SILVER),
    GREAT_QUESTION("Question score of 100 or more", BadgeLevel.GOLD);


    private String description;
    private BadgeLevel badgeLevel;

    
    Badge(String description, BadgeLevel badgeLevel) {
        this.description = description;
        this.badgeLevel = badgeLevel;
    }

    public String getDescription() {
        return description;
    }

    public BadgeLevel getBadgeLevel() {
        return badgeLevel;
    }
}
