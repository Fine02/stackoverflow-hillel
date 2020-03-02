package com.ra.course.ams.airline.manag.system.entity.person;

import com.ra.course.ams.airline.manag.system.entity.Address;
import com.ra.course.ams.airline.manag.system.entity.flight.FlightInstance;

import java.util.LinkedList;
import java.util.List;

public class Pilot extends Person {

    private List<FlightInstance> flightInstances;

    public Pilot() {
        super();
    }

    public Pilot(List<FlightInstance> flightInstances) {
        super();
        this.flightInstances = flightInstances;
    }

    public Pilot(Builder builder) {
        this.setName(builder.name);
        this.setEmail(builder.email);
        this.setPhone(builder.phone);
        this.setAddress(builder.address);
        flightInstances = builder.flightInstances;
    }

    public static class Builder {
        private transient String name;
        private transient String email;
        private transient String phone;
        private transient Address address;
        private List<FlightInstance> flightInstances = new LinkedList<>();

        public Pilot build() {
            return new Pilot(this);
        }

        public Builder addFlightInstance(FlightInstance flightInstance){
            this.flightInstances.add(flightInstance);
            return this;
        }

        public Builder setFlightInstances(List<FlightInstance> flightInstances) {
            this.flightInstances = flightInstances;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder setAddress(Address address) {
            this.address = address;
            return this;
        }
    }

    public List<FlightInstance> getFlightInstances() {
        return flightInstances;
    }

    public void setFlightInstances(List<FlightInstance> flightInstances) {
        this.flightInstances = flightInstances;
    }
}
