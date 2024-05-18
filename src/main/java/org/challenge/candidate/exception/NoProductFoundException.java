package org.challenge.candidate.exception;

public class NoProductFoundException extends RuntimeException {

    public NoProductFoundException(String code) {
        super(String.format("Cannot find product with code %s", code));
    }

    public NoProductFoundException() {
        super("Cannot find product with null code");
    }
}
