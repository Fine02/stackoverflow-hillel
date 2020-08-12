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

    @PostMapping("/{userId}/block")
    public RedirectView blockMember(@SessionAttribute SessionMemberDto account,
                                    @PathVariable Long userId){
        service.blockMember(userId, account);
        return new RedirectView("/view/member/" + userId, true);
    }

    @PostMapping("/{userId}/unblock")
    public RedirectView unblockMember(@SessionAttribute SessionMemberDto account,
                                    @PathVariable Long userId){
        service.unblockMember(userId, account);
        return new RedirectView("/view/member/" + userId, true);
    }

    @PostMapping("/{userId}/moder")
    public RedirectView setModer(@SessionAttribute SessionMemberDto account,
                                    @PathVariable Long userId){
        service.setMemberAsModer(userId, account);
        return new RedirectView("/view/member/" + userId, true);
    }

    @PostMapping("/{userId}/admin")
    public RedirectView setAdmin(@SessionAttribute SessionMemberDto account,
                                    @PathVariable Long userId){
        service.setMemberAsAdmin(userId, account);
        return new RedirectView("/view/member/" + userId, true);
    }

    @PostMapping("/{userId}/user")
    public RedirectView setUser(@SessionAttribute SessionMemberDto account,
                                    @PathVariable Long userId){
        service.setMemberAsUser(userId, account);
        return new RedirectView("/view/member/" + userId, true);
    }

}
