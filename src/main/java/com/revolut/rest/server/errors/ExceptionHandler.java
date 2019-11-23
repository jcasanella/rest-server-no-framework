package com.revolut.rest.server.errors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revolut.rest.server.constants.Headers;
import com.revolut.rest.server.constants.StatusCode;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class ExceptionHandler {

    private ObjectMapper objectMapper = new ObjectMapper();

    public void handle(Throwable throwable, HttpExchange exchange) {
        try {
            throwable.printStackTrace();
            exchange.getResponseHeaders().set(Headers.CONTENT_TYPE, Headers.APPL_JSON);
            ErrorResponse response = getErrorResponse(throwable, exchange);
            OutputStream responseBody = exchange.getResponseBody();
            responseBody.write(objectMapper.writeValueAsBytes(response));
            responseBody.close();
            exchange.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ErrorResponse getErrorResponse(Throwable throwable, HttpExchange exchange) throws IOException {
        ErrorResponse.ErrorResponseBuilder responseBuilder = ErrorResponse.builder();
        responseBuilder.message(throwable.getMessage()).code(StatusCode.BAD_REQUEST.getCode());
        exchange.sendResponseHeaders(StatusCode.BAD_REQUEST.getCode(), 0);

        return responseBuilder.build();
    }
}
