package com.gaming.wallet.axon.repository;

import com.gaming.wallet.axon.entity.Transaction;
import com.gaming.wallet.axon.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(propagation = Propagation.REQUIRES_NEW)
public interface TransactionRepository extends JpaRepository<Transaction, String> {
}
