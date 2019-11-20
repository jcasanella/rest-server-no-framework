package com.revolut.test.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@RequiredArgsConstructor
public class User {
    private @NonNull String name;
    private @NonNull String surname;
    private @NonNull String address;
    private @NonNull String city;
}
