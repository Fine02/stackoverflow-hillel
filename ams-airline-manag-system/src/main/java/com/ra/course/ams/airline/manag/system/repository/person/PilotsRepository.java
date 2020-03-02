package com.ra.course.ams.airline.manag.system.repository.person;

import com.ra.course.ams.airline.manag.system.entity.person.Pilot;
import com.ra.course.ams.airline.manag.system.exceptions.InstanceAlreadyExistException;
import com.ra.course.ams.airline.manag.system.repository.Repository;

import java.util.Collection;
import java.util.Map;

public class PilotsRepository implements Repository<Pilot, String> {

    private Map<String, Pilot> pilots;

    @Override
    public Pilot getInstance(String phoneNumber) {
        return pilots.get(phoneNumber);
    }

    @Override
    public Collection<Pilot> getInstances() {
        return pilots.values();
    }

    @Override
    public Pilot addInstance(Pilot pilot) {
        if (pilots.containsKey(pilot.getPhone())){
            throw new InstanceAlreadyExistException();
        }
        return pilots.put(pilot.getPhone(), pilot);
    }

    @Override
    public void updateInstance(Pilot pilot) {
        pilots.put(pilot.getPhone(), pilot);
    }

    @Override
    public void removeInstance(Pilot pilot) {
        pilots.remove(pilot.getPhone());
    }

    public Map<String, Pilot> getPilots() {
        return pilots;
    }

    public void setPilots(Map<String, Pilot> pilots) {
        this.pilots = pilots;
    }
}
