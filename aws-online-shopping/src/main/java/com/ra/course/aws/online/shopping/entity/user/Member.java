package com.ra.course.aws.online.shopping.entity.user;


public class Member extends Customer {
    private Account account;
    private long memberID;

    public Member() {
    }

    public Member(Account account) {
        this.account = account;
    }

    public Member(Account account, long memberID) {
        this.account = account;
        this.memberID = memberID;
    }


    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public long getMemberID() {
        return memberID;
    }

    public void setMemberID(long memberID) {
        this.memberID = memberID;
    }
}
