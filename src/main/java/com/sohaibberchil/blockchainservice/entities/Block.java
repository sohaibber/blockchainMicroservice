package com.sohaibberchil.blockchainservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Block {
    @Id
    private String id;
    private LocalDateTime creationDate;
    private String hash;
    private String prevHash;
    @OneToMany
    private List<Transaction> transactions = new ArrayList<>();
    private int nonce;

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
