package com.example.springdataflow.plugin.template.worker;

import com.example.springdataflow.plugin.dto.PluginSampleRequest;
import com.example.springdataflow.plugin.template.PluginSampleTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnProperty(name = "spring.batch.job.name", havingValue = "sample.plugin")
public class PluginTextTemplateWorker implements PluginTemplateWorker {
    @Override
    public void execute(PluginSampleRequest pluginSampleRequest) {
        log.info("############################");
        log.info("text plugin template");
        log.info("############################");
    }

    @Override
    public boolean supports(@NonNull PluginSampleTemplate sampleTemplate) {
        return sampleTemplate == PluginSampleTemplate.TEXT_TEMPLATE;
    }
}
