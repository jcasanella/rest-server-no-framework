package com.revolut.test.model;

import lombok.*;

@NoArgsConstructor @RequiredArgsConstructor
@Getter @Setter
public class User {
    private @NonNull String name;
    private @NonNull String surname;
    private @NonNull String address;
    private @NonNull String city;
}
