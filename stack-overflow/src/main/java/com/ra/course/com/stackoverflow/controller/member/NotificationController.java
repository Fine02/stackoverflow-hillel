package com.ra.course.com.stackoverflow.controller.member;

import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.service.system.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/members/notifications")
@AllArgsConstructor
public class NotificationController {

    private final NotificationService noteService;

    @GetMapping
    public ModelAndView viewAllNotifications(@SessionAttribute final SessionMemberDto account){
        final var model = new ModelAndView("member/notifications");
        model.addObject("notifications", noteService.getAllNotificationsByMember(account));
        return model;
    }

    @GetMapping("/{id}")
    public RedirectView getQuestionByNotification(@PathVariable final Long id){
        final var questionId = noteService.readNotificationAndGetViewedQuestionId(id);
        return new RedirectView("/view/question/" + questionId);
    }
}
