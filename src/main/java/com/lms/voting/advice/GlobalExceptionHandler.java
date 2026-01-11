package com.lms.voting.advice;

import com.lms.voting.dto.ApiResponse;
import com.lms.voting.dto.ErrorDetailsResponse;
import com.lms.voting.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        logger.error("Resource not found: {}", ex.getMessage());

        ApiResponse response = ApiResponse.error(
                "Resource Not Found",
                ex.getMessage(),
                ErrorDetailsResponse.builder()
                        .code("RESOURCE_NOT_FOUND")
                        .details(ex.getMessage())
                        .build(),
                HttpStatus.NOT_FOUND.value()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiResponse> handleDuplicateResource(DuplicateResourceException ex) {
        logger.error("Duplicate resource: {}", ex.getMessage());

        ApiResponse response = ApiResponse.error(
                "Duplicate Resource",
                ex.getMessage(),
                ErrorDetailsResponse.builder()
                        .code("DUPLICATE_RESOURCE")
                        .details(ex.getMessage())
                        .build(),
                HttpStatus.CONFLICT.value()
        );

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ApiResponse> handleInvalidRequest(InvalidRequestException ex) {
        logger.error("Invalid request: {}", ex.getMessage());

        ApiResponse response = ApiResponse.error(
                "Invalid Request",
                ex.getMessage(),
                ErrorDetailsResponse.builder()
                        .code("INVALID_REQUEST")
                        .details(ex.getMessage())
                        .build(),
                HttpStatus.BAD_REQUEST.value()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IneligibleVoterException.class)
    public ResponseEntity<ApiResponse> handleIneligibleVoter(IneligibleVoterException ex) {
        logger.error("Ineligible voter: {}", ex.getMessage());

        ApiResponse response = ApiResponse.error(
                "Ineligible Voter",
                ex.getMessage(),
                ErrorDetailsResponse.builder()
                        .code("INELIGIBLE_VOTER")
                        .details(ex.getMessage())
                        .build(),
                HttpStatus.FORBIDDEN.value()
        );

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        logger.error("Type mismatch: {}", ex.getMessage());

        String message = String.format("Invalid value '%s' for parameter '%s'. Expected type: %s",
                ex.getValue(),
                ex.getName(),
                ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "unknown");

        ApiResponse response = ApiResponse.error(
                "Invalid Parameter Type",
                message,
                ErrorDetailsResponse.builder()
                        .code("TYPE_MISMATCH")
                        .details(message)
                        .build(),
                HttpStatus.BAD_REQUEST.value()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        logger.error("Validation error: {}", ex.getMessage());

        String errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ApiResponse response = ApiResponse.error(
                "Validation Failed",
                errors,
                ErrorDetailsResponse.builder()
                        .code("VALIDATION_ERROR")
                        .details(errors)
                        .build(),
                HttpStatus.BAD_REQUEST.value()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGenericException(Exception ex) {
        logger.error("Unexpected error: ", ex);

        ApiResponse response = ApiResponse.error(
                "Internal Server Error",
                "An unexpected error occurred while processing your request",
                ErrorDetailsResponse.builder()
                        .code("INTERNAL_ERROR")
                        .details(ex.getMessage())
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}