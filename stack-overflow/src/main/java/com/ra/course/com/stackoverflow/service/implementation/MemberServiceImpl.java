package com.ra.course.com.stackoverflow.service.implementation;

import com.ra.course.com.stackoverflow.dto.QuestionDto;
import com.ra.course.com.stackoverflow.dto.implementation.QuestionDtoMapper;
import com.ra.course.com.stackoverflow.entity.implementation.Question;
import com.ra.course.com.stackoverflow.exception.repository.RepositoryException;
import com.ra.course.com.stackoverflow.exception.service.ServiceException;
import com.ra.course.com.stackoverflow.repository.GeneralRepository;
import com.ra.course.com.stackoverflow.service.MemberService;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Objects;

public class MemberServiceImpl implements MemberService {

    GeneralRepository<Question> questionRepository = null;

    public MemberServiceImpl(GeneralRepository<Question> repository) {
        Objects.requireNonNull(repository, "argument 'repository' must not be null");
        this.questionRepository = repository;
    }

    @Override
    public void createQuestion(QuestionDto questionDto) throws ServiceException {
        Objects.requireNonNull(questionDto, "argument 'questionDto' must not be null");

        QuestionDtoMapper mapper = Mappers.getMapper(QuestionDtoMapper.class);

        try {
            Question question = questionRepository.save(mapper.toEntity(questionDto));
        } catch (RepositoryException re) {
            throw new ServiceException(re.getMessage(), re.getCause());
        }
    }
}
