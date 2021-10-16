package com.training.task1;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class SumCalculation {

    private static final int COUNT = 100;
    private Map<Integer, Integer> targetMap;
    private ConcurrentModificationException exception;

    public void goHashMap() {
        go(new HashMap<>());
    }

    public void goConcurrentHashMap() {
        go(new ConcurrentHashMap<>());
    }

    public void goCollectionSynchronizedMap() {
        go(Collections.synchronizedMap(new HashMap<>()));
    }

    public void goThreadSafeMap() {
        go(new ThreadSafeMap<>());
    }

    public void goSynchronizedThreadSafeMap() {
        go(new SynchronizedThreadSafeMap<>());
    }

    private void go(Map<Integer, Integer> targetMap) {
        log.info("Start go");
        this.targetMap = targetMap;
        log.info("TargetMap = {}", targetMap.getClass().getSimpleName());
        Thread producer = new Thread(new PutRunnable());
        Thread sumConsumer = new Thread(new CalculateSumRunnable());

        sumConsumer.start();
        producer.start();

        try {
            producer.join();
            sumConsumer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (Objects.nonNull(exception)) {
            throw exception;
        }

        log.info("Stop go");
    }

    private class PutRunnable implements Runnable {

        @Override
        public void run() {
            log.info("Start 'Put' thread: {}", Thread.currentThread().getName());
            int sum = 0;
            for (int i = 0; i < COUNT; i++) {
                sum = sum + i;
                log.info("Put: {} , {}, sum = {}", i, i, sum);
                targetMap.put(i, i);
            }
            log.info("End 'Put' thread: {}", Thread.currentThread().getName());
        }
    }

    private class CalculateSumRunnable implements Runnable {

        @Override
        public void run() {
            log.info("Start 'CalculateSum' thread: {}", Thread.currentThread().getName());
            while (true) {
                int sum;
                try {
                    sum = targetMap.values().stream().reduce(Integer::sum).orElse(0);
                    log.info("Sum: {}", sum);
                } catch (ConcurrentModificationException e) {
                    exception = e;
                    break;
                }
                if (targetMap.size() == COUNT) {
                    sum = targetMap.values().stream().reduce(Integer::sum).orElse(0);
                    log.info("Final sum: {}", sum);
                    break;
                }
            }
            log.info("End 'SumElements' thread: {}", Thread.currentThread().getName());
        }
    }
}
