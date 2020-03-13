package com.ra.course.com.stackoverflow.service.validator.implementation;

import com.ra.course.com.stackoverflow.service.validator.ValidatorService;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class NameValidatorService implements ValidatorService<String> {

    @Override
    public boolean checkIsValid(final String nameToValidate) {
        final String regex = "^[A-za-z][A-Za-z0-9+_.-]{5,20}$";
        return  nameToValidate!=null && Pattern.matches(regex, nameToValidate);
    }
}
