package com.ra.course.ams.airline.manag.system.entity.flight;

public class FlightSeat extends Seat {

    private FlightInstance flightInstance;
    private float fare;
    private String reservationNumber;

    public FlightSeat() {
    }

    private FlightSeat(Builder builder) {
        flightInstance = builder.flightInstance;
        fare = builder.fare;
        reservationNumber = builder.reservationNumber;
    }

    public static class Builder {

        private transient FlightInstance flightInstance;
        private transient float fare;
        private transient String reservationNumber;

        public Builder () {}

        public Builder setFlightInstance(FlightInstance flightInstance) {
            this.flightInstance = flightInstance;
            return this;
        }

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

    public FlightInstance getFlightInstance() {
        return flightInstance;
    }

    public void setFlightInstance(FlightInstance flightInstance) {
        this.flightInstance = flightInstance;
    }

    public float getFare() {
        return fare;
    }

    public void setFare(float fare) {
        this.fare = fare;
    }

    public String getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(String reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("FlightSeat{");
        sb.append("\"flightInstance\":").append(flightInstance);
        sb.append(", \"fare\":").append(fare);
        sb.append(", \"reservationNumber\": \"").append(reservationNumber).append('"');
        sb.append('}');
        return sb.toString();
    }
}
