package com.example.spring.batch.strategy.template.worker;

import com.example.spring.batch.strategy.dto.StrategySampleRequest;
import com.example.spring.batch.strategy.template.StrategySampleTemplate;

public interface StrategyTemplateWorker {
    boolean supports(StrategySampleTemplate sampleTemplate);
    void execute(StrategySampleRequest strategySampleRequest);
}
