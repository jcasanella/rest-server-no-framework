package com.revolut.test.server.api;

import com.revolut.test.server.constants.NameResources;
import com.revolut.test.server.errors.ExceptionHandler;
import com.sun.net.httpserver.HttpExchange;
import com.revolut.test.server.constants.StatusCode;
import com.revolut.test.model.User;
import com.revolut.test.server.constants.Headers;
import java.io.OutputStream;
import java.net.URLDecoder;

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
            exchange.getResponseHeaders().set(Headers.CONTENT_TYPE, Headers.APPL_JSON);
            exchange.sendResponseHeaders(StatusCode.OK.getCode(), objectMapper.writeValueAsBytes(user).length);
            OutputStream output = exchange.getResponseBody();

            output.write(objectMapper.writeValueAsBytes(user));
            output.flush();
        } else if ("POST".equals(exchange.getRequestMethod())) {
            log.info(String.format("Processing POST call to /%s/%s", NameResources.VERSION, NameResources.USERS));
            String valor = exchange.getRequestBody().toString();
            String valor2 = URLDecoder.decode(valor, "UTF-8");
            User user = objectMapper.readValue(exchange.getRequestBody(), User.class);
            log.info(user.toString());
        } else {
            throw new Exception("Bad Request");
        }

        exchange.close();
    }
}
