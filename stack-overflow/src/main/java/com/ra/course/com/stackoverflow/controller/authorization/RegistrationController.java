package com.ra.course.com.stackoverflow.controller.authorization;

import com.ra.course.com.stackoverflow.dto.member.RegisterDto;
import com.ra.course.com.stackoverflow.service.member.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
@AllArgsConstructor
public class RegistrationController {

    private final transient MemberService service;

    @GetMapping
    public ModelAndView getRegisterNewMember() {
        final var model = new ModelAndView("authorization/registration");
            model.addObject("dto", new RegisterDto());
        return model;
    }

    @PostMapping
    public RedirectView postRegisterNewMember(@Valid final RegisterDto dto,
                                              final HttpSession session) {
        final var savedMember = service.registerMember(dto);
            session.setAttribute("account", savedMember);

        return new RedirectView("/members/profile", true);
    }
}
