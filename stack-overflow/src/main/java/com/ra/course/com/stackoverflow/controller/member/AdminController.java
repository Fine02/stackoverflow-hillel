package com.ra.course.com.stackoverflow.controller.member;

import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.service.member.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@AllArgsConstructor
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService service;
    private static final String VIEW_MEMBER = "/view/member/";
    
    @PostMapping("/{userId}/block")
    public RedirectView blockMember(@SessionAttribute final  SessionMemberDto account,
                                    @PathVariable final  Long userId){
        service.blockMember(userId, account);
        return new RedirectView(VIEW_MEMBER + userId, true);
    }

    @PostMapping("/{userId}/unblock")
    public RedirectView unblockMember(@SessionAttribute final SessionMemberDto account,
                                    @PathVariable final Long userId){
        service.unblockMember(userId, account);
        return new RedirectView(VIEW_MEMBER + userId, true);
    }

    @PostMapping("/{userId}/moder")
    public RedirectView changeAsModer(@SessionAttribute final SessionMemberDto account,
                                      @PathVariable final Long userId){
        service.setMemberAsModer(userId, account);
        return new RedirectView(VIEW_MEMBER + userId, true);
    }

    @PostMapping("/{userId}/admin")
    public RedirectView changeAsAdmin(@SessionAttribute final SessionMemberDto account,
                                      @PathVariable final Long userId){
        service.setMemberAsAdmin(userId, account);
        return new RedirectView(VIEW_MEMBER + userId, true);
    }

    @PostMapping("/{userId}/user")
    public RedirectView changeAsUser(@SessionAttribute final SessionMemberDto account,
                                     @PathVariable final Long userId){
        service.setMemberAsUser(userId, account);
        return new RedirectView(VIEW_MEMBER + userId, true);
    }

}
