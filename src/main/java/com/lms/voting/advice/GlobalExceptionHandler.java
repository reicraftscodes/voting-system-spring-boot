package com.lms.voting.advice;

import com.lms.voting.constant.ErrorCode;
import com.lms.voting.dto.ApiResponse;
import com.lms.voting.dto.ErrorDetailsResponse;
import com.lms.voting.exception.DuplicateResourceException;
import com.lms.voting.exception.IneligibleVoterException;
import com.lms.voting.exception.InvalidRequestException;
import com.lms.voting.exception.ResourceNotFoundException;
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

        ApiResponse response = ApiResponse.builder()
                .title("Resource not found")
                .detail("Resource not found")
                .error(ErrorDetailsResponse.builder()
                        .code(ErrorCode.RESOURCE_NOT_FOUND.name())
                        .details(ex.getMessage())
                        .build())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiResponse> handleDuplicateResource(DuplicateResourceException ex) {
        logger.error("Duplicate resource: {}", ex.getMessage());

        ApiResponse response = ApiResponse.builder()
                .title("Duplicate Resource")
                .detail("Duplicate Resource")
                .error(ErrorDetailsResponse.builder()
                        .code(ErrorCode.DUPLICATE_RESOURCE.name())
                        .details(ex.getMessage())
                        .build())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ApiResponse> handleInvalidRequest(InvalidRequestException ex) {
        logger.error("Invalid request: {}", ex.getMessage());

        ApiResponse response = ApiResponse.builder()
                .title("Invalid Request")
                .detail("Invalid Request")
                .error(ErrorDetailsResponse.builder()
                        .code(ErrorCode.INVALID_REQUEST.name())
                        .details(ex.getMessage())
                        .build())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IneligibleVoterException.class)
    public ResponseEntity<ApiResponse> handleIneligibleVoter(IneligibleVoterException ex) {
        logger.error("Ineligible voter: {}", ex.getMessage());

        ApiResponse response = ApiResponse.builder()
                .title("Ineligible Voter")
                .detail("User is under 18 not qualified to vote")
                .error(ErrorDetailsResponse.builder()
                        .code(ErrorCode.INVALID_REQUEST.name())
                        .details(ex.getMessage())
                        .build())
                .build();

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        logger.error("Type mismatch: {}", ex.getMessage());

        String expectedType = "unknown";
        if (ex.getRequiredType() != null) {
            expectedType = ex.getRequiredType().getSimpleName();
        }

        String message = String.format(
                "Invalid value '%s' for parameter '%s'. Expected type: %s",
                ex.getValue(),
                ex.getName(),
                expectedType
        );

        ApiResponse response = ApiResponse.builder()
                .title("Invalid Parameter Typer")
                .detail("Invalid Parameter Type")
                .error(ErrorDetailsResponse.builder()
                        .code(ErrorCode.INVALID_REQUEST.name())
                        .details(message)
                        .build())
                .build();


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

        ApiResponse response = ApiResponse.builder()
                .title("Validation Failed")
                .detail("Validation Failed")
                .error(ErrorDetailsResponse.builder()
                        .code(ErrorCode.VALIDATION_ERROR.name())
                        .details(errors)
                        .build())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGenericException(Exception ex) {
        logger.error("Unexpected error: ", ex);

        ApiResponse response = ApiResponse.builder()
                .title("Internal Server Error")
                .detail("An unexpected error occurred while processing your request")
                .error(ErrorDetailsResponse.builder()
                        .code(ErrorCode.VALIDATION_ERROR.name())
                        .details(ex.getMessage())
                        .build())
                .build();

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}