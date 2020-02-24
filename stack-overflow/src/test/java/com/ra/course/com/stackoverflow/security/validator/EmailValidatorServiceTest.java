package com.ra.course.com.stackoverflow.security.validator;

import com.ra.course.com.stackoverflow.security.validator.implementation.EmailValidatorService;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EmailValidatorServiceTest {

    private EmailValidatorService emailValidator;

    @BeforeEach
    void setUp() {
        emailValidator = new EmailValidatorService();
    }

    @Test
    public void testShouldReturnTrueIfEmailIsValid() {
        List<String> validEmail = new ArrayList<>() {{
            add("user@domain.com");
            add("user@domain.co.in");
            add("user.name@domain.com");
            add("user_name@domain.com");
            add("username@yahoo.corporate.in");
        }};
        for (String email : validEmail) {
            assertTrue(emailValidator.checkIsValid(email));
        }
    }

    @Test
    public void testShouldReturnFalseIfEmailIsInvalid() {
        List<String> invalidEmail = new ArrayList<>() {{
            add(".username@yahoo.com");
            add("username@yahoo.com.");
            add("username@yahoo..com");
            add("username@yahoo.c");
            add("username@yahoo.corporate");
        }};
        for (String email : invalidEmail) {
            assertFalse(emailValidator.checkIsValid(email));
        }
    }

    @Test
    public void testShouldReturnFalseIfEnterNull(){
        assertFalse(emailValidator.checkIsValid(null));
    }
}
