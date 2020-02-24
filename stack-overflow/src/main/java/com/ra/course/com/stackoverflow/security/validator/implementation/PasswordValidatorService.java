package com.ra.course.com.stackoverflow.security.validator.implementation;

import com.ra.course.com.stackoverflow.security.validator.ValidatorService;

import java.util.regex.Pattern;

public class PasswordValidatorService implements ValidatorService<String> {
    @Override
    public boolean checkIsValid(final String passToValidate) {
        final String regex = "^(?=\\P{Ll}*\\p{Ll})(?=\\P{Lu}*\\p{Lu})(?=\\P{N}*\\p{N})(?=[\\p{L}\\p{N}]*[^\\p{L}\\p{N}])[\\s\\S]{8,}$";
        return  passToValidate!=null && Pattern.matches(regex, passToValidate);
    }
}
