package com.ssafy.enjoytrip.exception;

public class UnAuthorizedException extends RuntimeException {
    private String message;

    public UnAuthorizedException(String message) {
        super(message);
        this.message = message;
    }

    public UnAuthorizedException() {
    }

    public String getMessage() {
        return message;
    }
}
