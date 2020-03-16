package com.ra.course.ams.airline.manag.system.service;

import com.ra.course.ams.airline.manag.system.entity.person.Account;

public interface AuthenticationService {

    /**
     * @param id - account id
     * @param password
     * @return instance of {@link Account}
     * @throws com.ra.course.ams.airline.manag.system.exception.BadCredentialsException
     */
    Account login(String id, String password);

    void logout(Account account);
}
