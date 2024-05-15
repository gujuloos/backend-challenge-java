package org.challenge.messaging;

import java.util.Queue;

public class MessagePublisherFactory implements MessagePublisher {

    private final Queue<String> queue;

    public MessagePublisherFactory(Queue<String> queue) {
        this.queue = queue;
    }

    @Override
    public Object send(String message) {
        queue.offer(message);
        return "Message received.";
    }
}