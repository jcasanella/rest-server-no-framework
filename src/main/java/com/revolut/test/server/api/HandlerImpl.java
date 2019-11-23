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
import java.util.Map;

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
            Map<String, String> params = getParams(exchange.getRequestURI().getRawQuery());
            User user = (User)ui.get(params.get("id"));
            set(exchange, user);
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
