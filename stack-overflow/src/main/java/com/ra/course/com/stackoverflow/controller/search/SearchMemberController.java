package com.ra.course.com.stackoverflow.controller.search;

import com.ra.course.com.stackoverflow.service.search.SearchMemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/search/members")
@AllArgsConstructor
public class SearchMemberController {

    private final SearchMemberService searchService;

    @GetMapping(params = "name")
    public ModelAndView getMembersByName(@RequestParam final String name){
        final var modeAndView = new ModelAndView("search/list-members");
        modeAndView.addObject("members", searchService.findByMemberName(name));
        return modeAndView;
    }
}
