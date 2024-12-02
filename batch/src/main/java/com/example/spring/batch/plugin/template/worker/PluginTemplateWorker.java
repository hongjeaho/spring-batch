package com.example.spring.batch.plugin.template.worker;

import com.example.spring.batch.plugin.dto.PluginSampleRequest;
import com.example.spring.batch.plugin.template.PluginSampleTemplate;
import org.springframework.plugin.core.Plugin;

public interface PluginTemplateWorker extends Plugin<PluginSampleTemplate> {

    void execute(PluginSampleRequest pluginSampleRequest);
}
