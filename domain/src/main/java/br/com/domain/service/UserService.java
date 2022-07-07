package br.com.domain.service;

import br.com.domain.entity.User;
import br.com.domain.entity.dto.UserDTO;
import br.com.domain.exception.rules.FieldRequiredException;
import br.com.domain.exception.rules.RuleException;
import br.com.domain.repository.UserRepository;
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
public class UserService implements CrudService<User, UserDTO> {

    private final UserRepository userRepository;

    @Override
    public MongoRepository<User, String> getRepository() {
        return userRepository;
    }

    @Override
    public User toEntity(String id, UserDTO dto) {
        return User.builder()
                .id(id)
                .name(dto.getName())
                .age(dto.getAge())
                .build();
    }

    @Override
    public UserDTO toDto(User entity) {
        return UserDTO.builder()
                .name(entity.getName())
                .age(entity.getAge())
                .build();
    }

    @Override
    public void validateFields(UserDTO dto) throws RuleException {
        if (Objects.isNull(dto)) {
            throw new RuleException("Dto is null");
        }
        if (StringUtils.isEmpty(dto.getName())) {
            throw new FieldRequiredException("Name");
        }
        if (Objects.isNull(dto.getAge())) {
            throw new FieldRequiredException("Age");
        }
    }

}
