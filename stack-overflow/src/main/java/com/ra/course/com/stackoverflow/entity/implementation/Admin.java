package com.ra.course.com.stackoverflow.entity.implementation;

public class Admin extends Member {
    public Admin(Account account) {
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
