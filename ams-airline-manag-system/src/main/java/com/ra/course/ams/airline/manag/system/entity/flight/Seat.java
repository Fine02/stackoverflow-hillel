package com.ra.course.ams.airline.manag.system.entity.flight;

public class Seat {

    private Aircraft aircraft;
    private String seatNumber;
    private SeatType type;
    // set name of field seatClass because of 'class' is key word
    private SeatClass seatClass;

    public Seat() {
    }

    private Seat(Builder builder) {
        aircraft = builder.aircraft;
        seatNumber = builder.seatNumber;
        type = builder.type;
        seatClass = builder.seatClass;
    }

    public static class Builder {

        private transient Aircraft aircraft;
        private transient String seatNumber;
        private transient SeatType type;
        private transient SeatClass seatClass;

        public Builder () {}

        public Builder setAircraft(Aircraft aircraft) {
            this.aircraft = aircraft;
            return this;
        }

        public Builder setSeatNumber(String seatNumber) {
            this.seatNumber = seatNumber;
            return this;
        }

        public Builder setType(SeatType type) {
            this.type = type;
            return this;
        }

        public Builder setSeatClass(SeatClass seatClass) {
            this.seatClass = seatClass;
            return this;
        }

        public Seat build() {
            return new Seat(this);
        }
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public SeatType getType() {
        return type;
    }

    public void setType(SeatType type) {
        this.type = type;
    }

    public SeatClass getSeatClass() {
        return seatClass;
    }

    public void setSeatClass(SeatClass seatClass) {
        this.seatClass = seatClass;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Seat{");
        sb.append("\"aircraft\":").append(aircraft);
        sb.append(", \"seatNumber\": \"").append(seatNumber).append('"');
        sb.append(", \"type\":").append(type);
        sb.append(", \"seatClass\":").append(seatClass);
        sb.append('}');
        return sb.toString();
    }
}
