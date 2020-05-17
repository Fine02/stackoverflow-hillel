package com.ra.course.com.stackoverflow.controller;

import com.ra.course.com.stackoverflow.dto.LogInDto;
import com.ra.course.com.stackoverflow.dto.MemberDto;
import com.ra.course.com.stackoverflow.dto.RegisterDto;
import com.ra.course.com.stackoverflow.service.storage.MemberStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@SessionAttributes("memberDto")
public class MemberController {

    private final MemberStorageService memberStorageService;

    @GetMapping("/login")
    public String getLoginMember( final Model model) {
        model.addAttribute("logInDto", new LogInDto());
        return "member/login";
    }

    @PostMapping("/login")
    public String postLoginMember(final LogInDto logInDto, final Model model){
        final var loginMember = memberStorageService.loginMember(logInDto);
        model.addAttribute("memberDto", loginMember);
        return "member/greeting";
    }

    @GetMapping("/{id}")
    public String viewMemberById(@PathVariable Long id, Model model){
        MemberDto member = memberStorageService.findMemberById(id);
        model.addAttribute("viewMembers", new ArrayList<>(List.of(member)));
        return "member/view-members";
    }

    @GetMapping("/register")
    public String getRegisterNewMember(final Model model){
        model.addAttribute("registerDto", new RegisterDto());
        return "member/registration";
    }
    @GetMapping("/exit")
    public String getExitMember(@ModelAttribute("memberDto") MemberDto memberDto, final Model model){
//        model.ad
        return "index";
    }

    @PostMapping("/register")
    public String postRegisterNewMember(@Valid final RegisterDto registerDto, final Model model){
        final var savedMember = memberStorageService.saveMemberToDB(registerDto);
        model.addAttribute("memberDto", savedMember);
        return "member/registration-done";
    }

    @ModelAttribute("memberDto")
    public MemberDto getMemberDto (final Model model) {
        return (MemberDto) model.getAttribute("memberDto");
    }
}
