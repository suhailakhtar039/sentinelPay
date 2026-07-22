package com.paymentservice.repository;

import com.paymentservice.entity.Payment;
import com.paymentservice.projection.analytics.AverageAmountProjection;
import com.paymentservice.projection.analytics.DailyTransactionProjection;
import com.paymentservice.projection.analytics.MonthlyVolumeProjection;
import com.paymentservice.projection.analytics.OverviewAnalyticsProjection;
import com.paymentservice.projection.analytics.PaymentStatusProjection;
import com.paymentservice.projection.analytics.TopReceiverProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

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
                SUM(CASE WHEN status = 'COMPLETED' THEN 1 ELSE 0 END) AS successfulPayments,
                SUM(CASE WHEN status IN ('FAILED','FRAUD_REJECTED') THEN 1 ELSE 0 END) AS failedPayments,
                SUM(CASE WHEN status = 'PENDING' THEN 1 ELSE 0 END) AS pendingPayments,
                COALESCE(
                          SUM(
                              CASE
                                  WHEN status = 'COMPLETED'
                                  THEN amount
                                  ELSE 0
                              END
                          ),
                          0
                      ) AS totalVolume,
                COALESCE(AVG(CASE WHEN status='COMPLETED' THEN amount END),0) AS averageTransactionAmount
            FROM payments
            WHERE sender_user_id = :userId
            """, nativeQuery = true)
    OverviewAnalyticsProjection getOverviewAnalytics(
            @Param("userId") Long userId
    );


    @Query(value = """
            SELECT
                status AS status,
                COUNT(*) AS count
            FROM payments
            WHERE sender_user_id = :userId
            GROUP BY status
            ORDER BY count DESC
            """, nativeQuery = true)
    List<PaymentStatusProjection> getPaymentStatusDistribution(
            @Param("userId") Long userId
    );


    @Query(value = """
            SELECT
                COALESCE(
                    AVG(
                        CASE
                            WHEN status='COMPLETED'
                            THEN amount
                        END
                    ),
                    0
                ) AS averageAmount
            FROM payments
            WHERE sender_user_id = :userId
            """, nativeQuery = true)
    AverageAmountProjection getAverageTransaction(
            @Param("userId") Long userId
    );


    @Query(value = """
            SELECT
                receiver_user_id AS receiverId,
                SUM(amount) AS totalReceived,
                COUNT(*) AS transactionCount
            FROM payments
            WHERE sender_user_id = :userId
              AND status='COMPLETED'
            GROUP BY receiver_user_id
            ORDER BY totalReceived DESC
            LIMIT 5
            """, nativeQuery = true)
    List<TopReceiverProjection> getTopReceivers(
            @Param("userId") Long userId
    );


    @Query(value = """
            SELECT
                DATE(created_at) AS date,
                COUNT(*) AS transactionCount
            FROM payments
            WHERE sender_user_id = :userId
            GROUP BY DATE(created_at)
            ORDER BY DATE(created_at)
            """, nativeQuery = true)
    List<DailyTransactionProjection> getDailyTransaction(
            @Param("userId") Long userId
    );


    @Query(value = """
            SELECT
                YEAR(created_at) AS year,
                MONTH(created_at) AS month,
                SUM(amount) AS totalVolume
            FROM payments
            WHERE sender_user_id = :userId
              AND status='COMPLETED'
            GROUP BY YEAR(created_at), MONTH(created_at)
            ORDER BY YEAR(created_at), MONTH(created_at)
            """, nativeQuery = true)
    List<MonthlyVolumeProjection> getMonthlyTransaction(
            @Param("userId") Long userId
    );

}