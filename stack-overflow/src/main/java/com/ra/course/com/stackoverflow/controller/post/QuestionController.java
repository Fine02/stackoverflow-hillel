package com.ra.course.com.stackoverflow.controller.post;

import com.ra.course.com.stackoverflow.dto.post.CreateDto;
import com.ra.course.com.stackoverflow.dto.post.CreateQuestionDto;
import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.dto.post.QuestionFullDto;
import com.ra.course.com.stackoverflow.dto.post.UpdateQuestionDto;
import com.ra.course.com.stackoverflow.service.post.QuestionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;


@RequestMapping("/questions")
@Controller
@AllArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    private final static String CREATE_URL = "/questions/create";
    private final static String VIEW_QUESTION = "view/question/";


    @GetMapping(CREATE_URL)
    public ModelAndView getCreateQuestion() {
        final var model = new ModelAndView("question/create");
        model.addObject("dto", new CreateQuestionDto());
        return model;
    }

    @PostMapping(CREATE_URL)
    public ModelAndView postCreateQuestion(@SessionAttribute final SessionMemberDto account,
                                     @Valid final CreateQuestionDto createQuestionDto) {
        final var question = questionService.createQuestion(createQuestionDto, account);
        return getModelAndView(question);
    }

    @PostMapping("/{id}/delete")
    public ModelAndView deleteQuestion(@SessionAttribute final SessionMemberDto account,
                                 @PathVariable Long id) {
        final var question = questionService.deleteQuestion(id, account);
        return getModelAndView(question);
    }

    @GetMapping("/update")
    public ModelAndView getUpdateQuestion() {
        var modelAndView = new ModelAndView("question/update");
        modelAndView.addObject("dto", new UpdateQuestionDto());
        return modelAndView;
    }

    @PostMapping("/{id}/update")
    public String updateQuestion(@SessionAttribute final SessionMemberDto account,
                                 @Valid UpdateQuestionDto updateQuestionDto,
                                 @PathVariable Long id) {
        final var question = questionService.updateQuestion(updateQuestionDto, id, account);
        return VIEW_QUESTION + id;
    }

    @PostMapping("/{id}/close")
    public RedirectView closeQuestion(@SessionAttribute final SessionMemberDto account,
                                      @PathVariable Long id){
        questionService.closeQuestion(id, account);
        return new RedirectView("/view/question" + id, true);
    }

    private ModelAndView getModelAndView(final QuestionFullDto question){
        final var model = new ModelAndView("view/question");
        model.addObject("question", question);
        model.addObject("dto", new CreateDto());
        return model;
    }
}
