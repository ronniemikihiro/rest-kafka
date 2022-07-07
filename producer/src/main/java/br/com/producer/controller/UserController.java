package br.com.producer.controller;

import br.com.avro.UserAvro;
import br.com.commons.kafka.controller.KafkaController;
import br.com.commons.kafka.producer.KafkaProducer;
import br.com.domain.entity.dto.UserDTO;
import br.com.producer.producers.UserProducer;
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
@RequestMapping("/brand")
@Api("Services to manage brands")
public class UserController implements KafkaController<UserAvro, UserDTO> {

	private final UserProducer userProducer;

	@Override
	public KafkaProducer<UserAvro> getKafkaProducer() {
		return userProducer;
	}

	@Override
	public UserAvro getAvro(UserDTO dto) {
		return UserAvro.newBuilder()
				.setId(UUID.randomUUID().toString())
				.setName(dto.getName())
				.setAge(dto.getAge())
				.build();
	}
}