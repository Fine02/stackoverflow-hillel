package com.ra.course.com.stackoverflow.controller;

import com.ra.course.com.stackoverflow.dto.LogInDto;
import com.ra.course.com.stackoverflow.dto.MemberDto;
import com.ra.course.com.stackoverflow.dto.RegisterDto;
import com.ra.course.com.stackoverflow.service.storage.MemberStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
//@SessionAttributes("memberDto")
public class MemberController {

    private final MemberStorageService memberService;

    private final static String MEMBER_DTO_NAME = "memberDto";
    private final static String REGISTER_TEMPLATE = "member/register";
    private final static String LOGIN_TEMPLATE = "member/login";

    static String logInTemplate(final Model model){
        model.addAttribute("logInDto", new LogInDto());
        return LOGIN_TEMPLATE;
    }
    static String registerTemplate(final Model model){
        model.addAttribute("registerDto", new RegisterDto());
        return REGISTER_TEMPLATE;
    }

    @GetMapping("/login")
    public String getLoginMember(final Model model) {
        return logInTemplate(model);
    }

    @PostMapping("/login")
    public String postLoginMember(@Valid @ModelAttribute final LogInDto logInDto, final HttpSession session) {
        final var loginMember = memberService.loginMember(logInDto);
        session.setAttribute(MEMBER_DTO_NAME, loginMember);
        return "member/greeting";
    }

    @GetMapping("/{id}")
    public String viewMemberById(@PathVariable final Long id, final Model model) {
        final var member = memberService.findMemberById(id);
        model.addAttribute("viewMembers", new ArrayList<>(List.of(member)));
        return "member/view-members";
    }

    @GetMapping("/register")
    public String getRegisterNewMember(final Model model) {
        return registerTemplate(model);
    }

    @GetMapping("/exit")
    public String getExitMember(final HttpSession session) {
        session.removeAttribute(MEMBER_DTO_NAME);
        return "main";
    }

    @PostMapping("/register")
    public String postRegisterNewMember(@Valid final RegisterDto registerDto, final HttpSession session) {
        final var savedMember = memberService.saveMemberToDB(registerDto);
        session.setAttribute(MEMBER_DTO_NAME, savedMember);
        return "member/registration-done";
    }

}
