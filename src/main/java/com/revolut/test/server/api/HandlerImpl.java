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
    private static final String uriResources = "/" + NameResources.VERSION + "/" + NameResources.USERS;

    @Override
    protected void execute(HttpExchange exchange) throws Exception {
        log.info("Call to " + uriResources);

        if ("GET".equals(exchange.getRequestMethod())) {
            log.info("Processing GET " + exchange.getRequestURI().toString());
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
        } else if ("DELETE".equals(exchange.getRequestMethod())) {
            log.info("Processing DELETE " + exchange.getRequestURI().toString());
            List<String> args = getParam(exchange.getRequestURI().toString());
            boolean res = false;
            if (!args.isEmpty())
                res = ui.delete(args.get(0));

            set(exchange, res);
        } else {
            throw new Exception("Bad Request");
        }

        exchange.close();
    }
}
