package com.training.task2;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
public class DeadlockResolve {

    public static final Long SLEEP = 10L;
    private static final List<Integer> listNumbers = new ArrayList<>();
    private final Random random = new Random();

    public static void main(String[] args) {
        new DeadlockResolve().go();
    }
    public void go() {
        runJob(new AddJod());
        runJob(new PrintSqrtSum());
        runJob(new PrintSum());
    }

    private void runJob(Runnable runnable) {
        new Thread(runnable).start();
    }


    private void sleepMillis() {
        try {
            long sl = random.nextInt(10) + 10;
            log.info("Sleep = {}", sl);
            Thread.sleep(sl);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class AddJod implements Runnable {

        @Override
        public void run() {
            while (true) {
                synchronized (listNumbers) {
                    int num = random.nextInt(10);
                    listNumbers.add(num);
                    log.info("Add number: {}", num);
                    sleepMillis();
                }
            }
        }
    }

    private class PrintSum implements Runnable {
        @Override
        public void run() {

            while (true) {
                synchronized (listNumbers) {
                    long sum = listNumbers.stream()
                            .mapToLong(n -> n)
                            .sum();
                    log.info("Sum: {}", sum);
                    sleepMillis();
                }
            }
        }
    }

    private class PrintSqrtSum implements Runnable {
        @Override
        public void run() {
            while (true) {
                synchronized (listNumbers) {
                    long sum = listNumbers.stream()
                            .mapToLong(n -> n * n)
                            .sum();
                    log.info("Sqrt from sum: {}", Math.sqrt(sum));
                    sleepMillis();
                }
            }
        }
    }

}
