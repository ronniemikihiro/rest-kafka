package br.com.commons.web.controller;

import br.com.commons.web.exception.ResponseException;
import br.com.domain.entity.IEntity;
import br.com.domain.entity.dto.IDTO;
import br.com.domain.exception.rules.NotFoundException;
import br.com.domain.service.CrudService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public interface CrudController<T extends IEntity, D extends IDTO> {

    Logger log = LoggerFactory.getLogger(CrudService.class);
    CrudService<T, D> getCrudService();

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("List all registers")
    default ResponseEntity<Object> listAll() {
        try {
            return ResponseEntity.ok().body(getCrudService().listAll());
        } catch (Exception e) {
            log.error("Erro ao listar todos", e);
            return ResponseException.exception(e);
        }
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Find register by id")
    default ResponseEntity<Object> findById(@PathVariable String id) {
        try {
            return ResponseEntity.ok().body(getCrudService().findById(id));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Erro ao obter por id", e);
            return ResponseException.exception(e);
        }
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Create new register")
    default ResponseEntity<Object> insert(@RequestBody D dto) {
        try {
            final IEntity iEntity = getCrudService().insert(dto);
            return ResponseEntity.ok().body(getCrudService().findById(iEntity.getId()));
        } catch (Exception e) {
            log.error("Erro ao criar o registro", e);
            return ResponseException.exception(e);
        }
    }

    @PutMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Update register")
    default ResponseEntity<Object> update(@PathVariable String id, @RequestBody D dto) {
        try {
            final IEntity iEntity = getCrudService().update(id, dto);
            return ResponseEntity.ok().body(getCrudService().findById(iEntity.getId()));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Erro ao atualizar o registro", e);
            return ResponseException.exception(e);
        }
    }

    @DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Delete register")
    default ResponseEntity<Object> deleteById(@PathVariable String id) {
        try {
            getCrudService().deleteById(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Erro ao deletar o registro", e);
            return ResponseException.exception(e);
        }
    }

}
