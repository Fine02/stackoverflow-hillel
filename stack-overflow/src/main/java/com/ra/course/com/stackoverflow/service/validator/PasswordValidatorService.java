package com.ra.course.com.stackoverflow.service.validator;

import java.util.regex.Pattern;

public class PasswordValidatorService implements ValidatorService<String> {
    @Override
    public boolean checkIsValid(String s) {
        String regex = "^.{8,14}";
        return Pattern.matches(regex, s);
    }
}
