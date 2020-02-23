package com.ra.course.com.stackoverflow.service.validator;

import com.ra.course.com.stackoverflow.service.validator.implementation.NameValidatorService;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class NameValidatorServiceTest {
        private NameValidatorService nameValidator;

        @BeforeEach
        void setUp() {
            nameValidator = new NameValidatorService();
        }

        @AfterEach
        void tearDown() {
            nameValidator = null;
        }

        @Test
        public void validNameTest(){
            List<String> validName= new ArrayList<>(){{
                add("askj12nfj");
                add("Alina.Fine02");
                add("alina_fine-02");
                add("A11111111");
            }};
            for (String name : validName) {
                assertTrue(nameValidator.checkIsValid(name));
            }
        }
        @Test
        public void invalidNameTest(){
            List<String> invalidName= new ArrayList<>(){{
                add("4glfkn");
                add("ert");
                add("111111111");
                add("tkl1&");
            }};
            for (String pass : invalidName) {
                assertFalse(nameValidator.checkIsValid(pass));
            }
        }
        @Test
        public void nullNameTest(){
            assertFalse(nameValidator.checkIsValid(null));
        }
}
