package com.ra.course.com.stackoverflow.controller;

import com.ra.course.com.stackoverflow.service.storage.MemberStorageService;
import com.ra.course.com.stackoverflow.service.storage.QuestionStorageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

import static com.ra.course.com.stackoverflow.controller.ControllerConstants.*;

@Controller
@RequestMapping(SEARCH_URL)
@AllArgsConstructor
public class SearchController {

    private final MemberStorageService memberService;
    private final QuestionStorageService questionService;

    private final static String PARAM_NAME = "name";
    private final static String PARAM_ID = "id";
    private final static String PARAM_AUTHOR_ID = "authorId";

    @GetMapping(params = PARAM_NAME, path = MEMBER_URL)
    public String getMembersByName(@RequestParam(PARAM_NAME) final String name, final Model model){
        final var memberList = memberService.findByMemberName(name);
        model.addAttribute("size",  memberList.size() + " members with name: " + name);
        model.addAttribute("viewMembers", memberList);
        return MEMBERS_VIEW;
    }

    @GetMapping(params = PARAM_ID, path =  MEMBER_URL)
    public String getMemberById(final Model model, @RequestParam(PARAM_ID) final Long id) {
        model.addAttribute("account", (memberService.findMemberById(id)));
        return PROFILE_VIEW;
    }



    @GetMapping(params = PARAM_ID, path = QUESTION_URL)
    public String getViewQuestion(final Model model, @RequestParam(PARAM_ID) Long id){
        final var question = questionService.getById(id);
        model.addAttribute("question", question);
        return QUESTION_VIEW;
    }

    @GetMapping(params = PARAM_AUTHOR_ID, path = QUESTION_URL)
    public String getQuestionsByMemberId(@RequestParam(PARAM_AUTHOR_ID) final Long authorId,
                                         final Model model){
        model.addAttribute("questions", questionService.getByAuthorId(authorId));
        return QUESTIONS_VIEW;
    }
}
