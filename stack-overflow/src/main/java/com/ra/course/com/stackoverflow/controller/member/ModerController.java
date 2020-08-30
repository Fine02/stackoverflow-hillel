package com.ra.course.com.stackoverflow.controller.member;

import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.dto.post.CreateDto;
import com.ra.course.com.stackoverflow.dto.post.QuestionFullDto;
import com.ra.course.com.stackoverflow.service.member.ModerateService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@AllArgsConstructor
@Controller
@RequestMapping("/moder")
public class ModerController {

    private final ModerateService service;

    @PostMapping("/question/{questionId}/close")
    public ModelAndView closeQuestion(@SessionAttribute final SessionMemberDto account,
                                      @PathVariable final Long questionId){
        final var question = service.closeQuestion(questionId, account);
        return getModelAndView(question);
    }

    @PostMapping("/question/{questionId}/reopen")
    public ModelAndView reopenQuestion(@SessionAttribute final SessionMemberDto account,
                                      @PathVariable final Long questionId){
        final var question = service.reopenQuestion(questionId, account);
        return getModelAndView(question);
    }

    @PostMapping("/question/{questionId}/undelete")
    public ModelAndView undeleteQuestion(@SessionAttribute final SessionMemberDto account,
                                      @PathVariable final Long questionId){
        final var question = service.undeleteQuestion(questionId, account);
        return getModelAndView(question);
    }

    private ModelAndView getModelAndView(final QuestionFullDto question){
        final var model = new ModelAndView("view/question");
        model.addObject("question", question);
        model.addObject("dto", new CreateDto());
        return model;
    }
}
