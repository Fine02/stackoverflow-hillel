package com.ra.course.com.stackoverflow.repository.memory;

import com.ra.course.com.stackoverflow.dto.MemberSaveDto;
import com.ra.course.com.stackoverflow.entity.implementations.Member;
import com.ra.course.com.stackoverflow.repository.interfaces.MemberRepository;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class InMemoryMemberRepository extends InMemoryGeneralRepository<MemberSaveDto, Member> implements MemberRepository {

    final transient private Map<Long, Member> data = super.getData();

    private static InMemoryMemberRepository instanceOf;

    private InMemoryMemberRepository() {}

    public static InMemoryMemberRepository getInstanceOf(){
        if (instanceOf == null){
            instanceOf = new InMemoryMemberRepository();
        }
        return instanceOf;
    }

    @Override
    public Optional<Member> findByMemberName(final String name) {
        return data.values().stream()
                .filter(Objects::nonNull)
                .filter(m -> m.getAccount().getName().equalsIgnoreCase(name))
                .findAny();
    }
}
