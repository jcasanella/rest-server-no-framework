package com.revolut.test.service;

import com.revolut.test.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;

public class UsersImpl implements Operations<User> {
    private ConcurrentLinkedQueue<User> users = new ConcurrentLinkedQueue<>();

    @Override
    public boolean add(User a) {
        if (users.contains(a)) {
            return false;
        } else {
            return users.add(a);
        }
    }

    @Override
    public boolean delete(String key) {
        Optional<User> optUser = users.stream()
                .filter(x -> x.getName().equals(key))
                .findFirst();

        if (optUser.isPresent()) {
            return users.remove(optUser.get());
        }

        return false;
    }

    @Override
    public User get(String key) {
        Optional<User> optUser = users.stream()
                .filter(x -> x.getName().equals(key))
                .findFirst();

        if (optUser.isPresent())
            return optUser.get();
        else
            return null;
    }

    @Override
    public List<User> getAll() {
        return Arrays.asList(
                Arrays.copyOf(users.toArray(), users.size(), User[].class)
        );
    }
}
