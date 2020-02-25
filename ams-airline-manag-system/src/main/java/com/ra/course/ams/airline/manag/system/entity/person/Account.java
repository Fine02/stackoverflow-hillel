package com.ra.course.ams.airline.manag.system.entity.person;

public class Account {

    private String id;
    private String password;
    private AccountStatus accountStatus;
    private Person person;

    public Account() {
    }

    public Account(String id) {
        this.id = id;
    }

    private Account(Builder builder) {
        this.id = id;
        this.password = password;
        this.accountStatus = accountStatus;
        this.person = person;
    }

    public static class Builder {
        private String id;
        private String password;
        private AccountStatus accountStatus;
        private Person person;

        public Account build(){
            return new Account(this);
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder accountStatus(AccountStatus accountStatus) {
            this.accountStatus = accountStatus;
            return this;
        }

        public Builder person(Person person) {
            this.person = person;
            return this;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Account{");
        sb.append("\"id\": \"").append(id).append('"');
        sb.append(", \"password\": \"").append(password).append('"');
        sb.append(", \"accountStatus\":").append(accountStatus);
        sb.append(", \"person\":").append(person);
        sb.append('}');
        return sb.toString();
    }
}
