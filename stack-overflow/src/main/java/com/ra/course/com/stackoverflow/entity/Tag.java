package com.ra.course.com.stackoverflow.entity;

public class Tag {
    private String name;
    private String description;
    private int dailyAskedFrequency;
    private int weeklyAskedFrequency;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDailyAskedFrequency() {
        return dailyAskedFrequency;
    }

    public void setDailyAskedFrequency(int dailyAskedFrequency) {
        this.dailyAskedFrequency = dailyAskedFrequency;
    }

    public int getWeeklyAskedFrequency() {
        return weeklyAskedFrequency;
    }

    public void setWeeklyAskedFrequency(int weeklyAskedFrequency) {
        this.weeklyAskedFrequency = weeklyAskedFrequency;
    }

    public Tag(String name, String description, int dailyAskedFrequency, int weeklyAskedFrequency) {
        this.name = name;
        this.description = description;
        this.dailyAskedFrequency = dailyAskedFrequency;
        this.weeklyAskedFrequency = weeklyAskedFrequency;
    }
}
