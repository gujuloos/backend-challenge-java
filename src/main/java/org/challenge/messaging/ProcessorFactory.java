package org.challenge.messaging;

public class ProcessorFactory implements Processor {

    @Override
    public Object process(String input) {
        if(input != null) {
            return "Processed command.";
        }
        return new DomainError("Could not deserialize input.");
    }
}
