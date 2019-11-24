package com.revolut.rest.service;

import com.revolut.rest.data.DataMemory;
import com.revolut.rest.model.Account;
import com.revolut.rest.model.User;
import java.util.List;
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
        return (DataMemory.accounts.remove(key) != null);
    }

    @Override
    public Account get(String key) {
        return DataMemory.accounts.get(key);
    }

    @Override
    public Account update(Account a) {
        Account toBeUpdated = DataMemory.accounts.get(a.getId());

        try {
            toBeUpdated.addQuantity(a.getBalance());
            return DataMemory.accounts.put(toBeUpdated.getId(), toBeUpdated);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<Account> getAll() {
        return DataMemory.accounts.values()
                .stream()
                .collect(Collectors.toList());
    }
}
