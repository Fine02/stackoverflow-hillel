package com.ra.course.com.stackoverflow.repository;

import com.ra.course.com.stackoverflow.entity.Notification;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository {

    List<Notification> getAllByMemberId(Long memberId);

    Optional<Notification> getById(Long id);

    Notification save(Notification notification);

    void updateRead(Notification notification);
}
