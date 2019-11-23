package com.revolut.test.service;

import com.revolut.test.model.User;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class UsersImpl implements Operations<User> {
    private Map<String, User> users = new ConcurrentHashMap<>();

    @Override
    public boolean add(User a) {
        if (users.get(a.getId()) != null)
            return false;
        else {
            users.put(a.getId(), a);
            return true;
        }
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
}
