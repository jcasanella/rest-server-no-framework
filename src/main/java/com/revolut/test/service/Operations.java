package com.revolut.test.service;

import java.util.List;

public interface Operations<T> {
    T add(T a);
    boolean delete(String key);
    T get(String key);
    T update(T a);
    List<T> getAll();
}
