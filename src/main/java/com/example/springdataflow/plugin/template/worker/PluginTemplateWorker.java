package com.example.springdataflow.plugin.template.worker;

import com.example.springdataflow.plugin.dto.PluginSampleRequest;
import com.example.springdataflow.plugin.template.PluginSampleTemplate;
import org.springframework.plugin.core.Plugin;

public interface PluginTemplateWorker extends Plugin<PluginSampleTemplate> {

    void execute(PluginSampleRequest pluginSampleRequest);
}
