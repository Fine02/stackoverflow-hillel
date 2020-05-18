package com.ra.course.com.stackoverflow.controller;

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

import static com.ra.course.com.stackoverflow.controller.MemberController.logInTemplate;
import static com.ra.course.com.stackoverflow.controller.MemberController.registerTemplate;

@ControllerAdvice
public class StackOverflowControllerAdvice {

    private final static String REGISTER_URL = "member/register";
    private final static String LOGIN_URL = "member/login";
    private final static String MAIN_TEMPLATE = "main";

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
        } else {
            return MAIN_TEMPLATE;
        }
    }

    @ExceptionHandler(MemberNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMemberExceptions(final Exception e, final Model model) {
        model.addAttribute("text", e.getMessage());
        return MAIN_TEMPLATE;
    }

    @ExceptionHandler(LoginMemberException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleLoginMemberException(final Exception e, final Model model) {
        model.addAttribute("text", e.getMessage());
        return logInTemplate(model);
    }

    @ExceptionHandler(AlreadyExistAccountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleAlreadyExistAccountException(final Exception e, final Model model) {
        model.addAttribute("text", e.getMessage());
        return registerTemplate(model);

    }
}
