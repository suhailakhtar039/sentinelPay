package com.ledgerservice.repository;

import com.ledgerservice.entity.LedgerEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LedgerEntryRepository extends JpaRepository<LedgerEntry, Long> {
    List<LedgerEntry> findBySenderUserIdOrReceiverUserId(
            Long senderUserId,
            Long receiverUserId
    );
}
