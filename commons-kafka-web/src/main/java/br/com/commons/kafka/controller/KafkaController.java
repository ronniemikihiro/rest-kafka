package br.com.commons.kafka.controller;

import br.com.commons.kafka.producer.KafkaProducer;
import br.com.domain.entity.dto.IDTO;
import org.apache.avro.specific.SpecificRecordBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface KafkaController<A extends SpecificRecordBase, D extends IDTO> {

    Logger log = LoggerFactory.getLogger(KafkaController.class);
    KafkaProducer<A> getKafkaProducer();
    A getAvro(D dto);

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    default ResponseEntity<Void> sendMessage(@RequestBody D dto) {
        getKafkaProducer().sendMessage(getAvro(dto));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
