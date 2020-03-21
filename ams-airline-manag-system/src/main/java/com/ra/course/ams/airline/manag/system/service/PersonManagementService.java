package com.ra.course.ams.airline.manag.system.service;

import com.ra.course.ams.airline.manag.system.entity.Address;
import com.ra.course.ams.airline.manag.system.entity.person.*;

import java.util.Optional;

public interface PersonManagementService<T extends Person> {

    Optional<T> findByEmail(String email);

    Optional<T> findByPhoneNumber(String phone);

    Optional<T> add(T t);

    Optional<T> updatePhone(T t, String phone);

    Optional<T> updateEmail(T t, String email);

    Optional<T> updateAddress(T t, Address address);

    void remove(T t);
}
