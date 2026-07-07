package com.paymentservice.repository;

import com.paymentservice.entity.Payment;
import com.paymentservice.projection.analytics.OverviewAnalyticsProjection;
import com.paymentservice.projection.analytics.PaymentStatusProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findById(Long paymentId);

    List<Payment> findBySenderUserIdOrReceiverUserId(
            Long senderUserId,
            Long receiverUserId
    );

    @Query(value = """
            SELECT
                COUNT(*) AS totalPayments,
                SUM(CASE WHEN status = 'COMPLETED' THEN 1 ELSE 0 END ) AS successfulPayments,
                SUM(CASE WHEN status IN ('FAILED', 'FRAUD REJECTED') THEN 1 ELSE 0 END) AS failedPayments,
                SUM(CASE WHEN status='PENDING' THEN 1 ELSE 0 END) AS pendingPayments,
                COALESCE(SUM(amount), 0) AS totalVolume,
                COALESCE(AVG(amount), 0) AS averageTransactionAmount
            FROM payments
            """, nativeQuery = true)
    OverviewAnalyticsProjection getOverviewAnalytics();

    @Query(value = """
            SELECT 
                status AS status,
                count(*) AS count
                FROM payments
                        GROUP BY status
                        ORDER BY count DESC
            """, nativeQuery = true)
    List<PaymentStatusProjection> getPaymentStatusDistribution();

}
