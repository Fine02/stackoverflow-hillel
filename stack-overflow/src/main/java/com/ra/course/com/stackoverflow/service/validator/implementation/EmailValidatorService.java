package com.ra.course.com.stackoverflow.service.validator.implementation;

import com.ra.course.com.stackoverflow.service.validator.ValidatorService;

import java.util.regex.Pattern;

public class EmailValidatorService implements ValidatorService<String> {
    @Override
    public boolean checkIsValid(final String emailToValidate) {
        final String regex = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        return emailToValidate != null && Pattern.matches(regex, emailToValidate);
    }
}