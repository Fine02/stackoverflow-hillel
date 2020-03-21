package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.Address;
import com.ra.course.ams.airline.manag.system.entity.person.Admin;
import com.ra.course.ams.airline.manag.system.exception.AdminAlreadyExistException;
import com.ra.course.ams.airline.manag.system.exception.AdminNotExistException;
import com.ra.course.ams.airline.manag.system.repository.person.AdminsRepository;
import com.ra.course.ams.airline.manag.system.service.PersonManagementService;

import java.util.Collection;
import java.util.Optional;

public class AdminManagementServiceImpl implements PersonManagementService<Admin> {

    private AdminsRepository adminRepository;


    @Override
    public Optional <Admin> findByEmail(final String email) {
        final Collection<Admin> admins = adminRepository.getInstances();
        final Admin findedAdmin = admins.stream()
                .filter(admin -> email.equals(admin.getEmail()))
                .findAny()
                .orElseThrow(() -> new AdminNotExistException("No admin found for given email"));

        return Optional.of(new Admin(findedAdmin));
    }

    @Override
    public Optional <Admin> findByPhoneNumber(final String phone) {
        final Admin adminFromRepo = adminRepository.getInstance(phone);
        if (adminFromRepo == null) {
            throw new AdminNotExistException("No admin found for given phone number");
        }
        return Optional.of(new Admin(adminFromRepo));
    }

    @Override
    public  Optional <Admin> add(final Admin admin) {
        Admin adminFromRepo = adminRepository.getInstance(admin.getPhone());
        if (adminFromRepo != null) {
            throw new AdminAlreadyExistException();
        }
        adminFromRepo = new Admin(admin);
        adminRepository.addInstance(adminFromRepo);
        return Optional.of(admin);
    }

    @Override
    public Optional <Admin> updatePhone(final Admin admin, final String phone) {
        final Admin adminFromRepo = adminRepository.getInstance(admin.getPhone());
        if (adminFromRepo == null) {
            throw new AdminNotExistException();
        }
        adminFromRepo.setPhone(phone);
        adminRepository.updateInstance(adminFromRepo);
        admin.setPhone(phone);

        return Optional.of(admin);
    }

    @Override
    public Optional <Admin> updateEmail(final Admin admin, final String email) {
        final Admin adminFromRepo = adminRepository.getInstance(admin.getPhone());
        if (adminFromRepo == null) {
            throw new AdminNotExistException();
        }
        adminFromRepo.setEmail(email);
        adminRepository.updateInstance(adminFromRepo);
        admin.setEmail(email);

        return Optional.of(admin);
    }

    @Override
    public Optional <Admin> updateAddress(final Admin admin, final Address address) {
        final Admin adminFromRepo = adminRepository.getInstance(admin.getPhone());
        if (adminFromRepo == null) {
            throw new AdminNotExistException();
        }
        adminFromRepo.setAddress(address);
        adminRepository.updateInstance(adminFromRepo);
        admin.setAddress(address);

        return Optional.of(admin);
    }

    @Override
    public void remove(final Admin admin) {
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
