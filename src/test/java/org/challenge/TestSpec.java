package org.challenge;

import org.challenge.messaging.MessageConsumerFactory;
import org.challenge.messaging.MessagePublisherFactory;
import org.junit.jupiter.api.Test;

public class TestSpec {

    @Test
    public void dummyProcessorTest() {
        Provided provided = new Provided();

        MessagePublisherFactory scanPublisher = new MessagePublisherFactory(provided.getScanQueue());
        MessagePublisherFactory checkoutPublisher = new MessagePublisherFactory(provided.getCheckoutQueue());

        MessageConsumerFactory scanConsumer = new MessageConsumerFactory();
        scanPublisher.send("Json scan message here");
        Object scanConsumerResult = scanConsumer.consume(provided.getScanQueue());
        assert scanConsumerResult == "Processed command.";

        MessageConsumerFactory checkoutConsumer = new MessageConsumerFactory();
        checkoutPublisher.send("Json checkout command here");
        Object checkoutConsumerResult = checkoutConsumer.consume(provided.getCheckoutQueue());
        assert checkoutConsumerResult == "Processed command.";
    }
}