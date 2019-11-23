package com.revolut.test.server.api;

import com.revolut.test.server.constants.NameResources;
import com.revolut.test.server.errors.ExceptionHandler;
import com.revolut.test.service.Operations;
import com.revolut.test.service.UsersImpl;
import com.sun.net.httpserver.HttpExchange;
import com.revolut.test.model.User;
import java.util.List;

public class HandlerImpl extends Handler {

    public HandlerImpl(ExceptionHandler exceptionHandler) {
        super(exceptionHandler);
    }

    private static Operations ui = new UsersImpl();

    @Override
    protected void execute(HttpExchange exchange) throws Exception {
        log.info(String.format("Call to /%s/%s", NameResources.VERSION, NameResources.USERS));

        if ("GET".equals(exchange.getRequestMethod())) {
            log.info(String.format("Processing GET call to /%s/%s", NameResources.VERSION, NameResources.USERS));
            List<String> args = getParam(exchange.getRequestURI().toString());
            if (args.isEmpty()) {
                List<User> users = ui.getAll();
                set(exchange, users);
            } else {
                User user = (User)ui.get(args.get(0));
                set(exchange, user);
            }
        } else if ("POST".equals(exchange.getRequestMethod())) {
            log.info(String.format("Processing POST call to /%s/%s", NameResources.VERSION, NameResources.USERS));
            User user = get(exchange, User.class);
            String key = ui.add(user);
            set(exchange, key);
        } else {
            throw new Exception("Bad Request");
        }

        exchange.close();
    }
}
