package com.ra.course.com.stackoverflow.service.post;

import com.ra.course.com.stackoverflow.dto.bounty.CreateBountyDto;
import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.dto.post.QuestionFullDto;
import com.ra.course.com.stackoverflow.entity.Answer;
import com.ra.course.com.stackoverflow.entity.Bounty;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.entity.enums.QuestionStatus;
import com.ra.course.com.stackoverflow.exception.service.BountyCreationException;
import com.ra.course.com.stackoverflow.exception.service.security.QuestionStatusException;
import com.ra.course.com.stackoverflow.repository.BountyRepository;
import com.ra.course.com.stackoverflow.repository.MemberRepository;
import com.ra.course.com.stackoverflow.repository.QuestionRepository;
import com.ra.course.com.stackoverflow.service.RepositoryUtils;
import com.ra.course.com.stackoverflow.service.post.impl.BountyServiceImpl;
import com.ra.course.com.stackoverflow.service.system.NotificationService;
import com.ra.course.com.stackoverflow.service.system.SecurityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.ra.course.com.stackoverflow.utils.Constants.ID;
import static com.ra.course.com.stackoverflow.utils.DtoCreationUtils.*;
import static com.ra.course.com.stackoverflow.utils.EntityCreationUtil.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BountyServiceTest {

    private BountyService service;

    private final QuestionRepository questionData = mock(QuestionRepository.class);
    private final MemberRepository memberData = mock(MemberRepository.class);
    private final BountyRepository bountyData = mock(BountyRepository.class);
    private final RepositoryUtils utils = mock(RepositoryUtils.class);
    private final SecurityService securityService = mock(SecurityService.class);
    private final NotificationService noteService = mock(NotificationService.class);

    private Question question;
    private Answer answer;
    private Bounty bounty;
    private Member member;
    private SessionMemberDto sessionMemberDto;
    private CreateBountyDto createBountyDto;
    private QuestionFullDto fullDto;

    @BeforeEach
    void setUp() {
        service = new BountyServiceImpl(questionData, bountyData, memberData, utils, securityService, noteService);

        bounty = getBounty();
            bounty.setReputation(10);
        question = getQuestion();
        answer = getAnswer();
        member = getMember();
            member.getAccount().setReputation(20);

        sessionMemberDto = getSessionMemberDto();
        createBountyDto = new CreateBountyDto();
            createBountyDto.setReputation(10);
            createBountyDto.setExpiry(LocalDateTime.MAX);
        var bountyDto = getBountyDto();
            bountyDto.setReputation(10);
        fullDto = getQuestionFullDto();
            fullDto.setBounty(bountyDto);

        when(utils.getQuestionFromDB(ID)).thenReturn(question);
        when(securityService.checkStatusAndReturnMember(sessionMemberDto)).thenReturn(member);
        when(utils.getMemberFromDB(ID)).thenReturn(member);
    }

    @Test
    void whenAddBountyAndQuestionWithBountyThenThrownBountyCreationException() {
        //given
        question.setBounty(bounty);
        //then
        assertThatThrownBy(() -> service.addBounty(ID, createBountyDto, sessionMemberDto))
                .isInstanceOf(BountyCreationException.class)
                .hasMessage("Question already has a bounty");
    }
    @Test
    void whenAddBountyAndQuestionIsCloseThenThrownException() {
        //given
        question.setStatus(QuestionStatus.CLOSE);
        //then
        assertThatThrownBy(() -> service.addBounty(ID, createBountyDto, sessionMemberDto))
                .isInstanceOf(QuestionStatusException.class);
    }

    @Test
    void whenAddBountyAndReputationIsToHighThenThrownBountyCreationException() {
        //given
        member.getAccount().setReputation(1);
        //then
        assertThatThrownBy(() -> service.addBounty(ID, createBountyDto, sessionMemberDto))
                .isInstanceOf(BountyCreationException.class)
                .hasMessage("Member reputation is less then bounty reputation");
    }

    @Test
    void whenAddBountyThenReturnQuestionFullDtoWithBounty() {
        //given
        var newBounty = getBounty();
            newBounty.setId(null);
            newBounty.setReputation(10);
            newBounty.setQuestionId(ID);
        when(bountyData.save(newBounty)).thenReturn(bounty);
        //when
        var result = service.addBounty(ID, createBountyDto, sessionMemberDto);
        //then
        assertEquals(fullDto, result);
        assertNotNull(result.getBounty());
        verify(noteService).sendNotification(question, "updated with new bounty");

    }

    @Test
    void whenChargeBountyAndQuestionWithoutBounty() {
        //when
        service.chargeBounty(answer);
        //
        verifyNoInteractions(memberData);
        verifyNoInteractions(questionData);
        verifyNoInteractions(bountyData);
    }

    @Test
    void whenChargeBounty() {
        //given
        question.setBounty(bounty);
        when(utils.getBountyFromDB(ID)).thenReturn(bounty);
        var creator = getMember();
            creator.getAccount().setReputation(20);
        var author = getMember();
            author.getAccount().setReputation(20);
        when(utils.getMemberFromDB(ID)).thenReturn(creator, author);
        //when
        service.chargeBounty(answer);
            question.setBounty(null);
        //then

        verify(memberData, times(2)).update(any());
        verify(questionData).update(question);
        verify(bountyData).deleteById(ID);
        verify(noteService).sendNotification(answer, "charge by bounty");
    }

    @Test
    void whenDeleteBounty() {
        //given
        bounty.setQuestionId(ID);
        when(securityService.checkRightOfMemberAndReturnBounty(sessionMemberDto, ID)).thenReturn(bounty);
        //when
        service.deleteBounty(ID, sessionMemberDto);
        question.setBounty(null);
        //then
        verify(questionData).update(question);
        verify(bountyData).deleteById(ID);
    }
}

