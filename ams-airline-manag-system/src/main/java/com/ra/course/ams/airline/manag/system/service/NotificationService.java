package com.ra.course.ams.airline.manag.system.service;

import com.ra.course.ams.airline.manag.system.entity.Address;

public interface NotificationService {

    int notificationByEmail(String email);

    int notificationByPostal(Address address);

    int notificationBySms(String email);

}
