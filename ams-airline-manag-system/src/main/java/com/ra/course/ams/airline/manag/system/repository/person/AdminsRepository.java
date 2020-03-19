package com.ra.course.ams.airline.manag.system.repository.person;

import com.ra.course.ams.airline.manag.system.entity.person.Admin;

import java.util.Collection;
import java.util.Map;

public interface AdminsRepository {

    Admin getInstance(final String phoneNumber);

    Collection<Admin> getInstances();

    Admin addInstance(final Admin admin);

    void updateInstance(final Admin admin);

    void removeInstance(final Admin admin);

    Map<String, Admin> getAdmins();

    void setAdmins(final Map<String, Admin> admins);
}
