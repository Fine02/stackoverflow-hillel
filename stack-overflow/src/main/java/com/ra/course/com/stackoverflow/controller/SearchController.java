package com.ra.course.com.stackoverflow.controller;

import com.ra.course.com.stackoverflow.service.storage.MemberStorageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.ra.course.com.stackoverflow.controller.ControllerConstants.MEMBERS_VIEW;
import static com.ra.course.com.stackoverflow.controller.ControllerConstants.SEARCH_URL;

@Controller
@RequestMapping(SEARCH_URL)
@AllArgsConstructor
public class SearchController {

    private final MemberStorageService memberService;
    private final static String PARAM_NAME = "name";

    @GetMapping(params = PARAM_NAME)
    public String getMembersView(@RequestParam(PARAM_NAME) final String name, final Model model){
        final var memberList = memberService.findByMemberName(name);
        model.addAttribute("size",  memberList.size() + " members with name: " + name);
        model.addAttribute("viewMembers", memberList);
        return MEMBERS_VIEW;
    }
}
