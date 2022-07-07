package br.com.domain.service;

import br.com.domain.entity.IEntity;
import br.com.domain.entity.dto.IDTO;
import br.com.domain.exception.errors.ErrorException;
import br.com.domain.exception.rules.NotFoundException;
import br.com.domain.exception.rules.RuleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CrudService<T extends IEntity, D extends IDTO> {

    Logger log = LoggerFactory.getLogger(CrudService.class);

    MongoRepository<T, String> getRepository();
    T toEntity(String id, D dto);
    D toDto(T entity);
    void validateFields(D dto) throws RuleException;

    default List<D> listAll() throws ErrorException {
        try {
            return getRepository().findAll()
                    .stream()
                    .map(this::toDto)
                    .toList();
        } catch (Exception e) {
            throw new ErrorException(log, "Error deleting entity", e);
        }
    }

    default D findById(String id) throws ErrorException, NotFoundException {
        try {
            return getRepository().findById(id)
                    .map(this::toDto)
                    .orElseThrow(NotFoundException::new);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ErrorException(log, "Error getting by id", e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    default T insert(D dto) throws RuleException, ErrorException {
        try {
            validateFields(dto);
            return getRepository().insert(toEntity(null, dto));
        } catch (RuleException e) {
            throw e;
        } catch (Exception e) {
            throw new ErrorException(log, "Error saving entity", e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    default T update(String id, D dto) throws RuleException, ErrorException {
        try {
            if (getRepository().existsById(id)) {
                validateFields(dto);
                return getRepository().save(toEntity(id, dto));
            } else {
                throw new NotFoundException();
            }
        } catch (RuleException e) {
            throw e;
        } catch (Exception e) {
            throw new ErrorException(log, "Error saving entity", e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    default void deleteById(String id) throws ErrorException, NotFoundException {
        try {
            if (getRepository().existsById(id)) {
                getRepository().deleteById(id);
            } else {
                throw new NotFoundException();
            }
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ErrorException(log, "Erro ao deletar a entidade", e);
        }
    }

}
