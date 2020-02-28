package com.ra.course.ams.airline.manag.system.repository;

//import com.ra.course.ams.airline.manag.system.exceptions.InstanceAlreadyExistException;
//import com.ra.course.ams.airline.manag.system.exceptions.InstanceNotExistException;

import java.util.Collection;

public interface Repository<T,I> {

    T getInstance(I instanceId);
    Collection<T> getInstances();
    T addInstance(T t); // throws InstanceAlreadyExistException
    void updateInstance(T t); // throws InstanceNotExistException
    void removeInstance(T t);

}
