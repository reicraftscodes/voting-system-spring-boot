package com.lms.voting.advice;

import com.lms.voting.dto.ErrorResponse;
import com.lms.voting.exception.NoVotingRecordsFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;


public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Handle NoVotingRecordsFoundException
    @ExceptionHandler(NoVotingRecordsFoundException.class)
    public ResponseEntity<ErrorResponse> handleVotingRecordsFoundException(NoVotingRecordsFoundException exception) {
        logger.error("No voting records found: {}", exception.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(
                exception.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
