package com.ra.course.com.stackoverflow.service.validator;

import java.util.regex.Pattern;

public class EmailValidatorService implements ValidatorService<String> {
    @Override
    public boolean checkIsValid(String s) {
        String regex = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        return Pattern.matches(regex, s);
    }
}