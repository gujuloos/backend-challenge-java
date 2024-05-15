package org.challenge;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Provided {
    private Queue<String> scanQueue;
    private Queue<String> checkoutQueue;

    public Provided() {
        this.scanQueue = new LinkedBlockingQueue<>();
        this.checkoutQueue = new LinkedBlockingQueue<>();
    }

    public Queue<String> getScanQueue() {
        return scanQueue;
    }

    public Queue<String> getCheckoutQueue() {
        return checkoutQueue;
    }


    public void setScanQueue(Queue<String> scanQueue) {
        this.scanQueue = scanQueue;
    }

    public void setCheckoutQueue(Queue<String> checkoutQueue) {
        this.checkoutQueue = checkoutQueue;
    }
}