package com.ra.course.com.stackoverflow.dto.mapper;

import com.ra.course.com.stackoverflow.dto.GeneralSaveDto;
import org.mapstruct.Mapper;

@Mapper
public interface EntityFromSaveDto<D extends GeneralSaveDto, I > {

    I createIDEntityFromSaveDto (D saveDto, long id);

}
