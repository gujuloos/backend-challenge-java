package org.challenge.messaging;

import java.util.Queue;

public interface MessageConsumer {
    Object consume(Queue<String> queue);
}