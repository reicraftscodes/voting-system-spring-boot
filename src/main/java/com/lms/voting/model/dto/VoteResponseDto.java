package com.lms.voting.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoteResponseDto {

    private String referenceNo;
    private String partyName;
    private String description;

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
}