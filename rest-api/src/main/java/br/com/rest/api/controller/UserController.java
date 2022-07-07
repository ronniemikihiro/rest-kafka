package br.com.rest.api.controller;

import br.com.commons.web.controller.CrudController;
import br.com.domain.entity.User;
import br.com.domain.entity.dto.UserDTO;
import br.com.domain.service.UserService;
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
@RequestMapping("/user")
@Api("Services to manage users")
public class UserController implements CrudController<User, UserDTO> {

	private final UserService userService;

	@Override
	public CrudService<User, UserDTO> getCrudService() {
		return userService;
	}

}