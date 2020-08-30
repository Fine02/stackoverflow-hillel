package com.ra.course.com.stackoverflow.controller.post;

import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.dto.post.CreateDto;
import com.ra.course.com.stackoverflow.service.post.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@AllArgsConstructor
@Controller
@RequestMapping("/questions/{questionId}/comments")
public class CommentController {

    private final CommentService commentService;

    private final static String REDIRECT_QUESTION = "/view/question/";

    @PostMapping("/create")
    public RedirectView createCommentToQuestion(@SessionAttribute final SessionMemberDto account,
                                                @Valid final CreateDto dto,
                                                @PathVariable final Long questionId){
        commentService.addCommentToQuestion(dto, questionId, account);
        return new RedirectView(REDIRECT_QUESTION + questionId, true);

    }

    @SuppressWarnings("PMD.ExcessiveParameterList")
    @PostMapping("/answers/{answerId}/create")
    public RedirectView createCommentToAnswer(@SessionAttribute final SessionMemberDto account,
                                              @Valid final CreateDto dto, @PathVariable final Long questionId,
                                              @PathVariable final Long answerId){
        commentService.addCommentToAnswer(dto, answerId, account);
        return new RedirectView(REDIRECT_QUESTION + questionId, true);
    }

    @PostMapping("/{id}/delete")
    public RedirectView deleteComment(@SessionAttribute final SessionMemberDto account,
                                      @PathVariable final Long id,
                                      @PathVariable final Long questionId){
        commentService.deleteComment(id, account);
        return new RedirectView(REDIRECT_QUESTION + questionId, true);
    }
}
