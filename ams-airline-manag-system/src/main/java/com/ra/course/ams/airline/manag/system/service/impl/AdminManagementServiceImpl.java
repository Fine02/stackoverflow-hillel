package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.Address;
import com.ra.course.ams.airline.manag.system.entity.person.Admin;
import com.ra.course.ams.airline.manag.system.exception.AdminAlreadyExistException;
import com.ra.course.ams.airline.manag.system.exception.AdminNotExistException;
import com.ra.course.ams.airline.manag.system.repository.person.AdminsRepository;
import com.ra.course.ams.airline.manag.system.service.PersonManagementService;

import java.util.Collection;

public class AdminManagementServiceImpl implements PersonManagementService<Admin> {

    private AdminsRepository adminRepository;


    @Override
    public Admin findByEmail(final String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email address for search cannot be null, empty or blank");
        }
        final Collection<Admin> admins = adminRepository.getInstances();
        final Admin findedAdmin = admins.stream()
                .filter(admin -> email.equals(admin.getEmail()))
                .findAny()
                .orElseThrow(() -> new AdminNotExistException("No admin found for given email"));
        return new Admin(findedAdmin);
    }

    @Override
    public Admin findByPhoneNumber(final String phone) {
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("Phone for search cannot be null, empty or blank");
        }
        final Admin adminFromRepo = adminRepository.getInstance(phone);
        if (adminFromRepo == null) {
            throw new AdminNotExistException("No admin found for given phone number");
        }
        return new Admin(adminFromRepo);
    }

    @Override
    public Admin add(final Admin admin) {
        if (admin == null) {
            throw new IllegalArgumentException("Cannot process add operation for null value argument.");
        }
        Admin adminFromRepo = adminRepository.getInstance(admin.getPhone());
        if (adminFromRepo != null) {
            throw new AdminAlreadyExistException();
        }
        adminFromRepo = new Admin(admin);
        adminRepository.addInstance(adminFromRepo);
        return admin;
    }

    @Override
    public Admin updatePhone(final Admin admin, final String phone) {
        if (admin == null) {
            throw new IllegalArgumentException("Cannot process updatePhone operation for null value argument.");
        }
        final Admin adminFromRepo = adminRepository.getInstance(admin.getPhone());
        if (adminFromRepo == null) {
            throw new AdminNotExistException();
        }
        adminFromRepo.setPhone(phone);
        adminRepository.updateInstance(adminFromRepo);
        admin.setPhone(phone);
        return admin;
    }

    @Override
    public Admin updateEmail(final Admin admin, final String email) {
        if (admin == null) {
            throw new IllegalArgumentException("Cannot process updateEmail operation for null value argument.");
        }
        final Admin adminFromRepo = adminRepository.getInstance(admin.getPhone());
        if (adminFromRepo == null) {
            throw new AdminNotExistException();
        }
        adminFromRepo.setEmail(email);
        adminRepository.updateInstance(adminFromRepo);
        admin.setEmail(email);
        return admin;
    }

    @Override
    public Admin updateAddress(final Admin admin, final Address address) {
        if (admin == null) {
            throw new IllegalArgumentException("Cannot process updateAddress operation for null value argument.");
        }
        final Admin adminFromRepo = adminRepository.getInstance(admin.getPhone());
        if (adminFromRepo == null) {
            throw new AdminNotExistException();
        }
        adminFromRepo.setAddress(address);
        adminRepository.updateInstance(adminFromRepo);
        admin.setAddress(address);
        return admin;
    }

    @Override
    public void remove(final Admin admin) {
        if (admin == null) {
            throw new IllegalArgumentException("Cannot process remove operation for null value argument.");
        }
        final Admin adminFromRepo = adminRepository.getInstance(admin.getPhone());
        if (adminFromRepo == null) {
            throw new AdminNotExistException();
        }
        adminRepository.removeInstance(adminFromRepo);
    }

    public AdminsRepository getAdminRepository() {
        return adminRepository;
    }

    public void setAdminRepository(final AdminsRepository  adminRepository) {
        this.adminRepository = adminRepository;
    }
}
