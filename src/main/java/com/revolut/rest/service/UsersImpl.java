package com.revolut.rest.service;

import com.revolut.rest.model.User;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

// TODO: Control errors
public class UsersImpl implements Operations<User> {
    private Map<String, User> users = new ConcurrentHashMap<>();

    @Override
    public User add(User a) {
        return users.put(a.getId(), a);
    }

    @Override
    public boolean delete(String key) {
        return (users.remove(key) != null);
    }

    @Override
    public User get(String key) {
        return users.get(key);
    }

    @Override
    public List<User> getAll() {
        return users.values()
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public User update(User a) {
        return add(a);
    }
}
