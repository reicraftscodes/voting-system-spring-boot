package com.lms.voting.exception;

public class NoVotingRecordsFoundException extends RuntimeException {

    public NoVotingRecordsFoundException(String message) {
        super(message);
    }
}
