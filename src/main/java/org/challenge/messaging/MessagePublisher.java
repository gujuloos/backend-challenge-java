package org.challenge.messaging;

public interface MessagePublisher {
    Object send(String message);
}