package com.sohaibberchil.blockchainservice.services;

import com.sohaibberchil.blockchainservice.entities.Block;
import com.sohaibberchil.blockchainservice.entities.Transaction;
import com.sohaibberchil.blockchainservice.repositories.BlockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class BlockServiceImpl implements BlockService {

    private BlockRepository blockRepository;

    Logger logger = LoggerFactory.getLogger(BlockServiceImpl.class);

    public BlockServiceImpl(BlockRepository blockRepository) {
        this.blockRepository = blockRepository;
    }

    @Override
    public Block newBlock(String prevHash) {
        Block block = new Block();
        block.setId(UUID.randomUUID().toString());
        block.setCreationDate(LocalDateTime.now());
        block.setPrevHash(prevHash);
        block.setHash(calculateHash(block));
        return blockRepository.save(block);
    }

    @Override
    public String calculateHash(Block block) {
        // calculate hashCode of all transactions
        int hc = 1;
        for (Transaction t : block.getTransactions())
            hc = 31*hc + (t==null ? 0 : t.hashCode());

        String blockHash = block.getPrevHash() + block.getCreationDate()
                + block.getNonce() + hc;
        MessageDigest digest;
        byte[] bytes = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            bytes = digest.digest(blockHash.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }

        return builder.toString();
    }

    @Override
    public void mineBlock(Block block, int difficulty) {
        // creer string avec "0" * difficulty
        String prefix = new String(new char[difficulty]).replace('\0', '0');
        while (!block.getHash().substring(0, difficulty).equals(prefix)) {
            // incrementer la valeur de nonce jusqu'à le hash cible est atteint
            block.setNonce(block.getNonce()+1);
            block.setHash(calculateHash(block));
        }
        blockRepository.save(block);
        logger.info("Block mined : "+block.getHash());
    }
}
