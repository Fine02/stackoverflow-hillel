package com.ra.course.com.stackoverflow.service.validator;

import java.util.regex.Pattern;

public class NameValidatorService implements ValidatorService<String> {

    @Override
    public boolean checkIsValid(String s) {
        String regex = "^[A-za-z][A-Za-z0-9+_.-]{5,20}$";
        return Pattern.matches(regex, s);
    }
}
