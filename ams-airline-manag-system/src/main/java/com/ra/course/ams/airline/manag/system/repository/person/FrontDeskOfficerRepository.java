package com.ra.course.ams.airline.manag.system.repository.person;

import com.ra.course.ams.airline.manag.system.entity.person.FrontDeskOfficer;
import com.ra.course.ams.airline.manag.system.exception.InstanceAlreadyExistException;
import com.ra.course.ams.airline.manag.system.repository.Repository;

import java.util.Collection;
import java.util.Map;

public class FrontDeskOfficerRepository implements Repository<FrontDeskOfficer, String> {

    private Map<String, FrontDeskOfficer> frontDeskOfficers;

    @Override
    public FrontDeskOfficer getInstance(final String phoneNumber) {
        return frontDeskOfficers.get(phoneNumber);
    }

    @Override
    public Collection<FrontDeskOfficer> getInstances() {
        return frontDeskOfficers.values();
    }

    @Override
    public FrontDeskOfficer addInstance(final FrontDeskOfficer officer) {
        if (frontDeskOfficers.containsKey(officer.getPhone())){
            throw new InstanceAlreadyExistException();
        }
        return frontDeskOfficers.put(officer.getPhone(), officer);
    }

    @Override
    public void updateInstance(final FrontDeskOfficer officer) {
        frontDeskOfficers.put(officer.getPhone(), officer);
    }

    @Override
    public void removeInstance(final FrontDeskOfficer officer) {
        frontDeskOfficers.remove(officer.getPhone());
    }

    public Map<String, FrontDeskOfficer> getFrontDeskOfficers() {
        return frontDeskOfficers;
    }

    public void setFrontDeskOfficers(final Map<String, FrontDeskOfficer> frontDeskOfficers) {
        this.frontDeskOfficers = frontDeskOfficers;
    }
}
