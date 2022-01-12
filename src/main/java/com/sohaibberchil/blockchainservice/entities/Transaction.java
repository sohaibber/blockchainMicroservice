package com.sohaibberchil.blockchainservice.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity @Data @AllArgsConstructor @NoArgsConstructor
public class Transaction {
    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime date;
    private String srcAddress;
    private String destAddress;
    private double amount;

    public Transaction(String srcAddress, String destAddress, double amount) {
        this.id = UUID.randomUUID().toString();
        this.date = LocalDateTime.now();
        this.srcAddress = srcAddress;
        this.destAddress = destAddress;
        this.amount = amount;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
