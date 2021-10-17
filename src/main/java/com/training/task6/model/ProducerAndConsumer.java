package com.training.task6.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class ProducerAndConsumer {

    private final int capacity;
    private int count = 0;
    private long startTime;
    private long endTime = 0L;

    protected ProducerAndConsumer(int capacity) {
        this.capacity = capacity;
    }

    public abstract void produce() throws InterruptedException;
    public abstract void consume() throws InterruptedException;
}
