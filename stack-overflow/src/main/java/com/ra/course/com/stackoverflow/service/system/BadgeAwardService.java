package com.ra.course.com.stackoverflow.service.system;

import com.ra.course.com.stackoverflow.entity.Member;

public interface BadgeAwardService<T> {
    Member awardMember(T cause);
}
