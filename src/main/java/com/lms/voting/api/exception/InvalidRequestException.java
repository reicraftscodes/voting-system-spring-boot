package com.lms.voting.api.exception;

public class InvalidRequestException extends VotingException {
    public InvalidRequestException(String message) {
        super(message);
    }
}