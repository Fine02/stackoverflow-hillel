package com.ra.course.com.stackoverflow.repository.memory;

import com.ra.course.com.stackoverflow.dto.GeneralSaveDto;
import com.ra.course.com.stackoverflow.dto.mapper.IDEntityFromSaveDto;
import com.ra.course.com.stackoverflow.entity.interfaces.IDEntity;
import com.ra.course.com.stackoverflow.repository.interfaces.GeneralRepository;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryGeneralRepository<D extends GeneralSaveDto,I extends IDEntity> implements GeneralRepository<D, I> {

    final transient private Map<Long, I> dataOfIdEntity = new ConcurrentHashMap<>();
    final transient private AtomicLong currentID = new AtomicLong(0);
    final transient private IDEntityFromSaveDto <D, I> idEntityMapper = Mappers.getMapper(IDEntityFromSaveDto.class);

    @Override
    public I save(final D saveDto) {
        final I savedIdEntity = idEntityMapper.createIDEntityFromSaveDto(saveDto, currentID.get());
        dataOfIdEntity.put(currentID.get(), savedIdEntity);
        return savedIdEntity;
    }

    @Override
    public Optional<I> findById(final long id) {
        return Optional.ofNullable(dataOfIdEntity.get(id));
    }

    @Override
    public void delete(final I idEntity) {
        dataOfIdEntity.remove(idEntity.getId());
    }

    @Override
    public List<I> findAll() {
        return new ArrayList<>(dataOfIdEntity.values());
    }

    protected Map<Long, I> getData() {
        return dataOfIdEntity;
    }
}
