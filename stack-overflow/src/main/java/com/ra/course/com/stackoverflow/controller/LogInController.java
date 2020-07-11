package com.ra.course.com.stackoverflow.controller;

import com.ra.course.com.stackoverflow.dto.LogInDto;
import com.ra.course.com.stackoverflow.dto.mapper.SessionMemberMapper;
import com.ra.course.com.stackoverflow.service.storage.MemberStorageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.ra.course.com.stackoverflow.controller.ControllerConstants.*;

@Controller
@RequestMapping(LOGIN_URL)
@AllArgsConstructor
public class LogInController {

    private final transient MemberStorageService service;
    private final transient SessionMemberMapper mapper;

    private final static String REDIRECT_MEMBER = "redirect:/members";

    @GetMapping
    public String getLoginMember(final Model model) {
        model.addAttribute("logInDto", new LogInDto());
        return LOGIN_VIEW;
    }

    @PostMapping
    public String postLoginMember(@Valid final LogInDto logInDto,
                                  final HttpSession session) {
        final var loginMember = service.loginMember(logInDto);
        session.setAttribute(MEMBER_ATTR, mapper.getSessionMember(loginMember));
        return REDIRECT_MEMBER;
    }
}
