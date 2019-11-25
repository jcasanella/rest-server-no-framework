package com.revolut.rest.server.api;

import com.revolut.rest.model.Account;
import com.revolut.rest.model.UserPayment;
import com.revolut.rest.server.constants.NameResources;
import com.revolut.rest.server.errors.ExceptionHandler;
import com.revolut.rest.service.UserPaymentsOper;
import com.sun.net.httpserver.HttpExchange;

import java.util.List;

public class HandlerUserPaymentsImpl extends Handler {

    private static UserPaymentsOper ui;
    private static final String uriResources = "/" + NameResources.VERSION + "/" + NameResources.USERS_PAYMENTS;

    public HandlerUserPaymentsImpl(ExceptionHandler exceptionHandler) {
        super(exceptionHandler);
        ui = new UserPaymentsOper();
    }

    @Override
    protected void execute(HttpExchange exchange) throws Exception {
        log.info("Call to " + uriResources);

        if ("GET".equals(exchange.getRequestMethod())) {
            log.info("Processing GET " + exchange.getRequestURI().toString());
            List<String> args = getParam(exchange.getRequestURI().toString(), NameResources.USERS_PAYMENTS);
            if (args.isEmpty()) {   // Get all
                List<UserPayment> users = ui.getAll();
                set(exchange, users);
            } else if (args.size() == 2 && args.get(0).equals(NameResources.USERS_PAYMENTS_SRC)) {
                List<UserPayment> users = ui.getByUserSrc(args.get(1));
                set(exchange, users);
            } else if (args.size() == 2 && args.get(0).equals(NameResources.USERS_PAYMENT_TRG)) {
                List<UserPayment> users = ui.getByUserTrg(args.get(1));
                set(exchange, users);
            } else if (args.size() == 1) {
                UserPayment user = ui.get(args.get(0));
                set(exchange, user);
            } else {
                throw new Exception("Bad arguments");
            }
        } else if ("POST".equals(exchange.getRequestMethod())) {
            log.info("Processing POST " + exchange.getRequestURI().toString());
            UserPayment userPayment = get(exchange, UserPayment.class);
            ui.add(userPayment);
            set(exchange, userPayment);
        } else {
            throw new Exception("Bad Request");
        }

        exchange.close();
    }
}
