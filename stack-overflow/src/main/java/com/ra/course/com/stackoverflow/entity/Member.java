package com.ra.course.com.stackoverflow.entity;

public class Member {

    private Account account;

    public Member(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
