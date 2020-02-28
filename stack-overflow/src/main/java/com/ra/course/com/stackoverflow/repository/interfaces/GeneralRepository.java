package com.ra.course.com.stackoverflow.repository.interfaces;

import com.ra.course.com.stackoverflow.dto.GeneralSaveDto;

import java.util.List;
import java.util.Optional;

public interface GeneralRepository<D extends GeneralSaveDto, I> {

    I save(D dto);

    Optional<I> findById(long id);

    void delete(I i);

    List<I> findAll();
}
