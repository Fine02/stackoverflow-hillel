package com.ra.course.ams.airline.manag.system.entity.person;

import com.ra.course.ams.airline.manag.system.entity.Address;

public class Admin extends Pilot {

    private Admin(Builder builder) {
        this.setName(builder.name);
        this.setEmail(builder.email);
        this.setPhone(builder.phone);
        this.setAddress(builder.address);
    }

    public Admin(Admin admin) {
        this.setName(admin.getName());
        this.setEmail(admin.getEmail());
        this.setPhone(admin.getPhone());
        this.setAddress(admin.getAddress());
    }

    public static class Builder {
        private transient String name;
        private transient String email;
        private transient String phone;
        private transient Address address;

        public Admin build() {
            return new Admin(this);
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
}
