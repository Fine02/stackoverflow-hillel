package com.ra.course.com.stackoverflow.dto;

import com.ra.course.com.stackoverflow.entity.enumeration.AccountStatus;

import java.util.Objects;

public class AccountSearchDto {
    private String email;

    public AccountSearchDto(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.email);
    }

    @Override
    public boolean equals(Object o){
        if (o == this) {
            return true;
        }

        if (!(o instanceof AccountSearchDto)) {
            return false;
        }

        AccountSearchDto that = (AccountSearchDto) o;

        return this.email.equals(that.email);
    }

    @Override
    public String toString() {
        return "AccountSearchDto{" +
                ", email='" + email + '\'' +
                '}';
    }
}