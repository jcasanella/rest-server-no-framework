package com.revolut.test.server.api;

import com.revolut.test.server.errors.ExceptionHandler;
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
        log.info("Call to /api/hello");

        ValidOpers vo = oper -> {
            if ("GET".equals(oper))
                return true;
            else if("POST".equals(oper))
                return true;
            else
                return false;
        };

        if (vo.isValid(exchange.getRequestMethod())) {
            User user = new User("jordi", "casanella", "1 Barr Piece", "London");
            exchange.getResponseHeaders().set(Headers.CONTENT_TYPE, Headers.APPL_JSON);
            exchange.sendResponseHeaders(StatusCode.OK.getCode(), objectMapper.writeValueAsBytes(user).length);
            OutputStream output = exchange.getResponseBody();

            output.write(objectMapper.writeValueAsBytes(user));
            output.flush();
        } else {
            exchange.sendResponseHeaders(StatusCode.BAD_REQUEST.getCode(), -1);
        }

        exchange.close();
    }
}
