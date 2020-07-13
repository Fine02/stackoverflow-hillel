package com.ra.course.com.stackoverflow.controller;

import com.ra.course.com.stackoverflow.dto.CreateQuestionDto;
import com.ra.course.com.stackoverflow.dto.SessionMemberDto;
import com.ra.course.com.stackoverflow.service.member.MemberService;
import com.ra.course.com.stackoverflow.service.question.QuestionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.ra.course.com.stackoverflow.controller.ControllerConstants.*;

@RequestMapping(QUESTION_URL)
@Controller
@AllArgsConstructor
public class QuestionController {

    private transient final MemberService memberService;
    private transient final QuestionService questionService;
    private final static String REDIRECT_QUESTION = "redirect:/search/questions?id=";

    @GetMapping(CREATE_URL)
    public String getCreateQuestion(final Model model){
        model.addAttribute("createQuestionDto", new CreateQuestionDto());
        return QUESTION_CREATE_VIEW;
    }

    @PostMapping(CREATE_URL)
    public String postCreateQuestion(@SessionAttribute(MEMBER_ATTR) final SessionMemberDto member,
                                    @Valid final CreateQuestionDto createQuestionDto){
        final var questionDto = memberService.createQuestion(createQuestionDto, member);
        createQuestionDto.getTags().forEach(tagName -> questionService.addTagToQuestion(tagName, questionDto));
        return REDIRECT_QUESTION + questionDto.getId();
    }



}
