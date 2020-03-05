package com.ra.course.com.stackoverflow.service.system;

import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.exception.service.InternalServerErrorException;

public interface BadgeAwardService<T> {
    Member awardMember(T cause) throws InternalServerErrorException;
}
