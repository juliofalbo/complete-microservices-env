package com.julio.poc.microservices.centralbank.services;

import com.julio.poc.microservices.centralbank.entities.Operation;
import com.julio.poc.microservices.centralbank.exceptions.OperationTypeNotFoundException;
import com.julio.poc.microservices.centralbank.repositories.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class OperationService {

    @Autowired
    private OperationRepository repository;

    public List<Operation> searchOperations(Specification<Operation> specification) {
        List<Operation> result = repository.findAll(specification);
        if (CollectionUtils.isEmpty(result)) {
            throw new OperationTypeNotFoundException("No taxes found for informed type");
        }
        return result;
    }

    public Operation save(Operation taxes) {
        return repository.save(taxes);
    }

    public Operation findOne(Long id) {
        return repository.findById(id).orElseThrow(() -> new OperationTypeNotFoundException(String.format("No operation type found for id: %s", id)));
    }

    public Operation findByOperation(String type) {
        return repository.findByOperation(type).orElseThrow(() -> new OperationTypeNotFoundException(String.format("No operation type found for type: %s", type)));
    }

    public void deleteById(Long id) {
        repository.delete(findOne(id));
    }


}
