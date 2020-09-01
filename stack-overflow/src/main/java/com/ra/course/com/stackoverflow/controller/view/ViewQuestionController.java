package com.ra.course.com.stackoverflow.controller.view;

import com.ra.course.com.stackoverflow.dto.post.CreateDto;
import com.ra.course.com.stackoverflow.service.post.QuestionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@AllArgsConstructor
@Controller
@RequestMapping("/view/question")
public class ViewQuestionController {

    private final QuestionService questionService;

    @GetMapping("/{id}")
    public ModelAndView questionView (@PathVariable final Long id){
        final var question = questionService.getQuestionById(id);

        final var model =  new ModelAndView("view/question");
        model.addObject("question", question);
        model.addObject("dto", new CreateDto());
        return model;
    }
}
