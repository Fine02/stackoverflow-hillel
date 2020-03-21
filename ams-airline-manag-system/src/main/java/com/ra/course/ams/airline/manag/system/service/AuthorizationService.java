package com.ra.course.ams.airline.manag.system.service;

import com.ra.course.ams.airline.manag.system.entity.person.Account;
import com.ra.course.ams.airline.manag.system.entity.person.Person;

public interface AuthorizationService {

    /**
     *
     * @param account instead of which we need to check grants
     * @param person that should be checked for delete account grands
     * @throws com.ra.course.ams.airline.manag.system.exception.UnauthorizedOperationException
     */
    void checkGrantsForDeleteAccountOperation(Account account, Person person);

    /**
     *
     * @param account instead of which we need to check grants
     * @param person that should be checked for block account grands
     * @throws com.ra.course.ams.airline.manag.system.exception.UnauthorizedOperationException
     */
    void checkGrantsForBlockAccountOperation(Account account, Person person);

    /**
     *
     * @param account instead of which we need to check grants
     * @param person that should be checked for update account grands
     * @throws com.ra.course.ams.airline.manag.system.exception.UnauthorizedOperationException
     */
    void checkGrantsForUpdateAccountOperation(Account account, Person person);

    /**
     * @param account instead of which we need to check grants
     * @param person that should be checked for update account grands
     * @throws com.ra.course.ams.airline.manag.system.exception.UnauthorizedOperationException
     */
    void checkGrantsForResetPasswordOperation(Account account, Person person);
}
