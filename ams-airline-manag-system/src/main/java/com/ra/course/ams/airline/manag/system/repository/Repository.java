package com.ra.course.ams.airline.manag.system.repository;

import com.ra.course.ams.airline.manag.system.exceptions.InstanceAlreadyExistException;
import com.ra.course.ams.airline.manag.system.exceptions.InstanceNotExistException;

import java.util.Collection;
import java.util.List;

public interface Repository<T,I> {

    public T getInstance(I instanceId);
    public Collection<T> getInstances();
    public T addInstance(T t) throws InstanceAlreadyExistException;
    public void updateInstance(T t) throws InstanceNotExistException;
    public void remoteInstance(T t);

}
