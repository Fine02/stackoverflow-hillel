package com.ra.course.ams.airline.manag.system.entity.person;

public class Account {

    private final String id;
    private String password;
    private AccountStatus accountStatus;
    private final Person person;

    public Account(String id, Person person) {
        this.id = id;
        this.person = person;
    }

    public Account(Account account) {
        this.id = account.getId();
        this.password = account.getPassword();
        this.accountStatus = account.getAccountStatus();
        this.person = account.getPerson();
    }

    private Account(Builder builder) {
        this.id = builder.id;
        this.password = builder.password;
        this.accountStatus = builder.accountStatus;
        this.person = builder.person;
    }

    public static class Builder {
        private transient String id;
        private transient String password;
        private transient AccountStatus accountStatus;
        private transient Person person;

        public Builder(String id, Person person) {
            this.id = id;
            this.person = person;
        }

        public Account build(){
            return new Account(this);
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setAccountStatus(AccountStatus accountStatus) {
            this.accountStatus = accountStatus;
            return this;
        }

        public Builder setPerson(Pilot person) {
            this.person = person;
            return this;
        }
    }

    public String getId() {
        return id;
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
