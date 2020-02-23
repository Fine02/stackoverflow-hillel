package com.ra.course.com.stackoverflow.entity.implementation;

import com.ra.course.com.stackoverflow.entity.enumeration.AccountStatus;

public class Account {
    final private long id;
    private String password;
    private AccountStatus status;
    private String name;
    private String email;
    private int reputation;

    public Account(Long id, String password, AccountStatus status, String name, String email, int reputation) {
        this.id = id;
        this.password = password;
        this.status = status;
        this.name = name;
        this.email = email;
        this.reputation = reputation;
    }

    public long getId() {
        return id;
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