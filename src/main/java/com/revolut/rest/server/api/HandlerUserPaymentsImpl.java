package com.revolut.rest.server.api;

import com.revolut.rest.model.Account;
import com.revolut.rest.model.UserPayment;
import com.revolut.rest.server.constants.NameResources;
import com.revolut.rest.server.errors.ExceptionHandler;
import com.revolut.rest.service.UserPaymentsImpl;
import com.revolut.rest.service.UserPaymentsOper;
import com.sun.net.httpserver.HttpExchange;

import java.util.List;

public class HandlerUserPaymentsImpl extends Handler {

    private static UserPaymentsOper ui;
    private static final String uriResources = "/" + NameResources.VERSION + "/" + NameResources.USERS_PAYMENTS;

    public HandlerUserPaymentsImpl(ExceptionHandler exceptionHandler) {
        super(exceptionHandler);
        ui = new UserPaymentsImpl();
    }

    @Override
    protected void execute(HttpExchange exchange) throws Exception {
        log.info("Call to " + uriResources);

        if ("GET".equals(exchange.getRequestMethod())) {
            log.info("Processing GET " + exchange.getRequestURI().toString());
            List<String> args = getParam(exchange.getRequestURI().toString(), NameResources.USERS_PAYMENTS);
            if (args.isEmpty()) {
                List<UserPayment> users = ui.getAll();
                set(exchange, users);
            } else {
                UserPayment user = ui.get(args.get(0));
                set(exchange, user);
            }
        } else if ("POST".equals(exchange.getRequestMethod())) {
            log.info("Processing POST " + exchange.getRequestURI().toString());
            UserPayment userPayment = get(exchange, UserPayment.class);
            UserPayment added = ui.add(userPayment);
            set(exchange, userPayment);
        } else {
            throw new Exception("Bad Request");
        }

        exchange.close();
    }
}
