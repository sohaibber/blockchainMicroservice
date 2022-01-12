package com.sohaibberchil.blockchainservice.repositories;

import com.sohaibberchil.blockchainservice.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
    @Query(value = "SELECT ISNULL(SELECT SUM(amount) FROM Transaction WHERE dest_address=:adresse, 0)" +
            "- ISNULL(SELECT SUM(amount) FROM Transaction WHERE src_address=:adresse, 0)", nativeQuery = true)
    double calculerSolde(String adresse);
}
