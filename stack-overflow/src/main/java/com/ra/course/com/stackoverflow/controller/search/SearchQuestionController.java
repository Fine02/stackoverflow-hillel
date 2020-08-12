package com.ra.course.com.stackoverflow.controller.search;

import com.ra.course.com.stackoverflow.dto.post.QuestionDto;
import com.ra.course.com.stackoverflow.service.search.SearchQuestionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/search/questions")
@AllArgsConstructor
public class SearchQuestionController {

    private final SearchQuestionService searchService;

    @GetMapping(params = "authorId")
    public ModelAndView getQuestionsByAuthorId(@RequestParam final Long authorId){
        return modelAndView(List.of("member " + authorId), searchService.searchByAuthorId(authorId));
    }

    @GetMapping(params = "tag")
    public ModelAndView getQuestionsByTag(@RequestParam final String tag){
        return modelAndView(List.of(tag), searchService.searchByTag(tag));
    }

    @GetMapping(params = "phrase")
    public ModelAndView getQuestionsByPhrase(@RequestParam final String phrase){
        return modelAndView(List.of(phrase), searchService.searchInTitle(phrase));
    }

    @GetMapping(params = {"tag", "phrase"})
    public ModelAndView getQuestionsByTagAndPhrase(@RequestParam final String tag,
                                             @RequestParam final String phrase){
        return modelAndView(List.of(phrase, tag), searchService.searchByTitleAndTagName(phrase, tag));
    }

    private ModelAndView modelAndView(final List<String> search, final List<QuestionDto> questions){
        final var modelAndView = new ModelAndView("search/list-questions");
        modelAndView.addObject("searchBy", search);
        modelAndView.addObject("questions", questions);
        return modelAndView;
    }
}
