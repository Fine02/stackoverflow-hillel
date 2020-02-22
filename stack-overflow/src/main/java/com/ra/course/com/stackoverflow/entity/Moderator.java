package com.ra.course.com.stackoverflow.entity;

public class Moderator extends Member {
    public Moderator(Account account) {
        super(account);
    }

    @Override
    public Account getAccount() {
        return super.getAccount();
    }

    @Override
    public void setAccount(Account account) {
        super.setAccount(account);
    }
}
