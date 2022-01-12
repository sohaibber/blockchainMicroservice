package com.sohaibberchil.blockchainservice.services;

import com.sohaibberchil.blockchainservice.entities.Blockchain;
import com.sohaibberchil.blockchainservice.entities.Block;
import com.sohaibberchil.blockchainservice.entities.Transaction;
import com.sohaibberchil.blockchainservice.repositories.BlockRepository;
import com.sohaibberchil.blockchainservice.repositories.BlockchainRepository;
import com.sohaibberchil.blockchainservice.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class BlockchainServiceImpl implements BlockchainService {
    private BlockService blockService;
    private BlockRepository blockRepository;
    private BlockchainRepository blockchainRepository;
    private TransactionRepository transactionRepository;

    public BlockchainServiceImpl(BlockService blockService, BlockRepository blockRepository,
                                 BlockchainRepository blockchainRepository, TransactionRepository transactionRepository) {
        this.blockService = blockService;
        this.blockRepository = blockRepository;
        this.blockchainRepository = blockchainRepository;
        this.transactionRepository = transactionRepository;
    }

    private final List<Transaction> pendingTransactions = new ArrayList<>();

    @Override
    public Blockchain newChain(int difficulty, int miningReward) {
        Blockchain chain = new Blockchain();
        chain.setId(UUID.randomUUID().toString());
        chain.setNom("Blockchain");
        chain.setDifficulty(difficulty);
        chain.setMiningReward(miningReward);
        List<Block> blocks = new ArrayList<>();
        //ajouter le GenesisBlock
        Block genesisBlock = blockService.newBlock("0");
        blocks.add(genesisBlock);

        chain.setBlocks(blocks);
        return blockchainRepository.save(chain);
    }

    @Override
    public Block addBlock(Blockchain chain, String miner) {
        // Ajouter une transaction bonus à l'adresse du mineur
        Transaction bonus = new Transaction("0", miner, chain.getMiningReward());
        pendingTransactions.add(bonus);
        transactionRepository.save(bonus);

        Block lastBlock = lastBlock();
        String previousHash = lastBlock.getHash();
        Block newBlock = blockService.newBlock(previousHash);
        newBlock.setTransactions(new ArrayList<>(pendingTransactions));
        blockService.mineBlock(newBlock, chain.getDifficulty());
        pendingTransactions.clear();
        chain.getBlocks().add(newBlock);
        blockRepository.save(newBlock);
        blockchainRepository.save(chain);

        return newBlock;
    }

    @Override
    public Transaction addTransaction(Transaction transaction) {
        pendingTransactions.add(transaction);
        return transaction;
    }

    @Override
    public Block lastBlock() {
        return blockRepository.findTopByOrderByCreationDateDesc();
    }

    @Override
    public boolean isChainValid(Blockchain chain) {
        boolean isValid = true;
        List<Block> blocks = chain.getBlocks();
        for (int i = 0; i < blocks.size(); i++) {
            String previousHash = i == 0 ? "0" : blocks.get(i - 1).getHash();
            isValid = blocks.get(i).getHash().equals(blockService.calculateHash(blocks.get(i)))
                    && previousHash.equals(blocks.get(i).getPrevHash());
            if (!isValid)
                break;
        }
        return isValid;
    }

    @Override
    public double calculateBalance(String address) {
        return transactionRepository.calculerSolde(address);
    }
}
