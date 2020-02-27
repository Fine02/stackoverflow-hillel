package com.ra.course.ams.airline.manag.system.service;

import com.ra.course.ams.airline.manag.system.entity.person.*;

public interface UserManagementService<T extends Person> {

    T findByEmail(String email);
    T findByPhoneNumber(String phone);
    T add(T t);
    void updateData(T t);
    void remove(T t);

}
