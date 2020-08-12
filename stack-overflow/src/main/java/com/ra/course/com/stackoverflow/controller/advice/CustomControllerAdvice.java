package com.ra.course.com.stackoverflow.controller.advice;

import com.ra.course.com.stackoverflow.exception.repository.MemberNotFoundException;
import com.ra.course.com.stackoverflow.exception.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
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
        attributes.addFlashAttribute("text", e.getMessage());
        String[] split = request.getRequestURI().split("/");

        return new RedirectView("/view/question/" + Long.valueOf(split[4]));
    }

    @ExceptionHandler({LoginMemberException.class, WrongPasswordException.class, AlreadyExistAccountException.class})
    public RedirectView handleLoginAndRegistrationException(final Exception e, final HttpServletRequest request,
                                                            final RedirectAttributes attributes) {
        attributes.addFlashAttribute(EXC_MSG, e.getMessage());
        return new RedirectView(request.getRequestURI(), true);
    }

//
//    @ExceptionHandler({MemberNotFoundException.class, QuestionNotFoundException.class,
//            AnswerNotFoundException.class, CommentNotFoundException.class})
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public String handleMemberExceptions(final Exception e, final Model model) {
//        model.addAttribute(TEXT_ATTR, e.getMessage());
//        return MAIN_VIEW;
//    }

//
//    @ExceptionHandler(AlreadyExistAccountException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public String handleAlreadyExistAccountException(final Exception e, final Model model) {
//        model.addAttribute(TEXT_ATTR, e.getMessage());
//        return registerTemplate(model);
//    }
//
//    @ExceptionHandler(WrongPasswordException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public String handleWrongPasswordException(final Exception e, final Model model,
//                                               final HttpServletRequest request) {
//
//        model.addAttribute(TEXT_ATTR, e.getMessage());
//
//        final var requestURI = request.getRequestURI();
//
//        if (requestURI.contains(LOGIN_URL)) {
//            return logInTemplate(model);
//        } else  {
////            (requestURI.contains(UPDATE_URL) || requestURI.contains(DELETE_URL))
//            return updateTemplate(model);
//        }
//    }
//    private String logInTemplate(final Model model) {
//        model.addAttribute("logInDto", new LogInDto());
//        return LOGIN_VIEW;
//    }
//
//    private String registerTemplate(final Model model) {
//        model.addAttribute("registerDto", new RegisterDto());
//        return REGISTER_VIEW;
//    }
//
//    private String updateTemplate(final Model model) {
//        model.addAttribute("updateDto", new UpdateDto());
//        return UPDATE_VIEW;
//    }
//
//    private String createQuestionTemplate(final Model model) {
//        model.addAttribute("createQuestionDto", new CreateQuestionDto());
//        return QUESTION_CREATE_VIEW;
//    }
//
}
