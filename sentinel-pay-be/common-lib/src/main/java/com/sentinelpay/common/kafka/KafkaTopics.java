package com.sentinelpay.common.kafka;

public class KafkaTopics {

    public static final String USER_REGISTERED =
            "user.registered";

    public static final String USER_REGISTERED_DLT =
            "user.registered.dlt";
    public static final String PAYMENT_INITIATED =
            "payment.initiated";

    public static final String PAYMENT_COMPLETED =
            "payment.completed";

    public static final String PAYMENT_FAILED =
            "payment.failed";

    public static final String FRAUD_APPROVED =
            "fraud-approved";

    public static final String FRAUD_REJECTED =
            "fraud-rejected";
}
