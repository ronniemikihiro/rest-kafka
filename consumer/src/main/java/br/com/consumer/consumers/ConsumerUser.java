package br.com.consumer.consumers;

import br.com.avro.UserAvro;
import br.com.commons.kafka.consumer.KafkaConsumer;
import br.com.domain.entity.User;
import br.com.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ConsumerUser implements KafkaConsumer<UserAvro, User> {

    private final UserRepository userRepository;

    @Override
    public MongoRepository<User, String> getRepository() {
        return userRepository;
    }

    @Override
    public User toEntity(UserAvro avro) {
        return User.builder()
                .id(avro.getId().toString())
                .name(avro.getName().toString())
                .age(avro.getAge())
                .build();
    }

    @Override
    @KafkaListener(topics = "${topic.name.users}")
    public void consumer(ConsumerRecord<String, UserAvro> consumerRecord, Acknowledgment ack) {
        KafkaConsumer.super.consumer(consumerRecord, ack);
    }
}
