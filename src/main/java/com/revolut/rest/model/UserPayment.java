package com.revolut.rest.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.revolut.rest.model.SerDe.LocalDateDeserializer;
import com.revolut.rest.model.SerDe.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@Data
public class UserPayment {
    private String id;
    private @NonNull  String srcAccountId;
    private @NonNull BigDecimal quantity;
    private @NonNull String trgAccountId;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate datePayment;

    public UserPayment() {

    }

    @JsonCreator
    public UserPayment(@JsonProperty("srcAccountId") String srcAccountId, @JsonProperty("quantity") BigDecimal quantity,
                       @JsonProperty("trgAccountId") String trgAccountId) {
        this.id = UUID.randomUUID().toString();
        this.srcAccountId = srcAccountId;
        this.quantity = quantity;
        this.trgAccountId = trgAccountId;
        this.datePayment = LocalDate.now();
    }
}
