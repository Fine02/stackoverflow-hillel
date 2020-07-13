package com.ra.course.com.stackoverflow.controller;

import com.ra.course.com.stackoverflow.service.storage.MemberStorageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import static com.ra.course.com.stackoverflow.controller.ControllerConstants.*;

@Controller
@RequestMapping(SEARCH_URL + MEMBER_URL)
@AllArgsConstructor
public class SearchMemberController {

    private final MemberStorageService memberService;

    private final static String PARAM_NAME = "name";
    private final static String PARAM_ID = "id";

    @GetMapping(params = PARAM_NAME)
    public ModelAndView getMembersByName(@RequestParam(PARAM_NAME) final String name){
        final var modeAndView = new ModelAndView(MEMBERS_VIEW);
        modeAndView.addObject("viewMembers", memberService.findByMemberName(name));
        return modeAndView;
    }

    @GetMapping(params = PARAM_ID)
    public String getMemberById(final Model model, @RequestParam(PARAM_ID) final Long id) {
        model.addAttribute("account", (memberService.findMemberById(id)));
        return MEMBER_VIEW;
    }

}
