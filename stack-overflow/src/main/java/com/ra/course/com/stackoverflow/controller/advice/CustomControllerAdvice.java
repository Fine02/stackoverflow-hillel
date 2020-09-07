package com.ra.course.com.stackoverflow.controller.advice;

import com.ra.course.com.stackoverflow.exception.service.*;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class CustomControllerAdvice {

    private final static String EXC_MSG = "text";

    @ExceptionHandler(Exception.class)
    public RedirectView handleBindException(final Exception e, final RedirectAttributes attributes,
                                            final HttpServletRequest request) {
        if(e instanceof HttpRequestMethodNotSupportedException){
            return new RedirectView("/", true);
        }

        attributes.addFlashAttribute(EXC_MSG, e.getMessage());

        final var requestURI = request.getRequestURI();

        return new RedirectView(requestURI, true);
    }

    @ExceptionHandler({AlreadyVotedException.class, CannotVoteOwnPostException.class})
    public RedirectView handleVoteExceptions(final Exception e, final HttpServletRequest request,
                                              final RedirectAttributes attributes){
        attributes.addFlashAttribute(EXC_MSG, e.getMessage());
        final String[] split = request.getRequestURI().split("/");

        return new RedirectView("/view/question/" + Long.valueOf(split[4]));
    }

    @ExceptionHandler({LoginMemberException.class, WrongPasswordException.class, AlreadyExistAccountException.class})
    public RedirectView handleLoginAndRegistrationException(final Exception e, final HttpServletRequest request,
                                                            final RedirectAttributes attributes) {
        attributes.addFlashAttribute(EXC_MSG, e.getMessage());
        return new RedirectView(request.getRequestURI(), true);
    }
}
