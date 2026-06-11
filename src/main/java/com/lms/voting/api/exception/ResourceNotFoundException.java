package com.lms.voting.api.exception;

public class ResourceNotFoundException extends VotingException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}