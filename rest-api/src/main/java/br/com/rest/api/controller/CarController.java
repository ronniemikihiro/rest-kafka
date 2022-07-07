package br.com.rest.api.controller;

import br.com.commons.web.controller.CrudController;
import br.com.domain.entity.Car;
import br.com.domain.entity.dto.CarDTO;
import br.com.domain.service.CarService;
import br.com.domain.service.CrudService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/car")
@Api("Services to manage cars")
public class CarController implements CrudController<Car, CarDTO> {

	private final CarService carService;

	@Override
	public CrudService<Car, CarDTO> getCrudService() {
		return carService;
	}

}