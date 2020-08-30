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

import javax.validation.Valid;


@RequestMapping("/questions")
@Controller
@AllArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    private final static String CREATE_URL = "/create";
    private final static String DTO = "dto";


    @GetMapping(CREATE_URL)
    public ModelAndView getCreateQuestion() {
        final var model = new ModelAndView("question/create");
        model.addObject(DTO, new CreateQuestionDto());
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
                                 @PathVariable final Long id) {
        final var question = questionService.deleteQuestion(id, account);
        return getModelAndView(question);
    }

    @GetMapping("/update")
    public ModelAndView getUpdateQuestion() {
        final var modelAndView = new ModelAndView("question/update");
            modelAndView.addObject(DTO, new UpdateQuestionDto());
        return modelAndView;
    }

    @PostMapping("/{id}/update")
    public ModelAndView updateQuestion(@SessionAttribute final SessionMemberDto account,
                                 @Valid final UpdateQuestionDto updateQuestionDto,
                                 @PathVariable final Long id) {
        final var question = questionService.updateQuestion(updateQuestionDto, id, account);
        return getModelAndView(question);
    }

    @PostMapping("/{id}/close")
    public ModelAndView closeQuestion(@SessionAttribute final SessionMemberDto account,
                                      @PathVariable final Long id){
        final var question = questionService.closeQuestion(id, account);
        return getModelAndView(question);
    }

    private ModelAndView getModelAndView(final QuestionFullDto question){
        final var model = new ModelAndView("view/question");
            model.addObject("question", question);
            model.addObject(DTO, new CreateDto());
        return model;
    }
}
