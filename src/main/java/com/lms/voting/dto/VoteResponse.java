package com.lms.voting.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoteResponse {
    private String referenceNo;
    private String partyName;
    private String description;


    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now(); // auto-set when building
}