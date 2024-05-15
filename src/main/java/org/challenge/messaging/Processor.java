package org.challenge.messaging;

public interface Processor {
    Object process(String input);
}