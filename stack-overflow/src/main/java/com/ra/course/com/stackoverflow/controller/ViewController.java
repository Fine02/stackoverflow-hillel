package com.ra.course.com.stackoverflow.controller;

import com.ra.course.com.stackoverflow.dto.post.CreateDto;
import com.ra.course.com.stackoverflow.service.member.MemberService;
import com.ra.course.com.stackoverflow.service.post.QuestionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@AllArgsConstructor
@Controller
@RequestMapping("/view")
public class ViewController {

    private final QuestionService questionService;
    private final MemberService memberService;


    @GetMapping("/question/{id}")
    public ModelAndView questionView (@PathVariable final Long id){
        final var question = questionService.getQuestionById(id);

        final var model =  new ModelAndView("view/question");
            model.addObject("question", question);
            model.addObject("dto", new CreateDto());
        return model;
    }

    @GetMapping("/member/{id}")
    public ModelAndView memberView(@PathVariable final Long id) {
        final var member = memberService.getMemberById(id);

        final var model =  new ModelAndView("view/member");
            model.addObject("member", member);
        return model;
    }
}
