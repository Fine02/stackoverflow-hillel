package com.ra.course.ams.airline.manag.system.entity.flight;

public class Seat {

    private String seatNumber;
    private SeatType type;
    // set name of field seatClass because of 'class' is key word
    private SeatClass seatClass;

    public Seat() {
    }

    public Seat(String seatNumber, SeatType type, SeatClass seatClass) {
        this.seatNumber = seatNumber;
        this.type = type;
        this.seatClass = seatClass;
    }

    private Seat(Builder builder) {
        seatNumber = builder.seatNumber;
        type = builder.type;
        seatClass = builder.seatClass;
    }

    public static class Builder {

        private transient String seatNumber;
        private transient SeatType type;
        private transient SeatClass seatClass;

        public Builder () {}

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

    public String getSeatNumber() {
        return seatNumber;
    }

    public Seat setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
        return this;
    }

    public SeatType getType() {
        return type;
    }

    public Seat setType(SeatType type) {
        this.type = type;
        return this;
    }

    public SeatClass getSeatClass() {
        return seatClass;
    }

    public Seat setSeatClass(SeatClass seatClass) {
        this.seatClass = seatClass;
        return this;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("com.ra.course.ams.airline.manag.system.entity.flight.Seat{");
        sb.append("\"seatNumber\": \"").append(seatNumber).append('"');
        sb.append(", \"seatType\":").append(type);
        sb.append(", \"seatClass\":").append(seatClass);
        sb.append('}');
        return sb.toString();
    }
}
