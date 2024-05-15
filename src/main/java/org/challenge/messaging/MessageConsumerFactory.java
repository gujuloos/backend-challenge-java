package org.challenge.messaging;

import java.util.Queue;

public class MessageConsumerFactory implements MessageConsumer {

    private final ProcessorFactory processor = new ProcessorFactory();

    @Override
    public Object consume(Queue<String> queue) {
        String message = queue.poll();
        if (message != null) {
            return  processor.process(message);
        }
        return new DomainError("Message is null. Message cannot be null.");
    }
}