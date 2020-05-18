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
@SessionAttributes("memberDto")
public class MemberController {

    private final MemberStorageService memberService;

    private final static String MEMBER_DTO_NAME = "memberDto";

    @GetMapping("/login")
    public String getLoginMember(final Model model) {
        model.addAttribute("logInDto", new LogInDto());
        return "member/login";
    }

    @PostMapping("/login")
    public String postLoginMember(@Valid @ModelAttribute final LogInDto logInDto, final Model model,
                                  final HttpSession session) {
        final var loginMember = memberService.loginMember(logInDto);
        setAttributes(model, session, loginMember);
        return "member/greeting";
    }

    @GetMapping("/{id:d+}")
    public String viewMemberById(@PathVariable final Long id, final Model model) {
        final var member = memberService.findMemberById(id);
        model.addAttribute("viewMembers", new ArrayList<>(List.of(member)));
        return "member/view-members";
    }

    @GetMapping("/register")
    public String getRegisterNewMember(final Model model) {
        model.addAttribute("registerDto", new RegisterDto());
        return "member/register";
    }

    @GetMapping("/exit")
    public String getExitMember(final HttpSession session) {
        session.removeAttribute(MEMBER_DTO_NAME);
        return "main";
    }

    @PostMapping("/register")
    public String postRegisterNewMember(@Valid final RegisterDto registerDto, final Model model,
                                        final HttpSession session) {
        final var savedMember = memberService.saveMemberToDB(registerDto);
        setAttributes(model, session, savedMember);
        return "member/registration-done";
    }

    @ModelAttribute("memberDto")
    public MemberDto getMemberDto(final HttpSession session) {
        return (MemberDto) session.getAttribute(MEMBER_DTO_NAME);
    }

    private void setAttributes(final Model model, final HttpSession session, final Object object){
        model.addAttribute(MEMBER_DTO_NAME, object);
        session.setAttribute(MEMBER_DTO_NAME, object);
    }
}
