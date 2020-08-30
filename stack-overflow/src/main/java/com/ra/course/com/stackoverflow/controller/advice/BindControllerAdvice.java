package com.ra.course.com.stackoverflow.controller.advice;

import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class BindControllerAdvice {

    @ExceptionHandler(BindException.class)
    public RedirectView handleBindException(final BindException e, final RedirectAttributes attributes,
                             final HttpServletRequest request) {

        e.getBindingResult().getFieldErrors().forEach(
                fieldError -> attributes.addFlashAttribute(fieldError.getField() + "Error",
                        fieldError.getDefaultMessage()));

        final var requestURI = request.getRequestURI();

        return new RedirectView(requestURI, true);
    }
}
