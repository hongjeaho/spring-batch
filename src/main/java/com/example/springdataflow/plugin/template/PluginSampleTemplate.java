package com.example.springdataflow.plugin.template;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PluginSampleTemplate {

    IMAGE_TEMPLATE("image", "이미지 처리"), TEXT_TEMPLATE("text", "텍스트 처리");

    private final String code;
    private final String description;
}
