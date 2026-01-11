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

    private Integer resultCode;
    private String resultMessage;
    private String resultDescription;
    private Boolean resultStatus;
    private Object data;
    private ErrorDetailsResponse error;

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    // Success response with data
    public static ApiResponse success(Object data, Integer httpStatusCode) {
        return ApiResponse.builder()
                .resultStatus(true)
                .resultCode(httpStatusCode)
                .data(data)
                .build();
    }

    // Success response with message and data
    public static ApiResponse success(String message, String description, Object data, Integer httpStatusCode) {
        return ApiResponse.builder()
                .resultStatus(true)
                .resultCode(httpStatusCode)
                .resultMessage(message)
                .resultDescription(description)
                .data(data)
                .build();
    }

    // Success response with only message
    public static ApiResponse successMessage(String message, String description, Integer httpStatusCode) {
        return ApiResponse.builder()
                .resultStatus(true)
                .resultCode(httpStatusCode)
                .resultMessage(message)
                .resultDescription(description)
                .build();
    }

    // Error response
    public static ApiResponse error(String message, String description, ErrorDetailsResponse error, Integer httpStatusCode) {
        return ApiResponse.builder()
                .resultStatus(false)
                .resultCode(httpStatusCode)
                .resultMessage(message)
                .resultDescription(description)
                .error(error)
                .build();
    }
}