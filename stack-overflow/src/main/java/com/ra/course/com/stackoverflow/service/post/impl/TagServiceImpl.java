package com.ra.course.com.stackoverflow.service.post.impl;

import com.ra.course.com.stackoverflow.dto.TagDto;
import com.ra.course.com.stackoverflow.dto.mapper.TagMapper;
import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.entity.enums.QuestionStatus;
import com.ra.course.com.stackoverflow.exception.service.TagAlreadyAddedException;
import com.ra.course.com.stackoverflow.exception.service.security.QuestionStatusException;
import com.ra.course.com.stackoverflow.repository.QuestionRepository;
import com.ra.course.com.stackoverflow.repository.TagRepository;
import com.ra.course.com.stackoverflow.service.RepositoryUtils;
import com.ra.course.com.stackoverflow.service.post.TagService;
import com.ra.course.com.stackoverflow.service.system.NotificationService;
import com.ra.course.com.stackoverflow.service.system.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagData;
    private final QuestionRepository questionData;
    private final RepositoryUtils utils;
    private final SecurityService securityService;
    private final NotificationService noteService;

    @Override
    public List<TagDto> getAllTags() {
        return tagData.findAll().stream()
                .map(TagMapper.MAPPER::toTagDto)
                .collect(Collectors.toList());
    }

    /*User can add tag to own open question, moder/admin - to any*/
    @Override
    public void addTagToQuestion(final String tagName, final Long questionId,
                                 final SessionMemberDto sessionMemberDto) {
        securityService.checkStatusAndReturnMember(sessionMemberDto);

        final var question = utils.getQuestionFromDB(questionId);
        if(!question.getStatus().equals(QuestionStatus.OPEN)){
            throw new QuestionStatusException("add tag to question", question.getStatus());
        }
        final var tag = utils.getTagFromDBByTagName(tagName);

        if (question.getTags().contains(tag)){
            throw new TagAlreadyAddedException("Tag " + tagName + " already added to this question.");
        }
        questionData.addTagToQuestion(tag, question);

        noteService.sendNotification(question, "updated with new tag");
    }
}
