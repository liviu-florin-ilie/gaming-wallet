package com.gaming.wallet.repository;

import com.gaming.wallet.entity.Transaction;
import com.gaming.wallet.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
}
