package com.lms.voting.exception;

public class DuplicateResourceException extends VotingException {
    public DuplicateResourceException(String message) {
        super(message);
    }
}