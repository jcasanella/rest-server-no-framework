package com.revolut.test.service;

import java.util.List;

public interface Operations<T> {
    boolean add(T a);
    boolean delete(String key);
    T get(String key);
    List<T> getAll();
}
