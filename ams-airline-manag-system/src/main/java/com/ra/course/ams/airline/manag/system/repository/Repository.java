package com.ra.course.ams.airline.manag.system.repository;

import com.ra.course.ams.airline.manag.system.exceptions.InstanceAlreadyExistException;
import com.ra.course.ams.airline.manag.system.exceptions.InstanceNotExistException;

import java.util.Collection;

public interface Repository<T,I> {

    T getInstance(I instanceId);// throws InstanceNotExistException;
    Collection<T> getInstances();

    /**
     * @param t
     * @return
     * @throws InstanceAlreadyExistException
     */
    T addInstance(T t);//throws InstanceAlreadyExistException;

    /**
     * @param t
     * @throws InstanceNotExistException
     */
    void updateInstance(T t);// throws InstanceNotExistException;
    void removeInstance(T t);

}
