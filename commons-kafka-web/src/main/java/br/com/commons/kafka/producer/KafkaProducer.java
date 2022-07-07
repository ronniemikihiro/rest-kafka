package br.com.commons.kafka.producer;

import org.apache.avro.specific.SpecificRecordBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public interface KafkaProducer<A extends SpecificRecordBase> {

    Logger log = LoggerFactory.getLogger(KafkaProducer.class);
    KafkaTemplate<String, A> getKafkaTemplate();
    String getTopicName();

    default void sendMessage(A avro) {
        getKafkaTemplate().send(getTopicName(), avro.get("id").toString(), avro).addCallback(
                success -> log.info("Message sent successfully: {}", avro),
                failure -> log.error("Failed to send message: {}", avro)
        );
    }

}
