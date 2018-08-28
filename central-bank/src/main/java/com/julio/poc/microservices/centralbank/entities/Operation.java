package com.julio.poc.microservices.centralbank.entities;

import com.julio.poc.microservices.centralbank.enums.TaxesType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Operation {

    public Operation(@NotEmpty String operation, @NotNull BigDecimal multiply) {
        this.operation = operation;
        this.multiply = multiply;
    }

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty(message = "Operation is required")
    @Column(unique=true)
    private String operation;

    @NotNull(message = "Multiply is required")
    private BigDecimal multiply;

}
