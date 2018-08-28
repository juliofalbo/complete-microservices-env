package com.julio.poc.microservices.falbobank.service;

import com.julio.poc.microservices.falbobank.annotations.PopulatePortOnObjectResponse;
import com.julio.poc.microservices.falbobank.clients.CentralBankClient;
import com.julio.poc.microservices.falbobank.dtos.AnalysisRequest;
import com.julio.poc.microservices.falbobank.dtos.AnalysisResponse;
import com.julio.poc.microservices.falbobank.dtos.CentralBankTaxes;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class AnalysisService {

    @Autowired
    private CentralBankClient centralBankClient;

    @Autowired
    private AuthService authService;

    @Value("${falbobank.base-taxes}")
    private BigDecimal currentTaxes;

    @PopulatePortOnObjectResponse(fieldName = "falboBankMicroservicePort")
    public AnalysisResponse analyze(@NonNull final AnalysisRequest analysisRequest) {
        String auth = authService.auth();
        CentralBankTaxes centralBankTaxes = centralBankClient.getCentralBankTaxes(auth, analysisRequest.getOperation());

        BigDecimal totalTaxes = currentTaxes.add(centralBankTaxes.getValue());
        BigDecimal totalValue = analysisRequest.getRequestedValue().multiply(totalTaxes);
        BigDecimal plotValue = totalValue.divide(new BigDecimal(analysisRequest.getPlots()), 2, RoundingMode.HALF_UP);

        return AnalysisResponse.builder()
                .document(analysisRequest.getDocument())
                .operation(centralBankTaxes.getOperation())
                .plots(analysisRequest.getPlots())
                .requestedValue(analysisRequest.getRequestedValue())
                .totalValue(totalValue)
                .taxes(totalTaxes)
                .plotValue(plotValue)
                .centralBankMicroservicePort(centralBankTaxes.getCentralBankMicroservicePort())
                .build();
    }

}
