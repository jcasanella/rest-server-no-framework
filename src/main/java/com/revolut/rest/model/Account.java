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
public class Account {
    private String id;
    private @NonNull String userId;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dateCreation;
    private @NonNull BigDecimal balance;

    public Account() {

    }

    @JsonCreator
    public Account(@JsonProperty("userId") String userId, @JsonProperty("balance") BigDecimal balance) {
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.dateCreation = LocalDate.now();
        this.balance = balance;
    }

    public void addQuantity(BigDecimal quantity) throws Exception {
        BigDecimal res = this.balance.add(quantity);
        if (res.compareTo(BigDecimal.ZERO) < 0)
            throw new Exception("Not enough quantity to make this operation");

        this.balance = res;
    }

    public boolean canOperate(BigDecimal quantity) {
        BigDecimal res = this.balance.add(quantity);
        return (res.compareTo(BigDecimal.ZERO) < 0);
    }
}
