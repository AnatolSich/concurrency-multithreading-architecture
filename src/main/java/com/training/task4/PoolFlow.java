package com.training.task4;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
@SuppressWarnings("BusyWait")
public class PoolFlow {

    public static final int WORK_TIME = 25_000;

    public void go() throws InterruptedException {
        BlockingObjectPool pool = new BlockingObjectPool(2);

        Thread consumer = new Thread(() -> {
            while (true) {
                try {
                //for blocking
                Thread.sleep(getRandomNumber(0, 5000));
            } catch (InterruptedException e) {
                log.error("InterruptedException:{}", e.getMessage());
                Thread.currentThread().interrupt();
            }
                pool.get();
            }
        });

        Thread producer = new Thread(() -> {
            while (true) {
                try {
                    //for blocking
                    Thread.sleep(getRandomNumber(0, 4000));
                } catch (InterruptedException e) {
                    log.error("InterruptedException:{}", e.getMessage());
                    Thread.currentThread().interrupt();
                }
                pool.take(new Random().nextInt(20));
            }
        });

        consumer.start();
        producer.start();

        Thread.sleep(WORK_TIME);
    }

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

}
