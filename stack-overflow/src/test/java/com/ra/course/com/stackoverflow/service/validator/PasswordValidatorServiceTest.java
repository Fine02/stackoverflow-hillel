package com.ra.course.com.stackoverflow.service.validator;

import com.ra.course.com.stackoverflow.service.validator.implementation.PasswordValidatorService;
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

    @AfterEach
    void tearDown() {
        passwordValidator = null;
    }

    @Test
    public void validPasswordTest(){
        List<String> validPassword= new ArrayList<>(){{
            add("Rt8&lkmf ");
            add("#jUU889 ");
        }};
        for (String pass : validPassword) {
            assertTrue(passwordValidator.checkIsValid(pass));
        }
    }
    @Test
    public void invalidPasswordTest(){
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
    public void nullPasswordTest(){
        assertFalse(passwordValidator.checkIsValid(null));
    }
}
