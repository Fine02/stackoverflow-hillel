package com.ra.course.ams.airline.manag.system.entity.flight;

import java.sql.Time;

public class WeeklySchedule {

    private String id;
    private Flight flight;
    private int dayOfWeek;
    private Time departureTime;

    public WeeklySchedule() {
    }

    private WeeklySchedule(Builder builder) {
        id = builder.id;
        flight = builder.flight;
        dayOfWeek = builder.dayOfWeek;
        departureTime = builder.departureTime;
    }

    public static class Builder {

        private transient String id;
        private transient Flight flight;
        private transient int dayOfWeek;
        private transient Time departureTime;

        public Builder () {}

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setFlight(Flight flight) {
            this.flight = flight;
            return this;
        }

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Time getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Time departureTime) {
        this.departureTime = departureTime;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WeeklySchedule{");
        sb.append("\"id\": \"").append(id).append('"');
        sb.append(", \"flight\":").append(flight);
        sb.append(", \"dayOfWeek\":").append(dayOfWeek);
        sb.append(", \"departureTime\": \"").append(departureTime).append('"');
        sb.append('}');
        return sb.toString();
    }
}
