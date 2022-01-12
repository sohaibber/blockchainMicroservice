package com.sohaibberchil.blockchainservice.services;

import com.sohaibberchil.blockchainservice.entities.Block;

public interface BlockService {
    Block newBlock(String prevHash);
    String calculateHash(Block block);
    void mineBlock(Block block, int difficulty);
}
