package com.ra.course.com.stackoverflow.controller;

import com.ra.course.com.stackoverflow.dto.LogInDto;
import com.ra.course.com.stackoverflow.dto.MemberDto;
import com.ra.course.com.stackoverflow.dto.RegisterDto;
import com.ra.course.com.stackoverflow.dto.UpdateDto;
import com.ra.course.com.stackoverflow.service.storage.MemberStorageService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.ra.course.com.stackoverflow.controller.ControllerConstants.*;


@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@SessionAttributes("memberDto")
@SuppressWarnings("PMD.UnusedFormalParameter")
public class MemberController {

    private final MemberStorageService memberService;

    @GetMapping(LOGIN_URL)
    public String getLoginMember(final Model model) {
        return logInTemplate(model);
    }

    @PostMapping(LOGIN_URL)
    public String postLoginMember(@Valid final LogInDto logInDto, final HttpSession session) {
        final var loginMember = memberService.loginMember(logInDto);
        session.setAttribute(MEMBER_ATTR, loginMember);
        return PROFILE_TEMPLATE;
    }

    @GetMapping(PROFILE_URL)
    public String getProfile(@NonNull @ModelAttribute final MemberDto sessionMember) {
        return PROFILE_TEMPLATE;
    }

    @GetMapping(REGISTER_URL)
    public String getRegisterNewMember(final Model model) {
        return registerTemplate(model);
    }

    @PostMapping(REGISTER_URL)
    public String postRegisterNewMember(@Valid final RegisterDto registerDto, final Model model,
                                        final HttpSession session) {
        final var savedMember = memberService.registerMember(registerDto);
        model.addAttribute("done", "Registration done! Welcome!");
        session.setAttribute(MEMBER_ATTR, savedMember);
        return PROFILE_TEMPLATE;
    }

    @GetMapping(UPDATE_URL)
    public String getUpdateMember(@NonNull @ModelAttribute final MemberDto sessionMember,
                                  final Model model) {
        return updateTemplate(model);
    }

    @PostMapping(UPDATE_URL)
    public String postUpdateMember(@Valid final UpdateDto updateDto,
                                   final String currentPassword,
                                   final HttpSession session) {
        updateDto.setId(sessionMember(session).getId());
        final var updatedMember = memberService.updateMember(updateDto, currentPassword);
        session.setAttribute(MEMBER_ATTR, updatedMember);
        return PROFILE_TEMPLATE;
    }

    @GetMapping(DELETE_URL)
    public String getDeleteMember(@NonNull @ModelAttribute final MemberDto sessionMember) {
        return UPDATE_TEMPLATE;
    }

    @PostMapping(DELETE_URL)
    public String postDeleteMember(@ModelAttribute final MemberDto sessionMember, final String currentPassword) {
        memberService.deleteMember(sessionMember.getId(), currentPassword);
        return "redirect:/member" + EXIT_URL;
    }

    @GetMapping(SEARCH_URL)
    public String getSearchMember() {
        return SEARCH_TEMPLATE;
    }

    @PostMapping(SEARCH_URL)
    public String postSearchMember(final String name, final Model model) {
        final var memberList = memberService.findByMemberName(name);
        model.addAttribute("size", "There are " + memberList.size() + " member with name: " + name);
        model.addAttribute("viewMembers", memberList);
        return VIEW_TEMPLATE;
    }

    @GetMapping(EXIT_URL)
    public String getExitMember(final HttpSession session, final SessionStatus status) {
        status.setComplete();
        session.removeAttribute(MEMBER_ATTR);
        return "redirect:/";
    }

    @ModelAttribute
    private MemberDto sessionMember(final HttpSession session) {
        return (MemberDto) session.getAttribute(MEMBER_ATTR);
    }

    private String logInTemplate(final Model model) {
        model.addAttribute("logInDto", new LogInDto());
        return LOGIN_TEMPLATE;
    }

    private String registerTemplate(final Model model) {
        model.addAttribute("registerDto", new RegisterDto());
        return REGISTER_TEMPLATE;
    }

    private String updateTemplate(final Model model) {
        model.addAttribute("updateDto", new UpdateDto());
        return UPDATE_TEMPLATE;
    }

}
