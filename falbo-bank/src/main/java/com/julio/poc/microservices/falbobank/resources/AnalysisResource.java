package com.julio.poc.microservices.falbobank.resources;

import com.julio.poc.microservices.falbobank.dtos.AnalysisRequest;
import com.julio.poc.microservices.falbobank.dtos.AnalysisResponse;
import com.julio.poc.microservices.falbobank.service.AnalysisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
