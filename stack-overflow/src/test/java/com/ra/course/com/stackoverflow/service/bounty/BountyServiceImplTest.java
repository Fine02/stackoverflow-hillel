package com.ra.course.com.stackoverflow.service.bounty;

import com.ra.course.com.stackoverflow.entity.Account;
import com.ra.course.com.stackoverflow.entity.Bounty;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.exception.repository.QuestionRepositoryException;
import com.ra.course.com.stackoverflow.exception.service.InternalServerErrorException;
import com.ra.course.com.stackoverflow.repository.BountyRepository;
import com.ra.course.com.stackoverflow.repository.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BountyServiceImplTest {
    private QuestionRepository questionRepository;
    private BountyRepository bountyRepository;
    private BountyService bountyService;
    private Question questionWithBounty ;
    private Question questionWithoutBounty ;
    private long id = 1L;
    private int reputation = 10;
    private Bounty bounty;
    private Account account = Account.builder()
                                     .password("password")
                                     .email("email")
                                     .name("name")
                                     .build();

    @BeforeEach
    void setUp() {
        questionRepository = mock(QuestionRepository.class);
        bountyRepository = mock(BountyRepository.class);
        bountyService = new BountyServiceImpl(questionRepository, bountyRepository);
        bounty = new Bounty(id, reputation, LocalDateTime.now(), Member.builder().account(account).build());
        questionWithBounty = constructQuestionWithBounty();
        questionWithoutBounty = constructQuestionWithoutBounty();
    }

    @Test
    public void shouldCreateAndAddBounty() {
        when(questionRepository.findById(questionWithoutBounty.getId())).thenReturn(Optional.of(questionWithoutBounty));
        when(bountyRepository.findById(bounty.getId())).thenReturn(Optional.empty());
        when(bountyRepository.save(bounty)).thenReturn(bounty);
        when(questionRepository.update(questionWithBounty)).thenReturn(questionWithBounty);

        Optional<Bounty> actualResult = bountyService.addBounty(questionWithoutBounty, bounty);

        assertThat(actualResult.get()).isEqualTo(bounty);

        verify(questionRepository).update(questionWithBounty);
    }

    @Test
    public void shouldAddExistingBountyToQuestion() {
        when(bountyRepository.findById(bounty.getId())).thenReturn(Optional.of(bounty));
        when(questionRepository.findById(questionWithoutBounty.getId())).thenReturn(Optional.of(questionWithoutBounty));
        when(questionRepository.update(questionWithBounty)).thenReturn(questionWithBounty);

        Optional<Bounty> actualResult = bountyService.addBounty(questionWithoutBounty, bounty);

        assertThat(actualResult.get()).isEqualTo(bounty);

        verify(questionRepository).update(questionWithBounty);
    }

    @Test
    public void shouldThrowExceptionIfQuestionNotFound() {
        when(questionRepository.findById(questionWithoutBounty.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bountyService.addBounty(questionWithoutBounty, bounty)).isInstanceOf(QuestionRepositoryException.class);
    }

    @Test
    public void shouldDeleteBountyIfQuestionUpdateThrowException() {
        when(bountyRepository.findById(bounty.getId())).thenReturn(Optional.of(bounty));
        when(questionRepository.findById(questionWithoutBounty.getId())).thenReturn(Optional.of(questionWithoutBounty));
        when(questionRepository.update(questionWithBounty)).thenThrow(new InternalServerErrorException(""));

        Optional<Bounty> actualResult = bountyService.addBounty(questionWithoutBounty, bounty);

        assertThat(actualResult).isEmpty();

        verify(questionRepository).update(questionWithBounty);
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
                       .author(Member.builder()
                                     .id(id)
                                     .account(account)
                                     .build())
                       .build();
    }
}