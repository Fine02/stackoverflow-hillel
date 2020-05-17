package com.ra.course.com.stackoverflow.controller;

import com.ra.course.com.stackoverflow.dto.LogInDto;
import com.ra.course.com.stackoverflow.dto.MemberDto;
import com.ra.course.com.stackoverflow.dto.RegisterDto;
import com.ra.course.com.stackoverflow.exception.service.AlreadyExistAccountException;
import com.ra.course.com.stackoverflow.exception.service.LoginException;
import com.ra.course.com.stackoverflow.exception.service.MemberNotFoundException;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class StackOverflowControllerAdvice {

    @ExceptionHandler(BindException.class)
    public String handleBindException(final BindException e, final Model model,
                                      final HttpServletRequest request) {

        e.getBindingResult().getFieldErrors().forEach(
                fieldError -> model.addAttribute(fieldError.getField() + "Error",
                        fieldError.getDefaultMessage()));

        if (request.getRequestURI().contains("member/register")) {
            model.addAttribute("registerDto", new RegisterDto());
            return "member/registration";
        } else if (request.getRequestURI().contains("member/login")) {
            model.addAttribute("logInDto", new LogInDto());
            return "member/login";
        } else {
            return "index";
        }
    }

    @ExceptionHandler({AlreadyExistAccountException.class, MemberNotFoundException.class, LoginException.class})
    public String handleAlreadyExistAccountException(final Exception e, final Model model) {

        model.addAttribute("memberDto", new MemberDto());
        model.addAttribute("text", e.getMessage());

        return e.getClass().isInstance(AlreadyExistAccountException.class) ? "member/registration"
             : e.getClass().isInstance(MemberNotFoundException.class) ? "member/view-members"
             : "member/login";

    }
}
