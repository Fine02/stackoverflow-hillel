package com.ra.course.com.stackoverflow.service.validator;

import com.ra.course.com.stackoverflow.service.RepositoryTestConfiguration;
import com.ra.course.com.stackoverflow.service.validator.implementation.NameValidatorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = RepositoryTestConfiguration.class)
public class NameValidatorServiceTest {
        @Autowired
        private NameValidatorService nameValidator;

        @Test
        public void whenNameIsValidThenReturnTrue(){
            //given
            List<String> validName= new ArrayList<>(){{
                add("askj12nfj");
                add("Alina.Fine02");
                add("alina_fine-02");
                add("A11111111");
            }};
            //then
            for (String name : validName) {
                assertTrue(nameValidator.checkIsValid(name));
            }
        }
        @Test
        public void whenNameIsInvalidThenReturnFalse(){
            //given
            List<String> invalidName= new ArrayList<>(){{
                add("4glfkn");
                add("ert");
                add("111111111");
                add("tkl1&");
            }};
            //then
            for (String pass : invalidName) {
                assertFalse(nameValidator.checkIsValid(pass));
            }
        }
        @Test
        public void whenNameIsNullThenReturnFalse(){
            assertFalse(nameValidator.checkIsValid(null));
        }
}
