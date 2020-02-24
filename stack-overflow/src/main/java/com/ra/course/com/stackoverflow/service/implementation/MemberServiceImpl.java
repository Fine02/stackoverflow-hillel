package com.ra.course.com.stackoverflow.service.implementation;

import com.ra.course.com.stackoverflow.dto.QuestionDto;
import com.ra.course.com.stackoverflow.dto.mapper.QuestionDtoMapper;
import com.ra.course.com.stackoverflow.entity.Searchable;
import com.ra.course.com.stackoverflow.entity.implementation.Member;
import com.ra.course.com.stackoverflow.entity.implementation.Question;
import com.ra.course.com.stackoverflow.exception.repository.RepositoryException;
import com.ra.course.com.stackoverflow.exception.service.ServiceException;
import com.ra.course.com.stackoverflow.repository.GeneralRepository;
import com.ra.course.com.stackoverflow.service.MemberService;
import org.mapstruct.factory.Mappers;

import java.util.Objects;

public class MemberServiceImpl implements MemberService {

    private transient final GeneralRepository<Searchable> questionRep;
    private transient final GeneralRepository<Member> memberRep;

    public MemberServiceImpl(final GeneralRepository<Searchable> questionRep, final GeneralRepository<Member> memberRep) {
        Objects.requireNonNull(questionRep, "argument 'questionRep' must not be null");
        this.questionRep = questionRep;
        this.memberRep = memberRep;
    }

    @Override
    public void createQuestion(final QuestionDto questionDto, final Member member) throws ServiceException {
        Objects.requireNonNull(questionDto, "argument 'questionDto' must not be null");
        Objects.requireNonNull(questionDto, "argument 'questionDto' must not be null");

        final QuestionDtoMapper mapper = Mappers.getMapper(QuestionDtoMapper.class);
        final

        Searchable question;
        try {
            question = questionRep.save(mapper.toEntity(questionDto));
        } catch (RepositoryException re) {
            throw (ServiceException) new ServiceException().initCause(re);
        }

        member.addQuestion(question);

        try{
            throw new RepositoryException("test"); //TODO: implement saving question for member
        } catch (RepositoryException re) {
            throw (ServiceException) new ServiceException().initCause(re);
        }
    }
}
