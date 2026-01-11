package com.lms.voting.exception;

public class InvalidRequestException extends VotingException {
    public InvalidRequestException(String message) {
        super(message);
    }
}