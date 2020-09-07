package com.ra.course.com.stackoverflow.controller.post;

import com.ra.course.com.stackoverflow.dto.post.CreateDto;
import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.service.post.AnswerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@AllArgsConstructor
@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {

    private final AnswerService answerService;

    private final static String REDIRECT_QUESTION = "/view/question/";

    @PostMapping("/create")
    public RedirectView getCreateAnswer(@SessionAttribute final SessionMemberDto account,
                                        @Valid final CreateDto answerDto,
                                        @PathVariable final Long questionId){
        answerService.addAnswer(answerDto, questionId, account);
        return new RedirectView(REDIRECT_QUESTION + questionId, true);
    }

    @PostMapping("/{id}/delete")
    public RedirectView deleteAnswer(@SessionAttribute final SessionMemberDto  account,
                                     @PathVariable final Long id,
                                     @PathVariable final Long questionId){
        answerService.deleteAnswer(id, account);
        return new RedirectView(REDIRECT_QUESTION + questionId, true);
    }

    @PostMapping("/{id}/accept")
    public RedirectView acceptAnswer(@SessionAttribute final SessionMemberDto account,
                                     @PathVariable final Long id,
                                     @PathVariable final Long questionId){
        answerService.acceptAnswer(id, account);
        return new RedirectView(REDIRECT_QUESTION + questionId, true);
    }

}
