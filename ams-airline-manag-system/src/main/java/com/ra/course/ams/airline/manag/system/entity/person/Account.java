package com.ra.course.ams.airline.manag.system.entity.person;

public class Account {

    private static final String DEFAULT_PASSWORD = "changeme";

    private String id;
    private String password;
    private AccountStatus accountStatus;
    private Person person;
    private boolean passwordResetNeeded;

    public boolean resetPassword(){
        return passwordResetNeeded;
    }

    public Account() {
    }


    public Account(String id) {
        this.id = id;
        this.password = DEFAULT_PASSWORD;
        this.passwordResetNeeded = true;
    }

    public Account(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public Account(String id, String password, Person person) {
        this.id = id;
        this.password = password;
        this.person = person;
        this.accountStatus = AccountStatus.Active;
    }

    public Account(String id, String password, AccountStatus accountStatus, Person person) {
        this.id = id;
        this.password = password;
        this.accountStatus = accountStatus;
        this.person = person;
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

    public void setPasswordResetNeeded(boolean passwordResetNeeded) {
        this.passwordResetNeeded = passwordResetNeeded;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Account{");
        sb.append("\"id\": \"").append(id).append('"');
        sb.append(", \"password\": \"").append(password).append('"');
        sb.append(", \"accountStatus\":").append(accountStatus);
        sb.append(", \"person\":").append(person);
        sb.append(", \"passwordResetNeeded\":").append(passwordResetNeeded);
        sb.append('}');
        return sb.toString();
    }
}
