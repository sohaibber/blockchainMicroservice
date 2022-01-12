package com.sohaibberchil.blockchainservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Blockchain {
    @Id
    private String id;
    private String nom;
    private int difficulty;
    private double miningReward;
    @OneToMany
    private List<Block> blocks = new ArrayList<>();
}
