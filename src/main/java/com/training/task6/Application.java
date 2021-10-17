package com.training.task6;

import com.training.task6.model.ProducerAndConsumer;
import com.training.task6.model.ConcurrentProducerAndConsumerBlockedQue;
import com.training.task6.model.ClassicProducerAndConsumerLinkedList;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Application {

    public static final Integer CAPACITY = 2;
    public static final Integer ESTIMATION_TIME = 1_000;

    public void go() {
        ProducerAndConsumer producerAndConsumer1 = new ClassicProducerAndConsumerLinkedList(CAPACITY);
        ProducerAndConsumer producerAndConsumer2 = new ConcurrentProducerAndConsumerBlockedQue(CAPACITY);

        log.info("LinkedList: ");
        Thread producer = new Thread(() -> {
            try {
                producerAndConsumer1.produce();
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
                exception.printStackTrace();
            }
        });
        Thread consumer = new Thread(() -> {
            try {
                producerAndConsumer1.consume();
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
                exception.printStackTrace();
            }
        });
        producer.start();
        consumer.start();

        try {
            Thread.sleep(ESTIMATION_TIME);
        } catch (InterruptedException e) {
            log.error("InterruptedException:{}", e.getMessage());
            Thread.currentThread().interrupt();
        }
        consumer.interrupt();
        producer.interrupt();

        log.info("BlockedQue: ");
        Thread producer2 = new Thread(() -> {
            try {
                producerAndConsumer2.produce();
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
                exception.printStackTrace();
            }
        });
        Thread consumer2 = new Thread(() -> {
            try {
                producerAndConsumer2.consume();
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
                exception.printStackTrace();
            }
        });
        producer2.start();
        consumer2.start();

        try {
            Thread.sleep(ESTIMATION_TIME);
        } catch (InterruptedException e) {
            log.error("InterruptedException:{}", e.getMessage());
            Thread.currentThread().interrupt();
        }
        consumer2.interrupt();
        producer2.interrupt();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("LinkedList: time report");
            printPerformance(producerAndConsumer1);
            System.out.println("BlockedQue: time report");
            printPerformance(producerAndConsumer2);
        }));
    }

    private void printPerformance(ProducerAndConsumer producerAndConsumer) {
        int count = producerAndConsumer.getCount();
        long timeDiff = (producerAndConsumer.getEndTime() - producerAndConsumer.getStartTime()) / 1_000_000_000;
        long performance = count / timeDiff;
        System.out.println("TimeDiff: " + timeDiff + " s, count: " + count + ", performance: " + performance);
    }
}
