package com.ra.course.ams.airline.manag.system.entity.flight;

import java.sql.Time;

public class WeeklySchedule {

    private int dayOfWeek;
    private Time departureTime;

    public WeeklySchedule() {
    }

    public WeeklySchedule(int dayOfWeek, Time departureTime) {
        this.dayOfWeek = dayOfWeek;
        this.departureTime = departureTime;
    }

    private WeeklySchedule(Builder builder) {
        dayOfWeek = builder.dayOfWeek;
        departureTime = builder.departureTime;
    }

    public static class Builder {

        private int dayOfWeek;
        private Time departureTime;

        public Builder () {}

        public Builder setDayOfWeek(int dayOfWeek) {
            this.dayOfWeek = dayOfWeek;
            return this;
        }

        public Builder setDepartureTime(Time departureTime) {
            this.departureTime = departureTime;
            return this;
        }

        public WeeklySchedule build() {
            return new WeeklySchedule(this);
        }
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public WeeklySchedule setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
        return this;
    }

    public Time getDepartureTime() {
        return departureTime;
    }

    public WeeklySchedule setDepartureTime(Time departureTime) {
        this.departureTime = departureTime;
        return this;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("com.ra.course.ams.airline.manag.system.entity.flight.WeeklySchedule{");
        sb.append("\"dayOfWeek\":").append(dayOfWeek);
        sb.append(", \"departureTime\": \"").append(departureTime).append('"');
        sb.append('}');
        return sb.toString();
    }
}
