package com.ra.course.com.stackoverflow.controller;

import com.ra.course.com.stackoverflow.dto.LogInDto;
import com.ra.course.com.stackoverflow.dto.MemberDto;
import com.ra.course.com.stackoverflow.dto.RegisterDto;
import com.ra.course.com.stackoverflow.exception.service.AlreadyExistAccountException;
import com.ra.course.com.stackoverflow.exception.service.LoginMemberException;
import com.ra.course.com.stackoverflow.exception.service.MemberNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class StackOverflowControllerAdvice {

    private final static String REGISTER_TEMPLATE = "member/register";
    private final static String LOGIN_TEMPLATE = "member/login";
    private final static String MAIN_TEMPLATE = "main";

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBindException(final BindException e, final Model model,
                                      final HttpServletRequest request) {

        e.getBindingResult().getFieldErrors().forEach(
                fieldError -> model.addAttribute(fieldError.getField() + "Error",
                        fieldError.getDefaultMessage()));

        if (request.getRequestURI().contains(REGISTER_TEMPLATE)) {
            return returnRegisterTemplate(model);
        } else if (request.getRequestURI().contains(LOGIN_TEMPLATE)) {
            return returnLogInTemplate(model);
        } else {
            return MAIN_TEMPLATE;
        }
    }

    @ExceptionHandler({AlreadyExistAccountException.class, MemberNotFoundException.class, LoginMemberException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMemberExceptions(final Exception e, final Model model) {

        model.addAttribute("text", e.getMessage());
        model.addAttribute("memberDto", new MemberDto());

        return e instanceof AlreadyExistAccountException
                ? returnRegisterTemplate(model)
                : e instanceof LoginMemberException
                    ? returnLogInTemplate(model)
                    : MAIN_TEMPLATE;

    }

    private String returnLogInTemplate(final Model model){
        model.addAttribute("logInDto", new LogInDto());
        return LOGIN_TEMPLATE;
    }
    private String returnRegisterTemplate(final Model model){
        model.addAttribute("registerDto", new RegisterDto());
        return REGISTER_TEMPLATE;
    }
}
