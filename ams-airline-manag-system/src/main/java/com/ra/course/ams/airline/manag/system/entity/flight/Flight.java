package com.ra.course.ams.airline.manag.system.entity.flight;

import java.util.List;

public class Flight {

    private String flightNumber;
    private Airport departure;
    private Airport arrival;
    private int durationInMinutes;
    private List<WeeklySchedule> weeklySchedules;
    private List<CustomSchedule> customSchedules;
    private List<FlightInstance> flightInstances;

    public Flight() {
    }

    private Flight(Builder builder) {
        flightNumber = builder.flightNumber;
        departure = builder.departure;
        arrival = builder.arrival;
        durationInMinutes = builder.durationInMinutes;
        weeklySchedules = builder.weeklySchedules;
        customSchedules = builder.customSchedules;
        flightInstances = builder.flightInstances;
    }

    public static class Builder {

        private transient String flightNumber;
        private transient Airport departure;
        private transient Airport arrival;
        private transient int durationInMinutes;
        private transient List<WeeklySchedule> weeklySchedules;
        private transient List<CustomSchedule> customSchedules;
        private transient List<FlightInstance> flightInstances;

        public Builder () {}

        public Builder setFlightNumber(String flightNumber) {
            this.flightNumber = flightNumber;
            return this;
        }

        public Builder setDeparture(Airport departure) {
            this.departure = departure;
            return this;
        }

        public Builder setArrival(Airport arrival) {
            this.arrival = arrival;
            return this;
        }

        public Builder setDurationInMinutes(int durationInMinutes) {
            this.durationInMinutes = durationInMinutes;
            return this;
        }

        public Builder setWeeklySchedules(List<WeeklySchedule> weeklySchedules) {
            this.weeklySchedules = weeklySchedules;
            return this;
        }

        public Builder setCustomSchedules(List<CustomSchedule> customSchedules) {
            this.customSchedules = customSchedules;
            return this;
        }

        public Builder setFlightInstances(List<FlightInstance> flightInstances) {
            this.flightInstances = flightInstances;
            return this;
        }

        public Flight build() {
            return new Flight(this);
        }
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public Flight setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
        return this;
    }

    public Airport getDeparture() {
        return departure;
    }

    public Flight setDeparture(Airport departure) {
        this.departure = departure;
        return this;
    }

    public Airport getArrival() {
        return arrival;
    }

    public Flight setArrival(Airport arrival) {
        this.arrival = arrival;
        return this;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public Flight setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
        return this;
    }

    public List<WeeklySchedule> getWeeklySchedules() {
        return weeklySchedules;
    }

    public Flight setWeeklySchedules(List<WeeklySchedule> weeklySchedules) {
        this.weeklySchedules = weeklySchedules;
        return this;
    }

    public List<CustomSchedule> getCustomSchedules() {
        return customSchedules;
    }

    public Flight setCustomSchedules(List<CustomSchedule> customSchedules) {
        this.customSchedules = customSchedules;
        return this;
    }

    public List<FlightInstance> getFlightInstances() {
        return flightInstances;
    }

    public Flight setFlightInstances(List<FlightInstance> flightInstances) {
        this.flightInstances = flightInstances;
        return this;
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Flight{");
        sb.append("\"flightNumber\": \"").append(flightNumber).append('"');
        sb.append(", \"departure\":").append(departure);
        sb.append(", \"arrival\":").append(arrival);
        sb.append(", \"durationInMinutes\":").append(durationInMinutes);
        sb.append(", \"weeklySchedules\":").append(weeklySchedules);
        sb.append(", \"customSchedules\":").append(customSchedules);
        sb.append(", \"flightInstances\":").append(flightInstances);
        sb.append('}');
        return sb.toString();
    }
}
