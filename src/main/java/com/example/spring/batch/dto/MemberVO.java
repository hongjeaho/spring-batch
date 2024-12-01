package com.example.spring.batch.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class MemberVO {

    private long seq;
    private String name;
    private String email;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
