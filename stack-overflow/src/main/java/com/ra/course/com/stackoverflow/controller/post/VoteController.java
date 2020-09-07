package com.ra.course.com.stackoverflow.controller.post;

import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.entity.Answer;
import com.ra.course.com.stackoverflow.entity.Comment;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.service.vote.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.view.RedirectView;

@AllArgsConstructor
@Controller
@RequestMapping("/vote")
public class VoteController {

    private final VoteService<Question> questionVote;
    private final VoteService<Answer> answerVote;
    private final VoteService<Comment> commentVote;

    private final static String REDIRECT_QUESTION = "/view/question/";

    @GetMapping("/up/question/{questionId}")
    public RedirectView upVoteQuestion(@SessionAttribute final SessionMemberDto account,
                                       @PathVariable final Long questionId) {
        questionVote.upVote(questionId, account);
        return new RedirectView(REDIRECT_QUESTION + questionId, true);
    }

    @GetMapping("/down/question/{questionId}")
    public RedirectView downVoteQuestion(@SessionAttribute final SessionMemberDto account,
                                         @PathVariable final Long questionId) {
        questionVote.downVote(questionId, account);
        return new RedirectView(REDIRECT_QUESTION + questionId, true);
    }

    @GetMapping("/up/question/{questionId}/answer/{answerId}")
    public RedirectView upVoteAnswer(@SessionAttribute final SessionMemberDto account,
                                     @PathVariable final Long questionId,
                                     @PathVariable final Long answerId) {
        answerVote.upVote(answerId, account);
        return new RedirectView(REDIRECT_QUESTION + questionId, true);
    }

    @GetMapping("/down/question/{questionId}/answer/{answerId}")
    public RedirectView downVoteAnswer(@SessionAttribute final SessionMemberDto account,
                                       @PathVariable final Long questionId,
                                       @PathVariable final Long answerId) {
        answerVote.downVote(answerId, account);
        return new RedirectView(REDIRECT_QUESTION + questionId, true);
    }

    @GetMapping("/up/question/{questionId}/comment/{commentId}")
    public RedirectView upVoteComment(@SessionAttribute final SessionMemberDto account,
                                      @PathVariable final Long questionId,
                                      @PathVariable final Long commentId) {
        commentVote.upVote(commentId, account);
        return new RedirectView(REDIRECT_QUESTION + questionId, true);
    }

    @GetMapping("/down/question/{questionId}/comment/{commentId}")
    public RedirectView downVoteComment(@SessionAttribute final SessionMemberDto account,
                                        @PathVariable final Long questionId,
                                        @PathVariable final Long commentId) {
        commentVote.downVote(commentId, account);
        return new RedirectView(REDIRECT_QUESTION + questionId, true);
    }

}
