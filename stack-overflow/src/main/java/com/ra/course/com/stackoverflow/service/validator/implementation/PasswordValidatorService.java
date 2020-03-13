package com.ra.course.com.stackoverflow.service.validator.implementation;

import com.ra.course.com.stackoverflow.service.validator.ValidatorService;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class PasswordValidatorService implements ValidatorService<String> {
    @Override
    public boolean checkIsValid(final String passToValidate) {
        final String regex = "^(?=\\P{Ll}*\\p{Ll})(?=\\P{Lu}*\\p{Lu})(?=\\P{N}*\\p{N})(?=[\\p{L}\\p{N}]*[^\\p{L}\\p{N}])[\\s\\S]{8,}$";
        return  passToValidate!=null && Pattern.matches(regex, passToValidate);
    }
}
