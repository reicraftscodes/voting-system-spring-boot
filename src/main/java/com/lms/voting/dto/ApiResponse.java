package com.lms.voting.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {

    private String title;
    private String detail;
    private ErrorDetailsResponse error;

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

}
