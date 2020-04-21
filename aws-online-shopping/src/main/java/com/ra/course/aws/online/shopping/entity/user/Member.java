package com.ra.course.aws.online.shopping.entity.user;


import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return memberID == member.memberID &&
                Objects.equals(account, member.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, memberID);
    }
}
