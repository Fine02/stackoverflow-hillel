package com.ra.course.ams.airline.manag.system.entity.flight;

import java.sql.Time;
import java.util.Date;

public class CustomSchedule {

    private Date customDate;
    private Time departureTime;

    public CustomSchedule() {
    }

    private CustomSchedule(Builder builder) {
        customDate = builder.customDate;
        departureTime = builder.departureTime;
    }

    public static class Builder {

        private transient Date customDate;
        private transient Time departureTime;

        public Builder () {}

        public Builder setCustomDate(Date customDate) {
            this.customDate = customDate;
            return this;
        }

        public Builder setDepartureTime(Time departureTime) {
            this.departureTime = departureTime;
            return this;
        }

        public CustomSchedule build() {
            return new CustomSchedule(this);
        }
    }

    public Date getCustomDate() {
        return customDate;
    }

    public CustomSchedule setCustomDate(Date customDate) {
        this.customDate = customDate;
        return this;
    }

    public Time getDepartureTime() {
        return departureTime;
    }

    public CustomSchedule setDepartureTime(Time departureTime) {
        this.departureTime = departureTime;
        return this;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("com.ra.course.ams.airline.manag.system.entity.flight.CustomSchedule{");
        sb.append("\"customDate\": \"").append(customDate).append('"');
        sb.append(", \"departureTime\": \"").append(departureTime).append('"');
        sb.append('}');
        return sb.toString();
    }
}
