package com.ra.course.com.stackoverflow.utils;

import com.ra.course.com.stackoverflow.dto.bounty.BountyDto;
import com.ra.course.com.stackoverflow.dto.NotificationDto;
import com.ra.course.com.stackoverflow.dto.member.*;
import com.ra.course.com.stackoverflow.dto.post.CreateQuestionDto;
import com.ra.course.com.stackoverflow.dto.post.QuestionFullDto;
import com.ra.course.com.stackoverflow.dto.TagDto;
import com.ra.course.com.stackoverflow.dto.post.QuestionDto;
import com.ra.course.com.stackoverflow.dto.post.UpdateQuestionDto;
import com.ra.course.com.stackoverflow.entity.enums.AccountRole;
import com.ra.course.com.stackoverflow.entity.enums.AccountStatus;

import java.time.LocalDateTime;
import java.util.List;

import static com.ra.course.com.stackoverflow.utils.Constants.*;

public class DtoCreationUtils {

    public static MemberDto getMemberDto(){
        var member = new MemberDto();
        member.setId(ID);
        member.setName(NAME);
        member.setEmail(EMAIL);
        member.setRole(AccountRole.USER);
        member.setStatus(AccountStatus.ACTIVE);
        return member;
    }

    public static RegisterDto getRegisterDto(){
        return new RegisterDto(NAME, EMAIL, PASSWORD);
    }

    public static QuestionDto getQuestionDto(){
        var question = new QuestionDto();
        question.setId(ID);
        question.setTitle(TITLE);
        question.setText(TEXT);
        question.setUpdateTime(LocalDateTime.MIN);
        question.setAuthor(ID);
        return question;
    }

    public static QuestionFullDto getQuestionFullDto(){
        var question = new QuestionFullDto();
        question.setId(ID);
        question.setTitle(TITLE);
        question.setText(TEXT);
        question.setAuthor(ID);
        return question;
    }

    public static TagDto getTagDto(){
        var tag = new TagDto();
        tag.setName(TITLE);
        return tag;
    }

    public static NotificationDto getNotificationDto(){
        var notification = new NotificationDto();
        notification.setId(ID);
        notification.setCreationTime(LocalDateTime.MIN);
        return notification;
    }

    public static SessionMemberDto getSessionMemberDto(){
        var member = new SessionMemberDto();
        member.setId(ID);
        member.setName(NAME);
        member.setRole(AccountRole.USER);
        return member;
    }

    public static LogInDto getLogInDto(){
        var dto = new LogInDto();
        dto.setEmail(EMAIL);
        dto.setPassword(PASSWORD);
        return dto;
    }

    public static UpdateDto getUpdateDto(){
        var dto = new UpdateDto();
        dto.setName(NAME);
        dto.setPassword(PASSWORD);
        return dto;
    }

    public static UpdateQuestionDto getUpdateQuestionDto(){
        var dto = new UpdateQuestionDto();
        dto.setText(NEW_TEXT);
        dto.setTitle(NEW_TITLE);
        return  dto;
    }

    public static CreateQuestionDto getCreateQuestionDto(){
        var dto = new CreateQuestionDto();
        dto.setText(TEXT);
        dto.setTitle(TITLE);
        dto.setTags(List.of(TITLE));
        return dto;
    }

    public static BountyDto getBountyDto(){
        var bounty = new BountyDto();
        bounty.setCreatorId(ID);
        bounty.setId(ID);
        bounty.setExpiry(LocalDateTime.MAX);
        return bounty;
    }
}
