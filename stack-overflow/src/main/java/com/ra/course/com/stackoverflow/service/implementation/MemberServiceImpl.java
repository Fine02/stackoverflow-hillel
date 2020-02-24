package com.ra.course.com.stackoverflow.service.implementation;

import com.ra.course.com.stackoverflow.dto.QuestionDto;
import com.ra.course.com.stackoverflow.dto.mapper.QuestionDtoMapper;
import com.ra.course.com.stackoverflow.entity.implementation.Question;
import com.ra.course.com.stackoverflow.exception.repository.RepositoryException;
import com.ra.course.com.stackoverflow.exception.service.ServiceException;
import com.ra.course.com.stackoverflow.repository.GeneralRepository;
import com.ra.course.com.stackoverflow.service.MemberService;
import org.mapstruct.factory.Mappers;

import java.util.Objects;

public class MemberServiceImpl implements MemberService {

    private transient final GeneralRepository<Question> repository;

    public MemberServiceImpl(final GeneralRepository<Question> repository) {
        Objects.requireNonNull(repository, "argument 'repository' must not be null");
        this.repository = repository;
    }

    @Override
    public void createQuestion(final QuestionDto questionDto) throws ServiceException {
        Objects.requireNonNull(questionDto, "argument 'questionDto' must not be null");

        final QuestionDtoMapper mapper = Mappers.getMapper(QuestionDtoMapper.class);

        try {
            repository.save(mapper.toEntity(questionDto));
        } catch (RepositoryException re) {
            throw (ServiceException) new ServiceException().initCause(re);
        }
    }
}
