package org.challenge.messaging;

public class DomainError {
    private String message;

    public DomainError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}