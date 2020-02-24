package com.ra.course.com.stackoverflow.security.validator;

import com.ra.course.com.stackoverflow.security.validator.implementation.PasswordValidatorService;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordValidatorServiceTest {

    private PasswordValidatorService passwordValidator;

    @BeforeEach
    void setUp() {
        passwordValidator = new PasswordValidatorService();
    }

    @Test
    public void testShouldReturnTrueIfPasswordIsValid(){
        List<String> validPassword= new ArrayList<>(){{
            add("Rt8&lkmf ");
            add("#jUU889 ");
        }};
        for (String pass : validPassword) {
            assertTrue(passwordValidator.checkIsValid(pass));
        }
    }
    @Test
    public void testShouldReturnFalseIfPasswordIsInvalid(){
        List<String> invalidPassword= new ArrayList<>(){{
            add("dsfg");
            add("PASSWORD");
            add("111111111");
            add("*&^%$#@$%^^&");
        }};
        for (String pass : invalidPassword) {
            assertFalse(passwordValidator.checkIsValid(pass));
        }
    }
    @Test
    public void testShouldReturnFalseIfEnterNull(){
        assertFalse(passwordValidator.checkIsValid(null));
    }
}
