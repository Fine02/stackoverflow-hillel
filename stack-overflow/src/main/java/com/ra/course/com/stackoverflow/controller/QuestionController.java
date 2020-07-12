package com.ra.course.com.stackoverflow.controller;

import com.ra.course.com.stackoverflow.dto.CreateQuestionDto;
import com.ra.course.com.stackoverflow.dto.SessionMemberDto;
import com.ra.course.com.stackoverflow.service.member.MemberService;
import com.ra.course.com.stackoverflow.service.storage.QuestionStorageService;
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
    private transient final QuestionStorageService questionStorageService;
    private final static String PARAM_ID = "id";

    @GetMapping(CREATE_URL)
    public String getCreateQuestion(final Model model){
        model.addAttribute("createQuestionDto", new CreateQuestionDto());
        return QUESTION_CREATE_VIEW;
    }

    @PostMapping(CREATE_URL)
    public String postCreateQuestion(@SessionAttribute(MEMBER_ATTR) final SessionMemberDto member,
                                    @Valid final CreateQuestionDto createQuestionDto,
                                    final Model model){
        final var questionDto = memberService.createQuestion(createQuestionDto, member);
        model.addAttribute("question", questionDto);
        return QUESTION_VIEW;
    }



}
