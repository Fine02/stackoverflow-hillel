package com.ra.course.com.stackoverflow.service.validator;

import com.ra.course.com.stackoverflow.StackOverflowApplication;
import com.ra.course.com.stackoverflow.service.RepositoryTestConfiguration;
import com.ra.course.com.stackoverflow.service.validator.implementation.EmailValidatorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = RepositoryTestConfiguration.class)
public class EmailValidatorServiceTest {
    @Autowired
    private EmailValidatorService emailValidator;

    @Test
    public void whenEmailIsValidThenReturnTrue() {
        //given
        List<String> validEmail = new ArrayList<>() {{
            add("user@domain.com");
            add("user@domain.co.in");
            add("user.name@domain.com");
            add("user_name@domain.com");
            add("username@yahoo.corporate.in");
        }};
        //then
        for (String email : validEmail) {
            assertTrue(emailValidator.checkIsValid(email));
        }
    }

    @Test
    public void whenEmailIsInvalidThenReturnFalse() {
        //given
        List<String> invalidEmail = new ArrayList<>() {{
            add(".username@yahoo.com");
            add("username@yahoo.com.");
            add("username@yahoo..com");
            add("username@yahoo.c");
            add("username@yahoo.corporate");
        }};
        //then
        for (String email : invalidEmail) {
            assertFalse(emailValidator.checkIsValid(email));
        }
    }

    @Test
    public void whenEmailIsNullThenReturnFalse() {
        assertFalse(emailValidator.checkIsValid(null));
    }
}
