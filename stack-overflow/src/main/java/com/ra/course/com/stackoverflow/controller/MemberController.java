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

import java.util.ArrayList;
import java.util.List;

import static com.ra.course.com.stackoverflow.controller.ControllerConstants.*;


@Controller
@RequestMapping(MEMBER_URL)
@RequiredArgsConstructor
public class MemberController {

    private final MemberStorageService memberService;

    private final static String REDIRECT_LOGOUT = "redirect:/member/logout";

    @GetMapping
    public String getProfile(final Model model,
                @SessionAttribute(MEMBER_ATTR) final MemberDto sessionMember) {
        model.addAttribute("account", memberService.findMemberById(sessionMember.getId()));
        return PROFILE_VIEW;
    }

    @GetMapping(UPDATE_URL)
    public String getUpdateMember(final Model model) {
        model.addAttribute("updateDto", new UpdateDto());
        return UPDATE_VIEW;
    }

    @PostMapping(UPDATE_URL)
    public String postUpdateMember(@Valid final UpdateDto updateDto,
                                   @SessionAttribute(MEMBER_ATTR) final MemberDto sessionMember,
                                   final String currentPassword) {
        updateDto.setId(sessionMember.getId());
        memberService.updateMember(updateDto, currentPassword);
        return REDIRECT_LOGOUT;
    }

    @PostMapping(DELETE_URL)
    public String postDeleteMember(@SessionAttribute(MEMBER_ATTR) final MemberDto sessionMember,
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

}
