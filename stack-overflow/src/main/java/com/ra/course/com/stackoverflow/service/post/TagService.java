package com.ra.course.com.stackoverflow.service.post;

import com.ra.course.com.stackoverflow.dto.TagDto;
import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;

import java.util.List;

public interface TagService {

    List<TagDto> getAllTags();

    void addTagToQuestion(String tagName, Long questionId, SessionMemberDto sessionMemberDto);

}
