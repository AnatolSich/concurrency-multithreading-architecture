package com.training.task3;

import org.junit.jupiter.api.Test;

public class MessageFlowTest {

    private final MessageFlow broker = new MessageFlow();

    @Test
    public void executeTest() throws InterruptedException {
        broker.go();
    }
}
