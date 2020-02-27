package com.ra.course.ams.airline.manag.system.entity.flight;

public class FlightSeat extends Seat {

    private float fare;
    private String reservationNumber;

    public FlightSeat() {
    }

    private FlightSeat(Builder builder) {
        fare = builder.fare;
        reservationNumber = builder.reservationNumber;
    }

    public static class Builder {

        private transient float fare;
        private transient String reservationNumber;

        public Builder () {}

        public Builder setFare(float fare) {
            this.fare = fare;
            return this;
        }

        public Builder setReservationNumber(String reservationNumber) {
            this.reservationNumber = reservationNumber;
            return this;
        }

        public FlightSeat build() {
            return new FlightSeat(this);
        }
    }

    public float getFare() {
        return fare;
    }

    public FlightSeat setFare(float fare) {
        this.fare = fare;
        return this;
    }

    public String getReservationNumber() {
        return reservationNumber;
    }

    public FlightSeat setReservationNumber(String reservationNumber) {
        this.reservationNumber = reservationNumber;
        return this;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("com.ra.course.ams.airline.manag.system.entity.flight.FlightSeat{");
        sb.append("\"fare\":").append(fare);
        sb.append(", \"reservationNumber\": \"").append(reservationNumber).append('"');
        sb.append('}');
        return sb.toString();
    }
}