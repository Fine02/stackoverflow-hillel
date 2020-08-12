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

    private final static String ACCOUNT = "account";
    private final static String REDIRECT_QUESTION = "/view/question/";

    @GetMapping("/up/question/{questionId}")
    public RedirectView upVoteQuestion(@SessionAttribute(ACCOUNT)SessionMemberDto sessionMemberDto,
                                       @PathVariable Long questionId){
        questionVote.upVote(questionId, sessionMemberDto);
        return new RedirectView(REDIRECT_QUESTION + questionId, true);
    }

    @GetMapping("/down/question/{questionId}")
    public RedirectView downVoteQuestion(@SessionAttribute(ACCOUNT)SessionMemberDto sessionMemberDto,
                                       @PathVariable Long questionId){
        questionVote.downVote(questionId, sessionMemberDto);
        return new RedirectView(REDIRECT_QUESTION + questionId, true);
    }

    @GetMapping("/up/question/{questionId}/answer/{answerId}")
    public RedirectView upVoteAnswer(@SessionAttribute(ACCOUNT)SessionMemberDto sessionMemberDto,
                                       @PathVariable Long questionId, @PathVariable Long answerId ){
        answerVote.upVote(answerId, sessionMemberDto);
        return new RedirectView(REDIRECT_QUESTION + questionId, true);
    }

    @GetMapping("/down/question/{questionId}/answer/{answerId}")
    public RedirectView downVoteAnswer(@SessionAttribute(ACCOUNT)SessionMemberDto sessionMemberDto,
                                       @PathVariable Long questionId, @PathVariable Long answerId ){
        answerVote.downVote(answerId, sessionMemberDto);
        return new RedirectView(REDIRECT_QUESTION + questionId, true);
    }

    @GetMapping("/up/question/{questionId}/comment/{commentId}")
    public RedirectView upVoteComment(@SessionAttribute(ACCOUNT)SessionMemberDto sessionMemberDto,
                                       @PathVariable Long questionId, @PathVariable Long commentId ){
        commentVote.upVote(commentId, sessionMemberDto);
        return new RedirectView(REDIRECT_QUESTION + questionId, true);
    }

    @GetMapping("/down/question/{questionId}/comment/{commentId}")
    public RedirectView downVoteComment(@SessionAttribute(ACCOUNT)SessionMemberDto sessionMemberDto,
                                       @PathVariable Long questionId, @PathVariable Long commentId ){
        commentVote.downVote(commentId, sessionMemberDto);
        return new RedirectView(REDIRECT_QUESTION + questionId, true);
    }

}
