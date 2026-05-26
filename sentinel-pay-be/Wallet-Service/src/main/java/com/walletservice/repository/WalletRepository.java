package com.walletservice.repository;

import com.walletservice.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByUserId(Long userId);

    @Query("""
SELECT CASE
       WHEN COUNT(w) > 0 THEN true
       ELSE false
       END
FROM Wallet w
WHERE w.userId = :userId
""")
    boolean existsByUserId(Long userId);

}
