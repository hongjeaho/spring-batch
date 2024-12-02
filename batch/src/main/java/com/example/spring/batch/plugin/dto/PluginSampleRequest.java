package com.example.spring.batch.plugin.dto;

import com.example.spring.batch.plugin.template.PluginSampleTemplate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class PluginSampleRequest {

    private final PluginSampleTemplate sampleTemplate;
}
