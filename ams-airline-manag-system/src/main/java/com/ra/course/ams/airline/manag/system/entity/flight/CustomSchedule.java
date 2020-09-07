package com.ra.course.ams.airline.manag.system.entity.flight;

import java.sql.Time;
import java.util.Date;

public class CustomSchedule {

    private String id;
    private Flight flight;
    private Date customDate;
    private Time departureTime;

    public CustomSchedule() {
    }

    private CustomSchedule(Builder builder) {
        id = builder.id;
        flight = builder.flight;
        customDate = builder.customDate;
        departureTime = builder.departureTime;
    }

    public static class Builder {

        private transient String id;
        private transient Flight flight;
        private transient Date customDate;
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

    public Date getCustomDate() {
        return customDate;
    }

    public void setCustomDate(Date customDate) {
        this.customDate = customDate;
    }

    public Time getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Time departureTime) {
        this.departureTime = departureTime;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CustomSchedule{");
        sb.append("\"id\": \"").append(id).append('"');
        sb.append(", \"flight\":").append(flight);
        sb.append(", \"customDate\": \"").append(customDate).append('"');
        sb.append(", \"departureTime\": \"").append(departureTime).append('"');
        sb.append('}');
        return sb.toString();
    }
}
