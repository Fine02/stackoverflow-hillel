package com.ra.course.com.stackoverflow.controller;

import com.ra.course.com.stackoverflow.dto.RegisterDto;
import com.ra.course.com.stackoverflow.service.storage.MemberStorageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.ra.course.com.stackoverflow.controller.ControllerConstants.*;


@Controller
@RequestMapping(REGISTER_URL)
@AllArgsConstructor
public class RegistrationController {

    private final transient MemberStorageService service;

    @GetMapping
    public String getRegisterNewMember(final Model model) {
        model.addAttribute("registerDto", new RegisterDto());
        return REGISTER_VIEW;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String postRegisterNewMember(@Valid final RegisterDto registerDto, final Model model,
                                        final HttpSession session) {
        final var savedMember = service.registerMember(registerDto);
        model.addAttribute("done", "Registration done! Welcome!");
        session.setAttribute(MEMBER_ATTR, savedMember);
        return PROFILE_VIEW;
    }
}
