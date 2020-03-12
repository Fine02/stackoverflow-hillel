package com.ra.course.ams.airline.manag.system.service;

import com.ra.course.ams.airline.manag.system.entity.person.Account;
import com.ra.course.ams.airline.manag.system.entity.person.AccountStatus;
import com.ra.course.ams.airline.manag.system.entity.person.Admin;

import java.util.List;

public interface AccountManagementService {

    Account createAccount(Account account);

    List<Account> getAccounts();

    /**
     * @param account {@link Account} on which performing delete operation
     * @param admin {@link Admin} that initiate delete operation
     * @throws com.ra.course.ams.airline.manag.system.exception.UnauthorizedOperationException
     */
    void deleteAccount(Account account, Admin admin);

    /**
     * @param account on which performing update status operation
     * @param status new {@link AccountStatus}
     * @param admin that initiate update status operation
     * @return
     * @throws com.ra.course.ams.airline.manag.system.exception.UnauthorizedOperationException
     */
    Account updateStatus(Account account, AccountStatus status, Admin admin);

    /**
     * @param account {@link Account} on which performing block operation
     * @param admin {@link Admin} that initiate block operation
     * @return
     * @throws com.ra.course.ams.airline.manag.system.exception.UnauthorizedOperationException
     */
    Account blockAccount(Account account, Admin admin);

    /**
     * @param account {@link Account} for which will reset password
     * @param oldPassword for check
     * @param newPassword for update
     * @throws com.ra.course.ams.airline.manag.system.exception.BadCredentialsException
     * if oldpassword is invalid or {@link Account} not exist
     */
    void resetPassword(Account account, String oldPassword, String newPassword);

    /**
     * @param account {@link Account} for which will reset password
     * @param newPassword for update
     * @param admin {@link Admin} that initiate reset password operation
     * @throws com.ra.course.ams.airline.manag.system.exception.UnauthorizedOperationException
     */
    void resetPassword(Account account, String newPassword, Admin admin);
}
