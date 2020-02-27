package com.ra.course.ams.airline.manag.system.service;

import com.ra.course.ams.airline.manag.system.entity.person.Account;

public interface AuthenticationService {

    Account login(String id, String password);
    void logout(Account account);

}
