package com.ra.course.com.stackoverflow.controller.authorization;

import com.ra.course.com.stackoverflow.dto.member.LogInDto;
import com.ra.course.com.stackoverflow.service.member.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/login")
@AllArgsConstructor
public class LogInController {

    private final MemberService service;

    @GetMapping
    public ModelAndView getLoginMember() {
        final var model = new ModelAndView("authorization/login");
            model.addObject("dto", new LogInDto());
        return model;
    }

    @PostMapping
    public RedirectView postLoginMember(@Valid final LogInDto dto,
                                        final HttpSession session) {
        final var loginMember = service.loginMember(dto);
            session.setAttribute("account", loginMember);

        return new RedirectView("/members/profile", true);
    }
}
