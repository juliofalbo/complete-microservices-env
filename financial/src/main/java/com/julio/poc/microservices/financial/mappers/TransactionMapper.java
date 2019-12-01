package com.julio.poc.microservices.financial.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.julio.poc.microservices.financial.dtos.TransactionDTO;
import com.julio.poc.microservices.financial.entities.Transaction;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    Transaction toEntity(TransactionDTO dto);

    TransactionDTO toDTO(Transaction entity);

    List<TransactionDTO> toDTO(List<Transaction> entities);

    default Page<TransactionDTO> toDTO(Page<Transaction> page) {
        List<TransactionDTO> responses = toDTO(page.getContent());
        return new PageImpl<>(responses, page.getPageable(), page.getTotalElements());
    }
}
