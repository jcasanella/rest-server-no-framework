package com.revolut.rest.server.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revolut.rest.server.constants.Headers;
import com.revolut.rest.server.constants.NameResources;
import com.revolut.rest.server.constants.StatusCode;
import com.revolut.rest.server.errors.ExceptionHandler;
import com.revolut.rest.service.DataOper;
import com.sun.net.httpserver.HttpExchange;
import io.vavr.control.Try;
import org.apache.log4j.Logger;

import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static io.vavr.Predicates.not;

public abstract class Handler {
    protected static Logger log = Logger.getLogger(Handler.class);
    protected static ObjectMapper objectMapper = new ObjectMapper();
    protected static DataOper ui = null;
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

    protected List<String> getParam(String args, String resource) {
        String urlResource = "/" + NameResources.VERSION + "/" + resource;
        String urlResource2 = "/" + NameResources.VERSION + "/" + resource + "/";

        if (args == null || "".equals(args) || urlResource.equals(args) || urlResource2.equals(args))
            return Collections.EMPTY_LIST;

        return Arrays.stream(args.split("/"))
                .skip(1)
                .filter(not(x -> NameResources.ACCOUNTS.equals(x) || NameResources.VERSION.equals(x)))
                .collect(Collectors.toList());
    }
}
