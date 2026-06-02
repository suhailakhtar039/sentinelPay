package com.paymentservice.config;

import com.sentinelpay.common.event.PaymentCompletedEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Bean
    public DefaultErrorHandler errorHandler() {
        return new DefaultErrorHandler(
                new FixedBackOff(2000L, 3)
        );
    }

    @Bean
    public ConsumerFactory<String, PaymentCompletedEvent>
    paymentCompletedConsumerFactory() {

        JsonDeserializer<PaymentCompletedEvent> deserializer =
                new JsonDeserializer<>(
                        PaymentCompletedEvent.class
                );

        deserializer.addTrustedPackages("*");

        Map<String, Object> props = new HashMap<>();

        props.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092"
        );

        props.put(
                ConsumerConfig.GROUP_ID_CONFIG,
                "payment-group"
        );

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                deserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<
            String,
            PaymentCompletedEvent>
    paymentCompletedKafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<
                        String,
                        PaymentCompletedEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(
                paymentCompletedConsumerFactory()
        );

        return factory;
    }
}