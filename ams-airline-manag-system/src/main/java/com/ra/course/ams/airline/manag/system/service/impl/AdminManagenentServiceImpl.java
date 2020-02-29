package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.person.Admin;
import com.ra.course.ams.airline.manag.system.exceptions.InstanceNotExistException;
import com.ra.course.ams.airline.manag.system.repository.Repository;
import com.ra.course.ams.airline.manag.system.service.UserManagementService;

import java.util.Collection;

public class AdminManagenentServiceImpl implements UserManagementService<Admin> {

    private Repository<Admin, String> adminRepository;
    
    @Override
    public Admin findByEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email address for search cannot be null, empty or blank");
        }
        Collection<Admin> admins = adminRepository.getInstances();
        return admins.stream()
                .filter(admin -> email.equals(admin.getEmail()))
                .findAny()
                .orElseThrow();
    }

    @Override
    public Admin findByPhoneNumber(String phone) {
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("Phone for search cannot be null, empty or blank");
        }
        Admin person = adminRepository.getInstance(phone);
        if (person == null) {
            throw new InstanceNotExistException("No admin found for given phone number");
        }
        return person;
    }

    @Override
    public Admin add(Admin admin) {
        if (admin == null) {
            throw new NullPointerException("Cannot process add operation for null value argument.");
        }
        return adminRepository.addInstance(admin);
    }

    @Override
    public void updateData(Admin admin) {
        if (admin == null) {
            throw new NullPointerException("Cannot process update operation for null value argument.");
        }
        adminRepository.updateInstance(admin);
    }

    @Override
    public void remove(Admin admin) {
        if (admin == null) {
            throw new NullPointerException("Cannot process remove operation for null value argument.");
        }
        adminRepository.removeInstance(admin);
    }

    public Repository<Admin, String> getAdminRepository() {
        return adminRepository;
    }

    public void setAdminRepository(Repository<Admin, String> adminRepository) {
        this.adminRepository = adminRepository;
    }
}
