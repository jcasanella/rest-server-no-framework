package com.revolut.test.server.api;

import com.revolut.test.server.constants.NameResources;
import com.revolut.test.server.errors.ExceptionHandler;
import com.revolut.test.service.Operations;
import com.revolut.test.service.UsersImpl;
import com.sun.net.httpserver.HttpExchange;
import com.revolut.test.server.constants.StatusCode;
import com.revolut.test.model.User;
import com.revolut.test.server.constants.Headers;
import java.io.OutputStream;

public class HandlerImpl extends Handler {

    public HandlerImpl(ExceptionHandler exceptionHandler) {
        super(exceptionHandler);
    }

    @Override
    protected void execute(HttpExchange exchange) throws Exception {
        log.info(String.format("Call to /%s/%s", NameResources.VERSION, NameResources.USERS));

        if ("GET".equals(exchange.getRequestMethod())) {
            log.info(String.format("Processing GET call to /%s/%s", NameResources.VERSION, NameResources.USERS));
            User user = new User("jordi", "casanella", "1 Barr Piece", "London");
            set(exchange, user);
        } else if ("POST".equals(exchange.getRequestMethod())) {
            log.info(String.format("Processing POST call to /%s/%s", NameResources.VERSION, NameResources.USERS));
            User user = get(exchange, User.class);
            Operations ui = new UsersImpl();
            boolean res = ui.add(user);
            set(exchange, res);
        } else {
            throw new Exception("Bad Request");
        }

        exchange.close();
    }
}
