package com.ra.course.com.stackoverflow.controller;

import com.ra.course.com.stackoverflow.dto.LogInDto;
import com.ra.course.com.stackoverflow.dto.MemberDto;
import com.ra.course.com.stackoverflow.dto.RegisterDto;
import com.ra.course.com.stackoverflow.exception.service.AlreadyExistAccountException;
import com.ra.course.com.stackoverflow.exception.service.LoginException;
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

    final static String REGISTER_TEMPLATE = "member/register";
    final static String LOGIN_TEMPLATE = "member/login";

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBindException(final BindException e, final Model model,
                                      final HttpServletRequest request) {

        e.getBindingResult().getFieldErrors().forEach(
                fieldError -> model.addAttribute(fieldError.getField() + "Error",
                        fieldError.getDefaultMessage()));

        if (request.getRequestURI().contains(REGISTER_TEMPLATE)) {
            model.addAttribute("registerDto", new RegisterDto());
            return REGISTER_TEMPLATE;
        } else if (request.getRequestURI().contains(LOGIN_TEMPLATE)) {
            model.addAttribute("logInDto", new LogInDto());
            return LOGIN_TEMPLATE;
        } else {
            return "main";
        }
    }

    @ExceptionHandler({AlreadyExistAccountException.class, MemberNotFoundException.class, LoginException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleAlreadyExistAccountException(final Exception e, final Model model) {

        model.addAttribute("memberDto", new MemberDto());
        model.addAttribute("logInDto", new LogInDto());
        model.addAttribute("registerDto", new RegisterDto());
        model.addAttribute("text", e.getMessage());

        return e.getClass().isInstance(AlreadyExistAccountException.class) ? REGISTER_TEMPLATE
             : e.getClass().isInstance(MemberNotFoundException.class) ? "member/view-members"
             : LOGIN_TEMPLATE;

    }
}
