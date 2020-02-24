package com.ra.course.com.stackoverflow.repository;

import com.ra.course.com.stackoverflow.exception.repository.RepositoryException;

public interface GeneralRepository<T> {
    T save(final T t) throws RepositoryException;
    long getNextId(final T t) throws RepositoryException;
}
