package com.haulmont.testtask.DAO;

import java.util.List;

public interface DoctorDaoInterface<T> {
    public void persist(T entity);

    public void update(T entity);

    public T findById(Long id);

    public void delete(T entity);

    public List<T> findAll();

    public void deleteAll();
}
