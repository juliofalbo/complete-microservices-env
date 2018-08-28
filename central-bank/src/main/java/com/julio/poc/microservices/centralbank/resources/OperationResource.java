package com.julio.poc.microservices.centralbank.resources;

import com.julio.poc.microservices.centralbank.entities.Operation;
import com.julio.poc.microservices.centralbank.services.OperationService;
import com.julio.poc.microservices.centralbank.specifications.builder.JApiSpecificationBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/operations")
public class OperationResource {

    @Autowired
    private OperationService service;

    @GetMapping
    public List<Operation> searchOperations(@RequestParam("operation") Optional<String> operation) {
        log.info("Method:searchTaxes Params: {}", operation.orElse("[empty]"));

        Specification<Operation> specification = JApiSpecificationBuilder.init()
                .withEqualFilter("type", operation.orElse(null))
                .buildSpec();

        return service.searchOperations(specification);
    }

    @PostMapping
    public ResponseEntity insertOperations(@Valid @RequestBody Operation user) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(service.save(user).getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }

}
