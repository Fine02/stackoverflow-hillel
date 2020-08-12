package com.ra.course.com.stackoverflow.dto.mapper;

import com.ra.course.com.stackoverflow.dto.NotificationDto;
import com.ra.course.com.stackoverflow.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    NotificationMapper MAPPER = Mappers.getMapper(NotificationMapper.class);

    NotificationDto toNotificationDto(Notification notification);

    List<NotificationDto> toNotificationDto(List<Notification> notification);
}
