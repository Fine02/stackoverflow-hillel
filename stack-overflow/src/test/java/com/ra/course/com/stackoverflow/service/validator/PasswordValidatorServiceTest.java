package com.ra.course.com.stackoverflow.service.validator;

import com.ra.course.com.stackoverflow.service.RepositoryTestConfiguration;
import com.ra.course.com.stackoverflow.service.validator.implementation.PasswordValidatorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = RepositoryTestConfiguration.class)
public class PasswordValidatorServiceTest {
    @Autowired
    private PasswordValidatorService passwordValidator;

    @Test
    public void whenPasswordIsValidThenReturnTrue(){
        //given
        List<String> validPassword= new ArrayList<>(){{
            add("Rt8&lkmf ");
            add("#jUU889 ");
        }};
        //then
        for (String pass : validPassword) {
            assertTrue(passwordValidator.checkIsValid(pass));
        }
    }
    @Test
    public void whenPasswordIsInvalidThenReturnFalse(){
        //given
        List<String> invalidPassword= new ArrayList<>(){{
            add("dsfg");
            add("PASSWORD");
            add("111111111");
            add("*&^%$#@$%^^&");
        }};
        //then
        for (String pass : invalidPassword) {
            assertFalse(passwordValidator.checkIsValid(pass));
        }
    }
    @Test
    public void whenPasswordIsNullThenReturnFalse(){
        assertFalse(passwordValidator.checkIsValid(null));
    }
}
