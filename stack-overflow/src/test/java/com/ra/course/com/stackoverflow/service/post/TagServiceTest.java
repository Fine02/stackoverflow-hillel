package com.ra.course.com.stackoverflow.service.post;

import com.ra.course.com.stackoverflow.dto.TagDto;
import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.entity.Tag;
import com.ra.course.com.stackoverflow.entity.enums.QuestionStatus;
import com.ra.course.com.stackoverflow.exception.service.TagAlreadyAddedException;
import com.ra.course.com.stackoverflow.exception.service.security.QuestionStatusException;
import com.ra.course.com.stackoverflow.repository.QuestionRepository;
import com.ra.course.com.stackoverflow.repository.TagRepository;
import com.ra.course.com.stackoverflow.service.RepositoryUtils;
import com.ra.course.com.stackoverflow.service.post.TagService;
import com.ra.course.com.stackoverflow.service.post.impl.TagServiceImpl;
import com.ra.course.com.stackoverflow.service.system.NotificationService;
import com.ra.course.com.stackoverflow.service.system.SecurityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.ra.course.com.stackoverflow.utils.Constants.ID;
import static com.ra.course.com.stackoverflow.utils.Constants.TITLE;
import static com.ra.course.com.stackoverflow.utils.DtoCreationUtils.getSessionMemberDto;
import static com.ra.course.com.stackoverflow.utils.DtoCreationUtils.getTagDto;
import static com.ra.course.com.stackoverflow.utils.EntityCreationUtil.getQuestion;
import static com.ra.course.com.stackoverflow.utils.EntityCreationUtil.getTag;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TagServiceTest {

    private TagService service;

    private final TagRepository tagData = mock(TagRepository.class);
    private final QuestionRepository questionData = mock(QuestionRepository.class);
    private final RepositoryUtils utils = mock(RepositoryUtils.class);
    private final SecurityService securityService = mock(SecurityService.class);
    private final NotificationService noteService = mock(NotificationService.class);

    private TagDto tagDto;
    private Tag tag;
    private Question question;
    private SessionMemberDto sessionMemberDto;

    @BeforeEach
    void setUp() {
        question = getQuestion();
        tag = getTag();
        tagDto = getTagDto();
        sessionMemberDto = getSessionMemberDto();

        when(utils.getQuestionFromDB(ID)).thenReturn(question);
        when(utils.getTagFromDBByTagName(TITLE)).thenReturn(tag);

        service = new TagServiceImpl(tagData, questionData, utils, securityService, noteService);
    }

    @Test
    void whenGetAllTagsThenReturnListWithTags() {
        //given
        when(tagData.findAll()).thenReturn(List.of(tag, tag));
        //when
        var result = service.getAllTags();
        //then
        assertEquals(List.of(tagDto, tagDto), result);
    }

    @Test
    void whenAddTagAndQuestionAlreadyHasItThenThrownTagAlreadyAddedException() {
        //given
        question.setTags(List.of(tag));
        //then
        assertThatThrownBy(() -> service.addTagToQuestion(TITLE, ID, sessionMemberDto))
                .isInstanceOf(TagAlreadyAddedException.class);
    }

    @Test
    void whenAddTagAndQuestionIsCloseThenThrownException() {
        //given
        question.setStatus(QuestionStatus.CLOSE);
        //then
        assertThatThrownBy(() -> service.addTagToQuestion(TITLE, ID, sessionMemberDto))
                .isInstanceOf(QuestionStatusException.class);
    }

    @Test
    void whenAddTagToQuestion() {
        //when
        service.addTagToQuestion(TITLE, ID, sessionMemberDto);
        question.setTags(List.of(tag));
        //then
        verify(questionData).addTagToQuestion(tag, question);
        verify(noteService).sendNotification(question, "updated with new tag");

    }
}
