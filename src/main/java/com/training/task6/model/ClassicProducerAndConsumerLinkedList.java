package com.training.task6.model;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

@Slf4j
public class ClassicProducerAndConsumerLinkedList extends ProducerAndConsumer {

    private final Queue<Integer> queue = new LinkedList<>();
    private static final int BOUND = 20;

    public ClassicProducerAndConsumerLinkedList(int capacity) {
        super(capacity);
    }

    @Override
    public void produce() throws InterruptedException {
        int count = 0;
        setStartTime(System.nanoTime());
        while (true) {
            synchronized (this) {
                if (queue.size() == getCapacity())
                    this.wait();
                Integer value = new Random().nextInt(BOUND);
                log.info("LinkedList | producer: {}", value);
                queue.add(value);
                count++;
                this.notifyAll();
                if (Thread.currentThread().isInterrupted()) {
                    log.info("LinkedList | producer stopped");
                    break;
                }
            }
        }
        setCount(count);
        setEndTime(System.nanoTime());
    }

    @Override
    public void consume() throws InterruptedException {
        while (true) {
            synchronized (this) {
                if (queue.isEmpty())
                    this.wait();
                Integer value = queue.poll();
                log.info("LinkedList | Consumer: {}", value);
                this.notifyAll();
                if (Thread.currentThread().isInterrupted()) {
                    log.info("LinkedList | consumer stopped");
                    break;
                }
            }
        }
    }
}

