package com.ra.course.com.stackoverflow.dto.mapper;

import com.ra.course.com.stackoverflow.dto.GeneralSaveDto;
import com.ra.course.com.stackoverflow.entity.interfaces.IDEntity;
import org.mapstruct.Mapper;

@Mapper
public interface IDEntityFromSaveDto<D extends GeneralSaveDto, I extends IDEntity> {

    I createIDEntityFromSaveDto (D saveDto, long id);

}
