package com.ra.course.com.stackoverflow.repository;

import com.ra.course.com.stackoverflow.exception.repository.RepositoryException;

public interface GeneralRepository<T> {
    T save(T t) throws RepositoryException;
}
