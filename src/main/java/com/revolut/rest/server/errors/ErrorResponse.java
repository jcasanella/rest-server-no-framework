package com.revolut.rest.server.errors;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ErrorResponse {
    int code;
    String message;
}
