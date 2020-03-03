package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.Address;
import com.ra.course.ams.airline.manag.system.entity.person.Admin;
import com.ra.course.ams.airline.manag.system.exceptions.AdminAlreadyExistException;
import com.ra.course.ams.airline.manag.system.exceptions.AdminNotExistException;
import com.ra.course.ams.airline.manag.system.exceptions.PilotNotExistException;
import com.ra.course.ams.airline.manag.system.repository.Repository;
import com.ra.course.ams.airline.manag.system.service.PersonManagementService;

import java.util.Collection;

public class AdminManagementServiceImpl implements PersonManagementService<Admin> {

    private Repository<Admin, String> adminRepository;


    @Override
    public Admin findByEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email address for search cannot be null, empty or blank");
        }
        Collection<Admin> admins = adminRepository.getInstances();
        Admin findedAdmin = admins.stream()
                .filter(admin -> email.equals(admin.getEmail()))
                .findAny()
                .orElseThrow(() -> new PilotNotExistException("No pilot found for given email"));
        return new Admin(findedAdmin);
    }

    @Override
    public Admin findByPhoneNumber(String phone) {
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("Phone for search cannot be null, empty or blank");
        }
        Admin adminFromRepo = adminRepository.getInstance(phone);
        if (adminFromRepo == null) {
            throw new AdminNotExistException("No admin found for given phone number");
        }
        return new Admin(adminFromRepo);
    }

    @Override
    public Admin add(Admin admin) {
        if (admin == null) {
            throw new NullPointerException("Cannot process add operation for null value argument.");
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
    public Admin updatePhone(Admin admin, String phone) {
        if (admin == null) {
            throw new NullPointerException("Cannot process updatePhone operation for null value argument.");
        }
        Admin adminFromRepo = adminRepository.getInstance(admin.getPhone());
        if (adminFromRepo == null) {
            throw new AdminNotExistException();
        }
        adminFromRepo.setPhone(phone);
        adminRepository.updateInstance(adminFromRepo);
        admin.setPhone(phone);
        return admin;
    }

    @Override
    public Admin updateEmail(Admin admin, String email) {
        if (admin == null) {
            throw new NullPointerException("Cannot process updateEmail operation for null value argument.");
        }
        Admin adminFromRepo = adminRepository.getInstance(admin.getPhone());
        if (adminFromRepo == null) {
            throw new AdminNotExistException();
        }
        adminFromRepo.setEmail(email);
        adminRepository.updateInstance(adminFromRepo);
        admin.setEmail(email);
        return admin;
    }

    @Override
    public Admin updateAddress(Admin admin, Address address) {
        if (admin == null) {
            throw new NullPointerException("Cannot process updateAddress operation for null value argument.");
        }
        Admin adminFromRepo = adminRepository.getInstance(admin.getPhone());
        if (adminFromRepo == null) {
            throw new AdminNotExistException();
        }
        adminFromRepo.setAddress(address);
        adminRepository.updateInstance(adminFromRepo);
        admin.setAddress(address);
        return admin;
    }

    @Override
    public void remove(Admin admin) {
        if (admin == null) {
            throw new NullPointerException("Cannot process remove operation for null value argument.");
        }
        Admin adminFromRepo = adminRepository.getInstance(admin.getPhone());
        if (adminFromRepo == null) {
            throw new AdminNotExistException();
        }
        adminRepository.removeInstance(adminFromRepo);
    }

    public Repository<Admin, String> getAdminRepository() {
        return adminRepository;
    }

    public void setAdminRepository(Repository<Admin, String> adminRepository) {
        this.adminRepository = adminRepository;
    }
}
