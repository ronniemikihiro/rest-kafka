package br.com.producer.producers;

import br.com.avro.UserAvro;
import br.com.commons.kafka.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserProducer implements KafkaProducer<UserAvro> {

    private final String topicName;
    private final KafkaTemplate<String, UserAvro> kafkaTemplate;

    public UserProducer(@Value("${topic.name.users}") String topicName, KafkaTemplate<String, UserAvro> kafkaTemplate) {
        this.topicName = topicName;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public KafkaTemplate<String, UserAvro> getKafkaTemplate() {
        return kafkaTemplate;
    }

    @Override
    public String getTopicName() {
        return topicName;
    }
}
