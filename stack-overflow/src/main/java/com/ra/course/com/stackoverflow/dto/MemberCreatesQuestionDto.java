package com.ra.course.com.stackoverflow.dto;

import com.ra.course.com.stackoverflow.entity.Searchable;
import com.ra.course.com.stackoverflow.entity.implementation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MemberCreatesQuestionDto {

    final private AccountSearchDto accountSearchDto;
    final private QuestionDto questionDto;


    public MemberCreatesQuestionDto(AccountSearchDto accountSearchDto, QuestionDto questionDto) {
        Objects.requireNonNull(accountSearchDto, "argument 'accountSearchDto' must not be null");
        Objects.requireNonNull(questionDto, "argument 'questionDto' must not be null");

        this.accountSearchDto = accountSearchDto;
        this.questionDto = questionDto;
    }

    public AccountSearchDto getAccountSearchDto() {
        return accountSearchDto;
    }

    public String getEmail() {
        return this.accountSearchDto.getEmail();
    }

    public QuestionDto getQuestionDto() {
        return questionDto;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getEmail());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if ( !(o instanceof MemberCreatesQuestionDto)) {
            return false;
        }
        MemberCreatesQuestionDto that = (MemberCreatesQuestionDto) o;

        return this.getEmail().equals(that.getEmail());
    }



    @Override
    public String toString() {
        return this.accountSearchDto.getEmail();
    }
}
