package com.sentinelpay.common.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCompletedEvent {

    private Long paymentId;

    private Long senderUserId;

    private Long receiverUserId;

}
