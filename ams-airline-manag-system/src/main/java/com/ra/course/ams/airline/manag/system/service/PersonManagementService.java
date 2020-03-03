package com.ra.course.ams.airline.manag.system.service;

import com.ra.course.ams.airline.manag.system.entity.Address;
import com.ra.course.ams.airline.manag.system.entity.person.*;

public interface PersonManagementService<T extends Pilot> {

    T findByEmail(String email);
    T findByPhoneNumber(String phone);
    T add(T t);
    T updatePhone(T t, String phone);
    T updateEmail(T t, String email);
    T updateAddress(T t, Address address);
    void remove(T t);

}
