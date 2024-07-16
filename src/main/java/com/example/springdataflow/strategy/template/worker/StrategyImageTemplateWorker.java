package com.example.springdataflow.strategy.template.worker;

import com.example.springdataflow.strategy.dto.StrategySampleRequest;
import com.example.springdataflow.strategy.template.StrategySampleTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnProperty(name = "spring.batch.job.name", havingValue = "sample.strategy")
public class StrategyImageTemplateWorker implements StrategyTemplateWorker{
    @Override
    public void execute(StrategySampleRequest strategySampleRequest) {
        log.info("############################");
        log.info("image strategy template");
        log.info("############################");
    }

    @Override
    public boolean supports(@NonNull StrategySampleTemplate sampleTemplate) {
        return sampleTemplate == StrategySampleTemplate.IMAGE_TEMPLATE;
    }
}
