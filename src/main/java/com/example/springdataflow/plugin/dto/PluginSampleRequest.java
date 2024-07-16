package com.example.springdataflow.plugin.dto;

import com.example.springdataflow.plugin.template.PluginSampleTemplate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class PluginSampleRequest {

    private final PluginSampleTemplate sampleTemplate;
}
