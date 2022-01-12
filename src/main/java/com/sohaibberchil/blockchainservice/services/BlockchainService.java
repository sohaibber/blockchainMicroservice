package com.sohaibberchil.blockchainservice.services;

import com.sohaibberchil.blockchainservice.entities.Blockchain;
import com.sohaibberchil.blockchainservice.entities.Block;
import com.sohaibberchil.blockchainservice.entities.Transaction;

public interface BlockchainService {
    Blockchain newChain(int difficulty, int miningReward);
    Block addBlock(Blockchain chain, String miner);
    Transaction addTransaction(Transaction transaction);
    Block lastBlock();
    boolean isChainValid(Blockchain chain);
    double calculateBalance(String address);
}
