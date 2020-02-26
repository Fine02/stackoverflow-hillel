package com.ra.course.ams.airline.manag.system.service;

import com.ra.course.ams.airline.manag.system.entity.person.*;

public interface UserManagementService {

    Account getAccountById(String id);

    Person getPersonById(String id);

    Person getPersonByEmail(String email);

    Person getPersonByPhone(String phone);

    Person addPerson(Person customer);

    Customer addCustomer(Customer customer);

    Pilot addPilot(Pilot pilot);

    Crew addCrew(Crew crew);

    Admin addAdmin(Admin admin);

    void deleteAccount(Account account);

    void blockAccount(Account account);

    void updateAccount(Account account);

    boolean resetPassword(Account account, String password);
}
