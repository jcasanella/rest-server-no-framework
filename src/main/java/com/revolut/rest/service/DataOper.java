package com.revolut.rest.service;

import java.util.List;

public interface DataOper<T> {
    T add(T a);
    boolean delete(String key);
    T get(String key);
    T update(T a);
    List<T> getAll();
}
