package src.main.java.com.ra.course.ams.airline.manag.system.entity.flight;

import java.util.List;

public class Flight {

    private String flightNumber;
    private Airport departure;
    private Airport arrival;
    private int durationInMinutes;
    private List<WeeklySchedule> weeklySchedules;
    private List<CustomSchedule> customSchedules;
    private List<FlightInstance> flightInstances;

    public List<FlightInstance> getInstances() {
        return flightInstances;
    }

    // TODO implementation is not clear
    public boolean cancel() {
        return false;
    }

    public boolean addFlightSchedule(WeeklySchedule weeklySchedule) {
        try {
            weeklySchedules.add(weeklySchedule);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean addFlightSchedule(CustomSchedule customSchedule) {
        try {
            customSchedules.add(customSchedule);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Flight() {
    }

    public Flight(String flightNumber, Airport departure, Airport arrival, int durationInMinutes) {
        this.flightNumber = flightNumber;
        this.departure = departure;
        this.arrival = arrival;
        this.durationInMinutes = durationInMinutes;
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

        private String flightNumber;
        private Airport departure;
        private Airport arrival;
        private int durationInMinutes;
        private List<WeeklySchedule> weeklySchedules;
        private List<CustomSchedule> customSchedules;
        private List<FlightInstance> flightInstances;

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

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public Airport getDeparture() {
        return departure;
    }

    public void setDeparture(Airport departure) {
        this.departure = departure;
    }

    public Airport getArrival() {
        return arrival;
    }

    public void setArrival(Airport arrival) {
        this.arrival = arrival;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public List<WeeklySchedule> getWeeklySchedules() {
        return weeklySchedules;
    }

    public void setWeeklySchedules(List<WeeklySchedule> weeklySchedules) {
        this.weeklySchedules = weeklySchedules;
    }

    public List<CustomSchedule> getCustomSchedules() {
        return customSchedules;
    }

    public void setCustomSchedules(List<CustomSchedule> customSchedules) {
        this.customSchedules = customSchedules;
    }

    public List<FlightInstance> getFlightInstances() {
        return flightInstances;
    }

    public void setFlightInstances(List<FlightInstance> flightInstances) {
        this.flightInstances = flightInstances;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("src.main.java.com.ra.course.ams.airline.manag.system.entity.flight.Flight{");
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
