package com.julio.poc.microservices.arruzzobank.resources;

import com.julio.poc.microservices.arruzzobank.dtos.AnalysisRequest;
import com.julio.poc.microservices.arruzzobank.dtos.AnalysisResponse;
import com.julio.poc.microservices.arruzzobank.service.AnalysisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/analysis")
public class AnalysisResource {

    @Autowired
    private AnalysisService analysisService;

    @PostMapping
    public AnalysisResponse analyze(@Valid @RequestBody AnalysisRequest analysisRequest){
        log.info("M=analyze, Bank: Arruzzo Bank, Analysis Request Json: {}", analysisRequest);
        return analysisService.analyze(analysisRequest);
    }

}
