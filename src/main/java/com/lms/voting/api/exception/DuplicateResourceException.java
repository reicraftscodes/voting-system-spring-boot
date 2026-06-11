package com.lms.voting.api.exception;

public class DuplicateResourceException extends VotingException {
    public DuplicateResourceException(String message) {
        super(message);
    }
}