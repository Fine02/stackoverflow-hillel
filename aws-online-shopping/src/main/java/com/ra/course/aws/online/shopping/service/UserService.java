package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.entity.user.AccountStatus;
import com.ra.course.aws.online.shopping.entity.user.Member;

public interface UserService {
    String registerAccount();
    AccountStatus blockUser(Member member);
}
