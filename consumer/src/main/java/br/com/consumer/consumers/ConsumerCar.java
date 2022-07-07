package br.com.consumer.consumers;

import br.com.avro.CarAvro;
import br.com.commons.kafka.consumer.KafkaConsumer;
import br.com.domain.entity.Car;
import br.com.domain.repository.CarRepository;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ConsumerCar implements KafkaConsumer<CarAvro, Car> {

    private final CarRepository carRepository;

    @Override
    public MongoRepository<Car, String> getRepository() {
        return carRepository;
    }

    @Override
    public Car toEntity(CarAvro avro) {
        return Car.builder()
                .id(avro.getId().toString())
                .name(avro.getName().toString())
                .brand(avro.getBrand().toString())
                .build();
    }

    @Override
    @KafkaListener(topics = "${topic.name.cars}")
    public void consumer(ConsumerRecord<String, CarAvro> consumerRecord, Acknowledgment ack) {
        KafkaConsumer.super.consumer(consumerRecord, ack);
    }
}
