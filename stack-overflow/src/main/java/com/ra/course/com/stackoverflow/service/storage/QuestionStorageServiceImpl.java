package com.ra.course.com.stackoverflow.service.storage;

import com.ra.course.com.stackoverflow.dto.QuestionDto;
import com.ra.course.com.stackoverflow.dto.mapper.impl.QuestionMapper;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.exception.service.QuestionNotFoundException;
import com.ra.course.com.stackoverflow.repository.QuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class QuestionStorageServiceImpl implements QuestionStorageService {

    private final transient QuestionRepository questionRepository;
    private final transient QuestionMapper mapper;

    @Override
    public List<QuestionDto> getByAuthorId(final Long id) {
        return mapper.dtoFromEntity(questionRepository.findByMemberId(id));
    }

    @Override
    public QuestionDto getById(final Long id) {
        return mapper.dtoFromEntity(getQuestionFromDb(id));
    }

    private Question getQuestionFromDb (final Long id){
        return questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException("No such question"));
    }
}
