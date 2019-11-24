package com.revolut.rest.data;

import com.revolut.rest.model.Account;
import com.revolut.rest.model.User;
import com.revolut.rest.model.UserPayment;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class DataMemory {

    public static Map<String, Account> accounts = new ConcurrentHashMap<>();
    public static Map<String, User> users = new ConcurrentHashMap<>();
    public static ConcurrentLinkedQueue<UserPayment> userPayments = new ConcurrentLinkedQueue<>();
}
