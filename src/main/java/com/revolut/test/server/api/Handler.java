package com.revolut.test.server.api;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revolut.test.server.constants.Headers;
import com.revolut.test.server.constants.StatusCode;
import com.revolut.test.server.errors.ExceptionHandler;
import com.sun.net.httpserver.HttpExchange;
import io.vavr.control.Try;
import org.apache.log4j.Logger;

import java.io.OutputStream;
import java.util.function.Function;

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

    // TODO: Create a better exception
    protected <T> T get(HttpExchange exchange, Class<T> valueType) {
        return Try.of(() -> objectMapper.readValue(exchange.getRequestBody(), valueType))
                .getOrElseThrow(() -> new RuntimeException(""));
    }

    protected <T> void set(HttpExchange exchange, T valueType) {
        exchange.getResponseHeaders().set(Headers.CONTENT_TYPE, Headers.APPL_JSON);
        OutputStream output = exchange.getResponseBody();

        Try.of(() -> objectMapper.writeValueAsBytes(valueType))
                .onFailure(exc -> log.error(exc.getMessage()))
                .andThen(x -> Try.run(() -> {
                    exchange.sendResponseHeaders(StatusCode.OK.getCode(), x.length);
                    output.write(x);
                    output.flush();
                }).onFailure(exc2 -> log.error(exc2.getMessage())));
    }
}
