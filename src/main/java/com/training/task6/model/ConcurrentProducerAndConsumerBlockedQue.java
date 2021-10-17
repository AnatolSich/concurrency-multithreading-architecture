package com.training.task6.model;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

@Slf4j
public class ConcurrentProducerAndConsumerBlockedQue extends ProducerAndConsumer {

    private final BlockingQueue<Integer> queue = new PriorityBlockingQueue<>(getCapacity());
    private static final int BOUND = 20;

    public ConcurrentProducerAndConsumerBlockedQue(int capacity) {
        super(capacity);
    }

    @Override
    public void produce() throws InterruptedException {
        int count = 0;
        setStartTime(System.nanoTime());
        while (true) {
            Integer value = new Random().nextInt(BOUND);
            log.info("BlockedQue | producer: {}", value);
            queue.put(value);
            count++;
            if (Thread.currentThread().isInterrupted()) {
                log.info("BlockedQue | producer stopped");
                break;
            }
        }
        setCount(count);
        setEndTime(System.nanoTime());
    }

    @Override
    public void consume() throws InterruptedException {
        while (true) {
            int value = queue.take();
            log.info("BlockedQue | Consumer: {}", value);
            if (Thread.currentThread().isInterrupted()) {
                log.info("BlockedQue | consumer stopped");
                break;
            }
        }
    }
}
