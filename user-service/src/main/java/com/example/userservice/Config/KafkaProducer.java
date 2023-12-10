package com.example.userservice.Config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(GenericMessage message) {
        final CompletableFuture<? extends SendResult<String, ?>> listenableResult = kafkaTemplate.send(message);

        listenableResult.whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("Unable to deliver message to kafka", ex);
            }else {
                if (Objects.isNull(result)) {
                    log.info("Empty result on success for message {}", message);
                    return;
                }
                log.info("Message :{} published, topic : {}, partition : {} and offset : {}", message.getPayload(),
                        result.getRecordMetadata().topic(),
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset());
            }
        });

    }
}
