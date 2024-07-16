package com.example.springdataflow.strategy.template;

import com.example.springdataflow.plugin.template.worker.PluginTemplateWorker;
import com.example.springdataflow.strategy.dto.StrategySampleRequest;
import com.example.springdataflow.strategy.template.worker.StrategyTemplateWorker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.lang.NonNull;
import org.springframework.plugin.core.config.EnablePluginRegistries;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@ConditionalOnProperty(name = "spring.batch.job.name", havingValue = "sample.strategy")
@EnablePluginRegistries(PluginTemplateWorker.class)
@RequiredArgsConstructor
@Component
public class StrategySampleWorkManager {

    private final List<StrategyTemplateWorker> strategyTemplateWorkers;

    public void work(@NonNull StrategySampleRequest request) {
        var resultWork = strategyTemplateWorkers.stream()
                .filter(work -> work.supports(request.getSampleTemplate()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(""));

        resultWork.execute(request);
    }
}
