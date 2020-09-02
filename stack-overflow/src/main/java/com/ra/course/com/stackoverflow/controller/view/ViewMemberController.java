package com.ra.course.com.stackoverflow.controller.view;

import com.ra.course.com.stackoverflow.service.member.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@AllArgsConstructor
@Controller
@RequestMapping("/view/member")
public class ViewMemberController {

    private final MemberService memberService;

    @GetMapping("/{id}")
    public ModelAndView memberView(@PathVariable final Long id) {
        final var member = memberService.getMemberById(id);

        final var model =  new ModelAndView("view/member");
            model.addObject("member", member);
        return model;
    }
}
