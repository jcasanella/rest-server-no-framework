package com.revolut.rest.server.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusCode {
    OK(200),

    BAD_REQUEST(400),
    WRONG_METHOD(405);

    private int code;
}
