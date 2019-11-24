package com.revolut.rest.service;

import com.revolut.rest.model.UserPayment;

import java.util.List;

public interface UserPaymentsOper {
    UserPayment add(UserPayment a);
    UserPayment get(String key);
    List<UserPayment> getAll();
}
