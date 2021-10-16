package com.training.task2;

import org.junit.jupiter.api.Test;

public class DeadlockResolveTest {

    private final DeadlockResolve deadlockResolve = new DeadlockResolve();

    @Test
    public void executeTest() {
        deadlockResolve.go();
    }
}
