package com.lms.voting.api.exception;

public class IneligibleVoterException extends RuntimeException {
    public IneligibleVoterException(String message) {
        super(message);
    }
}
