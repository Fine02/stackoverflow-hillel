package com.ra.course.com.stackoverflow.repository;

import com.ra.course.com.stackoverflow.entity.Account;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.enums.AccountStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
public class MemberRepositoryImplIntegrationTest {
    private long ID = 1L;
    private Account account = createNewAccount(ID);
    private Member member = createNewMember(account);

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void whenFindMemberByIdAndMemberPresentInDataBaseThenReturnMember() {
        var member = memberRepository.findById(2L).get();

        assertEquals(member.getAccount().getId(), 2L);
    }

    @Test
    public void whenFindMemberByIdAndMemberNotPresentInDataBaseThenReturnOptionalEmpty() {
        Optional<Member> member = memberRepository.findById(666L);

        assertThat(member.isEmpty()).isTrue();
    }

    @Test
    public void whenSaveMemberInDataBaseThenReturnMemberWithId() {
        var savedMember = memberRepository.save(member);

        assertThat(savedMember.getAccount().getId() > 0).isTrue();
    }

    @Test
    public void whenDeleteMemberAndTryFindItThenReturnMemberWithAccountStatusArchived() {
        memberRepository.delete(member);

        Member result = memberRepository.findById(member.getAccount().getId()).get();

        assertEquals(AccountStatus.ARCHIVED, result.getAccount().getStatus());
    }

    @Test
    public void whenUpdateMemberInDatabaseThenGetUpdatedMember() {
        var before = memberRepository.findById(2L).get();
        before.getAccount().setName("Test111");
        memberRepository.update(before);
        var after = memberRepository.findById(2L).get();

        assertEquals("Test111", after.getAccount().getName());
    }

    @Test
    public void whenFindMemberByNameThenReturnListOfMember() {
        var result = memberRepository.findByMemberName("name");

        for (Member m : result) {
            assertTrue(m.getAccount().getName().contains("name"));
        }
    }

    @Test
    @Rollback
    public void whenUpdateMemberWithChangesInLists(){
        var member = memberRepository.findById(1L).get();

        member.getDownVotedQuestionsId().add(2L);
        member.getUpVotedQuestionsId().add(3L);
        member.getDownVotedAnswersId().add(2L);
        member.getUpVotedAnswersId().add(3L);
        member.getDownVotedCommentsId().add(2L);
        member.getUpVotedCommentsId().add(3L);
        memberRepository.update(member);
        assertThat(memberRepository.findById(member.getAccount().getId()).get().getDownVotedQuestionsId().contains(2L));
        assertThat(memberRepository.findById(member.getAccount().getId()).get().getUpVotedQuestionsId().contains(3L));
        assertThat(memberRepository.findById(member.getAccount().getId()).get().getDownVotedAnswersId().contains(2L));
        assertThat(memberRepository.findById(member.getAccount().getId()).get().getUpVotedAnswersId().contains(3L));
        assertThat(memberRepository.findById(member.getAccount().getId()).get().getDownVotedCommentsId().contains(2L));
        assertThat(memberRepository.findById(member.getAccount().getId()).get().getUpVotedCommentsId().contains(3L));
    }

    private Account createNewAccount(long id) {
        return Account.builder()
                .id(id)
                .password("password")
                .email("email")
                .name("name")
                .build();
    }

    private Member createNewMember(Account account) {
        return Member.builder()
                .account(account)
                .build();
    }
}
