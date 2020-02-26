package com.ra.course.com.stackoverflow.repository.interfaces;

import com.ra.course.com.stackoverflow.dto.GeneralSaveDto;
import com.ra.course.com.stackoverflow.entity.interfaces.IDEntity;

import java.util.List;
import java.util.Optional;

public interface GeneralRepository<D extends GeneralSaveDto, I extends IDEntity> {

    I save(D dto);

    Optional<I> findById(long id);

    void delete(I i);

    List<I> findAll();
}
