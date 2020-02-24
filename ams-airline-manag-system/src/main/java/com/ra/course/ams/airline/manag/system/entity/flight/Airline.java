package src.main.java.com.ra.course.ams.airline.manag.system.entity.flight;

import java.util.List;

public class Airline {

    private String name;
    private String code;
    private List<Flight> flights;
    private List<Aircraft> aircrafts;

    public Airline() {
    }

    public Airline(String name, String code, List<Flight> flights, List<Aircraft> aircrafts) {
        this.name = name;
        this.code = code;
        this.flights = flights;
        this.aircrafts = aircrafts;
    }

    private Airline(Builder builder) {
        name = builder.name;
        code = builder.code;
        flights = builder.flights;
        aircrafts = builder.aircrafts;
    }

    public static class Builder {

        private String name;
        private String code;
        private List<Flight> flights;
        private List<Aircraft> aircrafts;

        public Builder () {}

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setCode(String code) {
            this.code = code;
            return this;
        }

        public Builder setFlights(List<Flight> flights) {
            this.flights = flights;
            return this;
        }

        public Builder setAircrafts(List<Aircraft> aircrafts) {
            this.aircrafts = aircrafts;
            return this;
        }

        public Airline build() {
            return new Airline(this);
        }
    }

    public String getName() {
        return name;
    }

    public Airline setName(String name) {
        this.name = name;
        return this;
    }

    public String getCode() {
        return code;
    }

    public Airline setCode(String code) {
        this.code = code;
        return this;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public Airline setFlights(List<Flight> flights) {
        this.flights = flights;
        return this;
    }

    public List<Aircraft> getAircrafts() {
        return aircrafts;
    }

    public Airline setAircrafts(List<Aircraft> aircrafts) {
        this.aircrafts = aircrafts;
        return this;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("src.main.java.com.ra.course.ams.airline.manag.system.entity.flight.Airline{");
        sb.append("\"name\": \"").append(name).append('"');
        sb.append(", \"code\": \"").append(code).append('"');
        sb.append(", \"flights\":").append(flights);
        sb.append(", \"aircrafts\":").append(aircrafts);
        sb.append('}');
        return sb.toString();
    }
}
