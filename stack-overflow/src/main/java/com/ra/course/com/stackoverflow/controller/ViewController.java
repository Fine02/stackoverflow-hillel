package com.ra.course.com.stackoverflow.controller;

import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.dto.post.CreateDto;
import com.ra.course.com.stackoverflow.entity.enums.AccountRole;
import com.ra.course.com.stackoverflow.service.member.MemberService;
import com.ra.course.com.stackoverflow.service.post.QuestionService;
import com.ra.course.com.stackoverflow.service.search.SearchMemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@AllArgsConstructor
@Controller
@RequestMapping("/view")
public class ViewController {

    private final QuestionService questionService;
    private final MemberService memberService;


    @GetMapping("/question/{id}")
    public ModelAndView questionView (@PathVariable final Long id, HttpSession session){
        final var question = questionService.getQuestionById(id);
//        session.setAttribute("account", account());

        var model =  new ModelAndView("view/question");
            model.addObject("question", question);
            model.addObject("dto", new CreateDto());
        return model;
    }

    @GetMapping("/member/{id}")
    public ModelAndView memberView(@PathVariable final Long id,  HttpSession session) {
        final var member = memberService.getMemberById(id);
//        session.setAttribute("account", account());

        var model =  new ModelAndView("view/member");
            model.addObject("member", member);
        return model;
    }
//
//    SessionMemberDto account(){
//        var account = new SessionMemberDto();
//        account.setRole(AccountRole.ADMIN);
//        account.setId(1L);
//        account.setName("name");
//        return account;
//    }
}
