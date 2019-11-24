package com.revolut.rest.service;

import com.revolut.rest.data.DataMemory;
import com.revolut.rest.model.UserPayment;
import io.vavr.control.Try;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class UserPaymentsImpl implements UserPaymentsOper {

    @Override
    public UserPayment add(UserPayment a) {

        Try.run(() -> DataMemory.accounts.get(a.getTrgAccountId()).addQuantity(a.getQuantity()))
                .onFailure(ex -> new RuntimeException());

        Try.run(() -> DataMemory.accounts.get(a.getSrcAccountId()).addQuantity(a.getQuantity().multiply(new BigDecimal(-1))))
                .onFailure(ex -> {
                    Try.run(() -> DataMemory.accounts.get(a.getTrgAccountId()).addQuantity(a.getQuantity().multiply(new BigDecimal(-1))))
                            .onFailure(ex2 -> new RuntimeException());
                    new RuntimeException();
                });

        Try.run(() -> DataMemory.userPayments.add(a))
                .onFailure(ex -> {
                    Try.run(() -> DataMemory.accounts.get(a.getTrgAccountId()).addQuantity(a.getQuantity().multiply(new BigDecimal(-1))))
                            .onFailure(ex2 -> new RuntimeException());

                    Try.run(() -> DataMemory.accounts.get(a.getSrcAccountId()).addQuantity(a.getQuantity()))
                            .onFailure(ex3 -> new RuntimeException());
                });

        return a;

//        Account src = DataMemory.accounts.get(a.getSrcAccountId());
//        Account trg;
//        if (src != null && src.canOperate(a.getQuantity())) {
//            trg = DataMemory.accounts.get(a.getTrgAccountId());
//            if (trg != null) {
//                try {
//                    DataMemory.accounts.get(a.getTrgAccountId()).addQuantity(a.getQuantity());
//                    DataMemory.accounts.get(a.getSrcAccountId()).addQuantity(a.getQuantity().multiply(new BigDecimal(-1)));
//                    DataMemory.userPayments.add(a);
//
//                    return a;
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        return null;
    }

    @Override
    public UserPayment get(String key) {
        return DataMemory.userPayments.stream()
                .filter(x -> x.getId().equals(key))
                .findFirst()
                .get();
    }

    @Override
    public List<UserPayment> getAll() {
        return DataMemory.userPayments.stream().collect(Collectors.toList());
    }
}
