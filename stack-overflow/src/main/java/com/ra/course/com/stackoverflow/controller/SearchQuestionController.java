package com.ra.course.com.stackoverflow.controller;

import com.ra.course.com.stackoverflow.dto.QuestionDto;
import com.ra.course.com.stackoverflow.service.search.SearchService;
import com.ra.course.com.stackoverflow.service.storage.QuestionStorageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.ra.course.com.stackoverflow.controller.ControllerConstants.*;

@Controller
@RequestMapping(SEARCH_URL + QUESTION_URL)
@AllArgsConstructor
public class SearchQuestionController {

    private final QuestionStorageService questionService;
    private final SearchService searchService;

    private final static String PARAM_ID = "id";
    private final static String PARAM_AUTHOR_ID = "authorId";
    private final static String PARAM_TAG = "tag";
    private final static String PARAM_PHRASE = "phrase";


    @GetMapping(params = PARAM_ID)
    public String getViewQuestion(final Model model, @RequestParam(PARAM_ID) Long id){
        final var question = questionService.getById(id);
        model.addAttribute("question", question);
        return QUESTION_VIEW;
    }

    @GetMapping(params = PARAM_AUTHOR_ID)
    public ModelAndView getQuestionsByAuthorId(@RequestParam(PARAM_AUTHOR_ID) final Long authorId){
        return modelAndView(List.of("member " + authorId), questionService.getByAuthorId(authorId));
    }

    @GetMapping(params = PARAM_TAG)
    public ModelAndView getQuestionsByTag(@RequestParam(PARAM_TAG) final String tag){
        return modelAndView(List.of(tag), searchService.searchByTag(tag));
    }

    @GetMapping(params = PARAM_PHRASE)
    public ModelAndView getQuestionsByPhrase(@RequestParam(PARAM_PHRASE) final String phrase){
        return modelAndView(List.of(phrase), searchService.searchInTitle(phrase));
    }

    @GetMapping(params = {PARAM_TAG, PARAM_PHRASE})
    public ModelAndView getQuestionsByTagAndPhrase(@RequestParam(PARAM_TAG) final String tag,
                                             @RequestParam(PARAM_PHRASE) final String phrase){
        return modelAndView(List.of(phrase, tag), searchService.searchInTitleByTag(phrase, tag));
    }

    private ModelAndView modelAndView(final List<String> search, final List<QuestionDto> questions){
        final var modelAndView = new ModelAndView(QUESTIONS_VIEW);
        modelAndView.addObject("searchlist", search);
        modelAndView.addObject(QUESTIONS_ATTR, questions);
        return modelAndView;
    }
}
