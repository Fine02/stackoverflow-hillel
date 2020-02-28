package com.ra.course.ams.airline.manag.system.repository.person;

import com.ra.course.ams.airline.manag.system.entity.person.Crew;
import com.ra.course.ams.airline.manag.system.exceptions.InstanceAlreadyExistException;
import com.ra.course.ams.airline.manag.system.repository.Repository;

import java.util.Collection;
import java.util.Map;

public class CrewRepository implements Repository<Crew, String> {

    private Map<String, Crew> crews;

    @Override
    public Crew getInstance(String phoneNumber) {
        return crews.get(phoneNumber);
    }

    @Override
    public Collection<Crew> getInstances() {
        return crews.values();
    }

    @Override
    public Crew addInstance(Crew crew) {
        if (crews.containsKey(crew.getPhone())){
            throw new InstanceAlreadyExistException();
        }
        return crews.put(crew.getPhone(), crew);
    }

    @Override
    public void updateInstance(Crew crew) {
        crews.put(crew.getPhone(), crew);
    }

    @Override
    public void removeInstance(Crew crew) {
        crews.remove(crew.getPhone());
    }

    public Map<String, Crew> getCrews() {
        return crews;
    }

    public void setCrews(Map<String, Crew> crews) {
        this.crews = crews;
    }
}
