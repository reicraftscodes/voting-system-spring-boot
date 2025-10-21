package com.lms.voting.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
public class ErrorResponse {

    private String message;
    private int status;
    private String timestamp;

}
