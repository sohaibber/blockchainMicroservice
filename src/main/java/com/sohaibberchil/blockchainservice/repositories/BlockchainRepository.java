package com.sohaibberchil.blockchainservice.repositories;

import com.sohaibberchil.blockchainservice.entities.Blockchain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockchainRepository extends JpaRepository<Blockchain, String> {
}
