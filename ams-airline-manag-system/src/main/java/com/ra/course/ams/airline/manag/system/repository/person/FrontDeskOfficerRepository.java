package com.ra.course.ams.airline.manag.system.repository.person;

import com.ra.course.ams.airline.manag.system.entity.person.FrontDeskOfficer;

import java.util.Collection;
import java.util.Map;

public interface FrontDeskOfficerRepository {

    FrontDeskOfficer getInstance(final String phoneNumber);

    FrontDeskOfficer findByPhoneNumber(final String phoneNumber);

    FrontDeskOfficer findByEmail(final String email);

    Collection<FrontDeskOfficer> getInstances();

    FrontDeskOfficer addInstance(final FrontDeskOfficer officer);

    void updateInstance(final FrontDeskOfficer officer);

    void removeInstance(final FrontDeskOfficer officer);

    Map<String, FrontDeskOfficer> getFrontDeskOfficers();

    void setFrontDeskOfficers(final Map<String, FrontDeskOfficer> frontDeskOfficers);
}
