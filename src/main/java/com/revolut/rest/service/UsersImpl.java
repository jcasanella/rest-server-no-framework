package com.revolut.rest.service;

import com.revolut.rest.data.DataMemory;
import com.revolut.rest.model.User;
import java.util.List;
import java.util.stream.Collectors;

// TODO: Control errors
public class UsersImpl implements DataOper<User> {

    @Override
    public User add(User a) {
        return DataMemory.users.put(a.getId(), a);
    }

    @Override
    public boolean delete(String key) {
        return (DataMemory.users.remove(key) != null);
    }

    @Override
    public User get(String key) {
        return DataMemory.users.get(key);
    }

    @Override
    public List<User> getAll() {
        return DataMemory.users.values()
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public User update(User a) {
        return add(a);
    }
}
