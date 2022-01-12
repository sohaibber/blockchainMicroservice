package com.sohaibberchil.blockchainservice.repositories;

import com.sohaibberchil.blockchainservice.entities.Block;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRepository extends JpaRepository<Block, String> {
    Block findTopByOrderByCreationDateDesc();
}
