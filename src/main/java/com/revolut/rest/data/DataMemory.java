package com.revolut.rest.data;

import com.revolut.rest.model.Account;
import com.revolut.rest.model.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DataMemory {

    public static Map<String, Account> accounts = new ConcurrentHashMap<>();
    public static Map<String, User> users = new ConcurrentHashMap<>();
//    private static DataMemory instance = null;

//    private DataMemory() {
//
//    }
//
//    public static DataMemory getInstance() {
//        if (instance == null) {
//            synchronized (DataMemory.class) {
//                if (instance == null) {
//                    instance = new DataMemory();
//                }
//            }
//        }
//
//        return instance;
//    }
//
//    public  Map<String, Account> getAccountsMemory() {
//        return accounts;
//    }
//
//    public Map<String, User> getUsersMemory() {
//        return users;
//    }
}
