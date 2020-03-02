package com.ra.course.ams.airline.manag.system.repository.person;

import com.ra.course.ams.airline.manag.system.entity.person.Admin;
import com.ra.course.ams.airline.manag.system.exceptions.InstanceAlreadyExistException;
import com.ra.course.ams.airline.manag.system.repository.Repository;

import java.util.Collection;
import java.util.Map;

public class AdminsRepository implements Repository<Admin, String> {

    private Map<String, Admin> admins;

    @Override
    public Admin getInstance(String phoneNumber) {
        return admins.get(phoneNumber);
    }

    @Override
    public Collection<Admin> getInstances() {
        return admins.values();
    }

    @Override
    public Admin addInstance(Admin admin) {
        if (admins.containsKey(admin.getPhone())){
            throw new InstanceAlreadyExistException();
        }
        return admins.put(admin.getPhone(), admin);
    }

    @Override
    public void updateInstance(Admin admin) {
        admins.put(admin.getPhone(), admin);
    }

    @Override
    public void removeInstance(Admin admin) {
        admins.remove(admin.getPhone());
    }

    public Map<String, Admin> getAdmins() {
        return admins;
    }

    public void setAdmins(Map<String, Admin> admins) {
        this.admins = admins;
    }
}
