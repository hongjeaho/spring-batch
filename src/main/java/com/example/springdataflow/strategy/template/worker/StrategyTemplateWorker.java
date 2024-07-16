package com.example.springdataflow.strategy.template.worker;

import com.example.springdataflow.strategy.dto.StrategySampleRequest;
import com.example.springdataflow.strategy.template.StrategySampleTemplate;

public interface StrategyTemplateWorker {
    boolean supports(StrategySampleTemplate sampleTemplate);
    void execute(StrategySampleRequest strategySampleRequest);
}
