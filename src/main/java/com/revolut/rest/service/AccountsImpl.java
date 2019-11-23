package com.revolut.rest.service;

import com.revolut.rest.data.DataMemory;
import com.revolut.rest.model.Account;
import com.revolut.rest.model.User;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class AccountsImpl implements DataOper<Account> {

    // TODO improve errors
    @Override
    public Account add(Account a) {
        User user = DataMemory.users.get(a.getUserId());
        if (user == null)
            return null;
        else
            return DataMemory.accounts.put(a.getId(), a);
    }

    @Override
    public boolean delete(String key) {
        return false;
    }

    @Override
    public Account get(String key) {
        return null;
    }

    @Override
    public Account update(Account a) {
        return null;
    }

    @Override
    public List<Account> getAll() {
        return DataMemory.accounts.values()
                .stream()
                .collect(Collectors.toList());
    }
}
