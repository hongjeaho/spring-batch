package com.example.springdataflow.strategy.dto;

import com.example.springdataflow.strategy.template.StrategySampleTemplate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class StrategySampleRequest {

    private final StrategySampleTemplate sampleTemplate;
}
