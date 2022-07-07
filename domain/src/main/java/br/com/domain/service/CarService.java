package br.com.domain.service;

import br.com.domain.entity.User;
import br.com.domain.entity.Car;
import br.com.domain.entity.dto.UserDTO;
import br.com.domain.entity.dto.CarDTO;
import br.com.domain.exception.rules.FieldRequiredException;
import br.com.domain.exception.rules.RuleException;
import br.com.domain.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CarService implements CrudService<Car, CarDTO> {

    private final CarRepository carRepository;

    @Override
    public MongoRepository<Car, String> getRepository() {
        return carRepository;
    }

    @Override
    public Car toEntity(String id, CarDTO dto) {
        return Car.builder()
                .id(id)
                .name(dto.getName())
                .brand(dto.getBrand())
                .build();
    }

    @Override
    public CarDTO toDto(Car entity) {
        return CarDTO.builder()
                .name(entity.getName())
                .brand(entity.getBrand())
                .build();
    }

    @Override
    public void validateFields(CarDTO dto) throws RuleException {
        if (Objects.isNull(dto)) {
            throw new RuleException("Dto is null");
        }
        if (StringUtils.isEmpty(dto.getName())) {
            throw new FieldRequiredException("Name");
        }
        if (StringUtils.isEmpty(dto.getBrand())) {
            throw new FieldRequiredException("Brand");
        }
    }

}
