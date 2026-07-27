package com.notificationservice.event;

import com.notificationservice.dto.NotificationChannel;
import com.notificationservice.dto.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequestEvent {

    private Long userId;

    private String email;

    private NotificationType type;

    private NotificationChannel channel;

    private String subject;

    private String message;

    /**
     * Correlation with business transaction
     */
    private Long referenceId;

    /**
     * PAYMENT, WALLET, FRAUD, USER...
     */
    private String referenceType;
}
