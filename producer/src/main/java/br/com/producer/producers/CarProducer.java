package br.com.producer.producers;

import br.com.avro.CarAvro;
import br.com.commons.kafka.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class CarProducer implements KafkaProducer<CarAvro> {

    private final String topicName;
    private final KafkaTemplate<String, CarAvro> kafkaTemplate;

    public CarProducer(@Value("${topic.name.cars}") String topicName, KafkaTemplate<String, CarAvro> kafkaTemplate) {
        this.topicName = topicName;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public KafkaTemplate<String, CarAvro> getKafkaTemplate() {
        return kafkaTemplate;
    }

    @Override
    public String getTopicName() {
        return topicName;
    }
}
