package com.ra.course.com.stackoverflow.controller.member;

import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.dto.member.UpdateDto;
import com.ra.course.com.stackoverflow.service.search.SearchMemberService;
import com.ra.course.com.stackoverflow.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private final static String UPDATE = "/update";
    private final static String REDIRECT_LOGOUT = "/members/logout";

    @GetMapping("/profile")
    public ModelAndView getProfile(@SessionAttribute final SessionMemberDto account) {
        final var profile =  memberService.getMemberById(account.getId());
        final var model = new ModelAndView("member/profile");
            model.addObject("profile", profile);
        return model;
    }

    @GetMapping(UPDATE)
    public ModelAndView getUpdateMember() {
        final var model = new ModelAndView("member/update");
            model.addObject("dto", new UpdateDto());
        return model;
    }

    @PostMapping(UPDATE)
    public RedirectView postUpdateMember(@Valid final UpdateDto updateDto,
                                   @SessionAttribute final SessionMemberDto account,
                                   final String currentPassword) {
            memberService.updateMember(updateDto, account, currentPassword);
        return new RedirectView(REDIRECT_LOGOUT, true);
    }

    @PostMapping("/delete")
    public RedirectView postDeleteMember(@SessionAttribute final SessionMemberDto account,
                                         final String currentPassword) {
            memberService.deleteMember(account, currentPassword);
        return new RedirectView(REDIRECT_LOGOUT, true);
    }

    @GetMapping("/logout")
    public RedirectView logOutMember(final HttpSession session, final SessionStatus status) {
            status.setComplete();
            session.removeAttribute("account");
        return new RedirectView("/", true);
    }

}
