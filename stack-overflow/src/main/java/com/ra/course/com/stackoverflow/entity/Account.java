package com.ra.course.com.stackoverflow.entity;

public class Account {
    private long id;
    private String password;
    private AccountStatus status;
    private String name;
    private String email;
    private int reputation;

    public Account(String password, AccountStatus status, String name, String email, int reputation) {
        this.password = password;
        this.status = status;
        this.name = name;
        this.email = email;
        this.reputation = reputation;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getReputation() {
        return reputation;
    }

    public void setReputation(int reputation) {
        this.reputation = reputation;
    }
}