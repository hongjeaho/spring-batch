package com.example.spring.batch.strategy.dto;

import com.example.spring.batch.strategy.template.StrategySampleTemplate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class StrategySampleRequest {

    private final StrategySampleTemplate sampleTemplate;
}
