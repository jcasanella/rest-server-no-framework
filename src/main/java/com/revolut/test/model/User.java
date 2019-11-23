package com.revolut.test.model;

import lombok.*;
import java.util.UUID;

@AllArgsConstructor
@Data
@Builder
public class User {
    private String id;
    private String name;
    private String surname;
    private String address;
    private String city;

    public void setId() {
        this.id = UUID.randomUUID().toString();
    }

    public User() {
        setId();
    }

    public static class UserBuilder {
        public UserBuilder id() {
            this.id = UUID.randomUUID().toString();
            return this;
        }
    }
}
