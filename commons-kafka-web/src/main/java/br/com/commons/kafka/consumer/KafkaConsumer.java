package br.com.commons.kafka.consumer;

import br.com.domain.entity.IEntity;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public interface KafkaConsumer<A extends SpecificRecordBase, T extends IEntity> {

    Logger log = LoggerFactory.getLogger(KafkaConsumer.class);
    MongoRepository<T, String> getRepository();
    T toEntity(A avro);

    default void consumer(ConsumerRecord<String, A> consumerRecord, Acknowledgment ack) {
        var avro = consumerRecord.value();
        var entity = toEntity(avro);
        getRepository().insert(entity);
        log.info("Message received successfully: {}", avro);
        ack.acknowledge();
    }

}
