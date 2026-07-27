package com.notificationservice.entity;

import com.notificationservice.dto.NotificationChannel;
import com.notificationservice.dto.NotificationStatus;
import com.notificationservice.dto.NotificationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Lob;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(
        name = "notifications",
        indexes = {
                @Index(name = "idx_notification_user", columnList = "user_id"),
                @Index(name = "idx_notification_status", columnList = "status"),
                @Index(name = "idx_notification_read", columnList = "is_read"),
                @Index(name = "idx_notification_created", columnList = "created_at")
        }
)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Recipient user.
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**
     * Email / SMS / Push / In-App
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private NotificationChannel channel;

    /**
     * PAYMENT_COMPLETED, PAYMENT_FAILED, etc.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private NotificationType type;

    /**
     * Subject of notification.
     */
    @Column(nullable = false, length = 255)
    private String subject;

    /**
     * Notification body.
     */
    @Lob
    @Column(nullable = false)
    private String message;

    /**
     * Delivery status.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private NotificationStatus status;

    /**
     * Read by user?
     */
    @Column(name = "is_read", nullable = false)
    @Builder.Default
    private Boolean read = false;

    /**
     * SMTP / SendGrid / SES / Twilio etc.
     */
    @Column(length = 100)
    private String provider;

    /**
     * Provider response/message id.
     */
    @Column(name = "provider_reference", length = 255)
    private String providerReference;

    /**
     * When notification was created.
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * When notification was successfully sent.
     */
    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    /**
     * Last update time.
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        if (status == null) {
            status = NotificationStatus.PENDING;
        }

        if (read == null) {
            read = false;
        }
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
