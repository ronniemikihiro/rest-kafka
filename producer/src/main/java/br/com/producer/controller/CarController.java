package br.com.producer.controller;

import br.com.avro.CarAvro;
import br.com.commons.kafka.controller.KafkaController;
import br.com.commons.kafka.producer.KafkaProducer;
import br.com.domain.entity.dto.CarDTO;
import br.com.producer.producers.CarProducer;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/car")
@Api("Services to manage cars")
public class CarController implements KafkaController<CarAvro, CarDTO>  {

	private final CarProducer carProducer;

	@Override
	public KafkaProducer<CarAvro> getKafkaProducer() {
		return carProducer;
	}

	@Override
	public CarAvro getAvro(CarDTO dto) {
		return CarAvro.newBuilder()
				.setId(UUID.randomUUID().toString())
				.setName(dto.getName())
				.setBrand(dto.getBrand())
				.build();
	}
}