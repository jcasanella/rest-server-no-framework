package com.revolut.test.server.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revolut.test.server.errors.ExceptionHandler;
import com.sun.net.httpserver.HttpExchange;
import io.vavr.control.Try;
import org.apache.log4j.Logger;

public abstract class Handler {
    protected static Logger log = Logger.getLogger(Handler.class);
    protected static ObjectMapper objectMapper = new ObjectMapper();
    private final ExceptionHandler exceptionHandler;

    public Handler(ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    public void handle(HttpExchange exchange) {
        Try.run(() -> execute(exchange))
                .onFailure(thr -> exceptionHandler.handle(thr, exchange));
    }

    protected abstract void execute(HttpExchange exchange) throws Exception;
}
