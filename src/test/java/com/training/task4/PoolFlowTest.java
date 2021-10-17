package com.training.task4;

import org.junit.jupiter.api.Test;

public class PoolFlowTest {

    private final PoolFlow poolFlow = new PoolFlow();

    @Test
    public void executeTest() throws InterruptedException {
        poolFlow.go();
    }
}
