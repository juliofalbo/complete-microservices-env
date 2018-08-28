package com.julio.poc.microservices.centralbank.repositories;

import com.julio.poc.microservices.centralbank.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface OperationRepository extends JpaRepository<Operation, Long>, JpaSpecificationExecutor<Operation> {

    Optional<Operation> findByOperation(String type);

}
