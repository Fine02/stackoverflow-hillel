package com.ra.course.aws.online.shopping.entity.user;

public class Member extends Customer {
    private Account account;

    public Member() {
    }

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
