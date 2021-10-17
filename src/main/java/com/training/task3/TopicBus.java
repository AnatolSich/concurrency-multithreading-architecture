package com.training.task3;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Queue;
import java.util.Random;

@Slf4j
public class TopicBus {

    private final Queue<Integer> queue;
    private static final int RANDOM_BOUND = 10;
    private static final int BUS_CAPACITY = 5;
    private final String topic;

    public TopicBus(Map<String, Queue<Integer>> queueMap, String topic) {
        this.queue = queueMap.get(topic);
        this.topic = topic;
    }

    public void produce(){
        while (true) {
            synchronized (this) {
                if (queue.size() == BUS_CAPACITY) {
                    try {
                        this.wait();
                    } catch (InterruptedException exception) {
                        Thread.currentThread().interrupt();
                    }
                }
                Integer message = new Random().nextInt(RANDOM_BOUND);
                log.info("{} | producer message: \"{}\"", topic, message);
                queue.add(message);
                this.notifyAll();
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
            }
        }
        log.info("{} | producer stopped", topic);
    }

    public void consume()  {
        while (true) {
            synchronized (this) {
                if (queue.isEmpty()) {
                    try {
                        this.wait();
                    } catch (InterruptedException exception) {
                        Thread.currentThread().interrupt();
                    }
                }
                Integer message = queue.poll();
                log.info("{} | consumer message: \"{}\"", topic, message);
                this.notifyAll();
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
            }
        }
        log.info("{} | consumer stopped", topic);
    }
}

