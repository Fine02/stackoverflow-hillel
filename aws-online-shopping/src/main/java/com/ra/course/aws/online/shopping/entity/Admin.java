package com.ra.course.aws.online.shopping.entity;

import com.ra.course.aws.online.shopping.entity.interfaces.User;

public class Admin implements User {
    private Long id;
    private String Login;
    private char[] password;
    private Account account;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
