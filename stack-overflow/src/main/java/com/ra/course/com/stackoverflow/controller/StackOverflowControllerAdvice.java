package com.ra.course.com.stackoverflow.controller;

import com.ra.course.com.stackoverflow.dto.LogInDto;
import com.ra.course.com.stackoverflow.dto.RegisterDto;
import com.ra.course.com.stackoverflow.dto.UpdateDto;
import com.ra.course.com.stackoverflow.exception.service.AlreadyExistAccountException;
import com.ra.course.com.stackoverflow.exception.service.LoginMemberException;
import com.ra.course.com.stackoverflow.exception.service.MemberNotFoundException;
import com.ra.course.com.stackoverflow.exception.service.WrongPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

import static com.ra.course.com.stackoverflow.controller.ControllerConstants.*;

@ControllerAdvice
public class StackOverflowControllerAdvice {

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBindException(final BindException e, final Model model,
                                      final HttpServletRequest request) {

        e.getBindingResult().getFieldErrors().forEach(
                fieldError -> model.addAttribute(fieldError.getField() + "Error",
                        fieldError.getDefaultMessage()));

        if (request.getRequestURI().contains(REGISTER_URL)) {
            return registerTemplate(model);
        } else if (request.getRequestURI().contains(LOGIN_URL)) {
            return logInTemplate(model);
        } else if (request.getRequestURI().contains(UPDATE_URL)) {
            return updateTemplate(model);
        } else {
            return MAIN_TEMPLATE;
        }
    }

    @ExceptionHandler(MemberNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleMemberExceptions(final Exception e, final Model model) {
        model.addAttribute(TEXT_ATTR, e.getMessage());
        return MAIN_TEMPLATE;
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleNPEExceptions(final Exception e, final Model model) {
        model.addAttribute(TEXT_ATTR, e.getMessage());
        return MAIN_TEMPLATE;
    }

    @ExceptionHandler(LoginMemberException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleLoginMemberException(final Exception e, final Model model) {
        model.addAttribute(TEXT_ATTR, e.getMessage());
        return logInTemplate(model);
    }

    @ExceptionHandler(AlreadyExistAccountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleAlreadyExistAccountException(final Exception e, final Model model) {
        model.addAttribute(TEXT_ATTR, e.getMessage());
        return registerTemplate(model);
    }

    @ExceptionHandler(WrongPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleWrongPasswordException(final Exception e, final Model model,
                                               final HttpServletRequest request) {

        model.addAttribute(TEXT_ATTR, e.getMessage());

        if (request.getRequestURI().contains(LOGIN_URL)) {
            return logInTemplate(model);
        } else if (request.getRequestURI().contains(UPDATE_URL)) {
            return updateTemplate(model);
        } else if (request.getRequestURI().contains(DELETE_URL)) {
            return UPDATE_TEMPLATE;
        } else {
            return MAIN_TEMPLATE;
        }
    }
    private String logInTemplate(final Model model) {
        model.addAttribute("logInDto", new LogInDto());
        return LOGIN_TEMPLATE;
    }

    private String registerTemplate(final Model model) {
        model.addAttribute("registerDto", new RegisterDto());
        return REGISTER_TEMPLATE;
    }

    private String updateTemplate(final Model model) {
        model.addAttribute("updateDto", new UpdateDto());
        return UPDATE_TEMPLATE;
    }

}
