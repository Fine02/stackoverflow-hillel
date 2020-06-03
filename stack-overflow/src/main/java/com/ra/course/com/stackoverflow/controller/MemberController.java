package com.ra.course.com.stackoverflow.controller;

import com.ra.course.com.stackoverflow.dto.MemberDto;
import com.ra.course.com.stackoverflow.dto.UpdateDto;
import com.ra.course.com.stackoverflow.service.storage.MemberStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.ra.course.com.stackoverflow.controller.ControllerConstants.*;


@Controller
@RequestMapping(MEMBER_URL)
@RequiredArgsConstructor
@SessionAttributes(MEMBER_ATTR)
@SuppressWarnings("PMD.UnusedPrivateMethod")
public class MemberController {

    private final MemberStorageService memberService;

    private final static String REDIRECT_LOGOUT = "redirect:/member/logout";

    @GetMapping
    public String getProfile() {
        return PROFILE_VIEW;
    }

    @GetMapping(UPDATE_URL)
    public String getUpdateMember(final Model model) {
        model.addAttribute("updateDto", new UpdateDto());
        return UPDATE_VIEW;
    }

    @PostMapping(UPDATE_URL)
    public String postUpdateMember(@Valid final UpdateDto updateDto,
                                   @ModelAttribute final MemberDto memberDto,
                                   final String currentPassword) {
        updateDto.setId(memberDto.getId());
        memberService.updateMember(updateDto, currentPassword);
        return REDIRECT_LOGOUT;
    }

    @PostMapping(DELETE_URL)
    public String postDeleteMember(@ModelAttribute final MemberDto sessionMember,
                                   final String currentPassword) {
        memberService.deleteMember(sessionMember.getId(), currentPassword);
        return REDIRECT_LOGOUT;
    }

    @GetMapping(LOGOUT_URL)
    public String logOutMember(final HttpSession session, final SessionStatus status) {
        status.setComplete();
        session.removeAttribute(MEMBER_ATTR);
        return "redirect:/";
    }

    @ModelAttribute
    private MemberDto sessionMember(final HttpSession session) {
        return (MemberDto) session.getAttribute(MEMBER_ATTR);
    }

}
