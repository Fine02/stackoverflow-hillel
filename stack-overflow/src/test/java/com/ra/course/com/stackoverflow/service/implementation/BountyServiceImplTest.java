package com.ra.course.com.stackoverflow.service.implementation;

import com.ra.course.com.stackoverflow.entity.Account;
import com.ra.course.com.stackoverflow.entity.Bounty;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.exception.service.QuestionRepositoryException;
import com.ra.course.com.stackoverflow.repository.interfaces.BountyRepository;
import com.ra.course.com.stackoverflow.repository.interfaces.QuestionRepository;
import com.ra.course.com.stackoverflow.service.BountyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

class BountyServiceImplTest {
    private QuestionRepository questionRepository;
    private BountyRepository bountyRepository;
    private BountyService bountyService;
    private long id = 1L;
    private int reputation = 10;

    private Account account = Account.builder()
                                     .password("password")
                                     .email("email")
                                     .name("name")
                                     .build();

    private Member member = Member.builder()
                                  .id(id)
                                  .account(account)
                                  .build();


    private Bounty bounty = new Bounty(id, reputation, LocalDateTime.now(), member);

    @BeforeEach
    void setUp() {
        questionRepository = mock(QuestionRepository.class);
        bountyRepository = mock(BountyRepository.class);
        bountyService = new BountyServiceImpl(questionRepository, bountyRepository);
    }

    @Test
    public void shouldAddBounty() throws QuestionRepositoryException {
        Question questionWithoutBounty = constructQuestionWithoutBounty();
        Question questionWithBounty = constructQuestionWithBounty();

        when(bountyRepository.findById(bounty.getId())).thenReturn(Optional.empty());
        when(bountyRepository.save(bounty)).thenReturn(bounty);
        when(questionRepository.findById(questionWithoutBounty.getId())).thenReturn(Optional.of(questionWithoutBounty));
        when(questionRepository.update(questionWithBounty)).thenReturn(questionWithBounty);

        Bounty actualResult = bountyService.addBounty(questionWithoutBounty, bounty);

        assertThat(actualResult).isEqualTo(bounty);

        verify(questionRepository).findById(questionWithoutBounty.getId());
        verify(questionRepository).update(questionWithBounty);
        verify(bountyRepository).save(bounty);
        verify(bountyRepository).findById(bounty.getId());

        verifyNoMoreInteractions(questionRepository, bountyRepository);
    }

    @Test
    public void shouldCreateAndAddBounty() throws QuestionRepositoryException {
        Question questionWithoutBounty = constructQuestionWithoutBounty();
        Question questionWithBounty = constructQuestionWithBounty();

        when(bountyRepository.findById(bounty.getId())).thenReturn(Optional.of(bounty));
        when(questionRepository.findById(questionWithoutBounty.getId())).thenReturn(Optional.of(questionWithoutBounty));
        when(questionRepository.update(questionWithBounty)).thenReturn(questionWithBounty);

        Bounty actualResult = bountyService.addBounty(questionWithoutBounty, bounty);

        assertThat(actualResult).isEqualTo(bounty);

        verify(questionRepository).findById(questionWithoutBounty.getId());
        verify(questionRepository).update(questionWithBounty);
        verify(bountyRepository).findById(bounty.getId());

        verifyNoMoreInteractions(questionRepository, bountyRepository);
    }

    @Test
    public void shouldThrowExceptionIfQuestionNotFound() throws QuestionRepositoryException {
        Question questionWithoutBounty = constructQuestionWithoutBounty();

        when(bountyRepository.findById(bounty.getId())).thenReturn(Optional.of(bounty));
        when(questionRepository.findById(questionWithoutBounty.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(()-> bountyService.addBounty(questionWithoutBounty, bounty)).isInstanceOf(QuestionRepositoryException.class);

        verify(questionRepository).findById(questionWithoutBounty.getId());
        verify(bountyRepository).findById(bounty.getId());

        verifyNoMoreInteractions(questionRepository, bountyRepository);
    }

    private Question constructQuestionWithBounty() {
        Question question = constructQuestionWithoutBounty();
        question.setBounty(Optional.of(bounty));
        return question;
    }

    private Question constructQuestionWithoutBounty() {
        return Question.builder()
                       .id(id)
                       .title("title")
                       .author(member)
                       .build();
    }
}