package com.revolut.rest.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.util.UUID;

@AllArgsConstructor
@Data
@Builder
public class User {
    private String id;
    private @NonNull String name;
    private @NonNull String surname;
    private @NonNull String address;
    private @NonNull String city;

//    public void setId() {
//        this.id = UUID.randomUUID().toString();
//    }

    public User() {
//        setId();
    }

    @JsonCreator
    public User(@JsonProperty("name") String name, @JsonProperty("surname") String surname,
                @JsonProperty("address") String address, @JsonProperty("city") String city) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.city = city;
        this.id = name + "_" + surname;
    }

    public static class UserBuilder {
        public UserBuilder id() {
            this.id = UUID.randomUUID().toString();
            return this;
        }
    }
}
